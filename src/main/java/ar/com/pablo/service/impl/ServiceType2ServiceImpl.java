package ar.com.pablo.service.impl;

import ar.com.pablo.service.ServiceType2Service;
import ar.com.pablo.domain.ServiceType2;
import ar.com.pablo.repository.ServiceType2Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ServiceType2}.
 */
@Service
@Transactional
public class ServiceType2ServiceImpl implements ServiceType2Service {

    private final Logger log = LoggerFactory.getLogger(ServiceType2ServiceImpl.class);

    private final ServiceType2Repository serviceType2Repository;

    public ServiceType2ServiceImpl(ServiceType2Repository serviceType2Repository) {
        this.serviceType2Repository = serviceType2Repository;
    }

    /**
     * Save a serviceType2.
     *
     * @param serviceType2 the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ServiceType2 save(ServiceType2 serviceType2) {
        log.debug("Request to save ServiceType2 : {}", serviceType2);
        return serviceType2Repository.save(serviceType2);
    }

    /**
     * Get all the serviceType2S.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ServiceType2> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceType2S");
        return serviceType2Repository.findAll(pageable);
    }

    /**
     * Get one serviceType2 by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceType2> findOne(Long id) {
        log.debug("Request to get ServiceType2 : {}", id);
        return serviceType2Repository.findById(id);
    }

    /**
     * Delete the serviceType2 by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServiceType2 : {}", id);
        serviceType2Repository.deleteById(id);
    }
}
