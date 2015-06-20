/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hablandode.idpff.prototype1.reader;

import com.hablandode.idpff.Utils;

/**
 *
 * @author Aldo Rangel
 */
public class IDPFFP1Reader {
    
    private String idpffPath;
    
    private IDPFFMetadata metadata;
    
    private IDPFFNavigationData navData;
    

    public IDPFFP1Reader(String filePath) {
        this.idpffPath = filePath;
    }
    
    
    
    public void parseMetadata(){
        //Utils.extractFileFromZip(null, null)
    }
    
}
