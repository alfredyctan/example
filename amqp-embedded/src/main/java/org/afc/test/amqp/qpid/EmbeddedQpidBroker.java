package org.afc.test.amqp.qpid;

import java.util.HashMap;
import java.util.Map;

import org.afc.util.FileUtil;
import org.apache.qpid.server.SystemLauncher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmbeddedQpidBroker {

	private static final Logger logger = LoggerFactory.getLogger(EmbeddedQpidBroker.class);
	
	private final SystemLauncher systemLauncher = new SystemLauncher();

    private Map<String, Object> attributes = new HashMap<>();

    public EmbeddedQpidBroker() {
    	this(5672);
    }
    
    public EmbeddedQpidBroker(int amqpPort) {
    	System.setProperty("qpid.amqp_port", String.valueOf(amqpPort));
        attributes.put("type", "Memory");
        attributes.put("initialConfigurationLocation", FileUtil.resolveAbsolutePath("classpath://qpid-config.json"));
        attributes.put("startupLoggedToSystemOut", true);
	}
	
	public void start() {
		try {
			systemLauncher.startup(attributes);
		} catch (Exception e) {
			logger.error("error on starting embedded broker", e);
		}
	}

	public void stop() {
		try {
	        systemLauncher.shutdown();
		} catch (Exception e) {
			logger.error("error on stopping embedded broker", e);
		}
	}
}