
// Import libraries
import java.io.IOException;
import java.util.UUID;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.util.Scanner;
import java.security.NoSuchAlgorithmException;


public class Main {

    private static int recordCounter = 1;

    public static void main(String[] args) {
        while (true) {

            Scanner scanner = new Scanner(System.in);

            System.out.println('\n' + "========================================================================================================================================================================================");
            System.out.println("===============================================================================   Welcome to MediGuard    ==============================================================================");
            System.out.println("========================================================================================================================================================================================" + '\n');


            System.out.println("Enter option type:\n"
                    + "1 - Add new medical record\n"
                    + "2 - View patient details\n"
                    + "3 - Generate prescription\n"
                    + "4 - View treatment history\n"
                    + "5 - Exit");

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
                        File file = new File("UserDetails.xml");

                        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                        Document doc ;

                        if (file.exists()) {
                            // If file exists, load existing content
                            doc = docBuilder.parse(file);
                        } else {
                            // If file doesn't exist, create a new document
                            doc = docBuilder.newDocument();
                            Element rootElement = doc.createElement("data");
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
                        passwordElement.appendChild(doc.createTextNode(password));
                        recordElement.appendChild(passwordElement);

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
                    // Code to view patient details
                    break;
                case 3:
                    // Code to generate prescription
                    break;
                case 4:
                    // Code to view treatment history
                    break;
                case 5:
                    System.out.println("Exiting MediGuard. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
                    break;
            }
        }
    }


    private static String generateUniqueId () {
        // Use a combination of UUID and record counter for unique IDs
        return UUID.randomUUID().toString() + "_" + recordCounter;
    }





}