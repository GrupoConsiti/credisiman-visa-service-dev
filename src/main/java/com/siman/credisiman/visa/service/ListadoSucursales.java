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

public class ListadoSucursales {
    private static final Logger log = LoggerFactory.getLogger(ListadoSucursales.class);
    private static final String namespace = "http://siman.com/ListadoSucursales";
    private static final String operationResponse = "ObtenerListadoSucursalesResponse";

    public static XmlObject obtenerListadoSucursales(String pais, String remoteJndiSunnel, String remoteJndiOrion,
                                                           String siscardUrl, String siscardUser, String binCredisiman) {
        //validar campos requeridos
        Utils utils = new Utils();
        Message message = new Message();
        List<Sucursal> listadoPlanes = new ArrayList<>();

        if (utils.validateNotNull(pais) || utils.validateNotEmpty(pais)) {
            log.info("pais required");
            return message.genericMessage("ERROR", "400", "El campo pais es obligatorio", namespace, operationResponse);
        }

        XmlObject result = XmlObject.Factory.newInstance();
        XmlCursor cursor = result.newCursor();
        QName responseQName = new QName(namespace, operationResponse);
        cursor.toNextToken();
        cursor.beginElement(responseQName);
        cursor.insertElementWithText(new QName(namespace, "statusCode"), "00");
        cursor.insertElementWithText(new QName(namespace, "status"), "SUCCESS");
        cursor.insertElementWithText(new QName(namespace, "statusMessage"), "Proceso exitoso");

        Sucursal plan1 = new Sucursal("01", "Sucursal 1");
        Sucursal plan2 = new Sucursal("02", "Sucursal 2");
        Sucursal plan3 = new Sucursal("03", "Sucursal 3");

        listadoPlanes.add(plan1);
        listadoPlanes.add(plan2);
        listadoPlanes.add(plan3);

        for (int i = 0; i < listadoPlanes.size(); i++) {
            cursor.beginElement(new QName(namespace, "sucursales"));
            cursor.insertElementWithText(new QName(namespace, "codigoSucursal"), listadoPlanes.get(i).getCodigoSucursal());
            cursor.insertElementWithText(new QName(namespace, "nombre"), listadoPlanes.get(i).getNombre());
            cursor.toParent();
        }

        cursor.toParent();
        log.info("response = [" + result + "]");
        return result;
    }
}
