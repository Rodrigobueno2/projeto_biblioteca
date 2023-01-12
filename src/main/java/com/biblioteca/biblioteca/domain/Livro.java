package com.biblioteca.biblioteca.domain;

import com.biblioteca.biblioteca.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Livro extends BaseEntity {
	
   @Column(name = "codigo_livro")
   @NotEmpty(message = "O código do livro não pode ser vazio")
   @NotNull(message = "O código do livro não pode ser nulo")
   private String codigoLivro;
   
   @NotEmpty(message = "O titulo não pode ser vazio")
   @NotNull(message = "O titulo não pode ser nulo")
   private String titulo;
   
   @NotEmpty(message = "O autor não pode ser vazio")
   @NotNull(message = "O autor não pode ser nulo")
   private String autor;
   
   @NotEmpty(message = "A editora não pode ser vazio")
   @NotNull(message = "A editora não pode ser nulo")
   private String editora;
   
   @NotEmpty(message = "O genero não pode ser vazio")
   @NotNull(message = "O genero não pode ser nulo")
   private String genero;
   
   @NotEmpty(message = "O isbn não pode ser vazio")
   @NotNull(message = "O isbn não pode ser nulo")
   private String isbn;
   
   
}
