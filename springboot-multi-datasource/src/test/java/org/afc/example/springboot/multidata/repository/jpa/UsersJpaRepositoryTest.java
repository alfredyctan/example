package org.afc.example.springboot.multidata.repository.jpa;

import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isIn;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.afc.example.springboot.multidata.config.UserDataSourceConfig;
import org.afc.example.springboot.multidata.model.Users;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@EnableAutoConfiguration
@TestPropertySource({ "classpath:/application.properties", "classpath:/application-local.properties" })
@ContextConfiguration(classes = { UserDataSourceConfig.class, UsersJpaRepository.class })
@RunWith(SpringJUnit4ClassRunner.class)
@Ignore
public class UsersJpaRepositoryTest {

	@Autowired
	private UsersJpaRepository repository;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		try {
			List<Users> users = repository.findByUsername("user");

			assertThat("count is 2", users.size(), is(2));
			
			List<String> names = users.stream()
				.map(mac -> mac.getUsername())
				.collect(Collectors.toList());
			System.out.println(names);
			
			assertThat("names match", names, everyItem(isIn(new String[] { "user1", "user2" }))); 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
