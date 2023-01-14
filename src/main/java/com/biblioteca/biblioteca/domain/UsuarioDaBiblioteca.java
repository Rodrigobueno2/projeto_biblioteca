package com.biblioteca.biblioteca.domain;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.validator.constraints.br.CPF;

import com.biblioteca.biblioteca.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuario_biblioteca")
public class UsuarioDaBiblioteca extends BaseEntity{
	
	@NotEmpty(message = "O nome não pode ser vazio")
	@NotNull(message = "O nome não pode ser nulo")
	private String nome;
	
	@NotEmpty(message = "O cpf não pode ser vazio")
	@NotNull(message = "O cpf não pode ser nulo")
	@CPF(message = "O cpf tem que ser válido")
	private String cpf;
	
	@NotNull(message = "A data de nascimento não pode ser nulo")
	@Past(message = "A data de nascimento deve estar no passado")
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(name = "data_de_nascimento")
	private LocalDate dataDeNascimento;
	
	@NotEmpty(message = "A matricula não pode ser vazio")
	@NotNull(message = "A matricula não pode ser nulo")
	private String matricula;
	
	
	@NotNull(message = "O endereço residencial não pode ser nulo")
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "endereco_residencial_id")
	private Endereco endereco;
	
	@NotEmpty(message = "O contato da pessoa não pode ser vazio, tem que ter no minimo um contato")
	@NotNull(message = "O contato da pessoa não pode ser nulo")
	@OneToMany(cascade = CascadeType.ALL)
	private List<Contato> contato;

}
