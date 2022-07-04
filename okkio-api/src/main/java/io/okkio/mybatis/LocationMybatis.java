package io.okkio.mybatis;

import org.apache.ibatis.annotations.Param;


public interface LocationMybatis {
    int updateLocationByIds(@Param("ids") Long ids, @Param("status") String status, @Param("name") String name,
                        @Param("address") String address, @Param("phone") String phone, @Param("title") String title,
                        @Param("description") String description, @Param("openTime") String openTime,
                            @Param("openDay") String openDay, @Param("conceptStore") String conceptStore,
                            @Param("images") String images);
}
