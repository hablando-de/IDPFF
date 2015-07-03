/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hablandode.idpff.prototype1.reader;

import java.util.ArrayList;

/**
 *
 * @author Aldo Rangel
 */
public class IDPFFNavigationData {
    
    private ArrayList<IDPFFContentElement> orderedElements;
    private int currentIndex;

    public IDPFFNavigationData() {
        orderedElements = new ArrayList<>();
        currentIndex = 0;
    }
    
    public void addElement(IDPFFContentElement element){
        orderedElements.add(element);
    
    }
    
    public void getNextElement(){
    
    }
    
    
    
    
}
