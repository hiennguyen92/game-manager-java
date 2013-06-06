/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Xml;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author ADMIN
 */
public class XmlReader {
    Document document = null;
    
    public XmlReader(String fileName) {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File("c:\\file.xml");
        try {
            document = (Document) builder.build(xmlFile);
        } catch (JDOMException ex) {
            Logger.getLogger(XmlReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XmlReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getString(String tagName) {
        String innerText = null;
        Element rootNode = document.getRootElement();
        innerText = rootNode.getChildText(tagName);
        return innerText;
    }
}
