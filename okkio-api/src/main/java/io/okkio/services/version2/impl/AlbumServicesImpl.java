package io.okkio.services.version2.impl;

import io.okkio.domain.version2.Album;
import io.okkio.dto.request.version2.RequestAlbumDto;
import io.okkio.mapper.version2.AlbumMapper;
import io.okkio.repository.version2.AlbumRepository;
import io.okkio.services.version2.AlbumServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * AlbumServicesImpl
 */
@Slf4j
@Service
public class AlbumServicesImpl extends BaseServiceImpl<Album, Long> implements AlbumServices {

    public AlbumServicesImpl(JpaRepository<Album, Long> jpaRepository) {
        super(jpaRepository);
    }

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private AlbumMapper albumMapper;

    @Value("${path.image}")
    private String pathImage;

    private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));

    @Override
    public Album addAlbum(RequestAlbumDto dto) {
        Album album = albumMapper.toEntity(dto);
        album.setCreatedBy(dto.getEmail());
        album.setStatus("ACTIVATED");
        return super.save(album);
    }

    @Override
    public String uploadFile(MultipartFile image) throws IOException {
        log.info("Begin upload file: {}", image.getName());
        Path staticPath = Paths.get(pathImage);
        Path uuid = Paths.get(UUID.randomUUID().toString());
        Path url = staticPath.resolve(uuid);
        Path folder = CURRENT_FOLDER.resolve(url);
        if (!Files.exists(folder)) {
            Files.createDirectories(folder);
        }
        Path file = folder.resolve(Objects.requireNonNull(image.getOriginalFilename()));
        try (OutputStream os = Files.newOutputStream(file)) {
            os.write(image.getBytes());
        }
        String pathUrl = url.resolve(image.getOriginalFilename()).toString();
        log.info("End upload file with url: {}", pathUrl);
        return pathUrl;
    }

    @Override
    public List<Album> getAllAlbumByType(String typeImage) {
        log.info("Begin getAllAlbumByType: {}", typeImage);
        return albumRepository.findAllAlbumByType(typeImage);
    }

    @Override
    public Album findAlbumById(Long id) {
        log.info("Begin findAlbumById: {}", id);
        return albumRepository.findAlbumById(id);
    }

    @Override
    public void deleteAlbumById(Long id) {
        log.info("Begin deleteAlbumById: {}", id);
        albumRepository.deleteAlbumById(id);
        log.info("End deleteAlbumById: {}", id);
    }
}
