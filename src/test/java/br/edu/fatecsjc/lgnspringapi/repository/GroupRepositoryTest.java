package br.edu.fatecsjc.lgnspringapi.repository;

import br.edu.fatecsjc.lgnspringapi.entity.Group;
import br.edu.fatecsjc.lgnspringapi.entity.Organization;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class GroupRepositoryTest {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Test
    void testSaveAndFind() {
        // Mock organization to satisfy the mandatory relationship
        Organization organization = Organization.builder()
                .name("Test Organization")
                .country("Brazil")
                .institutionName("Mock Institution") // Mandatory field
                .build();
        organization = organizationRepository.save(organization);

        // Mock group associated with the organization
        Group group = Group.builder()
                .name("Test Group")
                .organization(organization)
                .build();
        Group savedGroup = groupRepository.save(group);

        // Assertions
        assertNotNull(savedGroup.getId());
        assertEquals("Test Group", savedGroup.getName());
        assertEquals("Test Organization", savedGroup.getOrganization().getName());
    }

    @Test
    void testDelete() {
        // Mock organization
        Organization organization = Organization.builder()
                .name("Delete Organization")
                .country("Brazil")
                .institutionName("Mock Institution") // Mandatory field
                .build();
        organization = organizationRepository.save(organization);

        // Mock group associated with the organization
        Group group = Group.builder()
                .name("Delete Test")
                .organization(organization)
                .build();
        Group savedGroup = groupRepository.save(group);

        // Delete group and assert it no longer exists
        groupRepository.deleteById(savedGroup.getId());
        Optional<Group> foundGroup = groupRepository.findById(savedGroup.getId());
        assertFalse(foundGroup.isPresent());
    }
}
