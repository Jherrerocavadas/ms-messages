package br.com.jherrerocavadas.msmessages.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailResponseDTO {
    Integer statusCode;
    String statusCodeDescription;
    String message;
}
