package br.com.jherrerocavadas.msmessages.config;

import br.com.jherrerocavadas.msmessages.exception.dto.StandardExceptionResponseDTO;
import br.com.jherrerocavadas.msmessages.exception.throwable.SendEmailException;
import ch.qos.logback.core.model.processor.ProcessorException;
import feign.FeignException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandlerConfig extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<StandardExceptionResponseDTO> handleException(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .body(StandardExceptionResponseDTO.builder()
                        .timestamp(LocalDateTime.now())
                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .error("Erro interno")
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<StandardExceptionResponseDTO> handleRuntimeException(RuntimeException ex){
        logger.error(ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .body(StandardExceptionResponseDTO.builder()
                        .timestamp(LocalDateTime.now())
                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .error("Erro ao processar a requisição")
                        .message("Ocorreu um erro interno ao processar a requisição")
                        .build());
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<StandardExceptionResponseDTO> handleBadRequestException(BadRequestException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(StandardExceptionResponseDTO.builder()
                        .timestamp(LocalDateTime.now())
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .error("Requisição inválida")
                        .message(ex.getMessage())
                        .build());
    }


    @ExceptionHandler({HttpServerErrorException.InternalServerError.class})
    public ResponseEntity<StandardExceptionResponseDTO> handleNotFoundException(HttpServerErrorException.InternalServerError ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .body(StandardExceptionResponseDTO.builder()
                        .timestamp(LocalDateTime.now())
                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .error("Erro ao processar a requisição")
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler({ProcessorException.class})
    public ResponseEntity<StandardExceptionResponseDTO> handleProcessorException(ProcessorException ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .body(StandardExceptionResponseDTO.builder()
                        .timestamp(LocalDateTime.now())
                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .error("Erro de processamento")
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler({SendEmailException.class})
    public ResponseEntity<StandardExceptionResponseDTO> handleSendEmailException(SendEmailException ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .body(StandardExceptionResponseDTO.builder()
                        .timestamp(LocalDateTime.now())
                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .error("Erro ao enviar email")
                        .message(ex.getMessage())
                        .build());
    }

//TODO: Extrair mensagem de erro
    @ExceptionHandler({FeignException.class})
    public ResponseEntity<StandardExceptionResponseDTO> handleFeignException(FeignException ex){
        return ResponseEntity.status(ex.status())
                .body(StandardExceptionResponseDTO.builder()
                        .timestamp(LocalDateTime.now())
                        .statusCode(ex.status())
                        .error(ex.getLocalizedMessage())
                        .message(ex.getLocalizedMessage())
                        .build());
    }

}
