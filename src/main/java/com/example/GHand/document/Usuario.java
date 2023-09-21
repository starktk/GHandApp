package com.example.GHand.document;

import com.example.GHand.document.fornecedor.Fornecedor;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "Usuario")
public class Usuario {


    @Id
    private String username;
    private String name;
    private String password;
    private List<Fornecedor> fornecedors;

}
