package io.okkio.mybatis;

import org.apache.ibatis.annotations.Param;


public interface UtilMybatis {
    int updateUtilByIds(@Param("ids") Long ids, @Param("status") String status, @Param("name") String name,
                        @Param("description") String description, @Param("code") String code,
                        @Param("type") int type);
}
