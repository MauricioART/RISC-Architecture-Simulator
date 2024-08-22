/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arturoar.excepciones;

/**
 *
 * @author arturoar
 */
public class WarningException extends Exception{
    public WarningException() {
        super("Ocurrio algún error al correr el programa");
    }
}
