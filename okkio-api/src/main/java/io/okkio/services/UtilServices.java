package io.okkio.services;


import io.okkio.domain.Util;
import io.okkio.dto.request.RequestCategoryDto;
import io.okkio.dto.request.RequestUtilDto;

import java.util.List;

public interface UtilServices {
    List<Util> getAllUtil();
    Util addUtil(RequestUtilDto dto);
    Util getUtilById(Long id);
    void deleteUtilById(Long id);
    boolean update(RequestUtilDto dto);
    List<Util> getUtilByName(String name);
}
