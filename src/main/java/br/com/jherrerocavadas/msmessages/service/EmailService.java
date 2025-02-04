package br.com.jherrerocavadas.msmessages.service;

import br.com.jherrerocavadas.msmessages.dto.request.AnexoEmailDTO;
import br.com.jherrerocavadas.msmessages.dto.request.EmailRequestDTO;
import br.com.jherrerocavadas.msmessages.dto.response.EmailResponseDTO;
import br.com.jherrerocavadas.msmessages.exception.throwable.AttachmentOperationFailedException;
import br.com.jherrerocavadas.msmessages.util.MessageUtil;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Objects;

@SuppressWarnings({"ReturnInsideFinallyBlock", "finally"})
@Service
@Slf4j
public class EmailService {

    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    @Value("${spring.mail.username}")
    private String remetente;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, MessageSource messageSource) {
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
    }


    public EmailResponseDTO sendSimpleMessage(EmailRequestDTO emailRequestDTO) {

        var mailMessage = new SimpleMailMessage();
        HttpStatus responseStatus = HttpStatus.OK;
        String responseMessage = MessageUtil.messageWithoutParameters(
                messageSource,
                "mail.send.successful"
        );

        try{
            mailMessage.setTo(emailRequestDTO.getDestinatarios());

            if(Objects.nonNull(emailRequestDTO.getDestinatariosCC())){
                mailMessage.setCc(emailRequestDTO.getDestinatariosCC());
            }

            if(Objects.nonNull(emailRequestDTO.getDestinatariosBCC())){
                mailMessage.setBcc(emailRequestDTO.getDestinatariosBCC());
            }

            mailMessage.setFrom(remetente);
            mailMessage.setSubject(emailRequestDTO.getAssunto());
            mailMessage.setText(emailRequestDTO.getMensagem());

            javaMailSender.send(mailMessage);

            log.info("[finish] {}",
                    responseMessage
            );
        }
        catch (Exception e){
            responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseMessage = MessageUtil.messageWithoutParameters(
                    messageSource,
                    "mail.send.error"
            );

            log.error("[finish w/error] {}: {}",
                    responseMessage,
                    e.getLocalizedMessage()
            );

        }
        finally {
            return EmailResponseDTO.builder()
                    .statusCode(responseStatus.value())
                    .statusCodeDescription(responseStatus.getReasonPhrase())
                    .message(responseMessage)
                    .build();
        }

    }

    public EmailResponseDTO sendMimeMessage(EmailRequestDTO emailRequestDTO) {

        var mailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        HttpStatus responseStatus = HttpStatus.OK;
        String responseMessage = MessageUtil.messageWithoutParameters(
                messageSource,
                "mail.send.successful"
        );

        try{
            var anexosDTO = emailRequestDTO.getAnexosEmailDTO();
            helper = new MimeMessageHelper(mailMessage, true);
            helper.setTo(emailRequestDTO.getDestinatarios());

            if(Objects.nonNull(emailRequestDTO.getDestinatariosCC())){
                helper.setCc(emailRequestDTO.getDestinatariosCC());
            }

            if(Objects.nonNull(emailRequestDTO.getDestinatariosBCC())){
                helper.setBcc(emailRequestDTO.getDestinatariosBCC());
            }

            helper.setFrom(remetente);
            helper.setSubject(emailRequestDTO.getAssunto());
            helper.setText(emailRequestDTO.getMensagem(), true);
            if(Objects.nonNull(anexosDTO) && !anexosDTO.isEmpty()){
                anexosDTO.forEach(anexoDTO -> addAttachment(helper, anexoDTO));
            }

            javaMailSender.send(mailMessage);

            log.info("[finish] {}",
                    responseMessage
            );
        }
        catch (Exception e){
            responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseMessage = MessageUtil.messageWithoutParameters(
                    messageSource,
                    "mail.send.error"
            );

            log.error("[finish w/error] {}: {}",
                    responseMessage,
                    e.getLocalizedMessage()
            );

        }
        finally {
            return EmailResponseDTO.builder()
                    .statusCode(responseStatus.value())
                    .statusCodeDescription(responseStatus.getReasonPhrase())
                    .message(responseMessage)
                    .build();
        }
    }

    private void addAttachment(MimeMessageHelper helper, AnexoEmailDTO anexoEmailDTO) {
        try {
            String nomeArquivo = anexoEmailDTO.getNomeArquivo();
            String extensaoArquivo = anexoEmailDTO.getExtensaoArquivo();

            if ((nomeArquivo != null) && (anexoEmailDTO.getConteudoBase64() != null)) {
                var base64Decoded = Base64.getMimeDecoder().decode(anexoEmailDTO.getConteudoBase64());

                String nomeArquivoFormatado = nomeArquivo.lastIndexOf(".") != -1 ?
                        nomeArquivo :
                        nomeArquivo.concat(extensaoArquivo);

                if (anexoEmailDTO.isIncorporado()) {
                    helper.addInline(
                            nomeArquivo,
                            new ByteArrayResource(base64Decoded)
                    );
                } else {
                    helper.addAttachment(
                            nomeArquivoFormatado,
                            new ByteArrayResource(base64Decoded)
                    );
                }
            }

        } catch (MessagingException e) {
            throw new AttachmentOperationFailedException(e.getLocalizedMessage());
        }
    }
}

