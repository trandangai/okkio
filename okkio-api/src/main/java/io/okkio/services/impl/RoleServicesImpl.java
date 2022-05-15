package io.okkio.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.okkio.common.Constants;
import io.okkio.domain.Role;
import io.okkio.repository.RoleRepository;
import io.okkio.services.RoleServices;
import io.okkio.util.RedisUtil;
import io.okkio.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * RoleServicesImpl
 */
@Slf4j
@Service
public class RoleServicesImpl implements RoleServices {

    @Autowired
    private RedisUtil<String> redisUtil;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role get(Integer id) {
        Role result = new Role();
        String key = redisUtil.getValue(Constants.REDIS_ROLE + id);
        if (key == null || StringUtil.isEmpty(key)) {
            result = roleRepository.findRoleById(id);
            try {
                redisUtil.putValue(Constants.REDIS_ROLE + id, objectMapper.writeValueAsString(result));
            } catch (JsonProcessingException e) {
                log.warn("JsonProcessingException - Can not convert Object to Json String!" + e.getMessage());
            }
            redisUtil.setExpire(Constants.REDIS_ROLE + id, 1, TimeUnit.HOURS);
        } else {
            try {
                result = objectMapper.readValue(key, Role.class);
            } catch (JsonProcessingException e) {
                log.warn("JsonProcessingException - Can not convert String to Object! ");
            }
        }
        return result;
    }
}
