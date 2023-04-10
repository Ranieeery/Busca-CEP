package br.com.correios.model;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
@Entity
public class Endereco {
    @Id
    private String cep;
    private String rua;
    private String bairro;
    private String estado;
    private String cidade;
}
