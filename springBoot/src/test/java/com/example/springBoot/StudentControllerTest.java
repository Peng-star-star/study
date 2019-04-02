package com.example.springBoot;

import java.text.SimpleDateFormat;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.controller.HelloWorldController;
import com.example.controller.StudentController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentControllerTest {
	private MockMvc mvc;

	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.standaloneSetup(new StudentController()).build();
	}

	@Test
	public void getStudentListTest() throws Exception {

		// 测试getStudentList
		RequestBuilder request = null;
		request = MockMvcRequestBuilders.get("/students/");
		mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(Matchers.equalTo("[]")))
				.andDo(MockMvcResultHandlers.print()).andReturn().getResponse().getContentAsString();

		// 测试postStudent
		request = MockMvcRequestBuilders.post("/students/").param("id", "02").param("name", "测试02").param("age", "10")
				.param("birthday", "2018-01-01 12:00:00");
		mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(Matchers.equalTo("success")))
				.andDo(MockMvcResultHandlers.print()).andReturn().getResponse().getContentAsString();

		// 测试getStudent
		request = MockMvcRequestBuilders.get("/students/02");
		mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content()
						.string(Matchers.equalTo(
								"{\"id\":\"02\",\"name\":\"测试02\",\"age\":10,\"birthday\":\"2018-01-01 12:00:00\"}")))
				.andDo(MockMvcResultHandlers.print()).andReturn().getResponse().getContentAsString();

		// 测试putStudent
		request = MockMvcRequestBuilders.put("/students/02").param("name", "测试修改了");
		mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(Matchers.equalTo("success")))
				.andDo(MockMvcResultHandlers.print()).andReturn().getResponse().getContentAsString();

		// 测试deleteUser
		request = MockMvcRequestBuilders.delete("/students/02");
		mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(Matchers.equalTo("success")))
				.andDo(MockMvcResultHandlers.print()).andReturn().getResponse().getContentAsString();

		// 验证，用get查询应该是null
		request = MockMvcRequestBuilders.get("/students/");
		mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(Matchers.equalTo("[]")))
				.andDo(MockMvcResultHandlers.print()).andReturn().getResponse().getContentAsString();
	}
}
