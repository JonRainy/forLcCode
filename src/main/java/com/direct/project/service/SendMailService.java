package com.direct.project.service;


import com.direct.project.dto.EmailDTO;
import com.mashape.unirest.http.exceptions.UnirestException;

public interface SendMailService {

    Boolean sendMail(EmailDTO emailDTO);

}
