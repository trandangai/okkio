package io.okkio.services.version2;

import io.okkio.domain.version2.Album;
import io.okkio.dto.request.version2.RequestAlbumDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AlbumServices {
    Album addAlbum(RequestAlbumDto dto);
    String uploadFile(MultipartFile image) throws IOException;
}
