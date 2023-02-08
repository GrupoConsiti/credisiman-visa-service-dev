package com.siman.credisiman.visa.service;

import com.siman.credisiman.visa.dto.orquestadorsiman.Orquestador;
import com.siman.credisiman.visa.utils.ConnectionHandler;
import com.siman.credisiman.visa.utils.Message;
import com.siman.credisiman.visa.utils.Utils;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class OrquestadorSiman {

    private static final Logger log = LoggerFactory.getLogger(OrquestadorSiman.class);
    private static final String namespace = "http://siman.com/OrquestadorSiman";
    private static final String operationResponse = "ObtenerOrquestadorSimanResponse";

    public static XmlObject obtenerOrquestadorSiman(String servicio, String param1, String param2, String param3, String param4, String param5, String param6,
                                                    String remoteJndiSunnel, String remoteJndiOrion,
                                                    String siscardUrl, String siscardUser, String binCredisiman) {
        //validar campos requeridos
        Utils utils = new Utils();
        Message message = new Message();
        List<Orquestador> orquestadorSiman = new ArrayList<>();

        if (utils.validateNotNull(servicio) || utils.validateNotEmpty(servicio)) {
            return message.genericMessage("ERROR", "400", "El campo del servicio a llamar es obligatorio.", namespace, operationResponse);
        }

        if (utils.validateNotNull(param1) || utils.validateNotEmpty(param1)) {
            return message.genericMessage("ERROR", "400", "Los datos a enviar son obligatorios.", namespace, operationResponse);
        }

        XmlObject result = XmlObject.Factory.newInstance();
        XmlCursor cursor = result.newCursor();
        QName responseQName = new QName(namespace, operationResponse);
        cursor.toNextToken();
        cursor.beginElement(responseQName);
        cursor.insertElementWithText(new QName(namespace, "statusCode"), "00");
        cursor.insertElementWithText(new QName(namespace, "status"), "SUCCESS");
        cursor.insertElementWithText(new QName(namespace, "statusMessage"), "Proceso exitoso");

        ConnectionHandler connectionHandler = new ConnectionHandler();
        Connection conexion = connectionHandler.getConnection(remoteJndiOrion);
        PreparedStatement sentencia = null;
        try {
            switch (servicio) {
                case "ListadoSucursales":
                    ListadoSucursales.obtenerListadoSucursales(param1, remoteJndiSunnel, remoteJndiOrion, siscardUrl, siscardUser, binCredisiman);
                    break;
                case "ConsultaDatosCliente":
                    ConsultaDatosCliente.obtenerDatosCliente(param1, param2, remoteJndiSunnel, remoteJndiOrion, siscardUrl, siscardUser, binCredisiman);
                    break;
                case "ConsultaMovimientos":
                    ConsultaMovimientos.obtenerConsultaMovimientos(param1, param2, param3, param4, param5, remoteJndiSunnel, remoteJndiOrion, siscardUrl, siscardUser, binCredisiman);
                    break;
                case "ConsultaSaldoMonedero":
                    ConsultaSaldoMonedero.obtenerConsultaSaldoMonedero(param1, param2, param3, param4, param5, param6, remoteJndiSunnel, remoteJndiOrion, siscardUrl, siscardUser, binCredisiman);
                    break;
                case "ListadoTarjetas":
                    ListadoTarjetas.obtenerListadoTarjetas(param1, param2, remoteJndiSunnel, remoteJndiOrion, siscardUrl, siscardUser, binCredisiman);
                    break;
                case "ConsultaPolizas":
                    ConsultaPolizas.obtenerConsultaPolizas(param1, param2, remoteJndiSunnel, remoteJndiOrion, siscardUrl, siscardUser, binCredisiman, param3);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return message.genericMessage("ERROR", "600",
                    "Error general contacte al administrador del sistema...", namespace, operationResponse);
        }
        return result;
    }
}
