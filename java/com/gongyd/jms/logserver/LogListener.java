package com.gongyd.jms.logserver;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.log4j.spi.LoggingEvent;
public class LogListener implements MessageListener {
    private static final String TEMPLATE = "[%-5s] %s";
    public void onMessage(Message message) {
        try {
            // extract LoggingEvent from message
            // you must set org.apache.log4j.spi into the trusted packages list
            // see spring-beans.xml in classpath
            LoggingEvent event = (LoggingEvent) ((ActiveMQObjectMessage) message)
                    .getObject();
            String content = String.format(TEMPLATE, event.getLevel()
                    .toString(), event.getMessage().toString());
            System.out.println(content);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}