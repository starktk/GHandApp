package com.example.GHand.document.fornecedor;

import com.example.GHand.document.enums.Situacao;
import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class Fornecedor {


    private String razaoSocial;
    private String cnpj;
    private Situacao status;
    private Historico historico;

}
