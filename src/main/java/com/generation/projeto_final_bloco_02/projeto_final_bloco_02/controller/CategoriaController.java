package com.generation.projeto_final_bloco_02.projeto_final_bloco_02.controller;

import com.generation.projeto_final_bloco_02.projeto_final_bloco_02.model.Categoria;
import com.generation.projeto_final_bloco_02.projeto_final_bloco_02.repository.CategoriaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriaController {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping
	public ResponseEntity<List<Categoria>> getAll() {
		List<Categoria> categorias = categoriaRepository.findAll();
		if (categorias.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(categorias);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Categoria> getById(@PathVariable long id) {
		return categoriaRepository.findById(id).map(categoria -> ResponseEntity.ok(categoria))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/nome/{nomeCategoria}")
	public ResponseEntity<List<Categoria>> getByName(@PathVariable String nomeCategoria) {
		List<Categoria> categorias = categoriaRepository.findAllByNomeCategoriaContainingIgnoreCase(nomeCategoria);
		if (categorias.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(categorias);
	}

	@PostMapping
	public ResponseEntity<Categoria> post(@Valid @RequestBody Categoria categoria) {
		Categoria savedCategoria = categoriaRepository.save(categoria);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedCategoria);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Categoria> put(@PathVariable long id, @Valid @RequestBody Categoria categoria) {
		if (!categoriaRepository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada");
		}
		categoria.setId(id); // Define o ID para a atualização correta
		Categoria updatedCategoria = categoriaRepository.save(categoria);
		return ResponseEntity.status(HttpStatus.OK).body(updatedCategoria);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		Optional<Categoria> categoria = categoriaRepository.findById(id);
		if (categoria.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada");
		}

		categoriaRepository.deleteById(id);
	}
}
