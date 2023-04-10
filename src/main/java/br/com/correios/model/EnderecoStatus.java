package br.com.correios.model;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
@Entity
public class EnderecoStatus {
    public static final int DEFAULT_ID = 1;

    @Id
    private int id;
    private Status status;
}
