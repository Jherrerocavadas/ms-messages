package br.com.jherrerocavadas.msmessages.exception.throwable;

import org.springframework.context.MessageSource;

public class UnauthorizedAcessException extends BaseException{

    public UnauthorizedAcessException(String message) {
        super(message);
    }

    public UnauthorizedAcessException(MessageSource messageSource,
                                      Object ...parametros) {
        super(messageSource ,"unauthorized.access.exception", parametros);
    }
    public UnauthorizedAcessException(MessageSource messageSource) {
        super(messageSource,"unauthorized.access.exception");
    }
}