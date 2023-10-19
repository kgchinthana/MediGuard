
// Import libraries
import java.io.IOException;
import java.util.UUID;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.util.Scanner;
import java.security.NoSuchAlgorithmException;


public class Main {

    private static int recordCounter = 1;

    public static void main(String[] args) {
        try {


            while (true) {

                Scanner scanner = new Scanner(System.in);

                System.out.println('\n' + "========================================================================================================================================================================================");
                System.out.println("===============================================================================   Welcome to MediGuard    ==============================================================================");
                System.out.println("========================================================================================================================================================================================" + '\n');


                System.out.println("Enter option type:\n"
                        + "1 - User Registration\n"
                        + "2 - Add new medical record\n"
                        + "3 - View patient details\n"
                        + "4 - Generate prescription\n"
                        + "5 - View treatment history\n"
                        + "6 - Exit");

                System.out.println("========================================================================================================================================================================================");
                System.out.print("Enter your choice: ");
                int option = scanner.nextInt();
                System.out.println("========================================================================================================================================================================================");


                switch (option) {
                    case 1:

                        // Attribute
                        String name;
                        String password;
                        String userType;
                        String age;
                        String address;

                        // Create a Scanner object
                        Scanner input = new Scanner(System.in);


                        System.out.print("Please Enter Your Name: ");
                        name = input.nextLine();

                        System.out.print("Please Enter Your Password: ");
                        password = input.nextLine();

                        System.out.print("Please Enter Your Age: ");
                        age = input.nextLine();

                        System.out.print("Please Enter Your Address: ");
                        address = input.nextLine();

                        System.out.print("Please Enter Your User Type: ");
                        userType = input.nextLine();
                        try {
                            // Create a new Document
                            File file = new File("Database.xml");

                            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                            Document doc;

                            if (file.exists()) {
                                // If file exists, load existing content
                                doc = docBuilder.parse(file);
                            } else {
                                // If file doesn't exist, create a new document
                                doc = docBuilder.newDocument();
                                Element rootElement = doc.createElement("personalDetails");
                                doc.appendChild(rootElement);
                            }


                            // data entry
                            HashPasswordMD5 hashedPasswordMD = new HashPasswordMD5();
                            hashedPasswordMD.setPassword(password);
                            String hashedPassword = hashedPasswordMD.getHashPassword();
                            Element recordElement = doc.createElement("record");
                            recordElement.setAttribute("id", generateUniqueId());

                            Element nameElement = doc.createElement("name");
                            nameElement.appendChild(doc.createTextNode(name));
                            recordElement.appendChild(nameElement);

                            Element passwordElement = doc.createElement("password");
                            passwordElement.appendChild(doc.createTextNode(hashedPassword));
                            recordElement.appendChild(passwordElement);

                            Element ageElement = doc.createElement("age");
                            ageElement.appendChild(doc.createTextNode(age));
                            recordElement.appendChild(ageElement);

                            Element addressElement = doc.createElement("address");
                            addressElement.appendChild(doc.createTextNode(address));
                            recordElement.appendChild(addressElement);

                            Element userTypeElement = doc.createElement("userType");
                            userTypeElement.appendChild(doc.createTextNode(userType));
                            recordElement.appendChild(userTypeElement);

                            // Increment the counter for the next record
                            recordCounter++;
                            doc.getDocumentElement().appendChild(recordElement);


                            // Write the content into XML file
                            WriteFile write = new WriteFile();
                            write.writeToFile(doc, file);

                            System.out.println("Data written to XML successfully!");

                        } catch (ParserConfigurationException e) {
                            e.printStackTrace();
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (SAXException e) {
                            throw new RuntimeException(e);
                        }

                        break;
                    case 2:
                        try {

                            Scanner userInput = new Scanner(System.in);


                            System.out.print("Please Enter Patient Name: ");
                            String patientName = userInput.nextLine();

                            File file = new File("Database.xml");

                            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                            DocumentBuilder builder = factory.newDocumentBuilder();
                            Document doc = builder.parse(file);

                            // Get the root element
                            Element rootElement = doc.getDocumentElement();


                            // Get all records
                            NodeList recordList = rootElement.getElementsByTagName("record");

                            // Iterate through records
                            for (int i = 0; i < recordList.getLength(); i++) {
                                Node recordNode = recordList.item(i);
                                if (recordNode.getNodeType() == Node.ELEMENT_NODE) {
                                    Element recordElement = (Element) recordNode;

                                    // Get record attributes
                                    String id = recordElement.getAttribute("id");

                                    // Get record details
                                    String userName = getElementValue(recordElement, "name");
                                    String usertype = getElementValue(recordElement, "userType");



                                    if (userName.contains(patientName) && usertype.contains("patient")) {

                                        System.out.print("Enter sickness details: ");
                                        String sicknessDetails = userInput.nextLine();
                                        System.out.print("Severity (mild, moderate or severe): ");
                                        String severity = userInput.nextLine();
                                        System.out.print("Type ( flu, cold, allergy): ");
                                        String type = userInput.nextLine();

                                        // Create sicknessDetails element
                                        Element sicknessDetailsElement = doc.createElement("sicknessDetails");
                                        Element sicknessElement = doc.createElement("sickness");

                                        Element detailsElement = doc.createElement("details");
                                        detailsElement.appendChild(doc.createTextNode(sicknessDetails));
                                        sicknessElement.appendChild(detailsElement);

                                        Element severityElement = doc.createElement("severity");
                                        severityElement.appendChild(doc.createTextNode(severity));
                                        sicknessElement.appendChild(severityElement);

                                        Element typeElement = doc.createElement("type");
                                        typeElement.appendChild(doc.createTextNode(type));
                                        sicknessElement.appendChild(typeElement);

                                        sicknessDetailsElement.appendChild(sicknessElement);

                                        // Append sicknessDetails to the existing record
                                        recordElement.appendChild(sicknessDetailsElement);

                                        // Write the content into XML file
                                        WriteFile write = new WriteFile();
                                        write.writeToFile(doc, file);

                                        System.out.println("Sickness details added to the existing record successfully!");
                                        break;


                                    }


                                }
                            }


                        }catch (NullPointerException e){
                            System.out.println("No such a person exists");
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 3:
                        try{
                            Scanner userInput = new Scanner(System.in);


                            System.out.print("Please Enter Patient Name: ");
                            String patientName = userInput.nextLine();

                            File file = new File("Database.xml");

                            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                            DocumentBuilder builder = factory.newDocumentBuilder();
                            Document doc = builder.parse(file);

                            // Get the root element
                            Element rootElement = doc.getDocumentElement();


                            // Get all records
                            NodeList recordList = rootElement.getElementsByTagName("record");

                            // Iterate through records
                            for (int i = 0; i < recordList.getLength(); i++) {
                                Node recordNode = recordList.item(i);
                                if (recordNode.getNodeType() == Node.ELEMENT_NODE) {
                                    Element recordElement = (Element) recordNode;

                                    // Get record attributes
                                    String id = recordElement.getAttribute("id");

                                    // Get record details
                                    String userName = getElementValue(recordElement, "name");
                                    String usertype = getElementValue(recordElement, "userType");



                                        if (userName.equalsIgnoreCase(patientName) && usertype.equals("patient")) {
                                            NodeList sicknessDetailsList = recordElement.getElementsByTagName("sicknessDetails");
                                            if (sicknessDetailsList.getLength() > 0) {
                                                Element sicknessDetailsElement = (Element) sicknessDetailsList.item(0);
                                                NodeList sicknessList = sicknessDetailsElement.getElementsByTagName("sickness");

                                                System.out.println("Sickness Details for " + patientName + ":");
                                                for (int j = 0; j < sicknessList.getLength(); j++) {
                                                    Element sicknessElement = (Element) sicknessList.item(j);
                                                    String details = getElementValue(sicknessElement, "details");
                                                    String severity = getElementValue(sicknessElement, "severity");
                                                    String type = getElementValue(sicknessElement, "type");

                                                    System.out.println("Details: " + details);
                                                    System.out.println("Severity: " + severity);
                                                    System.out.println("Type: " + type);
                                                    System.out.println("========================================================================================================================================================================================");
                                                }
                                            } else {
                                                System.out.println("No sickness details found for " + patientName);
                                            }
                                            break;
                                        }
                                        break;


                                    }


                                }



                        }catch (NullPointerException e){
                            System.out.println("No such a person exists");
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;

                    case 4:
                        try {
                            Scanner userInput = new Scanner(System.in);


                            System.out.print("Please Enter Your Name: ");
                            String personName = userInput.nextLine();

                            File file = new File("Database.xml");

                            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                            DocumentBuilder builder = factory.newDocumentBuilder();
                            Document doc = builder.parse(file);

                            // Get the root element
                            Element rootElement = doc.getDocumentElement();


                            // Get all records
                            NodeList recordList = rootElement.getElementsByTagName("record");

                            // Iterate through records
                            for (int i = 0; i < recordList.getLength(); i++) {
                                Node recordNode = recordList.item(i);
                                if (recordNode.getNodeType() == Node.ELEMENT_NODE) {
                                    Element recordElement = (Element) recordNode;

                                    // Get record attributes
                                    String id = recordElement.getAttribute("id");

                                    // Get record details
                                    String userName = getElementValue(recordElement, "name");
                                    String userPassword = getElementValue(recordElement, "password");
                                    String usertype = getElementValue(recordElement, "userType");

                                    if (userName.contains(personName) && usertype.contains("medical staff")) {
                                        System.out.print("Please Enter Your Password: ");
                                        String personPassword = userInput.nextLine();
                                        HashPasswordMD5 hashedPasswordMD = new HashPasswordMD5();
                                        hashedPasswordMD.setPassword(personPassword);
                                        String hashedPassword = hashedPasswordMD.getHashPassword();

                                        if (hashedPassword.equals(userPassword)) {
                                            System.out.print("Please Enter patient Name: ");
                                            String patientName = userInput.nextLine();
                                            System.out.print("Please Enter Suitable Drugs for patient: ");
                                            String drugs = userInput.nextLine();

                                            File file1 = new File("Database.xml");

                                            DocumentBuilderFactory factory1 = DocumentBuilderFactory.newInstance();
                                            DocumentBuilder builder1 = factory1.newDocumentBuilder();
                                            Document doc1 = builder1.parse(file1);

                                            // Get the root element
                                            Element rootElement1 = doc1.getDocumentElement();

                                            // Get all records
                                            NodeList recordList1 = rootElement1.getElementsByTagName("record");

                                            for (int j = 0; j < recordList1.getLength(); j++) {
                                                Node recordNode1 = recordList1.item(j);
                                                if (recordNode1.getNodeType() == Node.ELEMENT_NODE) {
                                                    Element recordElement1 = (Element) recordNode1;

                                                    // Get record attributes
                                                    String id1 = recordElement1.getAttribute("id");

                                                    // Get record details
                                                    String userName1 = getElementValue(recordElement1, "name");
                                                    String userType1 = getElementValue(recordElement1, "userType");

                                                    if (userName1.equalsIgnoreCase(patientName) && userType1.equalsIgnoreCase("patient")) {
                                                        Element drugsDetailsElement = doc1.createElement("drugsDetails");
                                                        Element drugsElement = doc1.createElement("drugs");

                                                        Element detailsElement = doc1.createElement("details");
                                                        detailsElement.appendChild(doc1.createTextNode(drugs));
                                                        drugsElement.appendChild(detailsElement);

                                                        drugsDetailsElement.appendChild(drugsElement);

                                                        // Append drugsDetails to the existing record
                                                        recordElement1.appendChild(drugsDetailsElement);

                                                        // Write the content into XML file
                                                        WriteFile write = new WriteFile();
                                                        write.writeToFile(doc1, file1);

                                                        System.out.println("Drugs details added to the existing record successfully!");
                                                        break; // Stop iterating once the record is found and updated
                                                    }
                                                }
                                            }
                                        } else {
                                            System.out.println("Incorrect Password");
                                        }





                                    }else {
                                        if(i==recordList.getLength()-1){
                                            System.out.println("You are not a medical staff person");

                                        }
                                    }
                                }
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        break;
                    case 5:
                        try {
                            Scanner userInput = new Scanner(System.in);


                            System.out.print("Please Enter Your Name: ");
                            String patientName = userInput.nextLine();
                            System.out.print("Please Enter Your Password: ");
                            String personPassword = userInput.nextLine();

                            File file = new File("Database.xml");

                            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                            DocumentBuilder builder = factory.newDocumentBuilder();
                            Document doc = builder.parse(file);

                            // Get the root element
                            Element rootElement = doc.getDocumentElement();


                            // Get all records
                            NodeList recordList = rootElement.getElementsByTagName("record");

                            // Iterate through records
                            for (int i = 0; i < recordList.getLength(); i++) {
                                Node recordNode = recordList.item(i);
                                if (recordNode.getNodeType() == Node.ELEMENT_NODE) {
                                    Element recordElement = (Element) recordNode;

                                    // Get record attributes
                                    String id = recordElement.getAttribute("id");

                                    // Get record details
                                    String userName = getElementValue(recordElement, "name");
                                    String userPassword = getElementValue(recordElement, "password");
                                    String usertype = getElementValue(recordElement, "userType");


                                    HashPasswordMD5 hashedPasswordMD = new HashPasswordMD5();
                                    hashedPasswordMD.setPassword(personPassword);
                                    String hashedPassword = hashedPasswordMD.getHashPassword();


                                    if (userName.equalsIgnoreCase(patientName) && (usertype.equals("patient") || usertype.contains("medical staff")) && hashedPassword.equals(userPassword) ) {
                                        System.out.print("Please Enter Patient Name: ");
                                        String patientName1 = userInput.nextLine();

                                        File file1 = new File("Database.xml");

                                        DocumentBuilderFactory factory1 = DocumentBuilderFactory.newInstance();
                                        DocumentBuilder builder1 = factory1.newDocumentBuilder();
                                        Document doc1 = builder1.parse(file1);

// Get the root element
                                        Element rootElement1 = doc1.getDocumentElement();

// Get all records
                                        NodeList recordList1 = rootElement1.getElementsByTagName("record");

// Iterate through records
                                        for (int j = 0; j < recordList1.getLength(); j++) {
                                            Node recordNode1 = recordList1.item(j);
                                            if (recordNode1.getNodeType() == Node.ELEMENT_NODE) {
                                                Element recordElement1 = (Element) recordNode1;

                                                // Get record attributes
                                                String id1 = recordElement1.getAttribute("id");

                                                // Get record details
                                                String userName1 = getElementValue(recordElement1, "name");
                                                String userType1 = getElementValue(recordElement1, "userType");

                                                if (userName1.equalsIgnoreCase(patientName1) && userType1.equalsIgnoreCase("patient")) {
                                                    NodeList drugsDetailsList = recordElement1.getElementsByTagName("drugsDetails");
                                                    if (drugsDetailsList.getLength() > 0) {
                                                        Element drugsDetailsElement = (Element) drugsDetailsList.item(0);
                                                        NodeList drugsList = drugsDetailsElement.getElementsByTagName("drugs");
                                                        System.out.println("========================================================================================================================================================================================");

                                                        for (int k = 0; k < drugsList.getLength(); k++) {
                                                            Element drugsElement = (Element) drugsList.item(k);
                                                            String details = getElementValue(drugsElement, "details");

                                                            System.out.println("Drugs Details "+patientName1+": " + details);
                                                            System.out.println("========================================================================================================================================================================================");
                                                        }
                                                    } else {
                                                        System.out.println("No drugs details found for " + patientName1);
                                                    }
                                                    break;
                                                }
                                            }
                                        }


                                    }
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                    case 6:
                        System.out.println("Exiting MediGuard. Goodbye!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid option. Please choose a valid option.");
                        break;
                }
            }
        }catch (Exception e){
            System.out.println("Invalid Input");
            System.exit(0);
        }
    }


    private static String generateUniqueId () {
        // Use a combination of UUID and record counter for unique IDs
        return UUID.randomUUID().toString() + "_" + recordCounter;
    }

    private static String getElementValue(Element parentElement, String elementName) {
        NodeList nodeList = parentElement.getElementsByTagName(elementName).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }





}