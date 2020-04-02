package com.voverc.provisioning.controller;

import com.voverc.provisioning.service.ProvisioningService;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1")
public class ProvisioningController {

	private final ProvisioningService service;

	@Autowired
	public ProvisioningController(ProvisioningService service) {
		this.service = service;
	}

	@GetMapping("/provisioning/{macAddress}")
	public ResponseEntity<String> getFile(@PathVariable String macAddress) {
		try {
			return ResponseEntity.ok(service.getProvisioningFile(macAddress));
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Device with macAddress %s Not Found", macAddress), e);
		}
	}
}