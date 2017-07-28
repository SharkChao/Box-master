package com.lenovohit.administrator.box.parser;

import android.content.res.AssetManager;

import com.lenovohit.administrator.box.application.BoxApplication;
import com.lenovohit.administrator.box.exceptions.ConfigurationFileException;
import com.lenovohit.administrator.box.util.Constact;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.SAXParserFactory;

/**
 * Created by SharkChao on 2017-07-26.
 * 用来解析box.xml中的数据
 * pull解析
 */

public class BoxParser {

    static final String NODE_DB_NAME = "dbname";

    static final String NODE_VERSION = "version";

    static final String NODE_LIST = "list";

    static final String NODE_MAPPING = "mapping";

    static final String NODE_CASES = "cases";

    static final String NODE_STORAGE = "storage";

    static final String ATTR_VALUE = "value";

    static final String ATTR_CLASS = "class";

    private static BoxParser sBoxParser;

    public static BoxConfig parseXml(){
        if (sBoxParser == null){
            sBoxParser = new BoxParser();
        }
        return sBoxParser.usePullParse();
    }
    private BoxConfig usePullParse(){
        BoxConfig boxConfig = new BoxConfig();
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(getConfigInputStream(), "utf-8");
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = parser.getName();
                switch (eventType) {
                case XmlPullParser.START_TAG: {
                    if (NODE_DB_NAME.equals(nodeName)) {
                        String dbName = parser.getAttributeValue("", ATTR_VALUE);
                        boxConfig.setDbName(dbName);
                    } else if (NODE_VERSION.equals(nodeName)) {
                        String version = parser.getAttributeValue("", ATTR_VALUE);
                        boxConfig.setVersion(Integer.parseInt(version));
                    } else if (NODE_MAPPING.equals(nodeName)) {
                        String className = parser.getAttributeValue("", ATTR_CLASS);
                        boxConfig.addClassName(className);
                    } else if (NODE_CASES.equals(nodeName)) {
                        String cases = parser.getAttributeValue("", ATTR_VALUE);
                        boxConfig.setCases(cases);
                    } else if (NODE_STORAGE.equals(nodeName)) {
                        String storage = parser.getAttributeValue("", ATTR_VALUE);
                        boxConfig.setStorage(storage);
                    }
                    break;
                }
                default:
                    break;
            }
                eventType = parser.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return boxConfig;
    }
    private void useSAXParser() {
        BoxContentHandler handler;
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            XMLReader xmlReader = factory.newSAXParser().getXMLReader();
            handler = new BoxContentHandler();
            xmlReader.setContentHandler(handler);
            xmlReader.parse(new InputSource(getConfigInputStream()));
        } catch (Exception e) {
        }
    }
    private InputStream getConfigInputStream() throws IOException {
        AssetManager assetManager = BoxApplication.getContext().getAssets();
        String[] fileNames = assetManager.list("");
        if (fileNames != null && fileNames.length > 0) {
            for (String fileName : fileNames) {
                if (Constact.Config.CONFIG_FILE_NAME.equalsIgnoreCase(fileName)) {
                    return assetManager.open(fileName, AssetManager.ACCESS_BUFFER);
                }
            }
        }
        throw new ConfigurationFileException(ConfigurationFileException.CONFIG_FILE_NOT_FIND);
    }

}
