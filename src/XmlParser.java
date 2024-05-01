import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

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

    public String getTestOrderId(Element testCase) {
        return getTagValue(testCase, "orderId");
    }

    public String getTestCreationFile(Element testCase) {
        return getTagValue(testCase, "testOrder");
    }

    public NodeList getSoms(Element testCase) {
        return testCase.getElementsByTagName("som");
    }

    public String getSomName(Element som) {
        return getTagValue(som, "name");
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

    // Helper method to parse XML and get NodeList by tag name
    private NodeList getNodeListByTagName(String tagName) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(file));
            return doc.getElementsByTagName(tagName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Helper method to get tag value
    private String getTagValue(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }
}
