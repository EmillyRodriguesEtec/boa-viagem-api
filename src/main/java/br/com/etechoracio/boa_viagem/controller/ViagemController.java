package br.com.etechoracio.boa_viagem.controller;

import br.com.etechoracio.boa_viagem.entity.Viagem;
import br.com.etechoracio.boa_viagem.exceptions.ViagemInvalidaException;
import br.com.etechoracio.boa_viagem.repository.GastoRepository;
import br.com.etechoracio.boa_viagem.repository.ViagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/viagens")
public class ViagemController {
	
	@Autowired
	private ViagemRepository repository;

	@Autowired
	private GastoRepository gastoRepository;

	@GetMapping
	public List<Viagem> listarTodos(@RequestParam(required = false) String destino) {
		if (destino == null) {
			return repository.findAll();
		}
		return repository.findByDestinoIgnoreCase(destino);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Viagem> buscarPorId(@PathVariable Long id) {
		Optional<Viagem> existe = repository.findById(id);
		return existe.isPresent() ? ResponseEntity.ok(existe.get())
								  : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Viagem> inserir(@RequestBody Viagem obj) {
		Viagem salva = repository.save(obj);
		return ResponseEntity.ok(salva);
	}
	@PutMapping("/{id}")
	public ResponseEntity<Viagem> atualizar(@PathVariable Long id,
									        @RequestBody Viagem viagem) {
		Optional<Viagem> existe = repository.findById(id);
		if (existe.isPresent()) {
			//Não permitir a atualização de viagens encerradas, ou seja, com a da data de chegada preenchida.
			if (existe.get().getSaida() != null) {
				 throw new ViagemInvalidaException("Viagem finalizada não permite atualização");
			}
			Viagem salva = repository.save(viagem);
			return ResponseEntity.ok(salva);
		}
		else{
			return ResponseEntity.notFound().build();
		}
	}


	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Long id) {
		boolean existe = repository.existsById(id);
		if (existe) {
			// Verificar se existe gasto relacionado a viagem
			Viagem encontrada = repository.getById(id);
			if(gastoRepository.existsByViagem(encontrada))
			{
				throw new IllegalArgumentException();
			}

			if (encontrada.getSaida() != null && encontrada.getSaida().isBefore(LocalDate.now())) {
				throw new IllegalArgumentException("Não é possível excluir uma viagem encerrada.");
			}
			// Se não houver gastos relacionados a viagem, exclua a viagem
			repository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}
