
// Import libraries
import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.util.Scanner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;


public class Main {

    private static int recordCounter = 1;

    public static void main(String[] args) {
        System.out.println("=============================================================================Welcome to MediGuard=============================================================================");

        // Attribute
        String name;
        String password;
        String userType;

        // Create a Scanner object
        Scanner input = new Scanner(System.in);



        System.out.print("Please Enter Your Name: ");
        name = input.nextLine();

        System.out.print("Please Enter Your Password: ");
        password = input.nextLine();

        System.out.print("Please Enter Your User Type: ");
        userType = input.nextLine();



        try {
            // Create a new Document
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            // Root element
            Element rootElement = doc.createElement("data");
            doc.appendChild(rootElement);

            // data entry
            String hashedPassword = hashPasswordMD5(password);
            Element recordElement = createRecordElement(doc,name,hashedPassword,userType);
            rootElement.appendChild(recordElement);

            // Write the content into XML file
            writeToFile(doc, "UserDetails.xml");

            System.out.println("Data written to XML successfully!");

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
    }

    private static Element createRecordElement(Document doc, String name, String password, String userType) {
        // Create a new record element with a generated ID
        Element recordElement = doc.createElement("record");
        recordElement.setAttribute("id", generateUniqueId());

        Element nameElement = doc.createElement("name");
        nameElement.appendChild(doc.createTextNode(name));
        recordElement.appendChild(nameElement);

        Element passwordElement = doc.createElement("password");
        passwordElement.appendChild(doc.createTextNode(password));
        recordElement.appendChild(passwordElement);

        Element userTypeElement = doc.createElement("userType");
        userTypeElement.appendChild(doc.createTextNode(userType));
        recordElement.appendChild(userTypeElement);

        // Increment the counter for the next record
        recordCounter++;

        return recordElement;
    }

    private static String generateUniqueId() {
        // Use a combination of UUID and record counter for unique IDs
        return UUID.randomUUID().toString() + "_" + recordCounter;
    }

    private static void writeToFile(Document document, String fileName) {
        try {
            // Use a Transformer to write the XML document to a file
            javax.xml.transform.TransformerFactory transformerFactory = javax.xml.transform.TransformerFactory.newInstance();
            javax.xml.transform.Transformer transformer = transformerFactory.newTransformer();
            javax.xml.transform.dom.DOMSource source = new javax.xml.transform.dom.DOMSource(document);

            File file = new File(fileName);
            FileOutputStream outputStream = new FileOutputStream(file);
            javax.xml.transform.stream.StreamResult result = new javax.xml.transform.stream.StreamResult(outputStream);

            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static String hashPasswordMD5(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashInBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

        // Convert the byte array to a hexadecimal string representation
        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }
}