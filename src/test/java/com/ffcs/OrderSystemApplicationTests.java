package com.ffcs;

import com.ffcs.controller.OrderController;
import com.ffcs.mapper.OrderMapper;
import com.ffcs.mapper.ProductMapper;
import com.ffcs.pojo.Order;
import com.ffcs.pojo.Product;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderSystemApplicationTests {

	Logger logger;

	@Before
	public void init() {
		logger = Logger.getLogger(OrderController.class.getName());
		BasicConfigurator.configure();
	}

	@Autowired
	OrderMapper orderMapper;
	@Autowired
	ProductMapper productMapper;

	@Test
	public void listOrder() {
/*		List<Order> orders = orderMapper.listAll();

		logger.info("order: " + orders);
		for(Order order : orders){
			logger.info(order.getOrderItems());
		}*/
	}

	@Test
	public void listProduct() {
		List<Product> products = productMapper.listAll(0,10);
		for(Product product : products) {
			logger.info(product);
		}
	}

}
