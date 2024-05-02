package xml;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;

public class XmlParser {
    private String file;

    public XmlParser(String file) {
        this.file = file;
    }

    public NodeList getTestCases() {
        return getNodeListByTagName("testcase");
    }

    public String getTestName(Element testCase) {
        return getTagValue(testCase, "name");
    }

    public String getTestDescription(Element testCase) {
        return getTagValue(testCase, "description");
    }

    public String getTestOrderIdExample(Element testCase) {
        return getTagValue(testCase, "orderIdExample");
    }

    public String getTestOrderIdTest(Element testCase) {
        return getTagValue(testCase, "orderIdTest");
    }

    public String getTestCreationFile(Element testCase) {
        return getTagValue(testCase, "createOrderFile");
    }

    public String getTestCreationQueue(Element testCase) {
        return getTagValue(testCase, "createOrderQueue");
    }

    public NodeList getSoms(Element testCase) {
        return testCase.getElementsByTagName("som");
    }

    public String getSomName(Element som) {
        return getTagValue(som, "name");
    }

    public String getSomQueue(Element som) {
        return getTagValue(som, "queue");
    }

    public NodeList getSomInteractions(Element som) {
        return som.getElementsByTagName("interaction");
    }

    public String getInteractionName(Element interaction) {
        return getTagValue(interaction, "name");
    }

    public String getInteractionMessage(Element interaction) {
        return getTagValue(interaction, "message");
    }

    // New method to retrieve all connection objects
    public ArrayList<ConnectionData> getAllConnections() {
        ArrayList<ConnectionData> connectionsList = new ArrayList<>();
        NodeList connectionNodes = getNodeListByTagName("connection");
        if (connectionNodes != null) {
            for (int i = 0; i < connectionNodes.getLength(); i++) {
                Element connectionElement = (Element) connectionNodes.item(i);
                try {
                    connectionsList.add(createConnectionData(connectionElement));
                } catch (Exception e) {
                    System.err.println("Error creating ConnectionData object: " + e.getMessage());
                }
            }
        } else {
            System.err.println("No connection nodes found in the XML file.");
        }
        return connectionsList;
    }

    // Helper method to parse XML and get NodeList by tag name
    private NodeList getNodeListByTagName(String tagName) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(file));
            return doc.getElementsByTagName(tagName);
        } catch (Exception e) {
            System.err.println("Error parsing XML file: " + e.getMessage());
            return null;
        }
    }

    // Helper method to get tag value
    // Helper method to get tag value
    private String getTagValue(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName).item(0).getChildNodes();
        if (nodeList != null && nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            return node.getNodeValue();
        } else {
            return ""; // Return empty string if tag value is not found or empty
        }
    }


    // Helper method to create ConnectionData object
    private ConnectionData createConnectionData(Element connectionElement) throws Exception {
        String name = getTagValue(connectionElement, "name");
        String weblogicJmsUrl = getTagValue(connectionElement, "weblogic_jms_url");
        String weblogicJndiFactoryName = getTagValue(connectionElement, "weblogic_jndi_factory_name");
        String connectionFactoryJndiName = getTagValue(connectionElement, "connection_factory_jndi_name");
        String user = getTagValue(connectionElement, "user");
        String password = getTagValue(connectionElement, "password");

        // Check if any required field is empty
        if (name.isEmpty() || weblogicJmsUrl.isEmpty() || weblogicJndiFactoryName.isEmpty() ||
                connectionFactoryJndiName.isEmpty() || user.isEmpty() || password.isEmpty()) {
            throw new Exception("One or more required fields are missing.");
        }

        return new ConnectionData(name, weblogicJmsUrl, weblogicJndiFactoryName, connectionFactoryJndiName, user, password);
    }
}
