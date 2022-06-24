package com.adriano.os.sevices;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adriano.os.domain.Cliente;
import com.adriano.os.domain.OS;
import com.adriano.os.domain.Tecnico;
import com.adriano.os.domain.enuns.Prioridade;
import com.adriano.os.domain.enuns.Status;
import com.adriano.os.domain.repositories.ClienteRepository;
import com.adriano.os.domain.repositories.OSRepository;
import com.adriano.os.domain.repositories.TecnicoRepository;

@Service
public class DBService {
	
	
	
	@Autowired	
	private TecnicoRepository tecnicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private OSRepository osRepository;
	
	
	public void instanciaDB() {
		
		Tecnico t1 = new Tecnico(null, "Valdir Cesar", "435.512.870-90","(88) 9888-8889");
		Tecnico t2 = new Tecnico(null, "Joshe Walter", "426.622.143-68","(11) 9888-8889");
		Cliente c1 = new Cliente(null, "Betina Campos","012.153.030-25","(88) 9888-7778");
		
		OS os1 = new OS(null, Prioridade.ALTA, Status.ANDAMENTO, t1, c1, "Teste de OS");
		
		t1.getList().add(os1);
		c1.getList().add(os1);
			
		tecnicoRepository.saveAll(Arrays.asList(t1));
		tecnicoRepository.saveAll(Arrays.asList(t2));
		clienteRepository.saveAll(Arrays.asList(c1));
		osRepository.saveAll(Arrays.asList(os1));		
		
	}

}
