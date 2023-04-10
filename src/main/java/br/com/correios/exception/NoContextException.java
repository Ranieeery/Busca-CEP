package br.com.correios.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.NO_CONTENT) // 204
public class NoContextException extends Exception{
}
