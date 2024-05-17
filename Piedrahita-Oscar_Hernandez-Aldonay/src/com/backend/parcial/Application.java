package com.backend.parcial;

import com.backend.parcial.dbconnection.H2Connection;

public class Application {
    public static void main(String[] args) {
        H2Connection.ejecutarScriptInicial();
    }
}