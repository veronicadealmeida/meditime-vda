package com.medi.service;

import com.medi.mapper.HorarioMapper;
import com.medi.model.Horario;
import com.medi.model.Medicamento;
import com.medi.model.Usuario;
import com.medi.repository.HorarioRepository;
import com.medi.repository.MedicamentoRepository;
import com.medi.repository.UsuarioRepository;
import com.medi.repository.dto.HorarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HorarioService {

    @Autowired
    private HorarioRepository horarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @Autowired
    private HorarioMapper horarioMapper;

    public HorarioDTO create(HorarioDTO horarioDTO) {
        Usuario usuario = usuarioRepository.findById(horarioDTO.usuarioId().getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Medicamento medicamento = medicamentoRepository.findById(horarioDTO.medicamentoId().getId())
                .orElseThrow(() -> new RuntimeException("Medicamento não encontrado"));

//        Horario horario = horarioMapper.toEntity(horarioDTO);
        return horarioMapper.horarioToDTO((horarioRepository.save(horarioMapper.toEntity(horarioDTO))));

//        return horarioMapper.horarioToDTO(saved);
    }

    public Optional<Horario> buscarHorarioPorId(Long id) {
        return horarioRepository.findById(id);
    }

    public List<Horario> listarHorarios() {
        return horarioRepository.findAll();
    }

    public void removerHorario(Long id) {
        horarioRepository.deleteById(id);
    }
}
