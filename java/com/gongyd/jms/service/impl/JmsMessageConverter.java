package com.gongyd.jms.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.log4j.Logger;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import com.gongyd.jms.LoggingEventWrapper;

public class JmsMessageConverter implements MessageConverter {
	private static Logger log = Logger.getLogger(JmsMessageConverter.class);

	public Object fromMessage(Message msg) throws JMSException,MessageConversionException {
		if (msg instanceof ObjectMessage) {
			try {
				LoggingEventWrapper event = (LoggingEventWrapper) ((ActiveMQObjectMessage) msg).getObject();
				return event;
			} catch (Exception e) {
				log.error("failed to read object message: " + e.getMessage());
			}
		} else {
			throw new JMSException("Message: [" + msg + "] is not a Map !");
		}
		return null;
	}

	public Message toMessage(Object obj, Session session) throws JMSException,MessageConversionException {
		ActiveMQObjectMessage o = (ActiveMQObjectMessage) session.createObjectMessage();
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.close();
			bos.close();
		} catch (IOException e) {
			log.error("failed to write object message: " + e.getMessage());
			session.rollback();
		}
		return o;
	}
}