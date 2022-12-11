package com.siman.credisiman.visa.service;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.siman.credisiman.visa.utils.Message;
import com.siman.credisiman.visa.utils.Utils;

import javax.xml.namespace.QName;

public class ConsultaSubproductos {
    private static Logger log = LoggerFactory.getLogger(ConsultaSubproductos.class);

	public static XmlObject obtenerConsultaSubproductos(String pais, String numeroTarjeta, String remoteJndiSunnel,
			String remoteJndiOrion, String siscardUrl, String siscardUser, String binCredisiman, String tipoTarjeta) {
        String namespace = "http://siman.com/ConsultaSubproductos";
        String operationResponse = "ObtenerConsultaSubproductosResponse";
        String[] fechasCompra = {"20220716", "20220717", "20220718"};
        //OBTENER DATOS

        Utils utils = new Utils();
        Message message = new Message();

        //validar campos requeridos
        if (utils.validateNotNull(pais)|| utils.validateNotEmpty(pais)) {
            return message.genericMessage("ERROR", "025", "El campo pais es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(numeroTarjeta)|| utils.validateNotEmpty(numeroTarjeta)) {
            return message.genericMessage("ERROR", "025", "El campo número tarjeta es obligatorio", namespace, operationResponse);
        }

        //validar longitudes
        if (!utils.validateLongitude(pais,3)) {
            return message.genericMessage("ERROR", "025", "La longitud del campo pais debe ser menor o igual a 3", namespace, operationResponse);
        }
        if (!utils.validateLongitude(numeroTarjeta  ,16)) {
            return message.genericMessage("ERROR", "025", "La longitud del campo número tarjeta debe ser menor o igual a 16", namespace, operationResponse);
        }

        XmlObject result = XmlObject.Factory.newInstance();
        XmlCursor cursor = result.newCursor();
        QName responseQName = new QName(namespace, operationResponse);
        cursor.toNextToken();
        cursor.beginElement(responseQName);
        cursor.insertElementWithText(new QName(namespace, "statusCode"), "00");
        cursor.insertElementWithText(new QName(namespace, "status"), "SUCCESS");
        cursor.insertElementWithText(new QName(namespace, "statusMessage"), "Proceso exitoso");
        for (String fechaCompra : fechasCompra) {
            cursor.beginElement(new QName(namespace, "subproductos"));
            cursor.insertElementWithText(new QName(namespace, "tipoSubproducto"), "I");
            cursor.insertElementWithText(new QName(namespace, "fechaCompra"), fechaCompra);
            cursor.insertElementWithText(new QName(namespace, "moneda"), "DO");
            cursor.insertElementWithText(new QName(namespace, "montoCompra"), "000000000150");
            cursor.insertElementWithText(new QName(namespace, "montoCuota"), "000000000076");
            cursor.insertElementWithText(new QName(namespace, "fechaPago"), "20220816");
            cursor.insertElementWithText(new QName(namespace, "saldoActual"), "000000000150");
            cursor.toParent();
        }

    	cursor.toParent();

		log.info("obtenerConsultaSubproductos response = [" + result.toString() + "]");
		return result;
    }
}
