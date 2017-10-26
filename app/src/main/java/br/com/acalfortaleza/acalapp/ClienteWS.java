package br.com.acalfortaleza.acalapp;

import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

/**
 * Created by Administrador on 22/03/2017.
 */

public class ClienteWS {


    private String NAME_SPACE = "http://tempuri.org/";
    private String URL = "http://cloud.acalfortaleza.com.br:81/ServiceClientesAcal.asmx";
    private String METHOD_NAME = "ConsultarPedido";
    private String SOAP_ACTION = "http://tempuri.org/ConsultarPedido";




    public String ConsultarPedido(int pedido)  throws IOException,XmlPullParserException {

        SoapObject soap = new SoapObject(NAME_SPACE,METHOD_NAME);
        soap.addProperty("pedido",pedido);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(soap);

        HttpTransportSE httpTrans = new HttpTransportSE(URL);

        httpTrans.call(SOAP_ACTION,envelope);

        SoapPrimitive resultado = (SoapPrimitive)envelope.getResponse();

        return  resultado.toString();

    }


    public ArrayList<String>  ConsultaPorCgccpfPedido(String cgccpf) throws IOException,XmlPullParserException {

        ArrayList<String> listaPedidos = new ArrayList<String>();
        Pedidos[] retorno;
        SoapObject soap = new SoapObject(NAME_SPACE,"ConsultaPorCgccpf");
        soap.addProperty("cgccpf",cgccpf);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(soap);

        HttpTransportSE httpTrans = new HttpTransportSE(URL);

        httpTrans.call(NAME_SPACE+"ConsultaPorCgccpf",envelope);

        SoapObject resultado = (SoapObject)envelope.getResponse();


         retorno = new Pedidos[(resultado.getPropertyCount())];



        for (int i = 0; i <retorno.length; i++)
        {

            SoapObject ic = (SoapObject)resultado.getProperty(i);


            listaPedidos.add(String.valueOf(Integer.parseInt(ic.getProperty(0).toString())));

        }

        return  listaPedidos;

    }

    public ArrayList<String>  ConsultaPorCgccpfStatus(String cgccpf) throws IOException,XmlPullParserException {

        ArrayList<String> listaStatus = new ArrayList<String>();
        Pedidos[] retorno;
        SoapObject soap = new SoapObject(NAME_SPACE,"ConsultaPorCgccpf");
        soap.addProperty("cgccpf",cgccpf);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(soap);

        HttpTransportSE httpTrans = new HttpTransportSE(URL);

        httpTrans.call(NAME_SPACE+"ConsultaPorCgccpf",envelope);

        SoapObject resultado = (SoapObject) envelope.getResponse();


        retorno = new Pedidos[(resultado.getPropertyCount())];



        for (int i = 0; i <retorno.length; i++)
        {

            SoapObject ic = (SoapObject)resultado.getProperty(i);


            listaStatus.add((ic.getProperty(1).toString()));

        }

        return  listaStatus;

    }

    public String ValidarCgccpf(String cgccpf) throws IOException,XmlPullParserException {

    SoapObject soap = new SoapObject(NAME_SPACE,"ValidarCgccpf");
        soap.addProperty("cgccpf",cgccpf);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(soap);

        HttpTransportSE httpTrans = new HttpTransportSE(URL);

        httpTrans.call(NAME_SPACE+"ValidarCgccpf",envelope);

        SoapPrimitive resultado = (SoapPrimitive)envelope.getResponse();

        return  resultado.toString();

    }


}
