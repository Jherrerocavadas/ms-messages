package br.com.jherrerocavadas.msmessages.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StandardExceptionResponseDTO {
    private LocalDateTime timestamp;
    private Integer statusCode;
    private String error;
    private String message;
    @Builder.Default
    private UUID uuid = UUID.randomUUID();
}
