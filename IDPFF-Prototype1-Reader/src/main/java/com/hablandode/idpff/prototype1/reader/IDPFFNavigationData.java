/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hablandode.idpff.prototype1.reader;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Aldo Rangel
 */
public class IDPFFNavigationData {
    
    private final ArrayList<IDPFFContentElement> orderedElements;
    private HashMap<String,IDPFFContentElement> allUnSortedElements;
    private int currentIndex;

    public IDPFFNavigationData() {
        orderedElements = new ArrayList<>();
        currentIndex = 0;
    }
    
    public void addElement(IDPFFContentElement element){
        orderedElements.add(element);
    
    }
    
    public IDPFFContentElement getNextElement(){
        if (hasNextElement()) {
            currentIndex++;
            return orderedElements.get(currentIndex);
        }
        return null;
    }
    
    public IDPFFContentElement getPreviousElement(){
        if (hasPreviousElement()) {
            currentIndex--;
            return orderedElements.get(currentIndex);
        }
        return null;
    }
    
    public boolean hasNextElement(){
        return (currentIndex + 1 < orderedElements.size() );
    }
     public boolean hasPreviousElement(){
        return (currentIndex - 1 > -1 );
    }
    
     public IDPFFContentElement getElementAtIndex(int index, boolean moveCurrentIndex){
         if(index > -1 && index < orderedElements.size()){
             if(moveCurrentIndex){
                 currentIndex = index;
             }
         return orderedElements.get(index);
         } 
         return null;
     }
    
     public IDPFFContentElement getCurrentElementXID(){
     
         IDPFFContentElement el = getElementAtIndex(currentIndex, true);
         return allUnSortedElements.get(el.getId()+"xid");
     }
     
     public boolean currentElementHasXID(){
         IDPFFContentElement el = getElementAtIndex(currentIndex, true);
         return allUnSortedElements.containsKey(el.getId()+"xid");
     } 
     
    
    
}
