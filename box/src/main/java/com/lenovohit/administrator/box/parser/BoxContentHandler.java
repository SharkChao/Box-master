package com.lenovohit.administrator.box.parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by SharkChao on 2017-07-26.
 * sax解析html
 */

public class BoxContentHandler extends DefaultHandler{
    private BoxAttrs mBoxAttrs;
    @Override
    public void startDocument() throws SAXException {
        mBoxAttrs = BoxAttrs.getInstance();
        mBoxAttrs.getClassNames().clear();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (BoxParser.NODE_DB_NAME.equalsIgnoreCase(localName)) {
            for (int i = 0; i < attributes.getLength(); i++) {
                if (BoxParser.ATTR_VALUE.equalsIgnoreCase(attributes.getLocalName(i))) {
                    mBoxAttrs.setDbName(attributes.getValue(i).trim());
                }
            }
        } else if (BoxParser.NODE_VERSION.equalsIgnoreCase(localName)) {
            for (int i = 0; i < attributes.getLength(); i++) {
                if (BoxParser.ATTR_VALUE.equalsIgnoreCase(attributes.getLocalName(i))) {
                    mBoxAttrs.setVersion(Integer.parseInt(attributes.getValue(i).trim()));
                }
            }
        } else if (BoxParser.NODE_MAPPING.equalsIgnoreCase(localName)) {
            for (int i = 0; i < attributes.getLength(); i++) {
                if (BoxParser.ATTR_CLASS.equalsIgnoreCase(attributes.getLocalName(i))) {
                    mBoxAttrs.addClassName(attributes.getValue(i).trim());
                }
            }
        } else if (BoxParser.NODE_CASES.equalsIgnoreCase(localName)) {
            for (int i = 0; i < attributes.getLength(); i++) {
                if (BoxParser.ATTR_VALUE.equalsIgnoreCase(attributes.getLocalName(i))) {
                    mBoxAttrs.setCases(attributes.getValue(i).trim());
                }
            }
        } else if (BoxParser.NODE_STORAGE.equalsIgnoreCase(localName)) {
            for (int i = 0; i < attributes.getLength(); i++) {
                if (BoxParser.ATTR_VALUE.equalsIgnoreCase(attributes.getLocalName(i))) {
                    mBoxAttrs.setStorage(attributes.getValue(i).trim());
                }
            }
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

}
