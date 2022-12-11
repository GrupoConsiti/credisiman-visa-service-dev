package com.siman.credisiman.visa.service;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;

public class ConsultaSaldoMonedero {
    private static final Logger log = LoggerFactory.getLogger(ConsultaSaldoMonedero.class);

	public static XmlObject obtenerConsultaSaldoMonedero(String pais, String numeroTarjeta, String cuenta,
			String fechaCorte, String remoteJndiSunnel, String remoteJndiOrion, String siscardUrl, String siscardUser,
			String binCredisiman, String codigoEmisor, String tipoTarjeta) {
        String namespace = "http://siman.com/ConsultaSaldoMonedero";
        String operationResponse = "ObtenerConsultaSaldoMonederoResponse";
        //OBTENER DATOS
        XmlObject result = XmlObject.Factory.newInstance();
        XmlCursor cursor = result.newCursor();
        QName responseQName = new QName(namespace, operationResponse);
        cursor.toNextToken();
        cursor.beginElement(responseQName);
        cursor.insertElementWithText(new QName(namespace, "statusCode"), "00");
        cursor.insertElementWithText(new QName(namespace, "status"), "SUCCESS");
        cursor.insertElementWithText(new QName(namespace, "statusMessage"), "Proceso exitoso");
        cursor.insertElementWithText(new QName(namespace, "saldoInicial"),"000000050000");
        cursor.insertElementWithText(new QName(namespace, "puntosGanados"),"000000002000");
        cursor.insertElementWithText(new QName(namespace, "puntosCanjeados"),"000000010000");
        cursor.insertElementWithText(new QName(namespace, "saldoFinal"),"000000051000");
        cursor.toParent();

        log.info("obtenerConsultaSaldoMonedero response = [" + result.toString() + "]");
        return result;
    }
}