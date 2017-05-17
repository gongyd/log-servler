package com.gongyd.jms.adapter;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.log4j.Logger;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;

import com.gongyd.jms.service.impl.JmsMessageConsumer;

public class JmsMessageListenerAdapter extends MessageListenerAdapter {
	private static Logger logger = Logger.getLogger(JmsMessageConsumer.class);

	public JmsMessageListenerAdapter(Object delegate) {
		super(delegate);
	}

	public JmsMessageListenerAdapter() {
		super();
	}

	public void onMessage(Message message, Session session) throws JMSException {
		try {
			super.onMessage(message, session);
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
			throw new JMSException("处理jms消息异常");
		}
	}
}