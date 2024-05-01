import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Main {
    public static void main(String[] args) {

        String filePath = "src/resources/tests.xml"; // tests will be based on this file
        TestOrchestrationOSM.setFilePath(filePath); // asign file to the tests
        TestOrchestrationOSM.excecuteTest(); // execute

        // you could add a new file
        // and call a new function of TestOrchestrationOSM similar to executeTest() if the logic is different

    }
}
