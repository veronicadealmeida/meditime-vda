package com.medi.repository.dto;

import com.medi.model.Medicamento;
import com.medi.model.Usuario;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.UUID;
public record HorarioDTO(
        Long id,
        Usuario usuarioId,
        Medicamento medicamentoId
) {
}
