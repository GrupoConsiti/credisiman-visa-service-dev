package com.siman.credisiman.visa.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public Utils() {
    }

    public boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException var3) {
            return false;
        }
    }

    public boolean validateDate(String strDate) {
        if (strDate.trim().equals("")) {
            return true;
        } else {
            SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyyMMdd");
            sdfrmt.setLenient(false);

            try {
                Date javaDate = sdfrmt.parse(strDate);
                System.out.println("ok, validation date" + javaDate.toString());
                return true;
            } catch (ParseException var4) {
                return false;
            }
        }
    }

    public boolean validateDateFormat(String strDate, String format) {
        if (strDate.trim().equals("")) {
            return true;
        } else {
            SimpleDateFormat sdfrmt = new SimpleDateFormat(format);
            sdfrmt.setLenient(false);

            try {
                Date javaDate = sdfrmt.parse(strDate);
                System.out.println("ok, validation date" + javaDate.toString());
                return true;
            } catch (ParseException var5) {
                return false;
            }
        }
    }

    public boolean validateDUI(String s) {
        return s.matches("^[0-9]{8}-[0-9]$");
    }

    public boolean validateNIT(String s) {
        return s.matches("^[0-9]{4}-[0-9]{6}-[0-9]{3}-[0-9]$");
    }

    public boolean validateEmail(String s) {
        return s.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$");
    }

    public boolean validateEmailExtension(String s, String extension) {
        return s.matches("[a-z0-9]+".concat(extension));
    }

    public boolean validateNumberPhone(String s) {
        return s.matches("^[0-9]{4}-[0-9]{4}$");
    }

    public boolean validateNumberPhoneGeneric(String s) {
        return s.matches("^[0-9-()]+$");
    }

    public boolean validateNotEmpty(String s) {
        return s.trim().length() == 0;
    }

    public boolean validateNotNull(String s) {
        return s == null;
    }

    public boolean validatePositiveAmount(Double amount) {
        return amount > 0.0;
    }

    public boolean validateAmountPresicion(Double amount, int presicion) {
        String[] cadena = amount.toString().split("\\.");
        return cadena[1].length() == presicion;
    }

    public boolean validateLongitude(String s, Integer longitude) {
        return s.length() <= longitude;
    }

    public static String obtenerTarjeta(String s, int n) {
        if (s == null || n > s.length()) {
            return s;
        }
        return s.substring(s.length() - n);
    }
}
