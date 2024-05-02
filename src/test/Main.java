package test;

public class Main {
    public static void main(String[] args) {

        String filePath = "src/resources/tests.xml"; // tests will be based on this file
        TestOrchestrationOSM.setFilePath(filePath); // asign file to the tests
        TestOrchestrationOSM.excecuteTest(); // execute

        // you could add a new file
        // and call a new function of test.TestOrchestrationOSM similar to executeTest() if the logic is different

    }
}
