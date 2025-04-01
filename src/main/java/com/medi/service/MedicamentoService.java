package com.medi.service;

import com.medi.mapper.MedicamentoMapper;
import com.medi.model.Medicamento;
import com.medi.repository.MedicamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicamentoService {

    @Autowired
    MedicamentoRepository medicamentoRepository;

    @Autowired
    MedicamentoMapper medicamentoMapper;

    public com.medi.time.repository.dto.MedicamentoDTO create(com.medi.time.repository.dto.MedicamentoDTO medicamentoDTO) {
        return medicamentoMapper.medicamentoToDTO((medicamentoRepository.save(medicamentoMapper.toEntity(medicamentoDTO))));
    }

    public List<Medicamento>listarMedicamentos() {
        return medicamentoRepository.findAll();
    }

    public Optional<Medicamento>buscarMedicamentoPorId(Long id) {
        return medicamentoRepository.findById(id);
    }

    public void removerMedicamento(Long id) {
        medicamentoRepository.deleteById(id);
    }


}