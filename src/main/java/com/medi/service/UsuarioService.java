package com.medi.service;

import com.medi.mapper.UsuarioMapper;
import com.medi.model.Usuario;
import com.medi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    public com.medi.time.repository.dto.UsuarioDTO create(com.medi.time.repository.dto.UsuarioDTO usuario) {
        return usuarioMapper.usuarioToDTO(usuarioRepository.save(usuarioMapper.toEntity(usuario)));
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public void removerUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}