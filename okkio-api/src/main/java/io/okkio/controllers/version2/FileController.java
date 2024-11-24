package io.okkio.controllers.version2;

import io.okkio.common.Constants;
import io.okkio.domain.version2.Album;
import io.okkio.dto.TypeImage;
import io.okkio.dto.request.version2.RequestAlbumDto;
import io.okkio.security.JwtTokenProvider;
import io.okkio.services.version2.AlbumServices;
import io.okkio.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PreAuthorize("hasAnyRole('OKKIO_ADMIN')")
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile image, HttpServletRequest httpServletRequest) throws IOException {
        String email = jwtTokenProvider.getEmailFromToken(httpServletRequest.getHeader("Authorization"));
        RequestAlbumDto dto = new RequestAlbumDto();
        dto.setEmail(email);
        String pathImage = albumServices.uploadFile(image);
        log.info("uploadFile - Path of file: {}", pathImage);
        if (!StringUtils.isEmpty(pathImage)) {
            dto.setName(pathImage);
            albumServices.addAlbum(dto);
        }
        return ResponseUtil.ok(Constants.MESSAGE_INSERT_DATA_SUCCESS, pathImage);
    }

    @PreAuthorize("hasAnyRole('OKKIO_ADMIN')")
    @PostMapping("/upload-image")
    public ResponseEntity<?> uploadFileImage(@RequestParam MultipartFile image, HttpServletRequest httpServletRequest,@RequestParam @Validated TypeImage typeImage) throws IOException {
        String email = jwtTokenProvider.getEmailFromToken(httpServletRequest.getHeader("Authorization"));
        RequestAlbumDto dto = new RequestAlbumDto();
        dto.setEmail(email);
        String pathImage = albumServices.uploadFile(image);
        dto.setTypeImage(typeImage.name());
        dto.setDescription("Insert image of " + typeImage.name());
        log.info("uploadFileImage - Path of file: {}", pathImage);
        if (!StringUtils.isEmpty(pathImage)) {
            dto.setName(pathImage);
            albumServices.addAlbum(dto);
        }
        return ResponseUtil.ok(Constants.MESSAGE_INSERT_DATA_SUCCESS, pathImage);
    }

    @GetMapping("/get-all-image-by-type")
    public ResponseEntity<?> getAllByType(@RequestParam @Validated TypeImage typeImage) {
        if (StringUtils.isEmpty(typeImage.name())) {
            return ResponseUtil.badRequest("Missing type image");
        }
        return ResponseUtil.ok(Constants.MESSAGE_INSERT_DATA_SUCCESS, albumServices.getAllAlbumByType(typeImage.name()));
    }

    @PreAuthorize("hasAnyRole('OKKIO_ADMIN')")
    @DeleteMapping
    public ResponseEntity<?> deleteImage(@Param("id") Long id) {
        Album image = albumServices.findAlbumById(id);
        if (ObjectUtils.isEmpty(image)) {
            return ResponseUtil.ok(Constants.MESSAGE_DATA_IS_NOT_EXISTED + " with id: " + id, null);
        }
        albumServices.deleteAlbumById(id);
        return ResponseUtil.ok(Constants.MESSAGE_DELETE_DATA_SUCCESS + " with id" + id, null);
    }
}
