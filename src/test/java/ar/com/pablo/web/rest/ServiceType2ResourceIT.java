package ar.com.pablo.web.rest;

import ar.com.pablo.AdminApp;
import ar.com.pablo.domain.ServiceType2;
import ar.com.pablo.repository.ServiceType2Repository;
import ar.com.pablo.service.ServiceType2Service;
import ar.com.pablo.service.dto.ServiceType2Criteria;
import ar.com.pablo.service.ServiceType2QueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ServiceType2Resource} REST controller.
 */
@SpringBootTest(classes = AdminApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ServiceType2ResourceIT {

    private static final String DEFAULT_SERVICE_ID = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private ServiceType2Repository serviceType2Repository;

    @Autowired
    private ServiceType2Service serviceType2Service;

    @Autowired
    private ServiceType2QueryService serviceType2QueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceType2MockMvc;

    private ServiceType2 serviceType2;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceType2 createEntity(EntityManager em) {
        ServiceType2 serviceType2 = new ServiceType2()
            .serviceId(DEFAULT_SERVICE_ID)
            .descripcion(DEFAULT_DESCRIPCION);
        return serviceType2;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceType2 createUpdatedEntity(EntityManager em) {
        ServiceType2 serviceType2 = new ServiceType2()
            .serviceId(UPDATED_SERVICE_ID)
            .descripcion(UPDATED_DESCRIPCION);
        return serviceType2;
    }

    @BeforeEach
    public void initTest() {
        serviceType2 = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceType2() throws Exception {
        int databaseSizeBeforeCreate = serviceType2Repository.findAll().size();

        // Create the ServiceType2
        restServiceType2MockMvc.perform(post("/api/service-type-2-s")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceType2)))
            .andExpect(status().isCreated());

        // Validate the ServiceType2 in the database
        List<ServiceType2> serviceType2List = serviceType2Repository.findAll();
        assertThat(serviceType2List).hasSize(databaseSizeBeforeCreate + 1);
        ServiceType2 testServiceType2 = serviceType2List.get(serviceType2List.size() - 1);
        assertThat(testServiceType2.getServiceId()).isEqualTo(DEFAULT_SERVICE_ID);
        assertThat(testServiceType2.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createServiceType2WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceType2Repository.findAll().size();

        // Create the ServiceType2 with an existing ID
        serviceType2.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceType2MockMvc.perform(post("/api/service-type-2-s")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceType2)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceType2 in the database
        List<ServiceType2> serviceType2List = serviceType2Repository.findAll();
        assertThat(serviceType2List).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkServiceIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceType2Repository.findAll().size();
        // set the field null
        serviceType2.setServiceId(null);

        // Create the ServiceType2, which fails.

        restServiceType2MockMvc.perform(post("/api/service-type-2-s")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceType2)))
            .andExpect(status().isBadRequest());

        List<ServiceType2> serviceType2List = serviceType2Repository.findAll();
        assertThat(serviceType2List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServiceType2S() throws Exception {
        // Initialize the database
        serviceType2Repository.saveAndFlush(serviceType2);

        // Get all the serviceType2List
        restServiceType2MockMvc.perform(get("/api/service-type-2-s?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceType2.getId().intValue())))
            .andExpect(jsonPath("$.[*].serviceId").value(hasItem(DEFAULT_SERVICE_ID)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }
    
    @Test
    @Transactional
    public void getServiceType2() throws Exception {
        // Initialize the database
        serviceType2Repository.saveAndFlush(serviceType2);

        // Get the serviceType2
        restServiceType2MockMvc.perform(get("/api/service-type-2-s/{id}", serviceType2.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serviceType2.getId().intValue()))
            .andExpect(jsonPath("$.serviceId").value(DEFAULT_SERVICE_ID))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }


    @Test
    @Transactional
    public void getServiceType2SByIdFiltering() throws Exception {
        // Initialize the database
        serviceType2Repository.saveAndFlush(serviceType2);

        Long id = serviceType2.getId();

        defaultServiceType2ShouldBeFound("id.equals=" + id);
        defaultServiceType2ShouldNotBeFound("id.notEquals=" + id);

        defaultServiceType2ShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultServiceType2ShouldNotBeFound("id.greaterThan=" + id);

        defaultServiceType2ShouldBeFound("id.lessThanOrEqual=" + id);
        defaultServiceType2ShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllServiceType2SByServiceIdIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceType2Repository.saveAndFlush(serviceType2);

        // Get all the serviceType2List where serviceId equals to DEFAULT_SERVICE_ID
        defaultServiceType2ShouldBeFound("serviceId.equals=" + DEFAULT_SERVICE_ID);

        // Get all the serviceType2List where serviceId equals to UPDATED_SERVICE_ID
        defaultServiceType2ShouldNotBeFound("serviceId.equals=" + UPDATED_SERVICE_ID);
    }

    @Test
    @Transactional
    public void getAllServiceType2SByServiceIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceType2Repository.saveAndFlush(serviceType2);

        // Get all the serviceType2List where serviceId not equals to DEFAULT_SERVICE_ID
        defaultServiceType2ShouldNotBeFound("serviceId.notEquals=" + DEFAULT_SERVICE_ID);

        // Get all the serviceType2List where serviceId not equals to UPDATED_SERVICE_ID
        defaultServiceType2ShouldBeFound("serviceId.notEquals=" + UPDATED_SERVICE_ID);
    }

    @Test
    @Transactional
    public void getAllServiceType2SByServiceIdIsInShouldWork() throws Exception {
        // Initialize the database
        serviceType2Repository.saveAndFlush(serviceType2);

        // Get all the serviceType2List where serviceId in DEFAULT_SERVICE_ID or UPDATED_SERVICE_ID
        defaultServiceType2ShouldBeFound("serviceId.in=" + DEFAULT_SERVICE_ID + "," + UPDATED_SERVICE_ID);

        // Get all the serviceType2List where serviceId equals to UPDATED_SERVICE_ID
        defaultServiceType2ShouldNotBeFound("serviceId.in=" + UPDATED_SERVICE_ID);
    }

    @Test
    @Transactional
    public void getAllServiceType2SByServiceIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceType2Repository.saveAndFlush(serviceType2);

        // Get all the serviceType2List where serviceId is not null
        defaultServiceType2ShouldBeFound("serviceId.specified=true");

        // Get all the serviceType2List where serviceId is null
        defaultServiceType2ShouldNotBeFound("serviceId.specified=false");
    }
                @Test
    @Transactional
    public void getAllServiceType2SByServiceIdContainsSomething() throws Exception {
        // Initialize the database
        serviceType2Repository.saveAndFlush(serviceType2);

        // Get all the serviceType2List where serviceId contains DEFAULT_SERVICE_ID
        defaultServiceType2ShouldBeFound("serviceId.contains=" + DEFAULT_SERVICE_ID);

        // Get all the serviceType2List where serviceId contains UPDATED_SERVICE_ID
        defaultServiceType2ShouldNotBeFound("serviceId.contains=" + UPDATED_SERVICE_ID);
    }

    @Test
    @Transactional
    public void getAllServiceType2SByServiceIdNotContainsSomething() throws Exception {
        // Initialize the database
        serviceType2Repository.saveAndFlush(serviceType2);

        // Get all the serviceType2List where serviceId does not contain DEFAULT_SERVICE_ID
        defaultServiceType2ShouldNotBeFound("serviceId.doesNotContain=" + DEFAULT_SERVICE_ID);

        // Get all the serviceType2List where serviceId does not contain UPDATED_SERVICE_ID
        defaultServiceType2ShouldBeFound("serviceId.doesNotContain=" + UPDATED_SERVICE_ID);
    }


    @Test
    @Transactional
    public void getAllServiceType2SByDescripcionIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceType2Repository.saveAndFlush(serviceType2);

        // Get all the serviceType2List where descripcion equals to DEFAULT_DESCRIPCION
        defaultServiceType2ShouldBeFound("descripcion.equals=" + DEFAULT_DESCRIPCION);

        // Get all the serviceType2List where descripcion equals to UPDATED_DESCRIPCION
        defaultServiceType2ShouldNotBeFound("descripcion.equals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllServiceType2SByDescripcionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceType2Repository.saveAndFlush(serviceType2);

        // Get all the serviceType2List where descripcion not equals to DEFAULT_DESCRIPCION
        defaultServiceType2ShouldNotBeFound("descripcion.notEquals=" + DEFAULT_DESCRIPCION);

        // Get all the serviceType2List where descripcion not equals to UPDATED_DESCRIPCION
        defaultServiceType2ShouldBeFound("descripcion.notEquals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllServiceType2SByDescripcionIsInShouldWork() throws Exception {
        // Initialize the database
        serviceType2Repository.saveAndFlush(serviceType2);

        // Get all the serviceType2List where descripcion in DEFAULT_DESCRIPCION or UPDATED_DESCRIPCION
        defaultServiceType2ShouldBeFound("descripcion.in=" + DEFAULT_DESCRIPCION + "," + UPDATED_DESCRIPCION);

        // Get all the serviceType2List where descripcion equals to UPDATED_DESCRIPCION
        defaultServiceType2ShouldNotBeFound("descripcion.in=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllServiceType2SByDescripcionIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceType2Repository.saveAndFlush(serviceType2);

        // Get all the serviceType2List where descripcion is not null
        defaultServiceType2ShouldBeFound("descripcion.specified=true");

        // Get all the serviceType2List where descripcion is null
        defaultServiceType2ShouldNotBeFound("descripcion.specified=false");
    }
                @Test
    @Transactional
    public void getAllServiceType2SByDescripcionContainsSomething() throws Exception {
        // Initialize the database
        serviceType2Repository.saveAndFlush(serviceType2);

        // Get all the serviceType2List where descripcion contains DEFAULT_DESCRIPCION
        defaultServiceType2ShouldBeFound("descripcion.contains=" + DEFAULT_DESCRIPCION);

        // Get all the serviceType2List where descripcion contains UPDATED_DESCRIPCION
        defaultServiceType2ShouldNotBeFound("descripcion.contains=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllServiceType2SByDescripcionNotContainsSomething() throws Exception {
        // Initialize the database
        serviceType2Repository.saveAndFlush(serviceType2);

        // Get all the serviceType2List where descripcion does not contain DEFAULT_DESCRIPCION
        defaultServiceType2ShouldNotBeFound("descripcion.doesNotContain=" + DEFAULT_DESCRIPCION);

        // Get all the serviceType2List where descripcion does not contain UPDATED_DESCRIPCION
        defaultServiceType2ShouldBeFound("descripcion.doesNotContain=" + UPDATED_DESCRIPCION);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServiceType2ShouldBeFound(String filter) throws Exception {
        restServiceType2MockMvc.perform(get("/api/service-type-2-s?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceType2.getId().intValue())))
            .andExpect(jsonPath("$.[*].serviceId").value(hasItem(DEFAULT_SERVICE_ID)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));

        // Check, that the count call also returns 1
        restServiceType2MockMvc.perform(get("/api/service-type-2-s/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServiceType2ShouldNotBeFound(String filter) throws Exception {
        restServiceType2MockMvc.perform(get("/api/service-type-2-s?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServiceType2MockMvc.perform(get("/api/service-type-2-s/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingServiceType2() throws Exception {
        // Get the serviceType2
        restServiceType2MockMvc.perform(get("/api/service-type-2-s/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceType2() throws Exception {
        // Initialize the database
        serviceType2Service.save(serviceType2);

        int databaseSizeBeforeUpdate = serviceType2Repository.findAll().size();

        // Update the serviceType2
        ServiceType2 updatedServiceType2 = serviceType2Repository.findById(serviceType2.getId()).get();
        // Disconnect from session so that the updates on updatedServiceType2 are not directly saved in db
        em.detach(updatedServiceType2);
        updatedServiceType2
            .serviceId(UPDATED_SERVICE_ID)
            .descripcion(UPDATED_DESCRIPCION);

        restServiceType2MockMvc.perform(put("/api/service-type-2-s")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedServiceType2)))
            .andExpect(status().isOk());

        // Validate the ServiceType2 in the database
        List<ServiceType2> serviceType2List = serviceType2Repository.findAll();
        assertThat(serviceType2List).hasSize(databaseSizeBeforeUpdate);
        ServiceType2 testServiceType2 = serviceType2List.get(serviceType2List.size() - 1);
        assertThat(testServiceType2.getServiceId()).isEqualTo(UPDATED_SERVICE_ID);
        assertThat(testServiceType2.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceType2() throws Exception {
        int databaseSizeBeforeUpdate = serviceType2Repository.findAll().size();

        // Create the ServiceType2

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceType2MockMvc.perform(put("/api/service-type-2-s")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceType2)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceType2 in the database
        List<ServiceType2> serviceType2List = serviceType2Repository.findAll();
        assertThat(serviceType2List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceType2() throws Exception {
        // Initialize the database
        serviceType2Service.save(serviceType2);

        int databaseSizeBeforeDelete = serviceType2Repository.findAll().size();

        // Delete the serviceType2
        restServiceType2MockMvc.perform(delete("/api/service-type-2-s/{id}", serviceType2.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceType2> serviceType2List = serviceType2Repository.findAll();
        assertThat(serviceType2List).hasSize(databaseSizeBeforeDelete - 1);
    }
}
