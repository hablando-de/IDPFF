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
import java.io.InputStream;
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

    private final String idpffPath;

    private String decompressedRootPath;

    private IDPFFMetadata metadata;

    private IDPFFNavigationData navData;

    public IDPFFP1Reader(String filePath) {
        this.idpffPath = filePath;
    }

    public void logAllIDPFFData() {
        System.out.println("IDPFF data:");
        System.out.println("IDPFF Path:");
        System.out.println(idpffPath);
        System.out.println("IDPFF decompressedPath:");
        System.out.println(decompressedRootPath);
        System.out.println("Navigation Data:");
        System.out.println("ASD");
        System.out.println("Elements: ");
        System.out.println("**********");
        IDPFFContentElement elementZero = navData.getElementAtIndex(0, true);
        System.out.println("Path ");
        System.out.println(elementZero.getPathToElement());
        System.out.println("MimeType");
        System.out.println(elementZero.getMimeType());
        System.out.println("has xid?");
        System.out.println(navData.currentElementHasXID());
        while (navData.hasNextElement()) {
            System.out.println("**********");
            IDPFFContentElement element = navData.getNextElement();
            System.out.println("Path ");
            System.out.println(element.getPathToElement());
            System.out.println("MimeType");
            System.out.println(element.getMimeType());
        System.out.println("has xid?");
        System.out.println(navData.currentElementHasXID());
        if(navData.currentElementHasXID()){
                try {
                    XIDParer xid = new XIDParer();
                    
                    Utils.parseXML(new FileInputStream(decompressedRootPath + navData.getCurrentElementXID().getPathToElement()), xid);
                    System.out.println(xid.getJavascriptCode());
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(IDPFFP1Reader.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParserConfigurationException | SAXException | IOException ex) {
                    Logger.getLogger(IDPFFP1Reader.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        }
        System.out.println("**********");

    }

    public void loadIDPFF() {
        try {
            System.out.println("Getting containerFilePath");
            String contentFilePath = this.getContentFilePath();
            System.out.println("Finished getting containerFilePath");
            System.out.println("Parsing content file ");
            System.out.println("decompressedRootPath + File.separator + contentFilePath" + decompressedRootPath + File.separator + contentFilePath);
            this.readContentFile(decompressedRootPath + File.separator + contentFilePath);
            System.out.println("Finished parsing content file");

        } catch (IOException | ParserConfigurationException | SAXException ex) {
            Logger.getLogger(IDPFFP1Reader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void parseMetadata() {
        //Utils.extractFileFromZip(null, null)
    }

    private String getContainerFilePath(boolean extractIDPFF) {
        if (extractIDPFF) {//getContainerFilePath
            String extractedPath = this.idpffPath.substring(0, this.idpffPath.length() - 6);
            System.out.println("Extracted Path:" + extractedPath);
            Utils.extractZipInFolder(this.idpffPath, extractedPath);
            String extraPath = extractedPath.lastIndexOf(File.separator) != -1 ? extractedPath.substring(extractedPath.lastIndexOf(File.separator)) : extractedPath.substring(extractedPath.lastIndexOf("/"));
            System.out.println(extraPath);
            this.decompressedRootPath = extractedPath + File.separatorChar + extraPath + File.separatorChar;
            return extractedPath + File.separatorChar + extraPath + File.separatorChar + "META-INF/container.xml";

        } else {
            throw new UnsupportedOperationException("Not supported yet");
            //TODO: Implement logic of extracting one file
            //Utils.extractFileFromZip("META-INF/content.xml", idpffPath);
        }
    }

    private String getContentFilePath() throws IOException, ParserConfigurationException, SAXException {
        FileInputStream fis = new FileInputStream(this.getContainerFilePath(true));

        ContentHandler contentHandler = new ContentHandler();

        Utils.parseXML(fis, contentHandler);
        System.out.println("Test " + contentHandler.getContainerFilePath());
        return contentHandler.getContainerFilePath();
    }

    private void readContentFile(String contenFilePath) throws FileNotFoundException, ParserConfigurationException, SAXException, IOException {
        FileInputStream fis = new FileInputStream(contenFilePath);
        ContentParser parser = new ContentParser();
        Utils.parseXML(fis, parser);
        this.navData = parser.getNavData();
    }

    private class ContentHandler extends DefaultHandler {

        boolean bcontainer = false;
        boolean brootfiles = false;
        boolean brootfile = false;
        StringBuilder sb = new StringBuilder();
        private String containerFilePath;

        public String getContainerFilePath() {
            return containerFilePath;
        }

        @Override
        public void startElement(String uri, String localName, String qName,
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

    private class ContentParser extends DefaultHandler {

        private boolean bmanifest = false;
        private boolean bspine = false;

        private HashMap<String, IDPFFContentElement> elements = new HashMap<>();
        private IDPFFNavigationData navData = new IDPFFNavigationData();
        private IDPFFContentElement tableOfContents = null;

        @Override
        public void startElement(String uri, String localName, String qName,
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
                System.out.println("item = " + attributes.getValue("id") + " " + attributes.getValue("href"));
                elements.put(attributes.getValue("id"), new IDPFFContentElement(attributes.getValue("id"), attributes.getValue("href"), attributes.getValue("media-type")));
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

        }

        public HashMap<String, IDPFFContentElement> getElements() {
            return elements;
        }

        public void setElements(HashMap<String, IDPFFContentElement> elements) {
            this.elements = elements;
        }

        public IDPFFNavigationData getNavData() {
            navData.setAllUnSortedElements(this.elements);
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

    private class XIDParer extends DefaultHandler {

        boolean bcontainer = false;
        boolean brootfiles = false;
        boolean brootfile = false;
        StringBuilder sb = new StringBuilder();
        private String javascriptFunction;
        private String degrees = "0";
        private String scaleX = "1";
        private String scaleY = "1";
        private String moveX = "0";
        private String moveY = "0";
        private String duration = "0";
        private String elementId = "";

        @Override
        public void startElement(String uri, String localName, String qName,
                Attributes attributes) throws SAXException {

            System.out.println("Start Element :" + qName);
            if (qName.equalsIgnoreCase("element")) {
                sb.append(String.format("var %s = document.getElementById('%s');", attributes.getValue("id"), attributes.getValue("id")));
            }
            if (qName.equalsIgnoreCase("event")) {
                if ("userInteraction".equalsIgnoreCase(attributes.getValue("type"))) {
                    sb.append(String.format("%s.addEventListener(\"click\", %s);", attributes.getValue("elementId"), attributes.getValue("actionId")));

                } else if ("timed".equalsIgnoreCase(attributes.getValue("type"))) {
                    sb.append(String.format("setTimeOut(%s,%s);", attributes.getValue("actionId"), attributes.getValue("timeout")));
                }
            }
            if (qName.equalsIgnoreCase("action")) {
                sb.append(String.format("function %s(){", attributes.getValue("id")));
            }
            
            
            
            
            if (qName.equalsIgnoreCase("move")) {
                String type = Boolean.parseBoolean(attributes.getValue("relative")) ? "relative" : "absolute";
                Integer duration = Integer.parseInt(attributes.getValue("duration"));
                moveX = attributes.getValue("moveToX");
                moveY = attributes.getValue("moveToY");
                this.duration = attributes.getValue("duration");
                elementId = attributes.getValue("elementId");
                /*if (duration <= 0) {
                    sb.append(String.format("%s.style.position = '%s';", attributes.getValue("elementId"), type));
                    sb.append(String.format("%s.style.left= %s.style.left ? %s.style.left : 0; %s.style.top = %s.style.top ? %s.style.top : 0;", attributes.getValue("elementId"), attributes.getValue("elementId"), attributes.getValue("elementId"), attributes.getValue("elementId"), attributes.getValue("elementId"), attributes.getValue("elementId")));
                    if(type.contentEquals("relative")){
                        
                    sb.append(String.format("%s.style.left = parseInt(%s.style.left) + %s + 'px';", attributes.getValue("elementId"), attributes.getValue("elementId"), attributes.getValue("moveToX")));
                    sb.append(String.format("%s.style.top = parseInt(%s.style.top) + %s + 'px';", attributes.getValue("elementId"), attributes.getValue("elementId"), attributes.getValue("moveToY")));
                    }
                    else{
                        sb.append("");
                    }
                } else {

                        //jquery animate hybrid musst be here
                }*/
            }
            if (qName.equalsIgnoreCase("rotation")) {
                
                moveY = attributes.getValue("moveToY");
                this.duration = attributes.getValue("duration");
                elementId = attributes.getValue("elementId");

            }
            
            if (qName.equalsIgnoreCase("scale")) {
                
                scaleX = attributes.getValue("scaleX");
                scaleY = attributes.getValue("scaleY");
                this.duration = attributes.getValue("duration");
                elementId = attributes.getValue("elementId");

            }

        }

        @Override
        public void endElement(String uri, String localName,
                String qName) throws SAXException {

            System.out.println("End Element :" + qName);
            if (qName.equalsIgnoreCase("action")) {
                sb.append("transformElement(\"").append(elementId).append("\",").append(degrees).append(",").append(scaleX).append(",").append(scaleY).append(",").append(moveX).append(",").append(moveY).append(");");
                sb.append("setTransitionDurationToElement(\"").append(elementId).append("\",").append(duration).append(");");
                sb.append("}");
            }

        }

        @Override
        public void characters(char ch[], int start, int length) throws SAXException {

        }

        public String getJavascriptCode() {
            return sb.toString();
        }

    }

}
