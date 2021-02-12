/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author arturoar
 */
public class CodeSegmentViolatedException extends Exception{
    public CodeSegmentViolatedException() {
        super("Violación del segmento de código");
    }
}
