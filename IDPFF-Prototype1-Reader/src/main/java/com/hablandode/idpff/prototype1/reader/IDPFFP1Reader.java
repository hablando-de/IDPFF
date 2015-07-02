/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hablandode.idpff.prototype1.reader;

import com.hablandode.idpff.Utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

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
    
    public void loadIDPFF(){
        try {
            System.out.println("Getting containerFilePath");
            System.out.println("containerFilePath"+
            this.getContainerFilePath());
            System.out.println("Finished getting containerFilePath");
        } catch (IOException ex) {
            Logger.getLogger(IDPFFP1Reader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(IDPFFP1Reader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(IDPFFP1Reader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void parseMetadata(){
        //Utils.extractFileFromZip(null, null)
    }
    
    private String getContentFilePath(boolean extractIDPFF){
        if(extractIDPFF){
            String extractedPath = this.idpffPath.substring(0, this.idpffPath.length() - 6);
            System.out.println("Extracted Path:" + extractedPath);
            Utils.extractZipInFolder(this.idpffPath, extractedPath );
            return extractedPath + File.separatorChar + "META-INF/container.xml";
            
        
        }
        else{
            throw new UnsupportedOperationException("Not supported yet");
            //TODO: Implement logic of extracting one file
        //Utils.extractFileFromZip("META-INF/content.xml", idpffPath);
        }
    }
    
    private String getContainerFilePath() throws IOException, ParserConfigurationException, SAXException{
        FileInputStream fis = new FileInputStream(this.getContentFilePath(true));
        
        String containerFilePath;
        ContentHandler contentHandler = new ContentHandler();
        
        
        Utils.parseXML(fis,contentHandler);
        System.out.println("Test "+contentHandler.getContainerFilePath());
        return contentHandler.getContainerFilePath();
    }
    
    private class ContentHandler extends DefaultHandler{
    
        boolean bcontainer = false;
	boolean brootfiles = false;
	boolean brootfile = false;
        StringBuilder sb =  new StringBuilder();
        private String containerFilePath;

        public String getContainerFilePath() {
            return containerFilePath;
        }
        
        
        @Override
	public void startElement(String uri, String localName,String qName, 
                Attributes attributes) throws SAXException {
 
		System.out.println("Start Element :" + qName);
 
		if (qName.equalsIgnoreCase("container")) {
			bcontainer = true;
		}
 
		if (qName.equalsIgnoreCase("rootfiles")) {
			brootfiles = true;
		}
 
		if (qName.equalsIgnoreCase("rootfile")) {
                    containerFilePath = attributes.getValue("full-path");
			brootfile = true;
		}
 
	}
 
        @Override
	public void endElement(String uri, String localName,
		String qName) throws SAXException {

		System.out.println("End Element :" + qName);
 
	}
 
        @Override
	public void characters(char ch[], int start, int length) throws SAXException {
 
            /* Commented for testing purposses but i'll remove this once in dont need it anymore 
		if (bcontainer) {
			bcontainer = false;
		}
 
		if (brootfiles) {
			brootfiles = false;
		}
 
		if (brootfile) {
                    sb.append( new String(ch, start, length));
			brootfile = false;
		}*/
 
	}
 
        
    }
    
}
