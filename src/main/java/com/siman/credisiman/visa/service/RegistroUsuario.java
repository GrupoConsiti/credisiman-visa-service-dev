package com.siman.credisiman.visa.service;

import javax.xml.namespace.QName;

import com.siman.credisiman.visa.utils.Message;
import com.siman.credisiman.visa.utils.Utils;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class RegistroUsuario {
    //private static Logger log = LoggerFactory.getLogger(RegistroUsuario.class);

    public static XmlObject registroUsuario(String pais, String primerNombre, String segundoNombre,
                                            String primerApellido, String segundoApellido, String apellidoCasada, String paisResidencia,
                                            String fechaNacimiento, String tipoDocumento, String numeroDocumento, String correo, String celular,
                                            String correoNotificacion, String celularNotificacion, String remoteJndiSunnel, String remoteJndiOrion,
                                            String siscardUrl, String siscardUser, String binCredisiman) {

        String namespace = "http://siman.com/RegistroUsuario";
        String operationResponse = "RegistroUsuarioResponse";

        Utils utils = new Utils();
        Message message = new Message();

        //validar campos requeridos
        if (utils.validateNotNull(pais) || utils.validateNotEmpty(pais)) {
            //log.info("pais required");
            return message.genericMessage("ERROR", "400",
                    "El campo pais es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(primerNombre) || utils.validateNotEmpty(primerNombre)) {
            //log.info("primer nombre required");
            return message.genericMessage("ERROR", "400",
                    "El campo primer nombre es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(primerApellido) || utils.validateNotEmpty(primerApellido)) {
            //log.info("primer apellido required");
            return message.genericMessage("ERROR", "400",
                    "El campo primer apellido es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(paisResidencia) || utils.validateNotEmpty(paisResidencia)) {
            //log.info("pais residencia required");
            return message.genericMessage("ERROR", "400",
                    "El campo pais residencia es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(fechaNacimiento) || utils.validateNotEmpty(fechaNacimiento)) {
            //log.info("fecha nacimiento required");
            return message.genericMessage("ERROR", "400",
                    "El campo fecha nacimiento es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(tipoDocumento) || utils.validateNotEmpty(tipoDocumento)) {
            //log.info("tipo documento required");
            return message.genericMessage("ERROR", "400",
                    "El campo tipo documento es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(numeroDocumento) || utils.validateNotEmpty(numeroDocumento)) {
            //log.info("numero documento required");
            return message.genericMessage("ERROR", "400",
                    "El campo número documento es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(correo) || utils.validateNotEmpty(correo)) {
            //log.info("correo required");
            return message.genericMessage("ERROR", "400",
                    "El campo correo es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(celular) || utils.validateNotEmpty(celular)) {
            //log.info("celular required");
            return message.genericMessage("ERROR", "400",
                    "El campo celular es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(correoNotificacion) || utils.validateNotEmpty(correoNotificacion)) {
            //log.info("correo notificacion required");
            return message.genericMessage("ERROR", "400",
                    "El campo correo notificación es obligatorio", namespace, operationResponse);
        }
        if (utils.validateNotNull(celularNotificacion) || utils.validateNotEmpty(celularNotificacion)) {
            //log.info("celular notificacion required");
            return message.genericMessage("ERROR", "400",
                    "El campo celular notificación es obligatorio", namespace, operationResponse);
        }

        //validar fecha
        if (!utils.validateDate(fechaNacimiento)) {
            //log.info("validar fecha nacimiento");
            return message.genericMessage("ERROR", "400",
                    "El campo fecha nacimiento no cumple el formato establecido, ej. YYYYMMDD", namespace, operationResponse);
        }
        //validar celular
        if (!utils.validateNumberPhoneGeneric(celular)) {
            return message.genericMessage("ERROR", "400",
                    "El campo celular solo admite datos numericos", namespace, operationResponse);

        }
        if (!utils.validateNumberPhoneGeneric(celularNotificacion)) {
            return message.genericMessage("ERROR", "400",
                    "El campo celular notificación solo admite datos numericos", namespace, operationResponse);
        }
        //validar fecha
        if (!utils.isNumeric(fechaNacimiento)) {
            return message.genericMessage("ERROR", "400",
                    "El campo fecha nacimiento no admite letras ", namespace, operationResponse);
        }
        //validar longitudes

        if (!utils.validateLongitude(pais, 3)) {
            return message.genericMessage("ERROR", "400",
                    "La longitud del campo pais debe ser menor o igual a 3", namespace, operationResponse);
        }
        if (!utils.validateLongitude(primerNombre, 20)) {
            return message.genericMessage("ERROR", "400",
                    "La longitud del campo primer nombre debe ser menor o igual a 20", namespace, operationResponse);
        }
        if (!utils.validateNotNull(segundoNombre)) {
            if (!utils.validateLongitude(segundoNombre, 20)) {
                return message.genericMessage("ERROR", "400",
                        "La longitud del campo segundo nombre debe ser menor o igual a 20", namespace, operationResponse);
            }
        }
        if (!utils.validateLongitude(primerApellido, 15)) {
            return message.genericMessage("ERROR", "400",
                    "La longitud del campo primer apellido debe ser menor o igual a 20", namespace, operationResponse);
        }
        if (!utils.validateNotNull(segundoApellido)) {
            if (!utils.validateLongitude(segundoApellido, 15)) {
                return message.genericMessage("ERROR", "400",
                        "La longitud del campo segundo apellido debe ser menor o igual a 15", namespace, operationResponse);
            }
        }
        if (!utils.validateNotNull(apellidoCasada)) {
            if (!utils.validateLongitude(apellidoCasada, 15)) {
                return message.genericMessage("ERROR", "400",
                        "La longitud del campo apellido casada debe ser menor o igual a 15", namespace, operationResponse);
            }
        }
        if (!utils.validateLongitude(paisResidencia, 3)) {
            return message.genericMessage("ERROR", "400",
                    "La longitud del campo pais de residencia debe ser menor o igual a 3", namespace, operationResponse);
        }
        if (!utils.validateLongitude(tipoDocumento, 3)) {
            return message.genericMessage("ERROR", "400",
                    "La longitud del campo tipo documento debe ser menor o igual a 3", namespace, operationResponse);
        }
        if (!utils.validateLongitude(numeroDocumento, 19)) {
            return message.genericMessage("ERROR", "400",
                    "La longitud del campo número de documento debe ser menor o igual a 19", namespace, operationResponse);
        }
        if (!utils.validateLongitude(correo, 45)) {
            return message.genericMessage("ERROR", "400",
                    "La longitud del campo correo debe ser menor o igual a 45", namespace, operationResponse);
        }
        if (!utils.validateLongitude(celular, 15)) {
            return message.genericMessage("ERROR", "400",
                    "La longitud del campo celular debe ser menor o igual a 15", namespace, operationResponse);
        }
        if (!utils.validateLongitude(correoNotificacion, 45)) {
            return message.genericMessage("ERROR", "400",
                    "La longitud del campo correo notificación debe ser menor o igual a 45", namespace, operationResponse);
        }
        if (!utils.validateLongitude(celularNotificacion, 15)) {
            return message.genericMessage("ERROR", "400",
                    "La longitud del campo celular notificación debe ser menor o igual a 15", namespace, operationResponse);
        }

        //Validar formato de correo
        if (!utils.validateEmail(correo)) {
            return message.genericMessage("ERROR", "400",
                    "El campo correo no cumple con la estructura de un correo", namespace, operationResponse);
        }
        if (!utils.validateEmail(correoNotificacion)) {
            return message.genericMessage("ERROR", "400",
                    "El campo correo notificación no cumple con la estructura de un correo", namespace, operationResponse);
        }

        //INSERT / UPDATE / SELECT A BASE DE DATOS

        XmlObject result = XmlObject.Factory.newInstance();
        XmlCursor cursor = result.newCursor();
        QName responseQName = new QName(namespace, operationResponse);
        cursor.toNextToken();
        cursor.beginElement(responseQName);
        cursor.insertElementWithText(new QName(namespace, "statusCode"), "00");
        cursor.insertElementWithText(new QName(namespace, "status"), "SUCCESS");
        cursor.insertElementWithText(new QName(namespace, "statusMessage"), "Registro exitoso");
        cursor.toParent();

        //log.info("registroUsuario response = [" + result.toString() + "]");
        return result;
    }

    public static XmlObject actualizarUsuario(String pais, String primerNombre, String segundoNombre,
                                              String primerApellido, String segundoApellido, String apellidoCasada, String paisResidencia,
                                              String fechaNacimiento, String tipoDocumento, String numeroDocumento, String correo, String celular,
                                              String correoNotificacion, String celularNotificacion, String remoteJndiSunnel, String remoteJndiOrion,
                                              String siscardUrl, String siscardUser, String binCredisiman) {

        String namespace = "http://siman.com/RegistroUsuario";
        String operationResponse = "ActualizarUsuarioResponse";

        XmlObject result = XmlObject.Factory.newInstance();
        XmlCursor cursor = result.newCursor();
        QName responseQName = new QName(namespace, operationResponse);
        cursor.toNextToken();
        cursor.beginElement(responseQName);
        cursor.insertElementWithText(new QName(namespace, "statusCode"), "00");
        cursor.insertElementWithText(new QName(namespace, "status"), "SUCCESS");
        cursor.insertElementWithText(new QName(namespace, "statusMessage"), "Registro exitoso");
        cursor.toParent();

        //log.info("actualizarUsuario response = [" + result.toString() + "]");
        return result;
    }

    public static String registroUsuarioString(String pais, String primerNombre, String segundoNombre,
                                               String primerApellido, String segundoApellido, String apellidoCasada, String paisResidencia,
                                               String fechaNacimiento, String tipoDocumento, String numeroDocumento, String correo, String celular,
                                               String correoNotificacion, String celularNotificacion, String remoteJndiSunnel, String remoteJndiOrion,
                                               String siscardUrl, String siscardUser, String binCredisiman) {

        return RegistroUsuario.registroUsuario(pais, primerNombre, segundoNombre, primerApellido, segundoApellido, apellidoCasada, paisResidencia, fechaNacimiento, tipoDocumento, numeroDocumento, correo, celular, correoNotificacion, celularNotificacion, remoteJndiSunnel, remoteJndiOrion, siscardUrl, siscardUser, binCredisiman).toString();
    }
}
