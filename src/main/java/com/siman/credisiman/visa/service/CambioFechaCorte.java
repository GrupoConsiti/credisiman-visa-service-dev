package com.siman.credisiman.visa.service;


import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.siman.credisiman.visa.utils.Message;
import com.siman.credisiman.visa.utils.Utils;

import javax.xml.namespace.QName;

public class CambioFechaCorte {
    private static Logger log = LoggerFactory.getLogger(CambioFechaCorte.class);

    public static XmlObject obtenerCambioFechaCorte(String pais, String numeroTarjeta, String cuenta, String diaCorte,
                                                     String remoteJndiSunnel, String remoteJndiOrion, String siscardUrl,
                                                    String siscardUser, String binCredisiman, String tipoTarjeta) {
        String namespace = "http://siman.com/CambioFechaCorte";
        String operationResponse = "ObtenerCambioFechaCorteResponse";
        //VALIDAR DATOS

        //validar campos requeridos
        Utils utils = new Utils();
        Message message = new Message();

        if (utils.validateNotNull(pais) || utils.validateNotEmpty(pais)) {
            log.info("pais required");
            return message.genericMessage("ERROR", "025", "El campo pais es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(numeroTarjeta) || utils.validateNotEmpty(numeroTarjeta)) {
            log.info("numero tarjeta required");
            return message.genericMessage("ERROR", "025", "El campo numero tarjeta es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(cuenta) || utils.validateNotEmpty(cuenta)) {
            log.info("cuenta required");
            return message.genericMessage("ERROR", "025", "El campo cuenta es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(diaCorte) || utils.validateNotEmpty(diaCorte)) {
            log.info("dia corte required");
            return message.genericMessage("ERROR", "025", "El campo día corte es obligatorio", namespace, operationResponse);
        }

        //validar longitudes
        if (!utils.validateLongitude(pais,3)) {
            log.info("pais, size overload");
            return message.genericMessage("ERROR", "025", "La longitud del campo pais debe ser menor o igual a 3", namespace, operationResponse);
        }
        if (!utils.validateLongitude(numeroTarjeta  ,16)) {
            log.info("longitud numero de tarjeta");
            return message.genericMessage("ERROR", "774", "La longitud del campo número tarjeta debe ser menor o igual a 16", namespace, operationResponse);
        }
        if (!utils.validateLongitude(cuenta,5)) {
            log.info("cuenta, size overload");
            return message.genericMessage("ERROR", "025", "La longitud del campo cuenta debe ser menor o igual a 5", namespace, operationResponse);
        }
        if (!utils.validateLongitude(diaCorte,2)) {
            log.info("dia corte, size overload");
            return message.genericMessage("ERROR", "025", "La longitud del campo dia corte debe ser menor o igual a 2", namespace, operationResponse);
        }

        //OBTENER DATOS

        XmlObject result = XmlObject.Factory.newInstance();
        XmlCursor cursor = result.newCursor();
        QName responseQName = new QName(namespace, operationResponse);
        cursor.toNextToken();
        cursor.beginElement(responseQName);
        cursor.insertElementWithText(new QName(namespace, "statusCode"), "00");
        cursor.insertElementWithText(new QName(namespace, "status"), "SUCCESS");
        cursor.insertElementWithText(new QName(namespace, "statusMessage"), "Proceso exitoso");
        cursor.toParent();

        log.info("ObtenerCambioFechaCorte response = [" + result.toString() + "]");
        return result;
    }
}
