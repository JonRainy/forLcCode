package com.direct.project.service.imp;


import com.alibaba.fastjson.JSONObject;
import com.direct.project.config.MailConfig;
import com.direct.project.dto.EmailDTO;
import com.direct.project.service.SendMailService;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;


@Service
public class SendMailServiceImp implements SendMailService {

    private final static Logger logger = LoggerFactory.getLogger(SendMailServiceImp.class);

    @Autowired
    CloseableHttpClient httpClient;

    @Autowired
    RequestConfig requestConfig;

    @Autowired
    MailConfig mailConfig;

//    @Override
//    public Boolean sendMail(EmailDTO emailDTO) {
//
//
//        HttpPost post = new HttpPost("https://api.mailgun.net/v3/" + mailConfig.getDomain() + "/messages");
//        post.setHeader("api", mailConfig.getKey());
//
//
//        JSONObject bodyData = new JSONObject();
//        bodyData.put("subject", "my phone");
//        bodyData.put("html", "");
//        bodyData.put("from", mailConfig.getFrom());
//
//        HttpEntity body = new StringEntity(JSONObject.toJSONString(bodyData), ContentType.MULTIPART_FORM_DATA);
//
//        post.setEntity(body);
//        post.setConfig(requestConfig);
//
//        CloseableHttpResponse response = null;
//        try {
//            response = httpClient.execute(post);
//            HttpEntity responseEntity = response.getEntity();
//
//            if (responseEntity != null && response.getStatusLine().getStatusCode() == 200) {
//                logger.info("发送成功");
//                return true;
//            } else {
//                logger.error("发送失败, {}", EntityUtils.toString(responseEntity));
//            }
//            EntityUtils.consume(responseEntity);
//        } catch (Exception e ) {
//            logger.error("发送异常: {}", e);
//        }
//        return false;
//    }


    @Override
    public Boolean sendMail(EmailDTO emailDTO)  {
        try {
            HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + mailConfig.getDomain() + "/messages")
                    .basicAuth("api", mailConfig.getKey())
                    .queryString("from", mailConfig.getFrom())
                    .queryString("to", emailDTO.getSendTo())
                    .queryString("subject", "for phone")
                    .queryString("text", emailDTO.getContent())
                    .asJson();

            logger.info("发送结果: {}", request.getBody());
            if (request.getStatus() == 200) {
                return true;
            }
        } catch (Exception e) {
            logger.error("发送异常: {}", e);
        }
        return false;
    }
}
