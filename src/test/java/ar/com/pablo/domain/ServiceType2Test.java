package ar.com.pablo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ar.com.pablo.web.rest.TestUtil;

public class ServiceType2Test {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceType2.class);
        ServiceType2 serviceType21 = new ServiceType2();
        serviceType21.setId(1L);
        ServiceType2 serviceType22 = new ServiceType2();
        serviceType22.setId(serviceType21.getId());
        assertThat(serviceType21).isEqualTo(serviceType22);
        serviceType22.setId(2L);
        assertThat(serviceType21).isNotEqualTo(serviceType22);
        serviceType21.setId(null);
        assertThat(serviceType21).isNotEqualTo(serviceType22);
    }
}
