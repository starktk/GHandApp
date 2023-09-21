package com.example.GHand.repository;

import com.example.GHand.document.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {



}
