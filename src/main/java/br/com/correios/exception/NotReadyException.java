package br.com.correios.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.SERVICE_UNAVAILABLE, reason = "Esse serviço está sendo instalado, por favor aguarde um pouco.")
public class NotReadyException extends RuntimeException{
}
