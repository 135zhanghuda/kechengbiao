package com.example.kechengbiao.coursetablegetsample.SampleUtil.Html;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.StringReader;

import javax.xml.parsers.SAXParserFactory;

public class SampleSAX {
    public static  void  parseXMLWithSAX(String xmlData,DefaultHandler myXmlHandler){
        try{
            SAXParserFactory factory=SAXParserFactory.newInstance();
            XMLReader xmlReader=factory.newSAXParser().getXMLReader();

            xmlReader.setContentHandler(myXmlHandler);

            StringReader stringReader=new StringReader(xmlData);
            xmlReader.parse(new InputSource(stringReader));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}