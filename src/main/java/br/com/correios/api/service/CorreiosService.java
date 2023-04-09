package br.com.correios.api.service;

import br.com.correios.api.exception.NoContextException;
import br.com.correios.api.model.EnderecoStatus;
import br.com.correios.api.repository.EnderecoRepository;
import br.com.correios.api.repository.EnderecoStatusRepository;

import br.com.correios.api.model.Status;
import br.com.correios.api.model.Endereco;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CorreiosService {
    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EnderecoStatusRepository enderecoStatusRepository;

    public Status getStatus() {
        return this.enderecoStatusRepository.findById(enderecoStatusRepository.findById(EnderecoStatus)).orElseThrow(NoContextException::new).getStatus();
    }

    public Endereco getEnderecoPorCep(String cep) throws NoContextException{
        return enderecoRepository.findById(cep).orElseThrow(NoContextException::new);
    }


    public void setup() {

    }
}
