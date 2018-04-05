package org.afc.petstore.api.delegate;

import java.util.Map;

import org.afc.petstore.api.StoreApiDelegate;
import org.afc.petstore.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class StoreApiDelegateImpl implements StoreApiDelegate {

	private static final Logger logger = LoggerFactory.getLogger(StoreApiDelegateImpl.class);

	@Override
	public ResponseEntity<Void> deleteOrder(Long orderId) {
		logger.info("updatePet : {}", orderId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@Override
	public ResponseEntity<Map<String, Integer>> getInventory() {
		logger.info("getInventory");
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Order> getOrderById(Long orderId) {
		logger.info("getOrderById : {}", orderId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Order> placeOrder(Order body) {
		logger.info("placeOrder : {}", body);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
