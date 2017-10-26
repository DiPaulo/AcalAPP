package br.com.acalfortaleza.acalapp;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;


public class ConsultaEntregaActivity extends AppCompatActivity implements Runnable {

    private EditText edtPedido;
    private EditText edtCnpj;
    private Button btnConsultar;
    private Button btnCnpj;
    private Handler handler = new Handler();
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrega);

        edtPedido = (EditText) findViewById(R.id.edtPedido);
        btnConsultar = (Button) findViewById(R.id.btnConsultar);
        edtCnpj = (EditText) findViewById(R.id.edtCnpj);
        btnCnpj = (Button) findViewById(R.id.btnCnpj);
    }


    public void cliqueConsultar(View view) {


        if (edtPedido.getText().toString().trim().equals("")) {

            new AlertDialog.Builder(this).setTitle("Informação").setMessage("Informe o número do pedido!").setPositiveButton("OK", null).show();

        } else {

            dialog = new ProgressDialog(this);
            dialog.setMessage("Consultando...");
            dialog.setTitle("Por favor aguarde");
            dialog.show();
            Thread t = new Thread(this);
            t.start();
        }


    }


    @Override
    public void run() {


        int pedido = Integer.parseInt(edtPedido.getText().toString());

        try {

            ClienteWS ws = new ClienteWS();

            final String resultado = ws.ConsultarPedido(pedido);

            handler.post(new Runnable() {
                @Override
                public void run() {

                    new AlertDialog.Builder(ConsultaEntregaActivity.this).setTitle("Status do Pedido").setMessage(resultado).setPositiveButton("OK", null).show();


                }
            });


        } catch (Exception ex) {

            Log.e("ConsultaEntregaActivity", "Erro", ex);
        } finally {

            dialog.dismiss();
        }

    }


    public void ConsultarCgccpf(View view) {


        if (edtCnpj.getText().toString().trim().equals("")) {

            new AlertDialog.Builder(this).setTitle("Informação").setMessage("Informe o número do Cpf/Cnpj!").setPositiveButton("OK", null).show();

        } else {


            dialog = new ProgressDialog(this);
            dialog.setMessage("Consultando...");
            dialog.setTitle("Por favor aguarde");
            dialog.show();
            new Thread() {


                public void run() {


                    final String cgccpf = (edtCnpj.getText().toString());

                    try {

                        final ClienteWS ws = new ClienteWS();
                        final ArrayList<String> resultadoPedido = ws.ConsultaPorCgccpfPedido(cgccpf);
                        final ArrayList<String> resultadoStatus = ws.ConsultaPorCgccpfStatus(cgccpf);
                        if (ws.ValidarCgccpf(cgccpf).trim().equals("0")){
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    new AlertDialog.Builder(ConsultaEntregaActivity.this).setTitle("Informação").setMessage("Cpf/Cnpj Inválido!").setPositiveButton("OK", null).show();
                                }
                            });




                        } else {


                            if (ws.ValidarCgccpf(cgccpf).trim().equals("10")) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        new AlertDialog.Builder(ConsultaEntregaActivity.this).setTitle("Informação").setMessage("Cliente não possui pedidos de entrega!").setPositiveButton("OK", null).show();
                                    }
                                });


                            } else {


                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {


                                        Intent it = new Intent(ConsultaEntregaActivity.this,PedidosActivity.class);
                                        it.putStringArrayListExtra("CONSULTA",resultadoPedido);
                                        it.putStringArrayListExtra("CONSULTASTATUS",resultadoStatus);
                                        startActivity(it);


                                    }
                                });
                            }


                        }


                    } catch (Exception ex) {

                        Log.e("ConsultaEntregaActivity", "Erro", ex);
                    } finally {

                        dialog.dismiss();
                    }

                }


            }.start();
        }


    }




}
