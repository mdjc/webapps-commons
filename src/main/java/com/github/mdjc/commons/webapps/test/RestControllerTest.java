package com.github.mdjc.commons.webapps.test;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public abstract class RestControllerTest {
	protected MockMvc mockMvc;

	@SuppressWarnings("rawtypes")
	protected HttpMessageConverter converter;

	@Autowired
	protected WebApplicationContext webApplicationContext;

	protected void buildWebContext() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Autowired
	protected void setConverters(HttpMessageConverter<?>[] converters) {
		converter = Arrays.asList(converters).stream().filter(
				hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();
		Assert.assertNotNull("the JSON message converter must not be null", converter);
	}

	@SuppressWarnings({ "unchecked" })
	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.converter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}
}