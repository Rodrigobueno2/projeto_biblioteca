package com.biblioteca.biblioteca.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class BaseService<ENTITY extends BaseEntity,
                         REPOSITORY extends JpaRepository<ENTITY, Long>>{
	
	@Autowired
	private REPOSITORY repo;
	
	public ENTITY salvarEntidade(ENTITY entity) {
		return repo.save(entity);
	}
	
	public List<ENTITY> listarTodasEntidades(){
		return repo.findAll();
	}
	
	public ENTITY obterEntidadePorId(Long id) {
		verificarSeExisteId(id);
		return repo.findById(id).get();
	}
	
	public Page<ENTITY> obterEntidadesPaginadas(Pageable page){
		return repo.findAll(page);
	}
	
	public void deletarEntidadePorId(Long id) {
		verificarSeExisteId(id);
		repo.deleteById(id);
	}
	
	public ENTITY atualizarEntidade(ENTITY entidade) {
		verificarSeExisteId(entidade.getId());
		return repo.save(entidade);
	}
	
	public void verificarSeExisteId(Long id) {
		if(id == null || !repo.findById(id).isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entidade não existe");
		}
	}

}
