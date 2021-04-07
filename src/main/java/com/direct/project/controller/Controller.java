package com.direct.project.controller;

import com.alibaba.fastjson.JSONObject;
import com.direct.project.dto.HttpResult;
import com.direct.project.service.LicenceCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;


@RestController
public class Controller {

    private final static Logger logger = LoggerFactory.getLogger(Controller.class);


    @Autowired
    LicenceCodeService licenceCodeService;

    @GetMapping(path = "/bind/licence")
    public HttpResult bindLicence(@RequestParam("user") String userId,
                               @RequestParam("deviceCode") String deviceCode) {
        try {
            String code = licenceCodeService.bindLicenceCode(userId, deviceCode);
            if (Objects.isNull(code)) {
                return HttpResult.ofFail("绑定失败: 用户没有 licenceCode");
            }
            JSONObject r = new JSONObject();
            r.put("code", code);
            return HttpResult.ofSuccess("绑定成功", r);
        } catch (Exception e) {
            return HttpResult.ofFail("绑定失败: "+ e);
        }
    }

    @GetMapping(path = "/clean/licence")
    public HttpResult cleanLicence(@RequestParam("user") String userId) {
        try {
            licenceCodeService.cleanLicenceCode(userId);
            return HttpResult.ofSuccess("重新生成成功", null);
        } catch (Exception e) {
            return HttpResult.ofFail("重新生成失败: "+ e);
        }
    }


    @GetMapping(path = "/query/licence")
    public HttpResult cleanLicence(@RequestParam("user") String userId,
                                   @RequestParam("licence") String licence) {
        try {
            return HttpResult.ofSuccess("重新生成成功", licenceCodeService.cleanLicenceCode(userId));
        } catch (Exception e) {
            return HttpResult.ofFail("重新生成失败: "+ e);
        }
    }


    @GetMapping(path = "/create/licence")
    public HttpResult createLicence(@RequestParam("user") String userId,
                                    @RequestParam("userMail") String userMail,
                                    @RequestParam("codeCount") Integer cnt) {
        try {
            licenceCodeService.createLicenceCode(userId, userMail, cnt);
            return HttpResult.ofSuccess("生成成功", null);
        } catch (Exception e) {
            return HttpResult.ofFail("生成失败: "+ e);
        }
    }
}
