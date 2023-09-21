package com.example.GHand.service;

import com.example.GHand.document.fornecedor.Historico;
import com.example.GHand.document.fornecedor.enums.SituacaoProduto;
import com.example.GHand.document.fornecedor.product.Produto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HistoricoService {

    private final ObjectMapper objectMapper;

    private Historico historico;

    private void addProdutoToHistorico(Produto produto) {
        if (produto.getIsReceived() != SituacaoProduto.RECEBIDO) {

        }

        historico.addProdutos(produto);
        Boolean isFutureDate = historico.getProductsReceived()
                .stream()
                .anyMatch(produto1 -> produto1.getDateToReceiveOrReceived() == produto.getDateToReceiveOrReceived());



    }
//
//    public Boolean verifyFutureDate() {
//
//    }
}
