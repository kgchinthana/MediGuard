
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
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import java.util.Scanner;
import java.security.NoSuchAlgorithmException;


public class Main {

    private static int recordCounter = 1;

    public static void main(String[] args) {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");



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
                        + "6 - Generate lab test report\n"
                        + "7 - Exit");

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
                        String userCode;
                        String age;
                        String address;

                        // Create a Scanner object
                        Scanner input = new Scanner(System.in);


                        System.out.print("Please Enter Your Name: ");
                        name = input.nextLine();

                        System.out.print("Please Enter Your Password: ");
                        password = input.nextLine();

                        System.out.print("Please Enter Your User Type (patient, doctor, nurse): ");
                        userType = input.nextLine();


                        try {
                            // Create a new Document
                            File file = new File("config.xml");
                            File file1 = new File("data.xml");

                            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                            Document doc;

                            if (file.exists()) {
                                // If file exists, load existing content
                                doc = docBuilder.parse(file);
                                doc = docBuilder.parse(file1);
                            } else {
                                // If file doesn't exist, create a new document
                                doc = docBuilder.newDocument();
                                Element rootElement = doc.createElement("Details");
                                doc.appendChild(rootElement);
                                Element recordElement = doc.createElement("adminDetails");
                                recordElement.setAttribute("id", generateUniqueId());

                                HashPasswordMD5 hashedPasswordMD = new HashPasswordMD5();
                                hashedPasswordMD.setPassword("adminMediGuard");
                                String hashedPassword = hashedPasswordMD.getHashPassword();

                                Element nameElement = doc.createElement("name");
                                nameElement.appendChild(doc.createTextNode("Kavindu Chinthana"));
                                recordElement.appendChild(nameElement);

                                Element passwordElement = doc.createElement("password");
                                passwordElement.appendChild(doc.createTextNode(hashedPassword));
                                recordElement.appendChild(passwordElement);

                                Element userTypeElement = doc.createElement("userType");
                                userTypeElement.appendChild(doc.createTextNode("Admin"));
                                recordElement.appendChild(userTypeElement);

                                Element userPrivilegeElement = doc.createElement("userPrivilegeLevel");
                                userPrivilegeElement.appendChild(doc.createTextNode("Level 1"));
                                recordElement.appendChild(userPrivilegeElement);

                                doc.getDocumentElement().appendChild(recordElement);


                                // Write the content into XML file
                                WriteFile write = new WriteFile();
                                write.writeToFile(doc, file);

                            }


                            // data entry
                            HashPasswordMD5 hashedPasswordMD = new HashPasswordMD5();
                            hashedPasswordMD.setPassword(password);
                            String hashedPassword = hashedPasswordMD.getHashPassword();
                            Element recordElement = doc.createElement("personalDetails");
                            recordElement.setAttribute("id", generateUniqueId());



                            Element nameElement = doc.createElement("name");
                            nameElement.appendChild(doc.createTextNode(name));
                            recordElement.appendChild(nameElement);

                            Element passwordElement = doc.createElement("password");
                            passwordElement.appendChild(doc.createTextNode(hashedPassword));
                            recordElement.appendChild(passwordElement);



                            if(userType.equalsIgnoreCase("patient")) {
                                System.out.print("Please Enter Your Age: ");
                                age = input.nextLine();

                                System.out.print("Please Enter Your Address: ");
                                address = input.nextLine();

                                Element userTypeElement = doc.createElement("userType");
                                userTypeElement.appendChild(doc.createTextNode(userType));
                                recordElement.appendChild(userTypeElement);

                                Element ageElement = doc.createElement("age");
                                ageElement.appendChild(doc.createTextNode(age));
                                recordElement.appendChild(ageElement);

                                Element addressElement = doc.createElement("address");
                                addressElement.appendChild(doc.createTextNode(address));
                                recordElement.appendChild(addressElement);

                                Element userPrivilegeElement = doc.createElement("userPrivilegeLevel");
                                userPrivilegeElement.appendChild(doc.createTextNode("Level 4"));
                                recordElement.appendChild(userPrivilegeElement);
                            } else if (userType.equalsIgnoreCase("doctor")) {

                                System.out.print("Please Enter Your User Code: ");
                                userCode = input.nextLine();
                                if(userCode.equals("DocMediGuard")){

                                    Element userTypeElement = doc.createElement("userType");
                                    userTypeElement.appendChild(doc.createTextNode(userType));
                                    recordElement.appendChild(userTypeElement);

                                    Element userPrivilegeElement = doc.createElement("userPrivilegeLevel");
                                    userPrivilegeElement.appendChild(doc.createTextNode("Level 2"));
                                    recordElement.appendChild(userPrivilegeElement);
                                }else {
                                    System.out.println("Invalid Code");
                                    System.exit(0);
                                }
                            } else if (userType.equalsIgnoreCase("nurse")) {
                                System.out.print("Please Enter Your User Code: ");
                                userCode = input.nextLine();
                                if(userCode.equals("NurseMediGuard")){

                                    Element userTypeElement = doc.createElement("userType");
                                    userTypeElement.appendChild(doc.createTextNode(userType));
                                    recordElement.appendChild(userTypeElement);

                                    Element userPrivilegeElement = doc.createElement("userPrivilegeLevel");
                                    userPrivilegeElement.appendChild(doc.createTextNode("Level 3"));
                                    recordElement.appendChild(userPrivilegeElement);
                                }else {
                                    System.out.println("Invalid Code");
                                    System.exit(0);
                                }
                            }else {
                                System.out.println("Invalid User Type");
                                System.exit(0);
                            }


                            // Increment the counter for the next record
                            recordCounter++;
                            doc.getDocumentElement().appendChild(recordElement);


                            // Write the content into XML file
                            WriteFile write = new WriteFile();
                            write.writeToFile(doc, file);
                            write.writeToFile(doc,file1);

                            System.out.println("Registration successfully!");

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


                            if (mediclStaffLogin()) {
                                System.out.print("Please Enter Patient Name: ");
                                String patientName1 = userInput.nextLine();

                                File file1 = new File("data.xml");

                                DocumentBuilderFactory factory1 = DocumentBuilderFactory.newInstance();
                                DocumentBuilder builder1 = factory1.newDocumentBuilder();
                                Document doc1 = builder1.parse(file1);


                                Element rootElement1 = doc1.getDocumentElement();


                                NodeList recordList1 = rootElement1.getElementsByTagName("personalDetails");


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
                                            System.out.print("Enter sickness details: ");
                                            String sicknessDetails = userInput.nextLine();
                                            System.out.print("Severity (mild, moderate or severe): ");
                                            String severity = userInput.nextLine();
                                            System.out.print("Type ( flu, cold, allergy): ");
                                            String type = userInput.nextLine();
                                            LocalDateTime now = LocalDateTime.now();


                                            // Create sicknessDetails element
                                            NodeList sicknessDetailsList = recordElement1.getElementsByTagName("sicknessDetails");
                                            Element sicknessDetailsElement;

                                            if (sicknessDetailsList.getLength() > 0) {
                                                // If sicknessDetails element exists, use the first one
                                                sicknessDetailsElement = (Element) sicknessDetailsList.item(0);
                                            } else {
                                                // If sicknessDetails element doesn't exist, create a new one
                                                sicknessDetailsElement = doc1.createElement("sicknessDetails");
                                                recordElement1.appendChild(sicknessDetailsElement);
                                            }
                                            Element sicknessElement = doc1.createElement("sickness");

                                            Element detailsElement = doc1.createElement("details");
                                            detailsElement.appendChild(doc1.createTextNode(sicknessDetails));
                                            sicknessElement.appendChild(detailsElement);

                                            Element severityElement = doc1.createElement("severity");
                                            severityElement.appendChild(doc1.createTextNode(severity));
                                            sicknessElement.appendChild(severityElement);

                                            Element typeElement = doc1.createElement("type");
                                            typeElement.appendChild(doc1.createTextNode(type));
                                            sicknessElement.appendChild(typeElement);

                                            Element dateElement = doc1.createElement("date");
                                            dateElement.appendChild(doc1.createTextNode(dtf.format(now)));
                                            sicknessElement.appendChild(dateElement);

                                            sicknessDetailsElement.appendChild(sicknessElement);

                                            // Append sicknessDetails to the existing record
                                            recordElement1.appendChild(sicknessDetailsElement);

                                            // Write the content into XML file
                                            WriteFile write = new WriteFile();
                                            write.writeToFile(doc1, file1);

                                            System.out.println("Sickness details added to the existing record successfully!");
                                            break;
                                        }
                                    }
                                }
                            }


                        } catch (NullPointerException e) {
                            System.out.println("No such a person exists");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 3:
                        try {
                            Scanner userInput = new Scanner(System.in);


                            if (mediclStaffLogin()) {
                                System.out.print("Please Enter Patient Name: ");
                                String patientName1 = userInput.nextLine();

                                File file1 = new File("data.xml");

                                DocumentBuilderFactory factory1 = DocumentBuilderFactory.newInstance();
                                DocumentBuilder builder1 = factory1.newDocumentBuilder();
                                Document doc1 = builder1.parse(file1);


                                Element rootElement1 = doc1.getDocumentElement();


                                NodeList recordList1 = rootElement1.getElementsByTagName("personalDetails");


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
                                            String userAge1 = getElementValue(recordElement1, "age");

                                            NodeList sicknessDetailsList = recordElement1.getElementsByTagName("sicknessDetails");
                                            if (sicknessDetailsList.getLength() > 0) {
                                                Element sicknessDetailsElement = (Element) sicknessDetailsList.item(0);


                                                System.out.println("Sickness Details for " + patientName1 + " Age " + userAge1 + ":");
                                                for (int k = 0; k < sicknessDetailsList.getLength(); k++) {
                                                    NodeList sicknessList = sicknessDetailsElement.getElementsByTagName("sickness");
                                                    Element sicknessElement = (Element) sicknessList.item(j);
                                                    String details = getElementValue(sicknessElement, "details");
                                                    String severity = getElementValue(sicknessElement, "severity");
                                                    String type = getElementValue(sicknessElement, "type");

                                                    System.out.println("Details: " + details);
                                                    System.out.println("Severity: " + severity);
                                                    System.out.println("Type: " + type);
                                                    System.out.println("========================================================================================================================================================================================");
                                                }
                                            }
                                        }
                                    } else {
                                        System.out.println("No sickness details found for " + patientName1);
                                    }
                                    break;
                                }
                                break;


                            }


                        }catch (NullPointerException e) {
                            System.out.println("No such a person exists");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;

                    case 4:
                        try {
                            Scanner userInput = new Scanner(System.in);


                            if (mediclStaffLogin()) {
                                System.out.print("Please Enter patient Name: ");
                                String patientName = userInput.nextLine();
                                System.out.print("Please Enter Suitable Drugs for patient: ");
                                String drugs = userInput.nextLine();

                                File file1 = new File("data.xml");

                                DocumentBuilderFactory factory1 = DocumentBuilderFactory.newInstance();
                                DocumentBuilder builder1 = factory1.newDocumentBuilder();
                                Document doc1 = builder1.parse(file1);

                                // Get the root element
                                Element rootElement1 = doc1.getDocumentElement();

                                // Get all records
                                NodeList recordList1 = rootElement1.getElementsByTagName("personalDetails");


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
                                            LocalDateTime now = LocalDateTime.now();


                                            NodeList drugsDetailsList = recordElement1.getElementsByTagName("drugPrescriptions");
                                            Element drugsDetailsElement;

                                            if (drugsDetailsList.getLength() > 0) {
                                                // If drugsDetails element exists, use the first one
                                                drugsDetailsElement = (Element) drugsDetailsList.item(0);
                                            } else {
                                                // If drugsDetails element doesn't exist, create a new one
                                                drugsDetailsElement = doc1.createElement("drugPrescriptions");
                                                recordElement1.appendChild(drugsDetailsElement);
                                            }
                                            Element drugsElement = doc1.createElement("drugs");

                                            Element detailsElement = doc1.createElement("details");
                                            detailsElement.appendChild(doc1.createTextNode(drugs));
                                            drugsElement.appendChild(detailsElement);

                                            Element dateElement = doc1.createElement("date");
                                            dateElement.appendChild(doc1.createTextNode(dtf.format(now)));
                                            drugsElement.appendChild(dateElement);

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
                                System.out.println("You are not a medical staff member");
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;
                    case 5:
                        try {
                            Scanner userInput = new Scanner(System.in);


                            if (mediclStaffLogin()) {
                                System.out.print("Please Enter Patient Name: ");
                                String patientName1 = userInput.nextLine();

                                File file1 = new File("data.xml");

                                DocumentBuilderFactory factory1 = DocumentBuilderFactory.newInstance();
                                DocumentBuilder builder1 = factory1.newDocumentBuilder();
                                Document doc1 = builder1.parse(file1);


                                Element rootElement1 = doc1.getDocumentElement();


                                NodeList recordList1 = rootElement1.getElementsByTagName("personalDetails");


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
                                            NodeList drugsDetailsList = recordElement1.getElementsByTagName("drugPrescriptions");
                                            if (drugsDetailsList.getLength() > 0) {
                                                Element drugsDetailsElement = (Element) drugsDetailsList.item(0);
                                                System.out.println("========================================================================================================================================================================================");

                                                for (int k = 0; k < drugsDetailsList.getLength(); k++) {
                                                    NodeList drugsList = drugsDetailsElement.getElementsByTagName("drugs");

                                                    Element drugsElement = (Element) drugsList.item(k);
                                                    String details = getElementValue(drugsElement, "details");

                                                    System.out.println("Drugs Details " + patientName1 + ": " + details);
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
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 6:
                        try {
                            Scanner userInput = new Scanner(System.in);

                            if (mediclStaffLogin() || patientLogin()) {
                                System.out.print("Please Enter patient Name: ");
                                String patientName = userInput.nextLine();
                                System.out.print("Please Enter Report Details of patient: ");
                                String drugs = userInput.nextLine();

                                File file1 = new File("data.xml");

                                DocumentBuilderFactory factory1 = DocumentBuilderFactory.newInstance();
                                DocumentBuilder builder1 = factory1.newDocumentBuilder();
                                Document doc1 = builder1.parse(file1);

                                // Get the root element
                                Element rootElement1 = doc1.getDocumentElement();

                                // Get all records
                                NodeList recordList1 = rootElement1.getElementsByTagName("personalDetails");


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
                                            LocalDateTime now = LocalDateTime.now();


                                            NodeList drugsDetailsList = recordElement1.getElementsByTagName("labTestPrescriptions");
                                            Element drugsDetailsElement;

                                            if (drugsDetailsList.getLength() > 0) {
                                                // If drugsDetails element exists, use the first one
                                                drugsDetailsElement = (Element) drugsDetailsList.item(0);
                                            } else {
                                                // If drugsDetails element doesn't exist, create a new one
                                                drugsDetailsElement = doc1.createElement("labTestPrescriptions");
                                                recordElement1.appendChild(drugsDetailsElement);
                                            }
                                            Element drugsElement = doc1.createElement("test");

                                            Element detailsElement = doc1.createElement("details");
                                            detailsElement.appendChild(doc1.createTextNode(drugs));
                                            drugsElement.appendChild(detailsElement);

                                            Element dateElement = doc1.createElement("date");
                                            dateElement.appendChild(doc1.createTextNode(dtf.format(now)));
                                            drugsElement.appendChild(dateElement);

                                            drugsDetailsElement.appendChild(drugsElement);

                                            // Append drugsDetails to the existing record
                                            recordElement1.appendChild(drugsDetailsElement);

                                            // Write the content into XML file
                                            WriteFile write = new WriteFile();
                                            write.writeToFile(doc1, file1);

                                            System.out.println("Report details added to the existing record successfully!");
                                            break; // Stop iterating once the record is found and updated
                                        }
                                    }
                                }
                            } else {
                                System.out.println("You are not a medical staff member");
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 7:
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

    private static boolean patientLogin(){
        boolean condition = false;
        try {
            Scanner userInput = new Scanner(System.in);
            System.out.print("Please Enter Your Name: ");
            String personName = userInput.nextLine();
            System.out.print("Please Enter Your Password: ");
            String personPassword = userInput.nextLine();

            File file = new File("config.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);

            // Get the root element
            Element rootElement = doc.getDocumentElement();


            // Get all records
            NodeList recordList = rootElement.getElementsByTagName("personalDetails");

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
                    String userPrivilegeLevel = getElementValue(recordElement, "userPrivilegeLevel");


                    HashPasswordMD5 hashedPasswordMD = new HashPasswordMD5();
                    hashedPasswordMD.setPassword(personPassword);
                    String hashedPassword = hashedPasswordMD.getHashPassword();

                    if (userName.contains(personName) && userPrivilegeLevel.equalsIgnoreCase("Level 4") && hashedPassword.equals(userPassword)) {
                        condition = true;
                    }


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return condition;


    }

    private static boolean mediclStaffLogin(){
        boolean condition = false;
        try {
            Scanner userInput = new Scanner(System.in);
            System.out.print("Please Enter Your Name: ");
            String personName = userInput.nextLine();
            System.out.print("Please Enter Your Password: ");
            String personPassword = userInput.nextLine();

            File file = new File("config.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);

            // Get the root element
            Element rootElement = doc.getDocumentElement();


            // Get all records
            NodeList recordList = rootElement.getElementsByTagName("personalDetails");

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
                    String userPrivilegeLevel = getElementValue(recordElement, "userPrivilegeLevel");


                    HashPasswordMD5 hashedPasswordMD = new HashPasswordMD5();
                    hashedPasswordMD.setPassword(personPassword);
                    String hashedPassword = hashedPasswordMD.getHashPassword();

                    if (userName.contains(personName) && (userPrivilegeLevel.equalsIgnoreCase("Level 2") || userPrivilegeLevel.equalsIgnoreCase("Level 3")) && hashedPassword.equals(userPassword)) {
                        condition = true;
                    }


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return condition;


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