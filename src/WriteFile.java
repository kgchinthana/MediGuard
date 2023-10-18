import org.w3c.dom.Document;

// Import libraries
import java.io.File;
import java.io.FileOutputStream;

public class WriteFile {
    public void writeToFile (Document document, File fileName) {
        try {
            // Use a Transformer to write the XML document to the file
            javax.xml.transform.TransformerFactory transformerFactory = javax.xml.transform.TransformerFactory.newInstance();
            javax.xml.transform.Transformer transformer = transformerFactory.newTransformer();
            javax.xml.transform.dom.DOMSource source = new javax.xml.transform.dom.DOMSource(document);

            FileOutputStream outputStream = new FileOutputStream(fileName);
            javax.xml.transform.stream.StreamResult result = new javax.xml.transform.stream.StreamResult(outputStream);

            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
