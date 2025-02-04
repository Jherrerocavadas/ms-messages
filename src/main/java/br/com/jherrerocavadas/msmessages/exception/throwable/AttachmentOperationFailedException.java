package br.com.jherrerocavadas.msmessages.exception.throwable;

import org.springframework.context.MessageSource;

public class AttachmentOperationFailedException extends BaseException{

    public AttachmentOperationFailedException(String message) {
        super(message);
    }

    public AttachmentOperationFailedException(MessageSource messageSource,
                              Object ...parametros) {
        super(messageSource ,"mail.attachment.error", parametros);
    }
    public AttachmentOperationFailedException(MessageSource messageSource) {
        super(messageSource,"mail.attachment.error");
    }
}