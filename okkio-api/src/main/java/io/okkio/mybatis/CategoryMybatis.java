package io.okkio.mybatis;

import org.apache.ibatis.annotations.Param;


public interface CategoryMybatis {
    int updateCategoryByIds(@Param("ids") Long ids, @Param("status") String status, @Param("name") String name);
    int updateCategoryByIdsV2(@Param("ids") Long ids, @Param("status") String status, @Param("name") String name,
                              @Param("priority") int priority);
}
