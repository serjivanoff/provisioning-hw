package com.voverc.provisioning.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.voverc.provisioning.service.ProvisioningService;
import javax.persistence.EntityNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

@RunWith(MockitoJUnitRunner.class)
public class ProvisioningControllerTest {

	public final static String MAC_SUCCESSFUL = "f1-e2-d3-c4-b5-a6";
	private final static String MAC_UNSUCCESSFUL = "unsuccessful";
	private final static String RESPONSE_SUCCESSFUL = "Success";

	private MockMvc mvc;

	@Mock
	private ProvisioningService service;

	@InjectMocks
	private ProvisioningController controller;

	@Before
	public void setup() {
		this.mvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void getFileSuccessfulTest() throws Exception {
//		Given
		given(service.getProvisioningFile(MAC_SUCCESSFUL)).willReturn(RESPONSE_SUCCESSFUL);
//		When
		MockHttpServletResponse response = mvc.perform(
				get("/api/v1/provisioning/" + MAC_SUCCESSFUL))
				.andReturn().getResponse();
//		Then
		Assert.assertEquals(response.getStatus(), HttpStatus.OK.value());
		Assert.assertEquals(response.getContentAsString(), RESPONSE_SUCCESSFUL);
	}

	@Test
	public void getFileNotFoundTest() throws Exception {
//		Given
		given(service.getProvisioningFile(MAC_UNSUCCESSFUL)).willThrow(EntityNotFoundException.class);
//		When
		MvcResult result = mvc.perform(get("/api/v1/provisioning/" + MAC_UNSUCCESSFUL)).andReturn();
		MockHttpServletResponse response = result.getResponse();
//		Then
		Assert.assertEquals(result.getResolvedException().getClass(), ResponseStatusException.class);
		Assert.assertEquals(response.getStatus(), HttpStatus.NOT_FOUND.value());
		Assert.assertTrue(response.getContentAsString().isEmpty());
		Assert.assertEquals(response.getErrorMessage(), String.format("Device with macAddress %s Not Found", MAC_UNSUCCESSFUL));
	}
}
