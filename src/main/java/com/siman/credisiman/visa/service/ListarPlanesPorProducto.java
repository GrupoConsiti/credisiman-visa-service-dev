package com.siman.credisiman.visa.service;

import com.siman.credisiman.visa.dto.planes.Plan;
import com.siman.credisiman.visa.utils.Message;
import com.siman.credisiman.visa.utils.Utils;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;

public class ListarPlanesPorProducto {
    private static final Logger log = LoggerFactory.getLogger(ListarPlanesPorProducto.class);
    private static final String namespace = "http://siman.com/ListarPlanesPorProducto";
    private static final String operationResponse = "ObtenerListarPlanesPorProductoResponse";

    public static XmlObject obtenerListarPlanesPorProducto(String pais, String codigoProducto,
                                                                   String remoteJndiSunnel, String remoteJndiOrion,
                                                                   String siscardUrl, String siscardUser, String binCredisiman) {
        //validar campos requeridos
        Utils utils = new Utils();
        Message message = new Message();
        List<Plan> listadoPlanes = new ArrayList<>();

        if (utils.validateNotNull(pais) || utils.validateNotEmpty(pais)) {
            log.info("pais required");
            return message.genericMessage("ERROR", "400", "El campo pais es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(codigoProducto) || utils.validateNotEmpty(codigoProducto)) {
            log.info("codigoProducto required");
            return message.genericMessage("ERROR", "400", "El campo codigo producto es obligatorio", namespace, operationResponse);
        }

        XmlObject result = XmlObject.Factory.newInstance();
        XmlCursor cursor = result.newCursor();
        QName responseQName = new QName(namespace, operationResponse);
        cursor.toNextToken();
        cursor.beginElement(responseQName);
        cursor.insertElementWithText(new QName(namespace, "statusCode"), "00");
        cursor.insertElementWithText(new QName(namespace, "status"), "SUCCESS");
        cursor.insertElementWithText(new QName(namespace, "statusMessage"), "Proceso exitoso");

        Plan plan1 = new Plan("01", "Plan 1");
        Plan plan2 = new Plan("02", "Plan 2");
        Plan plan3 = new Plan("03", "Plan 3");

        listadoPlanes.add(plan1);
        listadoPlanes.add(plan2);
        listadoPlanes.add(plan3);

        for (int i = 0; i < listadoPlanes.size(); i++) {
            cursor.beginElement(new QName(namespace, "planes"));
            cursor.insertElementWithText(new QName(namespace, "codigoPlan"), listadoPlanes.get(i).getCodigoPlan());
            cursor.insertElementWithText(new QName(namespace, "nombre"), listadoPlanes.get(i).getNombre());
            cursor.toParent();
        }

        cursor.toParent();
        log.info("response = [" + result + "]");
        return result;
    }
}
