package io.okkio.services.impl;

import io.okkio.domain.Util;
import io.okkio.dto.UtilDto;
import io.okkio.dto.request.RequestUtilDto;
import io.okkio.mapper.UtilMapper;
import io.okkio.mybatis.UtilMybatis;
import io.okkio.repository.UtilRepository;
import io.okkio.services.UtilServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UtilServicesImpl
 */
@Slf4j
@Service
public class UtilServicesImpl extends BaseServiceImpl<Util, Long> implements UtilServices {

    public UtilServicesImpl(JpaRepository<Util, Long> jpaRepository) {
        super(jpaRepository);
    }

    @Autowired
    private UtilRepository utilRepository;

    @Autowired
    private UtilMapper utilMapper;

    @Autowired
    private UtilMybatis utilMybatis;

    @Override
    public List<Util> getAllUtil() {
        return super.findAll();
    }

    @Override
    public Util addUtil(RequestUtilDto dto) {
        Util util = utilMapper.toEntity(dto);
        util.setCreatedBy("System");
        return super.save(util);
    }

    @Override
    public Util getUtilById(Long id) {
        return utilRepository.findUtilById(id);
    }

    @Override
    public void deleteUtilById(Long id) {
        utilRepository.deleteUtilById(id);
        log.warn("UtilServicesImpl - Delete success with id: " + String.valueOf(id));
    }

    @Override
    public boolean update(RequestUtilDto dto) {
        int isUpdated = utilMybatis.updateUtilByIds(dto.getId(), dto.getStatus(), dto.getName(), dto.getDescription(),
                dto.getCode(), dto.getType());
        if (isUpdated < 0) {
            log.warn("Can't update Util with dto: " + dto);
            return false;
        }
        return true;
    }

    @Override
    public List<Util> getUtilByName(String name) {
        return utilRepository.findUtilByName(name);
    }

    @Override
    public UtilDto initUtil() {
        UtilDto result = new UtilDto();
        result.setGrinds(utilRepository.findUtilByName("GRIND"));
        result.setSizes(utilRepository.findUtilByName("SIZE"));
        result.setSubscriptions(utilRepository.findUtilByName("SUBSCRIPTION"));
        return result;
    }
}
