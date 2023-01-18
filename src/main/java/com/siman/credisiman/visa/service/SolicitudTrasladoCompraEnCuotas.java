package com.siman.credisiman.visa.service;

import com.siman.credisiman.visa.utils.Message;
import com.siman.credisiman.visa.utils.Utils;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;

public class SolicitudTrasladoCompraEnCuotas {
    private static final Logger log = LoggerFactory.getLogger(SolicitudTrasladoCompraEnCuotas.class);
    private static final String namespace = "http://siman.com/SolicitudTrasladoCompraEnCuotas";
    private static final String operationResponse = "ObtenerSolicitudTrasladoCompraEnCuotasResponse";

    public static XmlObject obtenerSolicitudTrasladoCompraEnCuotas(String pais, String identificacion, String numeroTarjeta,
                                                                   String correo, String telefono, String codigoProducto, String codigoPlan,
                                                                   String montoTransaccion, String fechaCompra, String codigoSucursal,
                                                                   String remoteJndiSunnel, String remoteJndiOrion,
                                                                   String siscardUrl, String siscardUser, String binCredisiman) {
        //validar campos requeridos
        Utils utils = new Utils();
        Message message = new Message();

        if (utils.validateNotNull(pais) || utils.validateNotEmpty(pais)) {
            log.info("pais required");
            return message.genericMessage("ERROR", "400", "El campo pais es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(numeroTarjeta) || utils.validateNotEmpty(numeroTarjeta)) {
            log.info("numero tarjeta required");
            return message.genericMessage("ERROR", "400", "El campo número tarjeta es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(identificacion) || utils.validateNotEmpty(identificacion)) {
            log.info("identificacion required");
            return message.genericMessage("ERROR", "400", "El campo identificacion es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(codigoProducto) || utils.validateNotEmpty(codigoProducto)) {
            log.info("codigoProducto required");
            return message.genericMessage("ERROR", "400", "El campo codigo producto es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(codigoPlan) || utils.validateNotEmpty(codigoPlan)) {
            log.info("codigoPlan required");
            return message.genericMessage("ERROR", "400", "El campo codigo plan es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(montoTransaccion) || utils.validateNotEmpty(montoTransaccion)) {
            log.info("montoTransaccion required");
            return message.genericMessage("ERROR", "400", "El campo monto transaccion es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(codigoSucursal) || utils.validateNotEmpty(codigoSucursal)) {
            log.info("codigoSucursal required");
            return message.genericMessage("ERROR", "400", "El campo codigo sucursal es obligatorio", namespace, operationResponse);
        }


        XmlObject result = XmlObject.Factory.newInstance();
        XmlCursor cursor = result.newCursor();
        QName responseQName = new QName(namespace, operationResponse);
        cursor.toNextToken();
        cursor.beginElement(responseQName);
        cursor.insertElementWithText(new QName(namespace, "statusCode"), "00");
        cursor.insertElementWithText(new QName(namespace, "status"), "SUCCESS");
        cursor.insertElementWithText(new QName(namespace, "statusMessage"), "Proceso exitoso");
        cursor.insertElementWithText(new QName(namespace, "numeroSolicitud"), "1234567");
        cursor.toParent();
        log.info("response = [" + result + "]");
        return result;
    }
}

