package com.example.cicd;

import com.example.cicd.Contoller.TestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;



@SpringBootTest
@AutoConfigureMockMvc
class CicdApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	TestController testController;



	@Test
	void contextLoads() {
		assertThat(testController).isNotNull();
	}


	@Test
	void testEndpoint() throws Exception {
		mockMvc.perform(get("/test"))
				.andExpect(status().isOk())
				.andExpect(content().string("Hello World"));
	}

}
