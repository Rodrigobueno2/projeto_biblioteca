package com.biblioteca.biblioteca.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.biblioteca.base.BaseController;
import com.biblioteca.biblioteca.domain.Livro;
import com.biblioteca.biblioteca.repository.LivroRepository;
import com.biblioteca.biblioteca.service.LivroService;

@Controller
@RestController
@RequestMapping("/api/livros")
public class LivroController extends BaseController<Livro, LivroRepository, LivroService>{

}
