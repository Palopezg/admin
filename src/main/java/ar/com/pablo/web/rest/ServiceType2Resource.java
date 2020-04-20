package ar.com.pablo.web.rest;

import ar.com.pablo.domain.ServiceType2;
import ar.com.pablo.service.ServiceType2Service;
import ar.com.pablo.web.rest.errors.BadRequestAlertException;
import ar.com.pablo.service.dto.ServiceType2Criteria;
import ar.com.pablo.service.ServiceType2QueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link ar.com.pablo.domain.ServiceType2}.
 */
@RestController
@RequestMapping("/api")
public class ServiceType2Resource {

    private final Logger log = LoggerFactory.getLogger(ServiceType2Resource.class);

    private static final String ENTITY_NAME = "serviceType2";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceType2Service serviceType2Service;

    private final ServiceType2QueryService serviceType2QueryService;

    public ServiceType2Resource(ServiceType2Service serviceType2Service, ServiceType2QueryService serviceType2QueryService) {
        this.serviceType2Service = serviceType2Service;
        this.serviceType2QueryService = serviceType2QueryService;
    }

    /**
     * {@code POST  /service-type-2-s} : Create a new serviceType2.
     *
     * @param serviceType2 the serviceType2 to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceType2, or with status {@code 400 (Bad Request)} if the serviceType2 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-type-2-s")
    public ResponseEntity<ServiceType2> createServiceType2(@Valid @RequestBody ServiceType2 serviceType2) throws URISyntaxException {
        log.debug("REST request to save ServiceType2 : {}", serviceType2);
        if (serviceType2.getId() != null) {
            throw new BadRequestAlertException("A new serviceType2 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceType2 result = serviceType2Service.save(serviceType2);
        return ResponseEntity.created(new URI("/api/service-type-2-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-type-2-s} : Updates an existing serviceType2.
     *
     * @param serviceType2 the serviceType2 to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceType2,
     * or with status {@code 400 (Bad Request)} if the serviceType2 is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceType2 couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-type-2-s")
    public ResponseEntity<ServiceType2> updateServiceType2(@Valid @RequestBody ServiceType2 serviceType2) throws URISyntaxException {
        log.debug("REST request to update ServiceType2 : {}", serviceType2);
        if (serviceType2.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceType2 result = serviceType2Service.save(serviceType2);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, serviceType2.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-type-2-s} : get all the serviceType2S.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceType2S in body.
     */
    @GetMapping("/service-type-2-s")
    public ResponseEntity<List<ServiceType2>> getAllServiceType2S(ServiceType2Criteria criteria, Pageable pageable) {
        log.debug("REST request to get ServiceType2S by criteria: {}", criteria);
        Page<ServiceType2> page = serviceType2QueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /service-type-2-s/count} : count all the serviceType2S.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/service-type-2-s/count")
    public ResponseEntity<Long> countServiceType2S(ServiceType2Criteria criteria) {
        log.debug("REST request to count ServiceType2S by criteria: {}", criteria);
        return ResponseEntity.ok().body(serviceType2QueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /service-type-2-s/:id} : get the "id" serviceType2.
     *
     * @param id the id of the serviceType2 to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceType2, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-type-2-s/{id}")
    public ResponseEntity<ServiceType2> getServiceType2(@PathVariable Long id) {
        log.debug("REST request to get ServiceType2 : {}", id);
        Optional<ServiceType2> serviceType2 = serviceType2Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceType2);
    }

    /**
     * {@code DELETE  /service-type-2-s/:id} : delete the "id" serviceType2.
     *
     * @param id the id of the serviceType2 to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-type-2-s/{id}")
    public ResponseEntity<Void> deleteServiceType2(@PathVariable Long id) {
        log.debug("REST request to delete ServiceType2 : {}", id);
        serviceType2Service.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
