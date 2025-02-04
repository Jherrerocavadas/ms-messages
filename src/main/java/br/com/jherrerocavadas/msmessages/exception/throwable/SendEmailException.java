package br.com.jherrerocavadas.msmessages.exception.throwable;


import org.springframework.context.MessageSource;

import java.util.List;

public class SendEmailException extends BaseException{

    public SendEmailException(String message) {
        super(message);
    }

    public SendEmailException(MessageSource messageSource,
                              List<String> parametros) {
        super(messageSource ,"mail.send.error", parametros);
    }
    public SendEmailException(MessageSource messageSource) {
        super(messageSource,"mail.send.error");
    }
}
