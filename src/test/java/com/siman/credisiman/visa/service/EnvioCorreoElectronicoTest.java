package com.siman.credisiman.visa.service;


import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EnvioCorreoElectronicoTest {
    static private final String NS = "http://siman.com/EnvioCorreoElectronico";

    @Test
    public void obtenerEnvioCorreoOk() {
        XmlObject result = EnvioCorreoElectronico.send("no-reply@simaninternet.net", "CREDISIMAN EL SALVADOR","prueba de corrreo",false,"<h1>Automatic JUnit Test </h1><p style='color:green;'>process complete</p>",
                "carlos_penate@siman.com,carlos salazar;","https://mandrillapp.com/api/1.0/messages/send.json",
                "md-jHsAPNoQFFypCiypPY0Flw","NotificacionCredisiman");
        assertEquals("00", ((SimpleValue) result.selectPath( "declare namespace ns='" +NS + "' " + ".//ns:statusCode")[0]).getStringValue());
        assertEquals("SUCCESS", ((SimpleValue) result.selectPath( "declare namespace ns='" + NS + "' " + ".//ns:status")[0]).getStringValue());

    }
}
