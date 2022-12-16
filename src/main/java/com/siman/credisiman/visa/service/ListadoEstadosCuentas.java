package com.siman.credisiman.visa.service;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;

public class ListadoEstadosCuentas {
    private static Logger log = LoggerFactory.getLogger(ListadoEstadosCuentas.class);

	public static XmlObject obtenerListadoEstadosCuenta(String pais, String numeroTarjeta, String remoteJndiSunnel,
			String remoteJndiOrion, String siscardUrl, String siscardUser, String binCredisiman, String tipoTarjeta, String urlCrm) {
        String namespace = "http://siman.com/ListadoEstadosCuenta";
        String operationResponse = "ObtenerListadoEstadosCuentaResponse";
        String[] listadoFechaCorte = {"20220711", "20220712", "20220713"};
        //OBTENER DATOS

        XmlObject result = XmlObject.Factory.newInstance();
        XmlCursor cursor = result.newCursor();
        QName responseQName = new QName(namespace, operationResponse);
        cursor.toNextToken();
        cursor.beginElement(responseQName);
        cursor.insertElementWithText(new QName(namespace, "statusCode"), "00");
        cursor.insertElementWithText(new QName(namespace, "status"), "SUCCESS");
        cursor.insertElementWithText(new QName(namespace, "statusMessage"), "Proceso exitoso");
        for (String fechaCorte : listadoFechaCorte) {
            cursor.beginElement(new QName(namespace, "fechasCorte"));
            cursor.insertElementWithText(new QName(namespace, "fechaCorte"), fechaCorte);
            cursor.toParent();
        }
        cursor.toParent();

        log.info("ObtenerListadoEstadosCuenta response = [" + result.toString() + "]");
        return result;
    }
}