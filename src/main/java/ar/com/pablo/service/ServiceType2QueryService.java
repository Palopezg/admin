package ar.com.pablo.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import ar.com.pablo.domain.ServiceType2;
import ar.com.pablo.domain.*; // for static metamodels
import ar.com.pablo.repository.ServiceType2Repository;
import ar.com.pablo.service.dto.ServiceType2Criteria;

/**
 * Service for executing complex queries for {@link ServiceType2} entities in the database.
 * The main input is a {@link ServiceType2Criteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ServiceType2} or a {@link Page} of {@link ServiceType2} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServiceType2QueryService extends QueryService<ServiceType2> {

    private final Logger log = LoggerFactory.getLogger(ServiceType2QueryService.class);

    private final ServiceType2Repository serviceType2Repository;

    public ServiceType2QueryService(ServiceType2Repository serviceType2Repository) {
        this.serviceType2Repository = serviceType2Repository;
    }

    /**
     * Return a {@link List} of {@link ServiceType2} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ServiceType2> findByCriteria(ServiceType2Criteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ServiceType2> specification = createSpecification(criteria);
        return serviceType2Repository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ServiceType2} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceType2> findByCriteria(ServiceType2Criteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ServiceType2> specification = createSpecification(criteria);
        return serviceType2Repository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServiceType2Criteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ServiceType2> specification = createSpecification(criteria);
        return serviceType2Repository.count(specification);
    }

    /**
     * Function to convert {@link ServiceType2Criteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ServiceType2> createSpecification(ServiceType2Criteria criteria) {
        Specification<ServiceType2> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ServiceType2_.id));
            }
            if (criteria.getServiceId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getServiceId(), ServiceType2_.serviceId));
            }
            if (criteria.getDescripcion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescripcion(), ServiceType2_.descripcion));
            }
        }
        return specification;
    }
}
