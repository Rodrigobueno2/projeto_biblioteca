package com.biblioteca.biblioteca.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

public class BaseController<ENTITY extends BaseEntity,
						    REPOSITORY extends JpaRepository<ENTITY, Long>,
						    SERVICE extends BaseService<ENTITY, REPOSITORY>> {
	@Autowired
	private SERVICE service;
	
	@PostMapping
	public ENTITY criarEntity(@RequestBody ENTITY entidade) {
		return service.salvarEntidade(entidade);
	}
	
	@GetMapping("/list")
	public List<ENTITY> listarEntidades(){
		return service.listarTodasEntidades();
	}
	
	@GetMapping
	public Page<ENTITY> listarEntidadesPaginados(Pageable pageable){
		return service.obterEntidadesPaginadas(pageable);
	}
	
	@GetMapping("/{id}")
	public ENTITY buscarEntidadePorId(@PathVariable("id") Long id) {
		return service.obterEntidadePorId(id);
	}
	
	@PutMapping("/{id}")
	public ENTITY atualizarEntidadePorId(@PathVariable("id") Long id, @RequestBody ENTITY entidade) {
		if(!id.equals(entidade.getId())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O ID da URL não é igual o ID da entidade no corpo da requisição");
		}
		return service.atualizarEntidade(entidade);
	}
	
	@DeleteMapping("/{id}")
	public void deletarEntidadePorId(@PathVariable("id") Long id) {
		service.deletarEntidadePorId(id);
	}

}
