package com.example.api.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.api.domain.Customer;
import com.example.api.domain.CustomerEntry;
import com.example.api.service.CustomerService;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	private CustomerService service;

	@Autowired
	public CustomerController(CustomerService service) {
		this.service = service;
	}

	@GetMapping("/{pageNumber}/{pageSize}")
	public List<Customer> findAll(@PathVariable int pageNumber, @PathVariable int pageSize) {
		return service.findAll(pageNumber, pageSize);
	}

	@GetMapping("/{id}")
	public Customer findById(@PathVariable Long id) {
		return service.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<String> insertCustomer(@RequestBody CustomerEntry customer) {
		String resultado = service.insert(customer);
		if(resultado.contains("inválido")) {
			return ResponseEntity.badRequest().body(resultado);
		}
		return ResponseEntity.ok(resultado);
	}
	
	@PostMapping("/editar")
	public ResponseEntity<String> editCustomer(@RequestBody CustomerEntry customer){
		String resultado = service.edit(customer);
		if(resultado.contains("inválido")) {
			return ResponseEntity.badRequest().body(resultado);
		}
		return ResponseEntity.ok(service.insert(customer));
	}
	
	@GetMapping("/excluir/{id}")
	public String deleteCustomer(@PathVariable Long id){
		return service.delete(id);
	}

}
