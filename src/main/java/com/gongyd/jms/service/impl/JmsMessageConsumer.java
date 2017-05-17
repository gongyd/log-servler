package com.gongyd.jms.service.impl;

import org.apache.log4j.Logger;

import com.gongyd.jms.LoggingEventWrapper;

public class JmsMessageConsumer {
	private static Logger logger = Logger.getLogger(JmsMessageConsumer.class);
	private static final String TEMPLATE = "[%-5s] %s";
	public void consume(LoggingEventWrapper event) {
		System.out.println("消费消息开始 <==" + System.currentTimeMillis());
		String content = String.format(TEMPLATE, event.getLevel()
                .toString(), event.getMessage().toString());
        System.out.println(content+"---"+event.getDetail()+"==="+event.getHostName()+"==="+event.getIpAddress() +"----"+event.getTimeStamp());
	
        System.out.println("消费消息结束 <==" +System.currentTimeMillis());
	}

}