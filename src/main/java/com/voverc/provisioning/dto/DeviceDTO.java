package com.voverc.provisioning.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.voverc.provisioning.entity.Device;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceDTO {

	private String username;
	private String password;
	private String domain;
	private String port;
	private String codecs;
	private String timeout;

	public DeviceDTO(Device d) {
		this.username = d.getUsername();
		this.password = d.getPassword();
	}
}
