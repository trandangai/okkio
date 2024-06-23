package io.okkio.controllers.version2;

import io.okkio.common.Constants;
import io.okkio.dto.request.version2.RequestAlbumDto;
import io.okkio.security.JwtTokenProvider;
import io.okkio.services.version2.AlbumServices;
import io.okkio.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/api/file")
@Slf4j
public class FileController {

    @Autowired
    private AlbumServices albumServices;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile image, HttpServletRequest httpServletRequest) throws IOException {
        String email = jwtTokenProvider.getEmailFromToken(httpServletRequest.getHeader("Authorization"));
        RequestAlbumDto dto = new RequestAlbumDto();
        dto.setEmail(email);
        String pathImage = albumServices.uploadFile(image);
        log.info("Path of file: {}", pathImage);
        if (!StringUtils.isEmpty(pathImage)) {
            dto.setName(pathImage);
            albumServices.addAlbum(dto);
        }
        return ResponseUtil.ok(Constants.MESSAGE_INSERT_DATA_SUCCESS, pathImage);
    }
}
