package com.example.springBoot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.springBoot.entity.User;
import com.example.springBoot.service.UserInfoService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoServiceTest {
	@Autowired
	private UserInfoService service;
	
	@Test
	public void testGetById(){
		try {
			User user = service.getById("37438DD12875437380B3DD89CF6E1EE0");
			System.err.println(user.getUserName());
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
