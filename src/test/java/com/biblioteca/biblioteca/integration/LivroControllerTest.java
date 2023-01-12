package com.biblioteca.biblioteca.integration;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.biblioteca.biblioteca.domain.Livro;
import com.biblioteca.biblioteca.repository.LivroRepository;
import com.fasterxml.jackson.core.type.TypeReference;

import lombok.SneakyThrows;
import utils.CustomPageImpl;


public class LivroControllerTest extends IntegrationTest {
	
	@Autowired
	private LivroRepository livroRepository;
	
	private int codigo = 0;

	@Test
	@SneakyThrows
	void shouldDeleteLivro() {
		Livro livro = new Livro();
		
		livro.setTitulo("Calculo 1");
		livro.setCodigoLivro("134A54");
		livro.setEditora("Moderna");
		livro.setGenero("Didático");
		livro.setId(1L);
		livro.setIsbn("58512894");
		livro.setAutor("Antônio");
		
		Livro livroSalvo = livroRepository.save(livro);
		
		Optional<Livro> repository = livroRepository.findById(livroSalvo.getId());
		assertFalse(repository.isEmpty());
		
		mockMvc.perform(delete("/api/livros/"+livroSalvo.getId())
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
		
		Optional<Livro> livroGetById = livroRepository.findById(livroSalvo.getId());
		assertFalse(livroGetById.isPresent());
	}
	
	
	@Test
	@SneakyThrows
	void shouldSaveLivro() {
		Livro livro = new Livro();
		livro.setAutor("Marcos");
		livro.setCodigoLivro("356a89");
		livro.setEditora("ABC");
		livro.setGenero("Didatica");
		livro.setIsbn("333333333");
		livro.setTitulo("Portugues");
		
		String jsonLivroString = objectMapper.writeValueAsString(livro);
		
		MvcResult result = mockMvc.perform(post("/api/livros")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonLivroString))
				.andExpect(status().is2xxSuccessful())
				.andReturn();
		
		Livro livroResponse = objectMapper.readValue(result.getResponse().getContentAsString(StandardCharsets.UTF_8),Livro.class);
		
		assertNotNull(livroResponse.getId());
		assertEquals(livroResponse.getAutor(),"Marcos");
		assertEquals(livroResponse.getCodigoLivro(),"356a89");
		assertEquals(livroResponse.getEditora(),"ABC");
		assertEquals(livroResponse.getGenero(),"Didatica");
		assertEquals(livroResponse.getIsbn(),"333333333");
		assertEquals(livroResponse.getTitulo(),"Portugues");
	}
	
	@Test
	@SneakyThrows
	void shouldThrowNotFoundErrorWhenUpdateLivroWithNotExistsId() {
		Livro livro = new Livro();
		livro.setId(500L);
		
		String jsonLivroString = objectMapper.writeValueAsString(livro);
		
		mockMvc.perform(put("/api/livros/"+livro.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonLivroString))
				.andExpect(status().isNotFound());
		
	}
	
