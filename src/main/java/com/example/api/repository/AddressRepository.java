package com.example.api.repository;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.example.api.domain.Address;

@Repository
public class AddressRepository {
	
	
	
	public Address obtainAddress(String cep) {
		
		RestTemplate restTemplate = new RestTemplate();
		String url = "https://viacep.com.br/ws/";
		ResponseEntity<Address> response = restTemplate.getForEntity(url+cep+"/json/",Address.class);
		
		return response.getBody();
	}

}
