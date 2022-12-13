package com.siman.credisiman.visa.utils;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;


public class ConnectionHandler {
    private static Logger log = LoggerFactory.getLogger(ConnectionHandler.class);
    public Connection getConnection(String jndi) {
        Connection connection = null;

        try {
            InitialContext context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup(jndi);
            connection = dataSource.getConnection();
        } catch (NamingException | SQLException e) {
            throw new RuntimeException("SQL CONEXION ERROR");
        }
        return connection;
    }
}
