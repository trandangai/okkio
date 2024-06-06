package io.okkio.repository.version2;

import io.okkio.domain.version2.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * AlbumRepository
 */
@Repository
public interface AlbumRepository extends JpaRepository<Album, Long>, JpaSpecificationExecutor<Album> {
	Album findAlbumById(Long id);
	void deleteAlbumById(Long id);
	Album findAlbumByName(String name);
}