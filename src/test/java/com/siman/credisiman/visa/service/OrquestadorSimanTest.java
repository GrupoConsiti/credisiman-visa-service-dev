package com.siman.credisiman.visa.service;

import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OrquestadorSimanTest {
    private static final String NS = "http://siman.com/OrquestadorSiman";

    @Test
    public void orquestadorSimanObtenerListadoSucursales() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("ListadoSucursales", "SV", null, null, null, null, null,
                null, null, null, null, null, null, null, null,
                "jdbc/SUNTSTGT", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void orquestadorSimanObtenerDatosCliente() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("ConsultaDatosCliente", "SV", "048382810", null, null, null, null,
                null, null, null, null, null, null, null, null, "jdbc/SUNTST", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "CDS00867", "600831, 600831, 600831");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void orquestadorSimanObtenerConsultaMovimientos() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("ConsultaMovimientos",
                "SV", "6008314502274917", "20180101", "20221231", "P", null, null, null, null, null, null, null, null, null,
                "jdbc/SUNTST", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void orquestadorSimanObtenerConsultaSaldoMonedero() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("ConsultaSaldoMonedero",
                "SV", "4000123456780000", "18140", "20211009", "P", "45738400", null, null, null, null, null, null, null, null,
                "jndi/SimacSV", "jdbc/ORIONREPOSV",
                "http://soauat.siman.com:7003/v1/orion", "usuario", "600831, 600831, 600831");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void orquestadorSimanObtenerListadoTarjetas() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("ListadoTarjetas", "SV", "333214569",
                null, null, null, null, null, null, null, null, null, null, null, null,
                "jdbc/SUNTST", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    // Revisar
    @Test
    public void orquestadorSimanObtenerConsultaPolizas() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("ConsultaPolizas", "SV", "4573840094950811",
                "V", null, null, null, null, null, null, null, null, null, null, null,
                "jdbc/SUNTST", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion", "usuario", "600831, 600831, 600831");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void orquestadorSimanObtenerListarPlanesPorProducto() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("ListarPlanesPorProducto", "SV","45738400", null,
                null, null, null, null, null, null, null, null, null, null, null,
                "jdbc/SUNTST", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion",
                "usuario", "600831, 600831, 600831");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void orquestadorSimanObtenerObtenerConsultaEstadoCuenta() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("ConsultaEstadoCuenta", "SV", "400012345507007",
                "12345","20221225", "V","https://api-crm.siman.com", "S4l3$force","$2a$10$lH7.4sT65MvGIkbr3buMpunEkmjc6fW735baDiwcvP8I7QJq3VUFe", "010161290",
                null, null, null, null, null,
                "jndi/SimacSV", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion", "usuario", "600831, 600831, 600831");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void orquestadorSimanObtenerConsultaSubproductos() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("ConsultaSubproductos", "SV", "4573840079870646",
                "V","https://api-crm.siman.com", "S4l3$force","$2a$10$lH7.4sT65MvGIkbr3buMpunEkmjc6fW735baDiwcvP8I7QJq3VUFe",
                null, null, null, null, null, null, null, null,
                "jdbc/SUNTST", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion", "usuario", "600831, 600831, 600831");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void orquestadorSimanObtenerListadoEstadosCuenta() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("ListadoEstadosCuenta", "SV", "400012345507007",
                "V","https://api-crm.siman.com", "S4l3$force","$2a$10$lH7.4sT65MvGIkbr3buMpunEkmjc6fW735baDiwcvP8I7QJq3VUFe", "010161290",
                null, null, null, null, null, null, null,
                "jndi/SimacSV", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion", "usuario", "600831, 600831, 600831");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void orquestadorSimanObtenerBloqueoDesbloqueoTarjeta() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("BloqueoDesbloqueoTarjeta","SV", "6008315500062519",
                "1", "M6", "P", null, null, null, null, null, null, null, null, null,
                "jdbc/SUNTST", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion", "usuario", "600831, 600831, 600831");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void orquestadorSimanObtenerCambioFechaCorte() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("CambioFechaCorte","SV","4000123456780000",
                "12345","25", "P", null, null, null, null, null, null, null, null, null,
                "jndi/SimacSV", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion", "usuario", "600831, 600831, 600831");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void orquestadorSimanCambioPIN() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("CambioPIN","SV", "4000123456780000",
                "xOCcB1WjksWWqbK/o8taUQ==", "V", null, null, null, null, null, null, null, null, null, null,
                "jdbc/SUNTST", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion", "CDS00867", "600831, 600831, 600831");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void orquestadorSimanModificarLimiteTarjeta() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("ModificarLimiteTarjeta","SLV", "0398765432",
                "000000500000", "P", null, null, null, null, null, null, null, null, null, null,
                "jndi/SimacSV", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion", "usuario", "600831, 600831, 600831");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void orquestadorSimanRegistroUsuario() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("RegistroUsuario","SV", "Juan",
                null, "Peréz", null, null, "SV", "20000907", "DUI", "001000105", "juan.perez@gmail.com", "77776666", "juan.perez@gmail.com", "60607070",
                "jndi/SimacSV", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion", "usuario", "600831, 600831, 600831");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void orquestadorSimanCrearSolicitudReposicionTarjeta() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("SolicitudReposicionTarjeta","SV", "4573840026044337",
                "CindySolisFernandez","1", "P", null, null, null, null, null, null, null, null, null,
                "jndi/SimacSV", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion", "usuario", "600831, 600831, 600831");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void orquestadorSimanObtenerSolicitudTrasladoCompraEnCuotas() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("SolicitudTrasladoCompraEnCuotas","SV", "442427223",
                "6008314502274917","carlos_penate@siman.com", "22224444","57","63","0.10","20230126", "02","840","DUI","Carlos Salazar", null,
                "jdbc/SUNTSTGT", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion", "usuario", "600831, 600831, 600831");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void orquestadorSimanEnvioCorreoElectronico() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("EnvioCorreoElectronico", "no-reply@simaninternet.net",
                "CREDISIMAN EL SALVADOR", "prueba de corrreo", "false",
                "<h1>Automatic JUnit Test </h1><p style='color:green;'>process complete</p>",
                "carlos_penate@siman.com,carlos salazar;", "https://mandrillapp.com/api/1.0/messages/send.json",
                "md-jHsAPNoQFFypCiypPY0Flw", "NotificacionCredisiman", null, null, null, null, null, null, null, null, null, null);

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }

    @Test
    public void orquestadorSimanObtenerEstadoSolicitudTransladoCompraenCuotas() {
        XmlObject result = OrquestadorSiman.obtenerOrquestadorSiman("EstadoSolicitudTransladoCompraenCuotas", "SV", "40",
                null, null, null, null, null, null, null, null, null, null, null, null,
                "jdbc/SUNTSTGT", "jdbc/ORIONREPOSV", "http://soauat.siman.com:7003/v1/orion", "usuario", "600831, 600831, 600831");

        //Status
        assertEquals("00", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath("declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());
    }
}
