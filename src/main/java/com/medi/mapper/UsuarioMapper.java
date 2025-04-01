package com.medi.mapper;

import com.medi.model.Usuario;
import com.medi.time.repository.dto.UsuarioDTO;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
    public UsuarioDTO usuarioToDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getTelefone()
        );
    }
    public Usuario toEntity( UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();

        if (usuario.getId() != null) {
            usuario.setId(usuarioDTO.id());
        }

        usuario.setNome(usuarioDTO.nome());
        usuario.setTelefone(usuarioDTO.telefone());

        return usuario;
    }
}
