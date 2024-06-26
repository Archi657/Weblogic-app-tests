package test;

import jms.JMSClient;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import xml.ConnectionData;
import xml.XmlParser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TestOrchestrationOSM {
    static String filePath; // file with the testcases

    public static void setFilePath(String filePath) {
        TestOrchestrationOSM.filePath = filePath;
    }

    public TestOrchestrationOSM(String filePath) {
        this.filePath = filePath;
    }

    // initialize test
    public static void excecuteTest() {
        sendMessages(); // stage 1 - send messages to complete one OSM order
    }

    // send messages to initialize and finish one OSM order
    public static void sendMessages() {
        // Create an instance of xml.XmlParser
        System.out.println("Starting tests");
        XmlParser xmlParser = new XmlParser(TestOrchestrationOSM.filePath);
        System.out.println("Tests file from: " + TestOrchestrationOSM.filePath);
        // Get all test cases
        NodeList testCases = xmlParser.getTestCases();

        // Retrieve all connections
        ArrayList<ConnectionData> connections = xmlParser.getAllConnections();

        // Iterate over connections
        System.out.println("___________________ Tests Started ________________________________");
        for (int i = 0; i < connections.size(); i++) {
            ConnectionData connection = connections.get(i);
            System.out.println("___________________ Connection [" + Integer.toString(i+1) + "/" + connections.size() + "] _____________________________");
            System.out.println(connection.getName());

            // Iterate over test cases
            for (int j = 0; j < testCases.getLength(); j++) {
                Element testCase = (Element) testCases.item(j); // testcase node used to get info from every specific testcase

                // Get test information
                String testName = xmlParser.getTestName(testCase); //testcase name example : Payment of devices
                String testDescription = xmlParser.getTestDescription(testCase); // description of this usecase
                String testOrderIdExample = xmlParser.getTestOrderIdExample(testCase); // OrderId of the order used to compare
                String testCreationFile = xmlParser.getTestCreationFile(testCase); // File used to create the order

                System.out.println("___________________ Test ["+ Integer.toString(j+1) +"/" + Integer.toString(testCases.getLength())+"] ___________________________________");
                System.out.println("Test Name: " + testName);
                System.out.println("Test Description: " + testDescription);
                System.out.println("Test Order ID Example: " + testOrderIdExample);
                System.out.println("Test Creation File: " + testCreationFile);

                // OrderId of the new order that will be created from the XML + now()
                // OrderId_year_month_day_hour_minutes_seconds example : testcase1_2024_05_01_12_43_22
                String testOrderId = xmlParser.getTestOrderIdTest(testCase);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MMM_dd_HH_mm_ss");
                Date date = new Date();
                testOrderId += "_" + (formatter.format(date));
                System.out.println("Test Order ID: " + testOrderId);
                // Get SOMs for this testcase
                NodeList soms = xmlParser.getSoms(testCase);

                // create order
                String queueCreation = xmlParser.getTestCreationQueue(testCase);
                JMSClient.sendMessage(
                        testOrderId,
                        connection.getWeblogicJmsUrl(),
                        connection.getWeblogicJndiFactoryName(),
                        connection.getConnectionFactoryJndiName(),
                        connection.getUser(),
                        connection.getPassword(),
                        queueCreation);
                // Iterate over SOMs
                for (int k = 0; k < soms.getLength(); k++) {
                    Element som = (Element) soms.item(k);

                    // Get SOM information
                    String somName = xmlParser.getSomName(som);
                    System.out.println("SOM Name: " + somName);
                    String queue = xmlParser.getSomQueue(som);

                    // Get interactions for this SOM
                    NodeList interactions = xmlParser.getSomInteractions(som);
                    // Send all interactions per SOM, to be sended through sendInteractions()
                    MessageQueueProcessor messageQueueProcessor = new MessageQueueProcessor();
                    messageQueueProcessor.sendInteractions(
                            somName,
                            testOrderId,
                            connection.getWeblogicJmsUrl(),
                            connection.getWeblogicJndiFactoryName(),
                            connection.getConnectionFactoryJndiName(),
                            connection.getUser(),
                            connection.getPassword(),
                            queue,
                            xmlParser,
                            interactions);
                }
                // stage 2 - check the messages created by the order and compare with expected output
                compareResults();
                System.out.println("___________________ Test ["+ Integer.toString(j+1) +"/" + Integer.toString(testCases.getLength())+"] Finished __________________________");
            }
        }

        System.out.println("___________________ Tests Finished _______________________________ ");

    }

    public static void compareResults() {
        System.out.println("comparing results");
    }

}
