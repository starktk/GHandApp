package com.example.GHand.service;


import com.example.GHand.dto.usuario.UsuarioDto;
import com.example.GHand.dto.usuario.UsuarioRequestDto;
import com.example.GHand.document.Usuario;
import com.example.GHand.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UsuarioService {


    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;

    public Boolean verifyUser(String username){
        Boolean answer;
        if (findUser(username) != null) {
            answer = true;
        } else {
            answer = false;
        }
        return answer;
    }
    public UsuarioRequestDto addUser(UsuarioRequestDto usuarioRequestDto) {
        if (usuarioRequestDto.getName().isEmpty() &&
                usuarioRequestDto.getUsername().isEmpty() && usuarioRequestDto.getPassword().isEmpty()) {
            throw new RuntimeException("Campos inválidos");
        } else if (verifyUser(usuarioRequestDto.getUsername())) {
            throw new RuntimeException("Usuario já existente");
        }
        Usuario userSave = objectMapper.convertValue(usuarioRequestDto, Usuario.class);
        UsuarioRequestDto userReturn = objectMapper.convertValue(usuarioRepository.save(userSave), UsuarioRequestDto.class);
        return userReturn;
    }

    public UsuarioDto findUser(String username) {
        if (username.isEmpty()) {
            throw new RuntimeException("Temporaria");
        }
        Optional<Usuario> usuario = usuarioRepository.findById(username);
        UsuarioDto userReturn = objectMapper.convertValue(usuario, UsuarioDto.class);
        return userReturn;
    }

    public UsuarioDto alterUser(UsuarioDto usuarioDto) {
        if (usuarioDto.getUsername().isEmpty()) {
            throw new RuntimeException("Username inválido");
        } else if (!verifyUser(usuarioDto.getUsername())) {
            throw new RuntimeException("Usuario não existente");
        }
        Usuario userToSave = objectMapper.convertValue(usuarioRepository.
                findById(usuarioDto.getUsername()), Usuario.class);
        userToSave.setUsername(usuarioDto.getUsername());
        userToSave.setName(usuarioDto.getName());
        UsuarioDto userRetorno = objectMapper.convertValue(usuarioRepository
                .save(userToSave), UsuarioDto.class);
        return userRetorno;
    }

    public void deleteUser(String username) {
        if (username.isEmpty()) {
            throw new RuntimeException("Username inválido!!");
        } else if (!verifyUser(username)) {
            throw new RuntimeException("Usuario não existente!!");
        }
        usuarioRepository.delete(objectMapper.convertValue(findUser(username),Usuario.class));
    }
}
