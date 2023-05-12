package com.siman.credisiman.visa.service;

import com.siman.credisiman.visa.utils.Message;
import com.siman.credisiman.visa.utils.Utils;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;

public class ModificarLimiteTarjeta {
    //private static Logger log = LoggerFactory.getLogger(ListadoTarjetas.class);

    public static XmlObject modificarLimiteTarjeta(String pais, String numeroTarjeta, String monto,
                                                   String remoteJndiSunnel, String remoteJndiOrion, String siscardUrl, String siscardUser,
                                                   String binCredisiman, String tipoTarjeta) {

        String namespace = "http://siman.com/ModificarLimiteTarjeta";
        String operationResponse = "ModificarLimiteTarjetaResponse";
        //OBTENER DATOS

        Utils utils = new Utils();
        Message message = new Message();

        XmlObject result = XmlObject.Factory.newInstance();
        XmlCursor cursor = result.newCursor();
        QName responseQName = new QName(namespace, operationResponse);
        cursor.toNextToken();
        cursor.beginElement(responseQName);
        cursor.insertElementWithText(new QName(namespace, "statusCode"), "00");
        cursor.insertElementWithText(new QName(namespace, "status"), "SUCCESS");
        cursor.insertElementWithText(new QName(namespace, "statusMessage"), "Proceso exitoso");
        cursor.toParent();

        //log.info("modificarLimiteTarjeta response = [" + result + "]");
        return result;
    }
}
