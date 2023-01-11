package com.biblioteca.biblioteca.service;

import org.springframework.stereotype.Service;

import com.biblioteca.biblioteca.base.BaseService;
import com.biblioteca.biblioteca.domain.Livro;
import com.biblioteca.biblioteca.repository.LivroRepository;

@Service
public class LivroService extends BaseService<Livro, LivroRepository>{

}
