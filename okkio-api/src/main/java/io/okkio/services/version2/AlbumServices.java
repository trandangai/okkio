package io.okkio.services.version2;

import io.okkio.domain.version2.Album;
import io.okkio.dto.request.version2.RequestAlbumDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AlbumServices {
    Album addAlbum(RequestAlbumDto dto);
    String uploadFile(MultipartFile image) throws IOException;
    List<Album> getAllAlbumByType(String typeImage);
    Album findAlbumById(Long id);
    void deleteAlbumById(Long id);
}
