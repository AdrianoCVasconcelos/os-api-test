package com.adriano.os.sevices;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adriano.os.domain.Pessoa;
import com.adriano.os.domain.Tecnico;
import com.adriano.os.domain.repositories.PessoaRepository;
import com.adriano.os.domain.repositories.TecnicoRepository;
import com.adriano.os.dtos.TecnicoDTO;
import com.adriano.os.sevices.exception.DataIntegratyViolationException;
import com.adriano.os.sevices.exception.ObjectNotFoundException;

@Service
public class TecnicoService {
	
	@Autowired
	private TecnicoRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	
	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: "+id+", Tipo: "+Tecnico.class.getName(), null));
	}

	public List<Tecnico> findAll() {
		return repository.findAll();
	}
	
	public Tecnico create(TecnicoDTO objDTO) {
		if(findbyCPF(objDTO) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		
		Tecnico newObj = new Tecnico(null,objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone());
		return repository.save(newObj);
	}
	
	public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
		Tecnico objOld = findById(id);
		
		if(findbyCPF(objDTO) != null && findbyCPF(objDTO).getId() != id){
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		
		objOld.setNome(objDTO.getNome());
		objOld.setCpf(objDTO.getCpf());
		objOld.setTelefone(objDTO.getTelefone());
		return repository.save(objOld);
	}
	
	public void delete(Integer id) {
		Tecnico obj = findById(id);
		if(obj.getList().size() > 0) {
			throw new DataIntegratyViolationException("Técnico possui ordens de seviço, não pode ser deletado.");
		}
		repository.deleteById(id);
		
	}
	
	private Pessoa findbyCPF(TecnicoDTO objDTO) {
		Pessoa obj = pessoaRepository.findbyCPF(objDTO.getCpf());
		if(obj != null) {
			return obj;
		}
		return null;
	}


}
