package br.com.correios.service;

import br.com.correios.CorreiosApplication;
import br.com.correios.model.EnderecoStatus;
import br.com.correios.repository.SetupRepository;
import br.com.correios.exception.NotReadyException;
import br.com.correios.exception.NoContextException;
import br.com.correios.repository.EnderecoRepository;
import br.com.correios.repository.EnderecoStatusRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.com.correios.model.Status;
import br.com.correios.model.Endereco;
import org.springframework.stereotype.Service;
import org.springframework.context.event.EventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
@Service
public class CorreiosService {

    private static Logger logger = LoggerFactory.getLogger(CorreiosService.class);

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EnderecoStatusRepository enderecoStatusRepository;

    @Autowired
    private SetupRepository setupRepository;

    public Status getStatus() {
        return enderecoStatusRepository.findById(EnderecoStatus.DEFAULT_ID).orElse(EnderecoStatus.builder().status(Status.NEED_SETUP).build()).getStatus();
    }

    public Endereco getEnderecoPorCep(String cep) throws NoContextException, NotReadyException {
        if (!this.getStatus().equals(Status.SETUP_DONE))
            throw new NotReadyException();

        return enderecoRepository.findById(cep).orElseThrow(NoContextException::new);
    }

    private void saveStatus(Status status) {
        this.enderecoStatusRepository.save(EnderecoStatus
                .builder()
                .id(EnderecoStatus.DEFAULT_ID)
                .status(status)
                .build());
    }

    @EventListener(ApplicationStartedEvent.class)
    protected void setupStartup() {
        try {
            this.setup();
        } catch (Exception e) {
            e.printStackTrace();
            CorreiosApplication.close(1);
        }
    }

    public void setup() {
        logger.info("---------");
        logger.info("---------SETUP RUNNING---------");

        if (this.getStatus().equals(Status.NEED_SETUP)) {
            this.saveStatus(Status.SETUP_RUNNING);
            try {
                this.enderecoRepository.saveAll(this.setupRepository.getFromOrigin());
            } catch (Exception e) {
                this.saveStatus(Status.NEED_SETUP);
            }
            this.saveStatus(Status.SETUP_DONE);
        }
        logger.info("---------");
        logger.info("---------SETUP DONE---------");
    }
}
