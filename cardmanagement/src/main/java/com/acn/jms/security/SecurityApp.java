package com.acn.jms.security;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import com.acn.jms.model.Payment;

public class SecurityApp {
	
	public static void main(String[] args) throws NamingException, JMSException {
		InitialContext context = new InitialContext();
		Topic topic = (Topic) context.lookup("topic/empTopic");
		
		try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext();) {

			JMSConsumer consumer1 = jmsContext.createSharedConsumer(topic, "shared consumer");
			JMSConsumer consumer2 = jmsContext.createSharedConsumer(topic, "shared consumer");
			
			for (int i = 0; i < 10; i+=2) {
				Message message = consumer1.receive();
				Payment payment = message.getBody(Payment.class);
				System.out.println(payment);
			
				Message message2 = consumer2.receive();
				Payment payment2 = message2.getBody(Payment.class);
				System.out.println(payment2);
			}
		}
	}
}
