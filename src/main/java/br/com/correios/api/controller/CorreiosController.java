package br.com.correios.api.controller;

import br.com.correios.api.exception.NoContextException;
import br.com.correios.api.model.Endereco;
import br.com.correios.api.service.CorreiosService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CorreiosController {

    @Autowired
    private CorreiosService correiosService;

    @GetMapping("/status")
    public String getStatus() {
        return "Status: " + this.correiosService.getStatus();
    }

    @GetMapping("/cep/{cep}")
    public Endereco getEnredecoPorCep(@PathVariable("cep") String cep) throws NoContextException {
        return this.correiosService.getEnderecoPorCep(cep);
    }

}
