package com.acn.jms.alert;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import com.acn.jms.model.Payment;

public class AlertApp {
	public static void main(String[] args) throws NamingException, JMSException, InterruptedException {
		InitialContext context = new InitialContext();
		Topic topic = (Topic) context.lookup("topic/empTopic");
		
		try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext();) {

			jmsContext.setClientID("securityApp");
			JMSConsumer consumer = jmsContext.createDurableConsumer(topic, "subscription1"); //Durable consumer
			consumer.close();
			
			Thread.sleep(10000);
			consumer = jmsContext.createDurableConsumer(topic, "subscription1");
			
			for (int i = 0; i < 10; i++) {
				Message message = consumer.receive();
				Payment payment = message.getBody(Payment.class);
				System.out.println(payment);
			}
			
			consumer.close();
			jmsContext.unsubscribe("subscription1");
		}
	}
}
