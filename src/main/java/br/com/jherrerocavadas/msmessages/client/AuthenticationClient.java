package br.com.jherrerocavadas.msmessages.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(url = "${ms-authentication.url}", name = "ms-authentication")
public interface AuthenticationClient {

    @PostMapping("/auth/v1/check-token")
    public ResponseEntity<Boolean> checarAutenticacao(@RequestHeader("X-API-TOKEN") String token);
}
