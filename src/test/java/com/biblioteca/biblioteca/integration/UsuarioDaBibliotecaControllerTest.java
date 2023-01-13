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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.biblioteca.biblioteca.domain.Contato;
import com.biblioteca.biblioteca.domain.Endereco;
import com.biblioteca.biblioteca.domain.Livro;
import com.biblioteca.biblioteca.domain.UsuarioDaBiblioteca;
import com.biblioteca.biblioteca.repository.UsuarioDaBibliotecaRepository;
import com.fasterxml.jackson.core.type.TypeReference;

import lombok.SneakyThrows;
import utils.CustomPageImpl;

public class UsuarioDaBibliotecaControllerTest extends IntegrationTest {

	private int contador = 0;

	@Autowired
	private UsuarioDaBibliotecaRepository usuarioDaBibliotecaRepository;

	@Test
	@SneakyThrows
	void shouldDeleteUsuarioDaBiblioteca() {
		UsuarioDaBiblioteca usuarioDaBiblioteca = new UsuarioDaBiblioteca();
		Contato contato1 = new Contato();
		Contato contato2 = new Contato();
		Endereco endereco = new Endereco();

		contato1.setNome("principal");
		contato1.setEmail("test@test.com");
		contato1.setTelefone("44999999999");

		contato2.setNome("secundario");
		contato2.setEmail("test@test.com");
		contato2.setTelefone("44888888888");

		List<Contato> contatos = new ArrayList();

		contatos.add(contato2);
		contatos.add(contato1);

		endereco.setBairro("jardim teste");
		endereco.setCidade("cidade teste");
		endereco.setNumero("400");
		endereco.setRua("rua teste");

		usuarioDaBiblioteca.setContato(contatos);
		usuarioDaBiblioteca.setEndereco(endereco);
		usuarioDaBiblioteca.setDataDeNascimento(LocalDate.of(1998, 10, 20));
		usuarioDaBiblioteca.setNome("Nome Teste");
		usuarioDaBiblioteca.setMatricula("201012345");
		usuarioDaBiblioteca.setCpf("856.678.210-01");

		UsuarioDaBiblioteca usuario = usuarioDaBibliotecaRepository.save(usuarioDaBiblioteca);

		Optional<UsuarioDaBiblioteca> repository = usuarioDaBibliotecaRepository.findById(usuario.getId());
		assertFalse(repository.isEmpty());

		mockMvc.perform(delete("/api/usuariosDaBiblioteca/" + usuario.getId()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		Optional<UsuarioDaBiblioteca> usuarioDaBibliotecaGetById = usuarioDaBibliotecaRepository
				.findById(usuario.getId());
		assertFalse(usuarioDaBibliotecaGetById.isPresent());
	}

	@Test
	@SneakyThrows
	void shouldSaveUsuarioDaBiblioteca() {
		UsuarioDaBiblioteca usuarioDaBiblioteca = new UsuarioDaBiblioteca();
		Contato contato1 = new Contato();
		Contato contato2 = new Contato();
		Endereco endereco = new Endereco();

		contato1.setNome("principal");
		contato1.setEmail("test@test.com");
		contato1.setTelefone("44999999999");

		contato2.setNome("secundario");
		contato2.setEmail("test@test.com");
		contato2.setTelefone("44888888888");

		List<Contato> contatos = new ArrayList();

		contatos.add(contato2);
		contatos.add(contato1);

		endereco.setBairro("jardim teste");
		endereco.setCidade("cidade teste");
		endereco.setNumero("400");
		endereco.setRua("rua teste");

		usuarioDaBiblioteca.setContato(contatos);
		usuarioDaBiblioteca.setEndereco(endereco);
		usuarioDaBiblioteca.setDataDeNascimento(LocalDate.of(1998, 10, 20));
		usuarioDaBiblioteca.setNome("Nome Teste");
		usuarioDaBiblioteca.setMatricula("201012345");
		usuarioDaBiblioteca.setCpf("856.678.210-01");

		String jsonUsuarioDaBibliotecaString = objectMapper.writeValueAsString(usuarioDaBiblioteca);

		MvcResult result = mockMvc.perform(post("/api/usuariosDaBiblioteca").contentType(MediaType.APPLICATION_JSON)
				.content(jsonUsuarioDaBibliotecaString)).andExpect(status().is2xxSuccessful()).andReturn();

		UsuarioDaBiblioteca usuarioResponse = objectMapper
				.readValue(result.getResponse().getContentAsString(StandardCharsets.UTF_8), UsuarioDaBiblioteca.class);

		assertNotNull(usuarioResponse.getId());
		assertEquals(usuarioResponse.getCpf(), "856.678.210-01");
		assertEquals(usuarioResponse.getDataDeNascimento(), LocalDate.of(1998, 10, 20));
		assertEquals(usuarioResponse.getMatricula(), "201012345");
		assertEquals(usuarioResponse.getNome(), "Nome Teste");
		assertEquals(usuarioResponse.getEndereco().getBairro(), "jardim teste");
		assertEquals(usuarioResponse.getContato().get(0).getNome(), "secundario");
		assertEquals(usuarioResponse.getContato().get(1).getNome(), "principal");
		assertEquals(usuarioResponse.getContato().get(0).getEmail(), "test@test.com");
		assertEquals(usuarioResponse.getContato().get(0).getTelefone(), "44888888888");
		assertEquals(usuarioResponse.getContato().get(1).getTelefone(), "44999999999");
	}

	@Test
	@SneakyThrows
	void shouldUpdateUsuarioDaBiblioteca() {

		UsuarioDaBiblioteca usuarioDaBiblioteca = new UsuarioDaBiblioteca();

		Contato contato1 = new Contato();
		Contato contato2 = new Contato();
		Endereco endereco = new Endereco();

		contato1.setNome("principal");
		contato1.setEmail("test@test.com");
		contato1.setTelefone("44999999999");

		contato2.setNome("secundario");
		contato2.setEmail("test@test.com");
		contato2.setTelefone("44888888888");

		List<Contato> contatos = new ArrayList();

		contatos.add(contato2);
		contatos.add(contato1);

		endereco.setBairro("jardim teste");
		endereco.setCidade("cidade teste");
		endereco.setNumero("400");
		endereco.setRua("rua teste");

		usuarioDaBiblioteca.setContato(contatos);
		usuarioDaBiblioteca.setEndereco(endereco);
		usuarioDaBiblioteca.setDataDeNascimento(LocalDate.of(1998, 10, 20));
		usuarioDaBiblioteca.setNome("Nome Teste");
		usuarioDaBiblioteca.setMatricula("201012345");
		usuarioDaBiblioteca.setCpf("856.678.210-01");

		usuarioDaBiblioteca = usuarioDaBibliotecaRepository.save(usuarioDaBiblioteca);

		endereco.setBairro("bairro novo");
		usuarioDaBiblioteca.setEndereco(endereco);
		usuarioDaBiblioteca.setNome("Nome Novo");

		String jsonUsuarioString = objectMapper.writeValueAsString(usuarioDaBiblioteca);

		MvcResult result = mockMvc
				.perform(put("/api/usuariosDaBiblioteca/" + usuarioDaBiblioteca.getId())
						.contentType(MediaType.APPLICATION_JSON).content(jsonUsuarioString))
				.andExpect(status().is2xxSuccessful()).andReturn();

		String res = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		UsuarioDaBiblioteca usuarioResponse = objectMapper.readValue(res, UsuarioDaBiblioteca.class);

		assertEquals(usuarioResponse.getId(), usuarioDaBiblioteca.getId());
		assertEquals("856.678.210-01", usuarioResponse.getCpf());
		assertEquals("201012345", usuarioResponse.getMatricula());
		assertEquals("bairro novo", usuarioResponse.getEndereco().getBairro());
		assertEquals("Nome Novo", usuarioResponse.getNome());
		assertNotEquals("jardim teste", usuarioResponse.getEndereco().getBairro());
		assertNotEquals("Nome Teste", usuarioResponse.getNome());

	}

	@Test
	@SneakyThrows
	void shouldThrowNotFoundErrorWhenUpdateUsuarioDaBibliotecaWithNotExistsId() {
		UsuarioDaBiblioteca usuarioDaBiblioteca = new UsuarioDaBiblioteca();
		usuarioDaBiblioteca.setId(500L);

		String jsonUsuarioString = objectMapper.writeValueAsString(usuarioDaBiblioteca);

		mockMvc.perform(put("/api/usuariosDaBiblioteca/" + usuarioDaBiblioteca.getId())
				.contentType(MediaType.APPLICATION_JSON).content(jsonUsuarioString)).andExpect(status().isNotFound());

	}

	@Test
	@SneakyThrows
	void shouldFindAllUsuariosDaBiblioteca() {
		List<String> cpfs = new ArrayList<>();

		cpfs.add("856.678.210-01");
		cpfs.add("557.202.400-71");
		cpfs.add("184.935.330-17");
		cpfs.add("766.863.670-60");
		cpfs.add("614.163.580-64");
		cpfs.add("186.431.200-90");

		cpfs.forEach(cpf -> {
			contador++;
			UsuarioDaBiblioteca usuarioDaBiblioteca = new UsuarioDaBiblioteca();

			Contato contato1 = new Contato();
			Contato contato2 = new Contato();
			Endereco endereco = new Endereco();

			contato1.setNome("principal");
			contato1.setEmail("test@test.com");
			contato1.setTelefone("44999999999");

			contato2.setNome("secundario");
			contato2.setEmail("test@test.com");
			contato2.setTelefone("44888888888");

			List<Contato> contatos = new ArrayList();

			contatos.add(contato2);
			contatos.add(contato1);

			endereco.setBairro("jardim teste");
			endereco.setCidade("cidade teste");
			endereco.setNumero("400");
			endereco.setRua("rua teste");

			usuarioDaBiblioteca.setContato(contatos);
			usuarioDaBiblioteca.setEndereco(endereco);
			usuarioDaBiblioteca.setDataDeNascimento(LocalDate.of(1998, 10, 20));
			usuarioDaBiblioteca.setNome("Nome " + contador);
			usuarioDaBiblioteca.setMatricula("2010" + contador);
			usuarioDaBiblioteca.setCpf(cpf);

			usuarioDaBibliotecaRepository.save(usuarioDaBiblioteca);

		});

		MvcResult result = mockMvc
				.perform(get("/api/usuariosDaBiblioteca/list").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		List<UsuarioDaBiblioteca> usuarioDaBiblioteca = objectMapper.readValue(
				result.getResponse().getContentAsString(StandardCharsets.UTF_8),

				new TypeReference<List<UsuarioDaBiblioteca>>() {

				});

		assertEquals(6, usuarioDaBiblioteca.size());
		assertEquals("856.678.210-01", usuarioDaBiblioteca.get(0).getCpf());
		assertEquals("20101", usuarioDaBiblioteca.get(0).getMatricula());
		assertEquals("Nome 1", usuarioDaBiblioteca.get(0).getNome());
		assertEquals("184.935.330-17", usuarioDaBiblioteca.get(2).getCpf());
		assertEquals("20103", usuarioDaBiblioteca.get(2).getMatricula());
		assertEquals("Nome 3", usuarioDaBiblioteca.get(2).getNome());
		assertEquals("186.431.200-90", usuarioDaBiblioteca.get(5).getCpf());
		assertEquals("20106", usuarioDaBiblioteca.get(5).getMatricula());
		assertEquals("Nome 6", usuarioDaBiblioteca.get(5).getNome());

	}

	@Test
	@SneakyThrows
	void shouldFindAllPageableUsuarioDaBiblioteca() {

		List<String> cpfs = new ArrayList<>();

		cpfs.add("856.678.210-01");
		cpfs.add("557.202.400-71");
		cpfs.add("184.935.330-17");
		cpfs.add("766.863.670-60");
		cpfs.add("614.163.580-64");
		cpfs.add("186.431.200-90");

		contador = 0;

		cpfs.forEach(cpf -> {
			contador++;
			UsuarioDaBiblioteca usuarioDaBiblioteca = new UsuarioDaBiblioteca();

			Contato contato1 = new Contato();
			Contato contato2 = new Contato();
			Endereco endereco = new Endereco();

			contato1.setNome("principal");
			contato1.setEmail("test@test.com");
			contato1.setTelefone("44999999999");

			contato2.setNome("secundario");
			contato2.setEmail("test@test.com");
			contato2.setTelefone("44888888888");

			List<Contato> contatos = new ArrayList();

			contatos.add(contato2);
			contatos.add(contato1);

			endereco.setBairro("jardim teste");
			endereco.setCidade("cidade teste");
			endereco.setNumero("400");
			endereco.setRua("rua teste");

			usuarioDaBiblioteca.setContato(contatos);
			usuarioDaBiblioteca.setEndereco(endereco);
			usuarioDaBiblioteca.setDataDeNascimento(LocalDate.of(1998, 10, 20));
			usuarioDaBiblioteca.setNome("Nome " + contador);
			usuarioDaBiblioteca.setMatricula("2010" + contador);
			usuarioDaBiblioteca.setCpf(cpf);

			usuarioDaBibliotecaRepository.save(usuarioDaBiblioteca);

		});

		MvcResult result = mockMvc
				.perform(get("/api/usuariosDaBiblioteca?size=3&sort=cpf").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		Page<UsuarioDaBiblioteca> pageUsuario = objectMapper.readValue(
				result.getResponse().getContentAsString(StandardCharsets.UTF_8),
				new TypeReference<CustomPageImpl<UsuarioDaBiblioteca>>() {
				});

		assertEquals(3, pageUsuario.getSize());
		assertThat(pageUsuario.getContent()).hasSize(3).extracting(UsuarioDaBiblioteca::getCpf)
				.containsExactlyInAnyOrder("184.935.330-17", "186.431.200-90", "557.202.400-71");

	}

	@Test
	@SneakyThrows
	void shouldFindByIdUsuarioDaBiblioteca() {

		UsuarioDaBiblioteca usuarioDaBiblioteca = new UsuarioDaBiblioteca();

		Contato contato1 = new Contato();
		Contato contato2 = new Contato();
		Endereco endereco = new Endereco();

		contato1.setNome("principal");
		contato1.setEmail("test@test.com");
		contato1.setTelefone("44999999999");

		contato2.setNome("secundario");
		contato2.setEmail("test@test.com");
		contato2.setTelefone("44888888888");

		List<Contato> contatos = new ArrayList();

		contatos.add(contato2);
		contatos.add(contato1);

		endereco.setBairro("jardim teste");
		endereco.setCidade("cidade teste");
		endereco.setNumero("400");
		endereco.setRua("rua teste");

		usuarioDaBiblioteca.setContato(contatos);
		usuarioDaBiblioteca.setEndereco(endereco);
		usuarioDaBiblioteca.setDataDeNascimento(LocalDate.of(1998, 10, 20));
		usuarioDaBiblioteca.setNome("Nome Teste");
		usuarioDaBiblioteca.setMatricula("201012345");
		usuarioDaBiblioteca.setCpf("856.678.210-01");

		usuarioDaBiblioteca = usuarioDaBibliotecaRepository.save(usuarioDaBiblioteca);

		MvcResult result = mockMvc.perform(
				get("/api/usuariosDaBiblioteca/" + usuarioDaBiblioteca.getId()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		UsuarioDaBiblioteca usuarioResponse = objectMapper
				.readValue(result.getResponse().getContentAsString(StandardCharsets.UTF_8), UsuarioDaBiblioteca.class);

		assertNotNull(usuarioResponse.getId());
		assertEquals(usuarioResponse.getCpf(), "856.678.210-01");
		assertEquals(usuarioResponse.getDataDeNascimento(), LocalDate.of(1998, 10, 20));
		assertEquals(usuarioResponse.getMatricula(), "201012345");
		assertEquals(usuarioResponse.getNome(), "Nome Teste");
		assertEquals(usuarioResponse.getEndereco().getBairro(), "jardim teste");
		assertEquals(usuarioResponse.getContato().get(0).getNome(), "secundario");
		assertEquals(usuarioResponse.getContato().get(1).getNome(), "principal");
		assertEquals(usuarioResponse.getContato().get(0).getEmail(), "test@test.com");
		assertEquals(usuarioResponse.getContato().get(0).getTelefone(), "44888888888");
		assertEquals(usuarioResponse.getContato().get(1).getTelefone(), "44999999999");


	}

}
