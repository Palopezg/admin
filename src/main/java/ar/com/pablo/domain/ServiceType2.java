package ar.com.pablo.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ServiceType2.
 */
@Entity
@Table(name = "service_type_2")
public class ServiceType2 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "service_id", nullable = false)
    private String serviceId;

    @Column(name = "descripcion")
    private String descripcion;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceId() {
        return serviceId;
    }

    public ServiceType2 serviceId(String serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public ServiceType2 descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceType2)) {
            return false;
        }
        return id != null && id.equals(((ServiceType2) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ServiceType2{" +
            "id=" + getId() +
            ", serviceId='" + getServiceId() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
