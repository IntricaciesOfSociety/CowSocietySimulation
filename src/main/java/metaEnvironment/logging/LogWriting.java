package metaEnvironment.logging;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;

public class LogWriting {

    static void createLogFiles() {
        /*
        try {
            Document cowLogDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            // root element
            Element root = cowLogDoc.createElement("cows");
            Element deadCows = cowLogDoc.createElement("deadCows");

            cowLogDoc.appendChild(root);
            root.appendChild(deadCows);

            *//*Attr attr = doc.createAttribute("company");
            attr.setValue("Ferrari");
            supercar.setAttributeNode(attr);*//*

            // carname element
            Element cowName = cowLogDoc.createElement("cow");
            Attr nameAttr = cowLogDoc.createAttribute("cowID");
            Attr nameAttr = cowLogDoc.createAttribute("fitness");
            nameAttr.setValue("noCow");

            cowName.setAttributeNode(nameAttr);
            cowName.appendChild(cowLogDoc.createTextNode("Ferrari 101"));
            supercar.appendChild(carname);

            Element carname1 = doc.createElement("carname");
            Attr attrType1 = doc.createAttribute("type");
            attrType1.setValue("sports");
            carname1.setAttributeNode(attrType1);
            carname1.appendChild(doc.createTextNode("Ferrari 202"));
            supercar.appendChild(carname1);

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("C:\\cars.xml"));
            transformer.transform(source, result);

            // Output to console for testing
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
