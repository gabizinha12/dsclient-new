package com.devsuperior.dsclient.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsclient.dto.ClientDTO;
import com.devsuperior.dsclient.entities.Client;
import com.devsuperior.dsclient.repositories.ClientRepository;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;

	@Transactional(readOnly = true)
	public Page<ClientDTO> fetchAllPaged(Pageable pageable) {
		Page<Client> clients = repository.findAll(pageable);
		return clients.map(c -> new ClientDTO(c));
	}

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> client = repository.findById(id);
		Client obj = client.get();
		return new ClientDTO(obj);
	}

	@Transactional
	public ClientDTO insert(ClientDTO dto) {
		Client entity = new Client();
		copyToEntity(dto, entity);
		entity = repository.save(entity);
		return new ClientDTO(entity);
	}

	@Transactional
	public ClientDTO update(ClientDTO dto, Long id) {
		Client entity = repository.getOne(id);
		copyToEntity(dto, entity);
		entity = repository.save(entity);
		return new ClientDTO(entity);

	}

	public void delete(Long id) {
		repository.deleteById(id);
	}

	private void copyToEntity(ClientDTO dto, Client entity) {
		entity.setBirthDate(dto.getBirthDate());
		entity.setName(dto.getName());
		entity.setCpf(dto.getCpf());
		entity.setIncome(dto.getIncome());
		entity.setChildren(dto.getChildren());
	}
}
