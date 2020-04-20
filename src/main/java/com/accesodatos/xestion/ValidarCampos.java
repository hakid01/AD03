/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accesodatos.xestion;

/**
 *
 * @author hak
 */
public class ValidarCampos {
    
    public static boolean validarNombre(String nombre){
        /* Expresion regular para validar nombre escritos con la primera 
        letra en mayúsculas y las siguientes en minúsculas 
        o todas ellas en mayúsculas. Acepta hasta tres nombre seguidos. */
        return nombre.matches("^([A-ZÁÉÍÓÚ]{1}([a-záéíóú\u00f1]+|[A-ZÁÉÍÓÚ\u00d1]+)[ ]*){1,3}$");
    }

    public static boolean validarEmail(String email){
        return email.matches("^([a-zA-Z0-9]+(\\.?[a-zA-Z0-9])*)+@(([a-zA-Z]+)\\.([a-zA-Z]+))+$");
    }

    public static boolean validarPrecio(String price) {
        return price.matches("^[0-9]+(\\.{1}[0-9]+)?$");
    }
    
    public static boolean validarEnteroPositivo(String stock){
        return stock.matches("^[0-9]+(\\.{0}[0-9]+)?$");
    }
    
}
