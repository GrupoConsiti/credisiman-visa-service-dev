package com.siman.credisiman.visa.service;

import com.siman.credisiman.visa.dto.sucursales.Sucursal;
import com.siman.credisiman.visa.utils.ConnectionHandler;
import com.siman.credisiman.visa.utils.Message;
import com.siman.credisiman.visa.utils.Utils;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        List<Sucursal> listadoSucursal = new ArrayList<>();

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

        String query1 = "SELECT BRANCHOFFICEID, DESCRIPTION FROM ORIONREPOSV.T_CBRANCHOFFICES ";

        String query2 = "SELECT BRANCHOFFICEID, DESCRIPTION FROM ORIONREPOGT.T_CBRANCHOFFICES ";

        String query3 = "SELECT BRANCHOFFICEID, DESCRIPTION FROM ORIONREPONI.T_CBRANCHOFFICES ";

        String query4 = "SELECT BRANCHOFFICEID, DESCRIPTION FROM ORIONREPOCR.T_CBRANCHOFFICES ";

        ConnectionHandler connectionHandler = new ConnectionHandler();
        Connection conexion = connectionHandler.getConnection(remoteJndiOrion);
        PreparedStatement sentencia = null;

        try {
            switch (pais) {
                case "SV":
                    sentencia = conexion.prepareStatement(query1);
                    break;
                case "GT":
                    sentencia = conexion.prepareStatement(query2);
                    break;
                case "NI":
                    sentencia = conexion.prepareStatement(query3);
                    break;
                case "CR":
                    sentencia = conexion.prepareStatement(query4);
                    break;
            }
            ResultSet rs = sentencia.executeQuery();

            while (rs.next()) {
                Sucursal sucursal = new Sucursal();
                sucursal.setCodigoSucursal(rs.getString("BRANCHOFFICEID"));
                sucursal.setNombre(rs.getString("DESCRIPTION"));
                listadoSucursal.add(sucursal);
            }

            conexion.close();
        } catch (Exception e) {
            e.printStackTrace();
            return message.genericMessage("ERROR", "600",
                    "Error general contacte al administrador del sistema...", namespace, operationResponse);
        }
        if (listadoSucursal.size() > 0) {
            for (int i = 0; i < listadoSucursal.size(); i++) {
                cursor.beginElement(new QName(namespace, "sucursales"));
                cursor.insertElementWithText(new QName(namespace, "codigoSucursal"), listadoSucursal.get(i).getCodigoSucursal());
                cursor.insertElementWithText(new QName(namespace, "nombre"), listadoSucursal.get(i).getNombre());
                cursor.toParent();
            }

            cursor.toParent();
            log.info("response = [" + result + "]");
            return result;
        } else {
            return message.genericMessage("ERROR", "400",
                    "No se encontraron sucursales disponibles", namespace, operationResponse);

        }

    }
}
