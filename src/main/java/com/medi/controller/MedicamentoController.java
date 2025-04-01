package com.medi.controller;

import com.medi.model.Medicamento;
import com.medi.repository.MedicamentoRepository;
import com.medi.repository.UsuarioRepository;
import com.medi.service.MedicamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/medicamentos")
@RequiredArgsConstructor
public class MedicamentoController {


    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    MedicamentoRepository medicamentoRepository;

    @Autowired
    MedicamentoService medicamentoService;


    @PostMapping
    @ResponseStatus( code = HttpStatus.CREATED)
    public com.medi.time.repository.dto.MedicamentoDTO adicionar(@RequestBody com.medi.time.repository.dto.MedicamentoDTO medicamento) {
        return medicamentoService.create(medicamento);
    }


    @GetMapping

    public List<Medicamento> listarMedicamentos() {
        return  medicamentoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Medicamento buscarMedicamentoPorId (@PathVariable Long id) {
        Optional<Medicamento> medicamento = medicamentoService.buscarMedicamentoPorId(id);
        return medicamento.orElse(null);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeMedicamento(@PathVariable Long id) {
        medicamentoService.removerMedicamento(id);
    }

}