package com.gongyd.jms.logclient;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

import com.gongyd.jms.LoggingEventWrapper;

/**
 * JMSQueue appender is a log4j appender that writes LoggingEvent to a queue.
 * @author faheem
 *
 */
public class JMSQueueAppender extends AppenderSkeleton implements Appender{

private static Logger logger = Logger.getLogger("JMSQueueAppender");

private String brokerUri;
private String queueName;
private String userName;
private String password;

@Override
public void close() {

}

@Override
public boolean requiresLayout() {
	return false;
}

@Override
protected synchronized void append(LoggingEvent event) {
   try {
     ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(this.brokerUri);
     connectionFactory.setUserName(userName);
     connectionFactory.setPassword(password);
     // Create a Connection
     javax.jms.Connection connection = connectionFactory.createConnection();
     connection.start();

     // Create a Session
     Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

     // Create the destination (Topic or Queue)
     Destination destination = session.createQueue(this.queueName);

     // Create a MessageProducer from the Session to the Topic or Queue
     MessageProducer producer = session.createProducer(destination);
     //设置队列不持久化，以提高系统性能
     producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

     ObjectMessage message = session.createObjectMessage(new LoggingEventWrapper(event));

     // Tell the producer to send the message
     producer.send(message);

     // Clean up
     session.close();
     connection.close();
  } catch (Exception e) {
     e.printStackTrace();
  }
}

public void setBrokerUri(String brokerUri) {
	this.brokerUri = brokerUri;
}

public String getBrokerUri() {
	return brokerUri;
}

public void setQueueName(String queueName) {
	this.queueName = queueName;
}

public String getQueueName() {
	return queueName;
}

public String getUserName() {
	return userName;
}

public void setUserName(String userName) {
	this.userName = userName;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}



}