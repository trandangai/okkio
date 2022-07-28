package io.okkio.repository;

import io.okkio.domain.Product;
import io.okkio.domain.Shipment;
import io.okkio.domain.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ShipmentRepository
 */
@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long>, JpaSpecificationExecutor<Shipment> {
	@Query(value = "select * from shipment cd where cd.status = ?1", nativeQuery = true)
	List<Shipment> findShipmentByStatus(@Param("status") String status);
	Shipment findShipmentById(Long id);
	@Query(value = "update shipment cd set cd.status = ?2 where cd.id = ?1", nativeQuery = true)
	int updateStatusShipment(@Param("id") Long id, @Param("status") String status);
}