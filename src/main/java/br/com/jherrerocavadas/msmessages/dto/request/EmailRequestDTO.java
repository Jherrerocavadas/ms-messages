package br.com.jherrerocavadas.msmessages.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailRequestDTO {

    String[] destinatarios;
    String[] destinatariosCC;
    String[] destinatariosBCC;
    String assunto;
    String mensagem;
    List<AnexoEmailDTO> anexosEmailDTO;
}
