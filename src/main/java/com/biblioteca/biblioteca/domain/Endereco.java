package com.biblioteca.biblioteca.domain;

import java.util.Date;

import com.biblioteca.biblioteca.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Endereco extends BaseEntity{
	
	@NotEmpty(message = "A cidade não pode ser vazio")
	@NotNull(message = "A cidade não pode ser nulo")
	private String cidade;
	
	@NotEmpty(message = "O bairro não pode ser vazio")
	@NotNull(message = "O bairro não pode ser nulo")
	private String bairro;
	
	@NotEmpty(message = "O nome da rua não pode ser vazio")
	@NotNull(message = "O nome da rua não pode ser nulo")
	private String rua;
	
	@NotEmpty(message = "O numero da casa não pode ser vazio")
	@NotNull(message = "O numero da casa não pode ser nulo")
	private String numero;
	
	private String complemento;
	
	private String observacoes;
	

}
