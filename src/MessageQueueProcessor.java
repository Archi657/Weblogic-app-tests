import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class MessageQueueProcessor {
    private void sendMessage(String name, String queue,  XmlParser xmlParser, NodeList interactions) {
        for (int i = 0; i < interactions.getLength(); i++) {
            Element interaction = (Element) interactions.item(i);
            System.out.println("Interaction " + Integer.toString(i) );
            String interactionName = xmlParser.getInteractionName(interaction);
            String interactionMessage = xmlParser.getInteractionMessage(interaction);
            System.out.println("Interaction Name: " + interactionName);
            System.out.println("Interaction File: " + interactionMessage);
        }
    }
}
