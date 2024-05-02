package test;

import jms.JMSClient;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import xml.XmlParser;

public class MessageQueueProcessor {
    void sendInteractions(String name,
                          String testOrderId,
                          String weblogic_jms_url,
                          String weblogic_jndi_factory_name,
                          String connection_factory_jndi_name,
                          String user,
                          String password,
                          String queue,
                          XmlParser xmlParser,
                          NodeList interactions) {
        System.out.println("------------------- Sending messages for " + name + "------------------- ");
        System.out.println("The queue is " + queue);
        for (int i = 0; i < interactions.getLength(); i++) {
            Element interaction = (Element) interactions.item(i);
            System.out.println("Interaction #" + Integer.toString(i+1) );
            String interactionName = xmlParser.getInteractionName(interaction);
            String interactionMessage = xmlParser.getInteractionMessage(interaction);
            System.out.println("Interaction Name: " + interactionName);
            System.out.println("Interaction File: " + interactionMessage);
            String mensaje = testOrderId + interactionName + interactionMessage;
            JMSClient.sendMessage(mensaje, weblogic_jms_url, weblogic_jndi_factory_name, connection_factory_jndi_name, user, password, queue);
        }
    }
}
