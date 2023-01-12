package com.biblioteca.biblioteca.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.biblioteca.base.BaseController;
import com.biblioteca.biblioteca.domain.UsuarioDaBiblioteca;
import com.biblioteca.biblioteca.repository.UsuarioDaBibliotecaRepository;
import com.biblioteca.biblioteca.service.UsuarioDaBibliotecaService;

@Controller
@RestController
@RequestMapping("/api/usuariosDaBiblioteca")
public class UsuarioDaBibliotecaController extends BaseController<UsuarioDaBiblioteca, UsuarioDaBibliotecaRepository, UsuarioDaBibliotecaService> {

}
