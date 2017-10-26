package br.com.acalfortaleza.acalapp;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.*;


import java.util.ArrayList;

public class PedidosActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    private ListView listaPedido;
    private ListView listaStatus;
    private TextView tv;
    private Handler handler = new Handler();
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        listaPedido = (ListView) findViewById(R.id.listaPedidos);
        listaStatus = (ListView) findViewById(R.id.listaStatus);


        Bundle bundle = getIntent().getExtras();


        if (bundle.containsKey("CONSULTA")) {


            ArrayList<String> resultado = (ArrayList<String>) bundle.getStringArrayList("CONSULTA");
            ArrayAdapter<String> adpter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, resultado);

            listaPedido.setAdapter(adpter);

        }
        if (bundle.containsKey("CONSULTASTATUS")) {
            ArrayList<String> resultadoStatus = (ArrayList<String>) bundle.getStringArrayList("CONSULTASTATUS");

            ArrayAdapter<String> adpterStatus = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, resultadoStatus);

            listaStatus.setAdapter(adpterStatus);


        }

        listaPedido.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        final String value = (String)parent.getItemAtPosition(position);

        if (value.toString().trim().equals("")) {

            new AlertDialog.Builder(PedidosActivity.this).setTitle("Informação").setMessage("Informe o número do pedido!").setPositiveButton("OK", null).show();

        } else {

            dialog = new ProgressDialog(PedidosActivity.this);
            dialog.setMessage("Consultando...");
            dialog.setTitle("Por favor aguarde");
            dialog.show();
            new Thread() {


                public void run() {


                    int pedido = Integer.parseInt(value.toString());

                    try {

                        ClienteWS ws = new ClienteWS();

                        final String resultado = ws.ConsultarPedido(pedido);

                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                new AlertDialog.Builder(PedidosActivity.this).setTitle("Status do Pedido").setMessage(resultado).setPositiveButton("OK", null).show();


                            }
                        });


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






