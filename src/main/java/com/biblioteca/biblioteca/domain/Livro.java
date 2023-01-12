package com.biblioteca.biblioteca.domain;

import com.biblioteca.biblioteca.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Livro extends BaseEntity {
	
   @Column(name = "codigo_livro")
   private String codigoLivro;
   
   private String titulo;
   
   private String autor;
   
   private String editora;
   
   private String genero;
   
   private String isbn;
   
   
}
