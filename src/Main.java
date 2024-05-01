import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Main {
    public static void main(String[] args) {
        // Specify the path to your XML file
        String filePath = "src/resources/tests.xml";

        // Create an instance of XmlParser
        XmlParser xmlParser = new XmlParser(filePath);

        // Get all test cases
        NodeList testCases = xmlParser.getTestCases();

        // Iterate over test cases
        for (int i = 0; i < testCases.getLength(); i++) {
            Element testCase = (Element) testCases.item(i);

            // Get test information
            String testName = xmlParser.getTestName(testCase);
            String testDescription = xmlParser.getTestDescription(testCase);
            String testOrderId = xmlParser.getTestOrderId(testCase);
            String testCreationFile = xmlParser.getTestCreationFile(testCase);

            // Print or do something with the test information
            System.out.println("Test Name: " + testName);
            System.out.println("Test Description: " + testDescription);
            System.out.println("Test Order ID: " + testOrderId);
            System.out.println("Test Creation File: " + testCreationFile);

            // Get SOMs for this testcase
            NodeList soms = xmlParser.getSoms(testCase);

            // Iterate over SOMs
            for (int j = 0; j < soms.getLength(); j++) {
                Element som = (Element) soms.item(j);

                // Get SOM information
                String somName = xmlParser.getSomName(som);
                System.out.println("SOM Name: " + somName);

                // Get interactions for this SOM
                NodeList interactions = xmlParser.getSomInteractions(som);

                // Iterate over interactions
                for (int k = 0; k < interactions.getLength(); k++) {
                    Element interaction = (Element) interactions.item(k);

                    // Get Interaction information
                    String interactionName = xmlParser.getInteractionName(interaction);
                    String interactionMessage = xmlParser.getInteractionMessage(interaction);
                    System.out.println("Interaction Name: " + interactionName);
                    System.out.println("Interaction Message: " + interactionMessage);
                }
            }
        }
    }
}
