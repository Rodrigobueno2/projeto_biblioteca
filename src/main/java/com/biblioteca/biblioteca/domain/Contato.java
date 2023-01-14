package com.biblioteca.biblioteca.domain;

import com.biblioteca.biblioteca.base.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contato extends BaseEntity{
   
	@NotEmpty(message="O nome não pode ser vazio")
	@NotNull(message="O nome não pode ser nulo")
	private String nome;

	@NotEmpty(message="O telefone não pode ser vazio")
	@NotNull(message="O telefone não pode ser nulo")
	private String telefone;

	@NotEmpty(message="O email não pode ser vazio")
	@NotNull(message="O email não pode ser nulo")
	@Email(message = "O email tem que ser válido")
	private String email;
	

}
