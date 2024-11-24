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
        // Mock organization
        Organization organization = Organization.builder()
                .name("Test Organization")
                .country("Brazil")
                .institutionName("Institution Name") // Mandatory field
                .build();
        Organization savedOrganization = organizationRepository.save(organization);

        // Assertions
        assertNotNull(savedOrganization.getId());
        assertEquals("Test Organization", savedOrganization.getName());
        assertEquals("Brazil", savedOrganization.getCountry());
    }

    @Test
    void testDelete() {
        // Mock organization
        Organization organization = Organization.builder()
                .name("Delete Test")
                .institutionName("Institution Name") // Mandatory field
                .build();
        Organization savedOrganization = organizationRepository.save(organization);

        // Delete organization and assert it no longer exists
        organizationRepository.deleteById(savedOrganization.getId());
        Optional<Organization> foundOrganization = organizationRepository.findById(savedOrganization.getId());
        assertFalse(foundOrganization.isPresent());
    }
}

