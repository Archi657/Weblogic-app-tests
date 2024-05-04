package jms;

import java.util.Hashtable;
import java.util.UUID;
import javax.naming.*; 
import javax.jms.*;

 public class JMSClient {
	 
	 private static InitialContext ctx = null;    
	 private static QueueConnectionFactory qcf = null;    
	 private static QueueConnection qc = null;    
	 private static QueueSession qsess = null;    
	 private static Queue q = null;   
	 private static QueueSender qsndr = null;    
	 private static TextMessage message = null;    
	 
	 // NOTE: The next two lines set the name of the Queue Connection Factory    //       and the Queue that we want to use.    
	 
	 //private static final String QCF_NAME = "oracle/communications/ordermanagement/osm/ExternalClientConnectionFactory";
	 //private static final String QUEUE_NAME = "receiveResponseActivateServicesEOCResponseQueue_OSM_MS1";
	 
	 public JMSClient() {        super();    }
	 
	 public static void sendMessage(
			 String messageText,
			 String weblogic_jms_url,
			 String weblogic_jndi_factory_name,
			 String connection_factory_jndi_name,
			 String user,
			 String password,
			 String queue
			 ) throws JMSException {
		 String QCF_NAME = connection_factory_jndi_name;
		 String QUEUE_NAME = queue;
		 // create InitialContext       
		 Hashtable<String, String> properties = new Hashtable<String, String>();        
		 properties.put(Context.INITIAL_CONTEXT_FACTORY,weblogic_jndi_factory_name);
		 // NOTE: The port number of the server is provided in the next line,        
		 //       followed by the userid and password on the next two lines.       
		 properties.put(Context.PROVIDER_URL, weblogic_jms_url);
		 properties.put(Context.SECURITY_PRINCIPAL, user);
		 properties.put(Context.SECURITY_CREDENTIALS, password);
		 
		 try {    
			 System.out.println("Trying InitialContext ");        
			 ctx = new InitialContext(properties);        
			 } catch (NamingException ne) 
		 {            ne.printStackTrace(System.err);           
		 System.exit(0);        }        
		 System.out.println("Got InitialContext " + ctx.toString());        
		 // create QueueConnectionFactory        
		 try {            qcf = (QueueConnectionFactory)ctx.lookup(QCF_NAME);
		 }        catch (NamingException ne) 
		 {            ne.printStackTrace(System.err);            
		 System.exit(0);        }        
		 System.out.println("Got QueueConnectionFactory " + qcf.toString());      
		 
		 // create QueueConnection       
		 try {            
			 qc = qcf.createQueueConnection();       
			 }        catch (JMSException jmse)
		 {            jmse.printStackTrace(System.err);            
		 System.exit(0);        }       
		 System.out.println("Got QueueConnection " + qc.toString());        
		 
		 // create QueueSession        
		 try {            qsess = qc.createQueueSession(false, 0);        
		 }        catch (JMSException jmse) 
		 {            jmse.printStackTrace(System.err);           
		 System.exit(0);        }        
		 System.out.println("Got QueueSession " + qsess.toString());       
		 
		 // lookup Queue      
		 try {            q = (Queue) ctx.lookup(QUEUE_NAME);       
		 }        catch (NamingException ne) {          
			 ne.printStackTrace(System.err);          
			 System.exit(0);        }        
		 System.out.println("Got Queue " + q.toString());     
		 
		 // create QueueSender       
		 try {            qsndr = qsess.createSender(q);     
		 }        catch (JMSException jmse) {          
			 jmse.printStackTrace(System.err);        
			 System.exit(0);        }        
		 System.out.println("Got QueueSender " + qsndr.toString());     
		 
		 // create TextMessage      
		 try {            message = qsess.createTextMessage();   
		 }        catch (JMSException jmse) {        
			 jmse.printStackTrace(System.err);          
			 System.exit(0);        }        
		 System.out.println("Got TextMessage " + message.toString());      
		 
		 // set message text in TextMessage       
		 try {            message.setText(messageText);     
		 }        catch (JMSException jmse) {         
			 jmse.printStackTrace(System.err);      
			 System.exit(0);        }       
		 System.out.println("Set text in TextMessage " + message.toString());

		 // add correlation id

		 try {            message.setJMSCorrelationID(UUID.randomUUID().toString());
		 }        catch (JMSException jmse) {
			 jmse.printStackTrace(System.err);
			 System.exit(0);        }
		 System.out.println("Set text in TextMessage " + message.toString());

		 // set message properties to be able to be sended
		 message.setStringProperty("_wls_mimehdrContent_Type", "text/xml; charset=UTF-8");
		 message.setStringProperty("URI","/osm/wsapi");
		 // send message        
		 try {            qsndr.send(message);      
		 }        catch (JMSException jmse) {         
			 jmse.printStackTrace(System.err);         
			 System.exit(0);        }       
		 System.out.println("Sent message ");     
		 
		 // clean up        
		 try {          
			 message = null;      
			 qsndr.close();  
			 qsndr = null;  
			 q = null;        
			 qsess.close();   
			 qsess = null;    
			 qc.close();      
			 qc = null;       
			 qcf = null;      
			 ctx = null;      
			 }        catch (JMSException jmse)
		 {            jmse.printStackTrace(System.err);      
		 }        System.out.println("Cleaned up and done.");   
		 }
	 }