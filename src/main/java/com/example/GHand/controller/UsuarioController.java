package com.example.GHand.controller;

import com.example.GHand.dto.usuario.UsuarioDto;
import com.example.GHand.dto.usuario.UsuarioRequestDto;
import com.example.GHand.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/usuario")
public class UsuarioController {


    private final UsuarioService usuarioService;

    @PostMapping("/createUser")
    public ResponseEntity insertUser(@RequestBody UsuarioRequestDto usuarioRequestDto) {
        usuarioService.addUser(usuarioRequestDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<UsuarioDto> findUser(@PathVariable ("id") String username) {
        return new ResponseEntity<>(usuarioService.findUser(username), HttpStatus.FOUND);
    }

    @PutMapping("/alterUser")
    public ResponseEntity<UsuarioDto> alterUser(@RequestBody UsuarioDto usuarioDto) {
        return new ResponseEntity<>(usuarioService.alterUser(usuarioDto), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity deleteUser(@PathVariable ("id") String username) {
        usuarioService.deleteUser(username);
        return new ResponseEntity(HttpStatus.OK);
    }
}