	@Test
	@SneakyThrows
	void shouldFindByIdLivro() {
		Livro livro = new Livro();
		
		livro.setAutor("Rodrigo");
		livro.setCodigoLivro("45690AD");
		livro.setEditora("ABC");
		livro.setGenero("Didática");
		livro.setIsbn("84848484");
		livro.setTitulo("Aprender");
		
		livro = livroRepository.save(livro);
		
		MvcResult result = mockMvc.perform(get("/api/livros/"+livro.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		
		Livro livroResponse = objectMapper.readValue(result.getResponse().getContentAsString(StandardCharsets.UTF_8),Livro.class);
		
		
		assertEquals("Rodrigo",livroResponse.getAutor());
		assertEquals("45690AD",livroResponse.getCodigoLivro());
		assertEquals("ABC",livroResponse.getEditora());
		assertEquals("Didática",livroResponse.getGenero());
		assertEquals("84848484",livroResponse.getIsbn());
		assertEquals("Aprender",livroResponse.getTitulo());
		
	}
	
	
	@Test
	@SneakyThrows
	void shouldFindAllLivros() {
		List<String> titulos = new ArrayList<>();
		
		
		titulos.add("Matemática 4");
		titulos.add("Lógica 5");
		titulos.add("História");
		titulos.add("Computação");
		titulos.add("Matemática 5");
		titulos.add("Matemática 6");
		
		titulos.forEach(titulo ->{
			Livro livro = new Livro();
			codigo = codigo + 1;
			livro.setCodigoLivro(titulo + "-cod-" + codigo);
			livro.setAutor("Autor do livro "+titulo);
			livro.setGenero("genero do livro "+titulo);
			livro.setIsbn("2222222"+titulo); 
			livro.setTitulo(titulo);
			livro.setEditora(titulo+" editora");
			livroRepository.save(livro);
		});
		
		MvcResult result = mockMvc.perform(get("/api/livros/list")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		
		List<Livro> livros = objectMapper.readValue(result.getResponse().getContentAsString(StandardCharsets.UTF_8),
				
				new TypeReference<List<Livro>>() {
			
		});
		
		assertEquals(6, livros.size());
		assertEquals("História-cod-3",livros.get(2).getCodigoLivro());
		assertEquals("História",livros.get(2).getTitulo());
	    assertEquals("História editora",livros.get(2).getEditora());
		
	}
	
	 @Test
	 @SneakyThrows
	 void shouldUpdateLivro() {


		 Livro livro = new Livro();
		 
		 livro.setAutor("Marcos");
		 livro.setCodigoLivro("4949");
		 livro.setEditora("nova");
		 livro.setGenero("romance");
		 livro.setIsbn("949494");
		 livro.setTitulo("conto 2");

		 livro = livroRepository.save(livro);
		 
		 livro.setAutor("Rodrigo");
		 
		 
		 String jsonLivroString = objectMapper.writeValueAsString(livro);
		 
		 MvcResult result = mockMvc.perform(put("/api/livros/" + livro.getId())
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(jsonLivroString))
	                .andExpect(status().is2xxSuccessful())
	                .andReturn();
		 
		 String res = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		 
		 Livro livroResponse = objectMapper.readValue(res, Livro.class);
		 
		 assertEquals(livroResponse.getId(),livro.getId());
		 
		 
	     assertEquals("Rodrigo", livroResponse.getAutor());
	     assertEquals("4949", livroResponse.getCodigoLivro());
	     assertEquals("nova", livroResponse.getEditora());
	     assertEquals("romance", livroResponse.getGenero());
	     assertEquals("949494", livroResponse.getIsbn());
	     assertEquals("conto 2", livroResponse.getTitulo());
	     assertNotEquals("Marcos", livroResponse.getAutor());
	     

		 
	 }
	
	 
	 @Test
	 @SneakyThrows
	 void shouldFindAllPageableLivro() {
		 
		 List<String> titulos = new ArrayList<>();
		 titulos.add("titulo1");
		 titulos.add("titulo2");
		 titulos.add("titulo3");
		 titulos.add("titulo4");
		 titulos.add("titulo5");
		 titulos.add("titulo6");
		 codigo = 0;
		 
		 
		 
		 titulos.forEach(titulo -> {
			 Livro livro = new Livro();
			 codigo = codigo + 1;
			 livro.setCodigoLivro(titulo + "-cod-" + codigo);
			 livro.setGenero("genero do livro "+titulo);
			 livro.setIsbn("2222222"+titulo); 
			 livro.setAutor("autor do livro "+titulo);
			 livro.setEditora("editora do livro "+titulo);
			 livro.setTitulo(titulo);
			 
		     livroRepository.save(livro);
			
		 });
		 
		 MvcResult result = mockMvc.perform(get("/api/livros?size=3&sort=titulo")
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk()).andReturn();

		 
		 
		 Page<Livro> pageLivro = objectMapper.readValue(result.getResponse().getContentAsString(StandardCharsets.UTF_8),
				 new TypeReference<CustomPageImpl<Livro>>() {
         });

		 
		 assertEquals(3, pageLivro.getSize());
		 assertThat(pageLivro.getContent()).hasSize(3)
           .extracting(Livro::getTitulo)
           .containsExactlyInAnyOrder("titulo1", "titulo2","titulo3");

		 assertThat(pageLivro.getContent()).hasSize(3)
         .extracting(Livro::getCodigoLivro)
         .containsExactlyInAnyOrder("titulo1-cod-1", "titulo2-cod-2","titulo3-cod-3");


		
	 }
	
	
}
