package io.okkio.services.impl;

import io.okkio.domain.User;
import io.okkio.dto.UserDto;
import io.okkio.dto.request.RequestUserDto;
import io.okkio.mapper.UserMapper;
import io.okkio.mybatis.UserMybatis;
import io.okkio.repository.UserRepository;
import io.okkio.services.EmailServices;
import io.okkio.services.UserServices;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;

/**
 * UserServicesImpl
 */
@Slf4j
@Service
public class UserServicesImpl extends BaseServiceImpl<User, Long> implements UserServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserMybatis userMybatis;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmailServices emailServices;

    public UserServicesImpl(JpaRepository<User, Long> jpaRepository) {
        super(jpaRepository);
    }

    @Override
    public List<User> findAll() {
        return super.findAll();
    }

    @Override
    public Optional<User> findOne(Long aLong) {
        return super.findOne(aLong);
    }

    @Override
    public User save(User entity) {
        if (entity.getCreatedBy() == null) {
            entity.setCreatedBy("System");
        }
        if (entity.getPassword() != null) {
            entity.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));
        }
        return super.save(entity);
    }

    @Override
    public UserDto insert(UserDto dto) {
        // By default user role
        dto.setRoleId(1);
        User user = userMapper.toEntity(dto);
        if (dto.getAccountType().equals("FACEBOOK")) {
            user.setSocialFacebook("FACEBOOK");
        } else if (dto.getAccountType().equals("GOOGLE")) {
            user.setSocialGoogle("GOOGLE");
        }
        if (!StringUtils.isEmpty(dto.getSocialId())) {
            user.setSocialId(dto.getSocialId());
        }
        return userMapper.toDto(save(user));
    }

    @Override
    public boolean findUserByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        return user != null;
    }

    @Override
    public boolean findUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        return user != null;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public UserDto get(Long id) {
        return userMapper.toDto(userRepository.findUserById(id));
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findUserById(id);
    }

    @Override
    public User findUserByEmailAndPassword(String email, String password) {
        User result = userRepository.findUserByEmail(email);
        if (result == null) {
            return null;
        }
        if (bCryptPasswordEncoder.matches(password, result.getPassword())) {
            return result;
        }
        return null;
    }

    @Override
    public UserDto getUserByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        if (user == null && username.equals("guest")) {
            save(createdUserGuest());
            return userMapper.toDto(userRepository.findUserByUsername(username));
        }
        return userMapper.toDto(user);
    }

    @Override
    public boolean resetPassword(String email) {
        String newPassword = RandomStringUtils.randomAlphanumeric(8);
        User user = getUserByEmail(email);
        if (emailServices.sendMailForgetPassword(email, user.getFirstName(), newPassword)) {
            user.setPassword(newPassword);
            save(user);
            log.info("Sent mail*****");
            return true;
        }
        return false;
    }

    @Override
    public boolean update(RequestUserDto dto) {
        if (dto.getPassword() != null) {
            dto.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        }
        int isUpdated = userMybatis.updateInfoUser(dto.getId(), dto.getStatus(), dto.getEmail(), dto.getRoleId(),
                dto.getAddress(), dto.getPhoneNumber(), dto.getFirstName(), dto.getLastName(), dto.getFullName(),
                dto.getUsername(), dto.getPassword());
        if (isUpdated < 0) {
            log.warn("Can't update User with dto: " + dto);
            return false;
        }
        return true;
    }

    private User createdUserGuest() {
        User user = new User();
        user.setUsername("guest");
        user.setRoleId(3);
        user.setEmail("guest@gmail.com");
        return user;
    }
}
