package com.siman.credisiman.visa.utils;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;

import javax.xml.namespace.QName;

public class Message {

    public XmlObject genericMessage(String status, String statusCode, String statusMessage,String namespace,
                                    String operationResponse){
        XmlObject result = XmlObject.Factory.newInstance();
        XmlCursor cursor = result.newCursor();
        QName responseQName = new QName(namespace, operationResponse);

        cursor.toNextToken();
        cursor.beginElement(responseQName);
        cursor.insertElementWithText(new QName(namespace, "status"),status);
        cursor.insertElementWithText(new QName(namespace, "statusCode"), statusCode);
        cursor.insertElementWithText(new QName(namespace, "statusMessage"), statusMessage);
        cursor.toParent();

        return result;
    }

}
