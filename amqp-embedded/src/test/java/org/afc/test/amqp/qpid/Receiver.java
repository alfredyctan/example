package org.afc.test.amqp.qpid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class Receiver {

	private static final Logger logger = LoggerFactory.getLogger(Receiver.class);
	
	private String msg;
	
	@RabbitListener(queues = {"#{binding.getDestination()}"})
    public void receive(String msg, Message message) throws InterruptedException {
		logger.info("msgin : {}", msg);
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}
}
