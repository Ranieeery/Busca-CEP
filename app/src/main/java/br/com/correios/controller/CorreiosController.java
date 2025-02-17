package br.com.correios.controller;

import br.com.correios.model.Endereco;
import br.com.correios.service.CorreiosService;
import br.com.correios.exception.NotReadyException;
import br.com.correios.exception.NoContextException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class CorreiosController {

    @Autowired
    private CorreiosService correiosService;

    @GetMapping("status")
    public String getStatus() {
        return "Status: " + this.correiosService.getStatus();
    }

    @GetMapping("cep/{cep}")
    public Endereco getEnredecoPorCep(@PathVariable("cep") String cep) throws NoContextException, NotReadyException {
        return this.correiosService.getEnderecoPorCep(cep);
    }

}
