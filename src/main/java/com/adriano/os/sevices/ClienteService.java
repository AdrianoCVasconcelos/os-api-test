package com.adriano.os.sevices;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adriano.os.domain.Cliente;
import com.adriano.os.domain.Pessoa;
import com.adriano.os.domain.repositories.ClienteRepository;
import com.adriano.os.domain.repositories.PessoaRepository;
import com.adriano.os.dtos.ClienteDTO;
import com.adriano.os.sevices.exception.DataIntegratyViolationException;
import com.adriano.os.sevices.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	
	public Cliente findById(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: "+id+", Tipo: "+Cliente.class.getName(), null));
	}

	public List<Cliente> findAll() {
		return repository.findAll();
	}
	
	public Cliente create(ClienteDTO objDTO) {
		if(findbyCPF(objDTO) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		
		Cliente newObj = new Cliente(null,objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone());
		return repository.save(newObj);
	}
	
	public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
		Cliente objOld = findById(id);
		
		if(findbyCPF(objDTO) != null && findbyCPF(objDTO).getId() != id){
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		
		objOld.setNome(objDTO.getNome());
		objOld.setCpf(objDTO.getCpf());
		objOld.setTelefone(objDTO.getTelefone());
		return repository.save(objOld);
	}
	
	public void delete(Integer id) {
		Cliente obj = findById(id);
		if(obj.getList().size() > 0) {
			throw new DataIntegratyViolationException("Pessoa possui ordens de seviço, não pode ser deletado.");
		}
		repository.deleteById(id);
		
	}
	
	private Pessoa findbyCPF(ClienteDTO objDTO) {
		Pessoa obj = pessoaRepository.findbyCPF(objDTO.getCpf());
		if(obj != null) {
			return obj;
		}
		return null;
	}


}
