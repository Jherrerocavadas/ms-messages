package br.com.jherrerocavadas.msmessages.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnexoEmailDTO {
    private String nomeArquivo;
    private String conteudoBase64;
    private String extensaoArquivo;
    @Builder.Default
    private boolean incorporado = false;
}
