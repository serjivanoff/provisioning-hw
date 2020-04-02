package com.voverc.provisioning.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.voverc.provisioning.ProvisioningPropertiesBean;
import com.voverc.provisioning.dto.DeviceDTO;
import com.voverc.provisioning.entity.Device;
import com.voverc.provisioning.repository.DeviceRepository;
import com.voverc.provisioning.util.DeviceHelper;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProvisioningServiceImpl implements ProvisioningService {

	private final DeviceRepository repository;
	private final ProvisioningPropertiesBean provisioningPropertiesBean;
	private static final ObjectMapper mapper = new ObjectMapper();

	@Autowired
	public ProvisioningServiceImpl(DeviceRepository repository, ProvisioningPropertiesBean provisioningPropertiesBean) {
		this.repository = repository;
		this.provisioningPropertiesBean = provisioningPropertiesBean;
	}

	@Override
	public String getProvisioningFile(String macAddress) {
		Device deviceFromDb = repository.getOne(macAddress);
		DeviceDTO dto = DeviceHelper.mergeDbWithProperties(deviceFromDb, provisioningPropertiesBean);
		return getProvisioningFile(dto, deviceFromDb);
	}

	private String getProvisioningFile(DeviceDTO dto, Device device) {
		switch (device.getModel()) {
			case DESK:
				return getDeskFile(dto, device.getOverrideFragment());
			case CONFERENCE:
				return getConferenceFile(dto, device.getOverrideFragment());
			default:
				throw new UnsupportedOperationException();
		}
	}

	private String getDeskFile(DeviceDTO dto, String overrideFragment) {
		Map<String, String> deviceMap = mapper.convertValue(dto, new TypeReference<Map<String, String>>() {
		});
		if (overrideFragment != null) {
			deviceMap.putAll(DeviceHelper.stringToMap(overrideFragment));
		}
		return DeviceHelper.mapToString(deviceMap);
	}

	private String getConferenceFile(DeviceDTO dto, String overrideFragment) {
		try {
			if (overrideFragment != null) {
				DeviceDTO overrideDto = mapper.readValue(overrideFragment, DeviceDTO.class);
				DeviceHelper.overrideFragments(dto, overrideDto);
			}
			return mapper.writeValueAsString(dto);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

}
