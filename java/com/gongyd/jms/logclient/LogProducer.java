package com.gongyd.jms.logclient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
public class LogProducer {
    private static final Log log = LogFactory.getLog(LogProducer.class);
    /**
     * @param args
     */
    public static void main(String[] args) {
        log.debug("this is a debug message.");
        log.info("this is a info message.");
        log.warn("this is a warn message.");
        log.error("this is a error message");
    }
}