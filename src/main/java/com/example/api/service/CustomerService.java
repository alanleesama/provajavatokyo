package com.example.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.api.domain.Address;
import com.example.api.domain.Customer;
import com.example.api.domain.CustomerEntry;
import com.example.api.repository.AddressRepository;
import com.example.api.repository.CustomerRepository;

@Service
public class CustomerService {

	private CustomerRepository repository;
	
	private AddressRepository addressRepository;

	@Autowired
	public CustomerService(CustomerRepository repository, AddressRepository addressRepository) {
		this.repository = repository;
		this.addressRepository = addressRepository;
	}

	public List<Customer> findAll(int pageNumber, int pageSize) {
		Pageable page = PageRequest.of(pageNumber, pageSize);
		return repository.findAllByOrderByNameAsc(page);
	}

	public Optional<Customer> findById(Long id) {
		return repository.findById(id);
	}

	public String insert(CustomerEntry customer){
		if(customer.getName().isEmpty() || customer.getName() == null) {
			return "Nome inválido";
		}
		if(customer.getEmail().isEmpty() || customer.getEmail() == null) {
			return "Email inválido";
		}
		for(String cep : customer.getCep()) {
			Customer customerEntity = new Customer();
			if(cep.length() != 8) {
				return "CEP inválido";
			}
			addressRepository.obtainAddress(cep);
			
			customerEntity.setName(customer.getName());
			customerEntity.setEmail(customer.getEmail());
			repository.save(customerEntity);
		}
		return "Usuário cadastrado";
	}

	public String edit(CustomerEntry customer) {
		if(customer.getName().isEmpty() || customer.getName() == null) {
			return "Nome inválido";
		}
		if(customer.getEmail().isEmpty() || customer.getEmail() == null) {
			return "Email inválido";
		}
		for(String cep : customer.getCep()) {
			Customer customerEntity = new Customer();
			if(cep.length() != 8) {
				return "CEP inválido";
			}
			Address address = addressRepository.obtainAddress(cep);
			customerEntity.setId(customer.getId());
			customerEntity.setName(customer.getName());
			customerEntity.setEmail(customer.getEmail());
			repository.save(customerEntity);
		}
		return "Usuário alterado";
	}

	public String delete(Long id) {
		repository.deleteById(id);
		return "Registro excluído";
	}
	
	

}
