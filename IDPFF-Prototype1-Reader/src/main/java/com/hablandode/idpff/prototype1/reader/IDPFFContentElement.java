/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hablandode.idpff.prototype1.reader;

/**
 *
 * @author Aldo Rangel
 */
public class IDPFFContentElement {
    private String id;
    private String pathToElement;
    private String mimeType;

    public IDPFFContentElement() {
    }

    public IDPFFContentElement(String id,String pathToElement, String mimeType) {
        this.id = id;
        this.pathToElement = pathToElement;
        this.mimeType = mimeType;
    }

    public String getId() {
        return id;
    }

    public String getPathToElement() {
        return pathToElement;
    }

    public void setPathToElement(String pathToElement) {
        this.pathToElement = pathToElement;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
    
    
}
