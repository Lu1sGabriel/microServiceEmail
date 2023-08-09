package com.ms.email.controllers;

import com.ms.email.dtos.EmailDto;
import com.ms.email.models.EmailModel;
import com.ms.email.services.EmailService;

import jakarta.validation.Valid;

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
        return new ResponseEntity<>(emailService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/getOneEmail/{emailId}")
    public ResponseEntity<Object> getOneEmail(@PathVariable(value = "emailId") UUID emailId) {
        Optional<EmailModel> emailModelOptional = emailService.findById(emailId);
        return emailModelOptional.<ResponseEntity<Object>>map(emailModel -> ResponseEntity.status(HttpStatus.OK).body(emailModel)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found."));
    }
}