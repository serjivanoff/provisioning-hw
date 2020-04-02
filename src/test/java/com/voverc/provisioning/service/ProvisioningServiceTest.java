package com.voverc.provisioning.service;

import javax.persistence.EntityNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ProvisioningServiceTest {

	@Autowired
	private ProvisioningService service;

	private final static String MAC_ADDRESS_DESK = "aa-bb-cc-dd-ee-ff";
	private final static String MAC_ADDRESS_CONFERENCE = "f1-e2-d3-c4-b5-a6";
	private final static String MAC_ADDRESS_DESK_WITH_OVERRIDE = "a1-b2-c3-d4-e5-f6";
	private final static String MAC_ADDRESS_CONFERENCE_WITH_OVERRIDE = "1a-2b-3c-4d-5e-6f";

	private final static String RESPONSE_DESK = "username=john\npassword=doe\ndomain=sip.voverc.com\nport=5060\ncodecs=G711,G729,OPUS";
	private final static String RESPONSE_DESK_WITH_OVERRIDE = "username=walter\npassword=white\ndomain=sip.anotherdomain.com\nport=5161\ncodecs=G711,G729,OPUS\ntimeout=10";
	private final static String RESPONSE_CONFERENCE = "{\"username\":\"sofia\",\"password\":\"red\",\"domain\":\"sip.voverc.com\",\"port\":\"5060\",\"codecs\":\"G711,G729,OPUS\"}";
	private final static String RESPONSE_CONFERENCE_WITH_OVERRIDE = "{\"username\":\"eric\",\"password\":\"blue\",\"domain\":\"sip.anotherdomain.com\",\"port\":\"5161\",\"codecs\":\"G711,G729,OPUS\",\"timeout\":\"10\"}";

	@Test
	public void getProvisioningFileDeskTest() {
//		When
		String deskFile = service.getProvisioningFile(MAC_ADDRESS_DESK);
//		Then
		Assert.assertEquals(deskFile, RESPONSE_DESK);
	}

	@Test
	public void getProvisioningFileConferenceTest() {
//		When
		String conferenceFile = service.getProvisioningFile(MAC_ADDRESS_CONFERENCE);
//		Then
		Assert.assertEquals(conferenceFile, RESPONSE_CONFERENCE);
	}

	@Test
	public void getProvisioningFileDeskWithOverrideTest() {
//		When
		String deskFile = service.getProvisioningFile(MAC_ADDRESS_DESK_WITH_OVERRIDE);
//		Then
		Assert.assertEquals(deskFile, RESPONSE_DESK_WITH_OVERRIDE);
	}

	@Test
	public void getProvisioningFileConferenceWithOverrideTest() {
//		When
		String conferenceFile = service.getProvisioningFile(MAC_ADDRESS_CONFERENCE_WITH_OVERRIDE);
//		Then
		Assert.assertEquals(conferenceFile, RESPONSE_CONFERENCE_WITH_OVERRIDE);
	}

	@Test(expected = EntityNotFoundException.class)
	public void getProvisioningFileNotFoundTest() {
//		Given
		String badMac = "bad_mac";
//		When
		service.getProvisioningFile(badMac);
//		Then
//		expecting exception
	}

}
