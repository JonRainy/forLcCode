package com.direct.project.dao;

import com.direct.project.dto.LicenceCodeDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicenceCodeDao {

    void insert(@Param("lcodes")List<LicenceCodeDTO> licenceCodeDTOs);

    Integer countByUser(@Param("user") String user);

    Integer deleteByUser(@Param("user") String user);

    void bindLiceneCode(@Param("user")String user, @Param("code")String code, @Param("deviceCode")String deviceCode);

    List<LicenceCodeDTO> queryLiceneCodeForUpdate(@Param("user")String user);

    List<LicenceCodeDTO> queryEarliestLiceneCode(@Param("user")String user);


}
