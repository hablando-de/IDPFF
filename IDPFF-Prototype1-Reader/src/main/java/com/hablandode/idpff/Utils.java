/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hablandode.idpff;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Aldo Rangel
 */
public class Utils {

    public static ZipInputStream extractFileFromZip(String filePath, String zipFile) throws FileNotFoundException, IOException {
    
        FileInputStream fin = new FileInputStream(zipFile);
        BufferedInputStream bin = new BufferedInputStream(fin);
        ZipInputStream zin = new ZipInputStream(bin);
        ZipEntry ze;
        //read the zip entries until we found the one we need
        while ((ze = zin.getNextEntry()) != null) {
            if (ze.getName().equals(filePath)) {
                //return the ZipInputStream pointing to the desired file
                return zin;
            }
        }
        return null;
    }
    
    public static void extractZipInFolder(String zipFile, String outputFolder){
     byte[] buffer = new byte[2048];
 
     try{
    	//create output directory if doesn't exists
    	File folder = new File(outputFolder);
    	if(!folder.exists()){
    		folder.mkdir();
    	}
 
    	ZipInputStream zis;
         zis = new ZipInputStream(new FileInputStream(zipFile));
    	//get the zipped file list entry
    	ZipEntry ze = zis.getNextEntry();
 
    	while(ze!=null){
            if (ze.isDirectory()) {
                ze = zis.getNextEntry();
                continue;
            }
    	   String fileName = ze.getName();
           File decompressedFile = new File(outputFolder + File.separator + fileName);
 
           System.out.println("file unzip : "+ decompressedFile.getAbsoluteFile());
 
            //create all non existentfolders
            new File(decompressedFile.getParent()).mkdirs();
 
            FileOutputStream fos = new FileOutputStream(decompressedFile);             
            
            int len;
            //Read the file and write it back in the new folder
            while ((len = zis.read(buffer)) > 0) {
       		fos.write(buffer, 0, len);
            }
 
            fos.close();   
            ze = zis.getNextEntry();
    	}
 
        zis.closeEntry();
    	zis.close();
 
    }catch(IOException ex){
         
       System.out.println("IOException: " + ex.getMessage());
       
    }
   }    
    
    public static void parseXML(InputStream is, DefaultHandler handler) throws ParserConfigurationException, SAXException, IOException{
        
        SAXParserFactory factory = SAXParserFactory.newInstance();
	SAXParser saxParser = factory.newSAXParser();
        saxParser.parse(is, handler);
    }
    
    

}
