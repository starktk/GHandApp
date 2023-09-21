package com.example.GHand.document.fornecedor.product;

import com.example.GHand.document.fornecedor.enums.SituacaoProduto;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Produto {


    private String nameProduct;
    private Integer amount;
    private LocalDate dateToReceiveOrReceived;
    private SituacaoProduto isReceived;



}
