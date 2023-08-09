package com.ms.email.controllers;

import com.ms.email.dtos.EmailDto;
import com.ms.email.models.EmailModel;
import com.ms.email.services.EmailService;
import jakarta.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/emails")
public class EmailController {
    final
    EmailService emailService;
    Logger logger = LogManager.getLogger(EmailController.class); // Instanciando o Logger para gerar logs. O parâmetro é a classe que gera o log. Pode ser usado o this.getClass() para pegar a classe atual.

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }


    @PostMapping("/sendingEmail")
    public ResponseEntity<EmailModel> sendingEmail(@RequestBody @Valid EmailDto emailDto) {
        var emailModel = new EmailModel();
        BeanUtils.copyProperties(emailDto, emailModel);
        emailService.sendEmail(emailModel);
        return new ResponseEntity<>(emailModel, HttpStatus.CREATED);
    }

    @GetMapping("/getAllEmails")
    public ResponseEntity<Page<EmailModel>> getAllEmails(@PageableDefault(size = 5, sort = "emailId", direction = Sort.Direction.DESC) Pageable pageable) {
        logger.trace("TRACE"); // usado para rastrear o progresso do programa (ex: passo a passo de um algoritmo)
        logger.debug("DEBUG"); // usado para depurar o programa (ex: valores de variáveis)
        logger.info("INFO"); // usado para informar o usuário sobre o que acontece no programa (ex: início e fim de um método)
        logger.warn("WARN"); // usado para avisar o usuário sobre algo que pode dar errado no programa (ex: tentativa de conexão com banco de dados)
        logger.warn("ERROR"); // usado para avisar o usuário sobre algo que deu errado no programa (ex: erro de conexão com banco de dados)
        logger.fatal("FATAL"); // usado para avisar o usuário sobre algo que deu errado no programa e o programa não pode continuar (ex: erro de conexão com banco de dados)
        // TRACE e DEBUG não são mostrados no console, apenas no arquivo de log. INFO, WARN, ERROR e FATAL são mostrados no console e no arquivo de log. O nível de log padrão é INFO. Para mudar o nível de log, basta mudar o nível no arquivo log4j2.xml.
        return new ResponseEntity<>(emailService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/getOneEmail/{emailId}")
    public ResponseEntity<Object> getOneEmail(@PathVariable(value = "emailId") UUID emailId) {
        Optional<EmailModel> emailModelOptional = emailService.findById(emailId);
        return emailModelOptional.<ResponseEntity<Object>>map(emailModel -> ResponseEntity.status(HttpStatus.OK).body(emailModel)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found."));
    }

}