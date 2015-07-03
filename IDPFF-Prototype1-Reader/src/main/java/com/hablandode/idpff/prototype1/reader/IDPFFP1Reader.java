/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hablandode.idpff.prototype1.reader;

import com.hablandode.idpff.Utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
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
    
    private String decompressedRootPath;
    
    private IDPFFMetadata metadata;
    
    private IDPFFNavigationData navData;
    

    public IDPFFP1Reader(String filePath) {
        this.idpffPath = filePath;
    }
    
    public void loadIDPFF(){
        try {
            System.out.println("Getting containerFilePath");
            String contentFilePath = this.getContentFilePath();
            System.out.println("Finished getting containerFilePath");
            System.out.println("Parsing content file ");
            this.readContentFile(decompressedRootPath + File.separator + contentFilePath);
            System.out.println("Finished parsing content file");
            
        } catch (IOException | ParserConfigurationException | SAXException ex) {
            Logger.getLogger(IDPFFP1Reader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void parseMetadata(){
        //Utils.extractFileFromZip(null, null)
    }
    
    private String getContainerFilePath(boolean extractIDPFF){
        if(extractIDPFF){//getContainerFilePath
            String extractedPath = this.idpffPath.substring(0, this.idpffPath.length() - 6);
            System.out.println("Extracted Path:" + extractedPath);
            Utils.extractZipInFolder(this.idpffPath, extractedPath );
            this.decompressedRootPath = extractedPath;
            return extractedPath + File.separatorChar + "META-INF/container.xml";
            
        
        }
        else{
            throw new UnsupportedOperationException("Not supported yet");
            //TODO: Implement logic of extracting one file
        //Utils.extractFileFromZip("META-INF/content.xml", idpffPath);
        }
    }
    
    private String getContentFilePath() throws IOException, ParserConfigurationException, SAXException{
        FileInputStream fis = new FileInputStream(this.getContainerFilePath(true));
        
        ContentHandler contentHandler = new ContentHandler();
        
        
        Utils.parseXML(fis,contentHandler);
        System.out.println("Test "+contentHandler.getContainerFilePath());
        return contentHandler.getContainerFilePath();
    }
    
    private void readContentFile(String contenFilePath) throws FileNotFoundException, ParserConfigurationException, SAXException, IOException{
        FileInputStream fis = new FileInputStream(contenFilePath);
        ContentParser parser = new ContentParser();
        Utils.parseXML(fis, parser);
        this.navData = parser.getNavData();
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
    
    private class ContentParser extends DefaultHandler{
        
        private boolean bmanifest = false;
	private boolean bspine = false;
        
        private HashMap<String,IDPFFContentElement> elements = new HashMap<>();
        private IDPFFNavigationData navData = new IDPFFNavigationData();
        private IDPFFContentElement tableOfContents = null;
        
        
        
        
        @Override
	public void startElement(String uri, String localName,String qName, 
                Attributes attributes) throws SAXException {
 
		System.out.println("Start Element :" + qName);
 
		if (qName.equalsIgnoreCase("manifest")) {
			bmanifest = true;
		}
 
		if (qName.equalsIgnoreCase("spine")) {
                    System.out.println("TOC = " + attributes.getValue("toc"));
			tableOfContents = elements.get(attributes.getValue("toc"));
		}
                
                if (qName.equalsIgnoreCase("item")) {
                    System.out.println("item = " + attributes.getValue("id")+" "+attributes.getValue("href"));
                    elements.put(attributes.getValue("id"), new IDPFFContentElement(attributes.getValue("href"), attributes.getValue("media-type")));
		}
                
                if (qName.equalsIgnoreCase("itemref")) {
                    System.out.println("itemref = " + elements.get(attributes.getValue("idref")));
			navData.addElement(elements.get(attributes.getValue("idref")));
                        
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

        public HashMap<String,IDPFFContentElement> getElements() {
            return elements;
        }

        public void setElements(HashMap<String,IDPFFContentElement> elements) {
            this.elements = elements;
        }

        public IDPFFNavigationData getNavData() {
            return navData;
        }

        public void setNavData(IDPFFNavigationData navData) {
            this.navData = navData;
        }

        public IDPFFContentElement getTableOfContents() {
            return tableOfContents;
        }

        public void setTableOfContents(IDPFFContentElement tableOfContents) {
            this.tableOfContents = tableOfContents;
        }
        
    }
}
