import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class MessageQueueProcessor {
    void sendInteractions(String name, String queue, XmlParser xmlParser, NodeList interactions) {
        System.out.println("------------------- Sending messages for " + name + "------------------- ");
        System.out.println("The queue is " + queue);
        for (int i = 0; i < interactions.getLength(); i++) {
            Element interaction = (Element) interactions.item(i);
            System.out.println("Interaction #" + Integer.toString(i) );
            String interactionName = xmlParser.getInteractionName(interaction);
            String interactionMessage = xmlParser.getInteractionMessage(interaction);
            System.out.println("Interaction Name: " + interactionName);
            System.out.println("Interaction File: " + interactionMessage);
            System.out.println("call function of send message()...");
        }
    }

    private void sendMessage(String message, String queue){

    }
}
