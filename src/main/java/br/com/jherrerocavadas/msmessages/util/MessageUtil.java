package br.com.jherrerocavadas.msmessages.util;

import org.springframework.context.MessageSource;

import java.util.Locale;

public class MessageUtil {


    public static String messageWithParameters(MessageSource messageSource, String placeholder, Object ...parametros){
        return messageSource.getMessage(placeholder, parametros, Locale.getDefault());
    }

    public static String messageWithoutParameters(MessageSource messageSource, String placeholder){
        return messageSource.getMessage(placeholder, null, Locale.getDefault());
    }


}
