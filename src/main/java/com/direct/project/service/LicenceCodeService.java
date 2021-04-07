package com.direct.project.service;

public interface LicenceCodeService {

    String bindLicenceCode(String user, String deviceCode);

    Boolean cleanLicenceCode(String user);

    Boolean createLicenceCode(String user, String userMail, Integer codeCount);

}
