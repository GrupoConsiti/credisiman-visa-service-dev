package com.siman.credisiman.visa.service;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;

public class ConsultaEstadoCuenta {
    private static final Logger log = LoggerFactory.getLogger(ConsultaEstadoCuenta.class);

    public static XmlObject obtenerConsultaEstadoCuenta(String pais, String numeroTarjeta, String cuenta,
                                                        String fechaCorte, String remoteJndiSunnel, String remoteJndiOrion, String siscardUrl, String siscardUser,
                                                        String binCredisiman, String tipoTarjeta) {
        String namespace = "http://siman.com/ConsultaEstadoCuenta";
        String operationResponse = "ObtenerConsultaEstadoCuentaResponse";
        //OBTENER DATOS
        XmlObject result = XmlObject.Factory.newInstance();
        XmlCursor cursor = result.newCursor();
        QName responseQName = new QName(namespace, operationResponse);
        cursor.toNextToken();
        cursor.beginElement(responseQName);
        cursor.insertElementWithText(new QName(namespace, "statusCode"), "00");
        cursor.insertElementWithText(new QName(namespace, "status"), "SUCCESS");
        cursor.insertElementWithText(new QName(namespace, "statusMessage"), "Proceso exitoso");
        cursor.insertElementWithText(new QName(namespace, "correo"), "juan.perez@correo.com");
        cursor.insertElementWithText(new QName(namespace, "archivo"), "StringBase24");
        cursor.toParent();

        log.info("obtenerConsultaEstadoCuenta response = [" + result.toString() + "]");
        return result;
    }

}
