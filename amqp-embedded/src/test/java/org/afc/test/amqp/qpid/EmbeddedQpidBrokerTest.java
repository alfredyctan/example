package org.afc.test.amqp.qpid;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.afc.util.JUnit4Util;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@ActiveProfiles("test")
@SpringBootTest
public class EmbeddedQpidBrokerTest {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private Receiver receiver;

	private static EmbeddedQpidBroker broker;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		broker = new EmbeddedQpidBroker();
		broker.start();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		broker.stop();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		JUnit4Util.startCurrentTest(getClass());
		try {
			rabbitTemplate.convertAndSend("exchange.test", "routingkey.test", "hello");
			JUnit4Util.sleep(1000);

			String actual = JUnit4Util.actual(receiver.getMsg());
			String expect = JUnit4Util.expect("hello");

			assertThat("received message", actual, is(equalTo(expect)));
		} catch (Exception e) {
			e.printStackTrace();
		}

		JUnit4Util.endCurrentTest(getClass());
	}

	@Configuration
	static class ContextConfiguration {

		@Bean
		public Receiver receiver() {
			return new Receiver();
		}

		@Bean
		public Binding binding(AmqpAdmin amqpAdmin) {
			Queue queue = new Queue("queue.test");
			TopicExchange exchange = new TopicExchange("exchange.test");
			Binding binding = BindingBuilder.bind(queue).to(exchange).with("routingkey.test");
			amqpAdmin.declareQueue(queue);
			amqpAdmin.declareExchange(exchange);
			amqpAdmin.declareBinding(binding);
			return binding;
		}
	}

}
