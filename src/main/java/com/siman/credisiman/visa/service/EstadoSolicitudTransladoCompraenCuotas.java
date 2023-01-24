package com.siman.credisiman.visa.service;

import com.siman.credisiman.visa.dto.planes.Sucursal;
import com.siman.credisiman.visa.utils.Message;
import com.siman.credisiman.visa.utils.Utils;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;

public class EstadoSolicitudTransladoCompraenCuotas {
    private static final Logger log = LoggerFactory.getLogger(EstadoSolicitudTransladoCompraenCuotas.class);
    private static final String namespace = "http://siman.com/EstadoSolicitudTransladoCompraenCuotas";
    private static final String operationResponse = "ObtenerEstadoSolicitudTransladoCompraenCuotasResponse";

    public static XmlObject obtenerEstadoSolicitudTransladoCompraenCuotas(String pais, String numeroSolicitud, String remoteJndiSunnel, String remoteJndiOrion,
                                                     String siscardUrl, String siscardUser, String binCredisiman) {
        //validar campos requeridos
        Utils utils = new Utils();
        Message message = new Message();

        if (utils.validateNotNull(pais) || utils.validateNotEmpty(pais)) {
            log.info("pais required");
            return message.genericMessage("ERROR", "400", "El campo pais es obligatorio", namespace, operationResponse);
        }

        if (utils.validateNotNull(numeroSolicitud) || utils.validateNotEmpty(numeroSolicitud)) {
            log.info("numero de solicitud required");
            return message.genericMessage("ERROR", "400", "El campo numero de solicitud es obligatorio", namespace, operationResponse);
        }

        XmlObject result = XmlObject.Factory.newInstance();
        XmlCursor cursor = result.newCursor();
        QName responseQName = new QName(namespace, operationResponse);
        cursor.toNextToken();
        cursor.beginElement(responseQName);
        cursor.insertElementWithText(new QName(namespace, "statusCode"), "00");
        cursor.insertElementWithText(new QName(namespace, "status"), "SUCCESS");
        cursor.insertElementWithText(new QName(namespace, "statusMessage"), "Proceso exitoso");
        cursor.insertElementWithText(new QName(namespace, "estado"), "Pendiente");

        cursor.toParent();
        log.info("response = [" + result + "]");
        return result;
    }
}
