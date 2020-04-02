package com.voverc.provisioning.util;

import com.voverc.provisioning.ProvisioningPropertiesBean;
import com.voverc.provisioning.dto.DeviceDTO;
import com.voverc.provisioning.entity.Device;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class DeviceHelperTest {

	public final static String USERNAME = "Name";
	public final static String TIMEOUT = "100";
	public final static String CODECS = "codecs";
	public final static String PORT = "8080";
	public final static String DOMAIN = "domain";
	public final static String PASSWORD = "password";
	public final static String NAME_NEW = "New Name";
	public final static String TIMEOUT_NEW = "111";
	public final static String CODECS_NEW = "new codecs";
	public final static String PORT_NEW = "8181";
	public final static String DOMAIN_NEW = "new domain";
	public final static String PASSWORD_NEW = "new password";

	private final static Map<String, String> keyValues = new HashMap<>(4, 1f);

	static {
		keyValues.put("key1", "value1");
		keyValues.put("key2", "value2");
		keyValues.put("key3", "value3");
	}

	private final static String KEY_VALUES_STRING = "key1=value1\nkey2=value2\nkey3=value3";

	@Test
	public void overrideFragmentsTest() {
//		Given
		DeviceDTO target = new DeviceDTO(), source = new DeviceDTO();
		target.setUsername(USERNAME);
		target.setTimeout(TIMEOUT);
		target.setCodecs(CODECS);
		target.setPort(PORT);
		target.setDomain(DOMAIN);
		target.setPassword(PASSWORD);
		source.setUsername(NAME_NEW);
		source.setTimeout(TIMEOUT_NEW);
		source.setCodecs(CODECS_NEW);
		source.setPort(PORT_NEW);
		source.setDomain(DOMAIN_NEW);
		source.setPassword(PASSWORD_NEW);
//		When
		DeviceHelper.overrideFragments(target, source);
//		Then
		Assert.assertEquals(target.getUsername(), NAME_NEW);
		Assert.assertEquals(target.getTimeout(), TIMEOUT_NEW);
		Assert.assertEquals(target.getCodecs(), CODECS_NEW);
		Assert.assertEquals(target.getPort(), PORT_NEW);
		Assert.assertEquals(target.getDomain(), DOMAIN_NEW);
		Assert.assertEquals(target.getPassword(), PASSWORD_NEW);
	}

	@Test
	public void mapToStringTest() {
		Assert.assertEquals(DeviceHelper.mapToString(keyValues), KEY_VALUES_STRING);
	}

	@Test
	public void stringToMapTest() {
		Assert.assertEquals(keyValues, DeviceHelper.stringToMap(KEY_VALUES_STRING));
	}

	@Test
	public void mergeDbWithPropertiesTest() {
//		Given
		Device fromDb = new Device();
		fromDb.setUsername(USERNAME);
		fromDb.setPassword(PASSWORD);
		ProvisioningPropertiesBean bean = new ProvisioningPropertiesBean();
		bean.setCodecs(CODECS);
		bean.setPort(PORT);
		bean.setDomain(DOMAIN);
//		When
		DeviceDTO merged = DeviceHelper.mergeDbWithProperties(fromDb, bean);
//		Then
		Assert.assertEquals(merged.getUsername(), USERNAME);
		Assert.assertEquals(merged.getPassword(), PASSWORD);
		Assert.assertEquals(merged.getCodecs(), CODECS);
		Assert.assertEquals(merged.getPort(), PORT);
		Assert.assertEquals(merged.getDomain(), DOMAIN);
	}

}
