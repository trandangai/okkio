package io.okkio.repository.version2;

import io.okkio.domain.version2.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * AlbumRepository
 */
@Repository
public interface AlbumRepository extends JpaRepository<Album, Long>, JpaSpecificationExecutor<Album> {
	Album findAlbumById(Long id);
	Album findAlbumByName(String name);
	@Query(
			value = "SELECT * FROM album u WHERE u.type_image = ?1 and u.status = 'ACTIVATED'",
			nativeQuery = true)
	List<Album> findAllAlbumByType(String typeImage);
	@Transactional
	@Modifying
	void deleteAlbumById(Long id);

}