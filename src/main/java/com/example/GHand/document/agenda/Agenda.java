package com.example.GHand.document.agenda;

import com.example.GHand.document.fornecedor.product.Produto;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class Agenda {

    private List<Produto> produtos;
    private LocalDate mes;
}
