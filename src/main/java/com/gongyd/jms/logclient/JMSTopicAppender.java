package com.gongyd.jms.logclient;

import org.apache.log4j.net.JMSAppender;
import org.apache.log4j.spi.LoggingEvent;

public class JMSTopicAppender extends JMSAppender {
	
	 public void append(LoggingEvent event) {
		 super.append(event);
		 this.close();
	 }

}
