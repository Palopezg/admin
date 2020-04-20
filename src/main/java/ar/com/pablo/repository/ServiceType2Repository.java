package ar.com.pablo.repository;

import ar.com.pablo.domain.ServiceType2;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ServiceType2 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceType2Repository extends JpaRepository<ServiceType2, Long>, JpaSpecificationExecutor<ServiceType2> {
}
