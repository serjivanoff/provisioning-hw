package com.voverc.provisioning.util;

import com.voverc.provisioning.ProvisioningPropertiesBean;
import com.voverc.provisioning.dto.DeviceDTO;
import com.voverc.provisioning.entity.Device;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class DeviceHelper {

	private static final String KEY_VALUE_DELIMITER = "=";

	public static void overrideFragments(DeviceDTO dto, DeviceDTO overrideDto) {
		if (overrideDto.getUsername() != null) {
			dto.setUsername((overrideDto.getUsername()));
		}
		if (overrideDto.getPassword() != null) {
			dto.setPassword((overrideDto.getPassword()));
		}
		if (overrideDto.getDomain() != null) {
			dto.setDomain((overrideDto.getDomain()));
		}
		if (overrideDto.getPort() != null) {
			dto.setPort((overrideDto.getPort()));
		}
		if (overrideDto.getCodecs() != null) {
			dto.setCodecs((overrideDto.getCodecs()));
		}
		if (overrideDto.getTimeout() != null) {
			dto.setTimeout((overrideDto.getTimeout()));
		}
	}

	public static String mapToString(Map<String, String> deviceMap) {
		StringJoiner sj = new StringJoiner("\n");
		deviceMap.entrySet()
				.stream()
				.filter(e -> e.getValue() != null)
				.forEach(e -> sj.add(e.getKey() + KEY_VALUE_DELIMITER + e.getValue()));
		return sj.toString();
	}

	public static Map<String, String> stringToMap(String content) {
		String[] keyValues = content.split("\n");
		if (keyValues.length != 0) {
			Map<String, String> result = new HashMap<>(keyValues.length, 1f);
			for (String kv : keyValues) {
				String[] keyValue = kv.split(KEY_VALUE_DELIMITER);
				result.put(keyValue[0], keyValue[1]);
			}
			return result;
		}
		return Collections.emptyMap();
	}

	public static DeviceDTO mergeDbWithProperties(Device fromDb, ProvisioningPropertiesBean propertiesBean) {
		DeviceDTO dto = new DeviceDTO(fromDb);
		dto.setPort(propertiesBean.getPort());
		dto.setDomain(propertiesBean.getDomain());
		dto.setCodecs(propertiesBean.getCodecs());
		return dto;
	}
}
