package com.siman.credisiman.visa.service;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.siman.credisiman.visa.utils.Message;
import com.siman.credisiman.visa.utils.Utils;

import javax.xml.namespace.QName;

public class SolicitudReposicionTarjeta {
    private static Logger log = LoggerFactory.getLogger(SolicitudReposicionTarjeta.class);

	public static XmlObject crearSolicitudReposicionTarjeta(String pais, String numeroTarjeta, String nombreEmbozar,
			String razonReposicion, String remoteJndiSunnel, String remoteJndiOrion, String siscardUrl,
			String siscardUser, String binCredisiman, String tipoTarjeta) {
        String namespace = "http://siman.com/SolicitudReposicionTarjeta";
        String operationResponse = "ObtenerSolicitudReposicionTarjetaResponse";
        //OBTENER DATOS

        //validar campos requeridos
        Utils utils = new Utils();
        Message message = new Message();

        if (utils.validateNotNull(pais)) {
            log.info("pais required");
            return message.genericMessage("ERROR", "025", "El campo pais es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(numeroTarjeta)) {
            log.info("numero tarjeta required");
            return message.genericMessage("ERROR", "025", "El campo número tarjeta es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(nombreEmbozar)) {
            log.info("nombre embozar required");
            return message.genericMessage("ERROR", "025", "El campo nombre embozar es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(razonReposicion)) {
            log.info("razon reposicion required");
            return message.genericMessage("ERROR", "025", "El campo razón reposición es obligatorio", namespace, operationResponse);
        }

        //validar longitudes
        if (!utils.validateLongitude(pais,3)) {
            log.info("longitud pais");
            return message.genericMessage("ERROR", "025", "La longitud del campo pais debe ser menor o igual a 3", namespace, operationResponse);
        }
        if (!utils.validateLongitude(numeroTarjeta  ,16)) {
            log.info("longitud numero de tarjeta");
            return message.genericMessage("ERROR", "025", "La longitud del campo número tarjeta debe ser menor o igual a 16", namespace, operationResponse);
        }
        if (!utils.validateLongitude(nombreEmbozar  ,29)) {
            log.info("longitud nombre embozar");
            return message.genericMessage("ERROR", "025", "La longitud del campo nombre embozar debe ser menor o igual a 29", namespace, operationResponse);
        }
        if (!utils.validateLongitude(razonReposicion  ,1)) {
            log.info("longitud razon reposicion");
            return message.genericMessage("ERROR", "025", "La longitud del campo razón reposición debe ser  1", namespace, operationResponse);
        }

        XmlObject result = XmlObject.Factory.newInstance();
        XmlCursor cursor = result.newCursor();
        QName responseQName = new QName(namespace, operationResponse);
        cursor.toNextToken();
        cursor.beginElement(responseQName);
        cursor.insertElementWithText(new QName(namespace, "statusCode"), "00");
        cursor.insertElementWithText(new QName(namespace, "status"), "SUCCESS");
        cursor.insertElementWithText(new QName(namespace, "statusMessage"), "Proceso exitoso");
        cursor.insertElementWithText(new QName(namespace, "nuevoNumeroTarjeta"), "4000123456780001");
        cursor.toParent();

        log.info("obtenerSolicitudReposicionTarjeta response = [" + result.toString() + "]");
        return result;
    }
}