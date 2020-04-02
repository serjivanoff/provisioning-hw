package com.voverc.provisioning;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
@Getter  @Setter
@Configuration
@ConfigurationProperties(prefix = "provisioning")
public class ProvisioningPropertiesBean {

	private String domain;
	private String port;
	private String codecs;

}
