/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hablandode.idpff.prototype1;

import com.hablandode.idpff.prototype1.reader.IDPFFP1Reader;
import java.io.File;

/**
 *
 * @author Aldo Rangel
 */
public class Prototype1Test {

    private static final String PATH_TO_IDPFF = "./../Prueba_idpff.idpff";
    //private static final String PATH_TO_IDPFF = "C:\\NewProjectsRepo\\java\\IDPFF\\Prueba_idpff.idpff";
    //C:\NewProjectsRepo\java\IDPFF\
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        runIDPFFTest();
    }
    
    
    private static void runIDPFFTest(){
        /*
        File folder = new File("./../Prueba_idpff.idpff");
         for (final File fileEntry : folder.listFiles()) {
        
            System.out.println(fileEntry.getName());
        }*/
    
   
        IDPFFP1Reader reader = new IDPFFP1Reader(PATH_TO_IDPFF);
        reader.loadIDPFF();
    }
    
    
}
