package br.com.jherrerocavadas.msmessages.controller;

import br.com.jherrerocavadas.msmessages.dto.request.EmailRequestDTO;
import br.com.jherrerocavadas.msmessages.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/emails/v1")
@Tag(name = "Email", description = "Operação envios de email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }


    @Operation(summary =  "Enviar Email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Email enviado"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @PostMapping("/enviar")
    public ResponseEntity<Object> enviarEmail(@RequestBody EmailRequestDTO emailRequestDTO){
        return ResponseEntity.ok(emailService.sendMimeMessage(emailRequestDTO));
    }

    @Operation(summary =  "Enviar Email simples")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Email enviado"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @PostMapping("/enviar/simples")
    public ResponseEntity<Object> enviarEmailSimples(@RequestBody EmailRequestDTO emailRequestDTO){
        return ResponseEntity.ok(emailService.sendSimpleMessage(emailRequestDTO));
    }
}
