package br.edu.fatecsjc.lgnspringapi.repository;

import br.edu.fatecsjc.lgnspringapi.entity.Organization;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test") 
public class OrganizationRepositoryTest {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Test
    void testSaveAndFind() {
        Organization organization = Organization.builder()
                .name("Test Organization")
                .country("Brazil")
                .build();

        Organization savedOrganization = organizationRepository.save(organization);

        assertNotNull(savedOrganization.getId());
        assertEquals("Test Organization", savedOrganization.getName());

        Optional<Organization> foundOrganization = organizationRepository.findById(savedOrganization.getId());
        assertTrue(foundOrganization.isPresent());
        assertEquals("Brazil", foundOrganization.get().getCountry());
    }

    @Test
    void testDelete() {
        Organization organization = Organization.builder()
                .name("Delete Test")
                .build();

        Organization savedOrganization = organizationRepository.save(organization);

        organizationRepository.deleteById(savedOrganization.getId());

        Optional<Organization> foundOrganization = organizationRepository.findById(savedOrganization.getId());
        assertFalse(foundOrganization.isPresent());
    }
}
