package com.acn.jms.card;

import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import com.acn.jms.model.Payment;

public class CardApp {
	
	public static void main(String[] args) throws NamingException {
		InitialContext context = new InitialContext();
		Topic topic = (Topic) context.lookup("topic/empTopic");
		
		try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext();) {

			Payment payment = new Payment();
			payment.setId("1");
			payment.setAmount(12444l);
			for (int i = 0; i < 10; i++) {
				jmsContext.createProducer().send(topic, payment);
			}
			
			System.out.println("Message sent");
		}
	}
}
