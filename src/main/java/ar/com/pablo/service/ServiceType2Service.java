package ar.com.pablo.service;

import ar.com.pablo.domain.ServiceType2;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ServiceType2}.
 */
public interface ServiceType2Service {

    /**
     * Save a serviceType2.
     *
     * @param serviceType2 the entity to save.
     * @return the persisted entity.
     */
    ServiceType2 save(ServiceType2 serviceType2);

    /**
     * Get all the serviceType2S.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ServiceType2> findAll(Pageable pageable);

    /**
     * Get the "id" serviceType2.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServiceType2> findOne(Long id);

    /**
     * Delete the "id" serviceType2.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
