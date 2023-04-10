package br.com.correios.repository;

import br.com.correios.model.Endereco;
import org.springframework.data.repository.CrudRepository;

public interface EnderecoRepository extends CrudRepository<Endereco, String> {
}
