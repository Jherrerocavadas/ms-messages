package br.com.jherrerocavadas.msmessages.config;

import br.com.jherrerocavadas.msmessages.client.AuthenticationClient;
import br.com.jherrerocavadas.msmessages.exception.throwable.UnauthorizedAcessException;
import br.com.jherrerocavadas.msmessages.util.MessageUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final String API_TOKEN_HEADER = "X-API-TOKEN";


    private final ObjectProvider<AuthenticationClient> authenticationClient;

    private final MessageSource messageSource;
    @Autowired
    public AuthenticationInterceptor(ObjectProvider<AuthenticationClient> authenticationClient,
                                     MessageSource messageSource) {
        this.authenticationClient = authenticationClient;
        this.messageSource = messageSource;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Interceptando requisição - Buscando Header de autenticação");
        if (request.getHeader(API_TOKEN_HEADER) == null){
            log.info("Token de autenticação não enviado");
            throw new UnauthorizedAcessException(MessageUtil.messageWithoutParameters(messageSource, "unauthorized.access.exception"));
        }
        var client = authenticationClient.getObject();
        boolean isAuthenticated = Boolean.TRUE.equals(client.checarAutenticacao(request.getHeader(API_TOKEN_HEADER)).getBody());
        log.info("Requisição autenticada? {}", isAuthenticated? "Sim": "Não");
        return isAuthenticated;

    }
}