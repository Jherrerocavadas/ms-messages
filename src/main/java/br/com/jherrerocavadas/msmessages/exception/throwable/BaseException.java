package br.com.jherrerocavadas.msmessages.exception.throwable;

import br.com.jherrerocavadas.msmessages.util.MessageUtil;
import org.springframework.context.MessageSource;

public class BaseException extends RuntimeException{

    public BaseException(String message) {
        super(message);
    }

    public BaseException(MessageSource messageSource,
                         String placeholder,
                         Object ...parametros) {
        super(MessageUtil.messageWithParameters(
                messageSource,
                placeholder,
                parametros)
        );
    }
    public BaseException(MessageSource messageSource,
                         String placeholder) {
        super(MessageUtil.messageWithoutParameters(
                messageSource,
                placeholder)
        );
    }
}
