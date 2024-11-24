package br.edu.fatecsjc.lgnspringapi.repository;

import br.edu.fatecsjc.lgnspringapi.entity.Group;
import br.edu.fatecsjc.lgnspringapi.entity.Member;
import br.edu.fatecsjc.lgnspringapi.entity.Organization;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Test
    void testSaveAndFind() {
        // Mock organization to satisfy the mandatory relationship
        Organization organization = Organization.builder()
                .name("Mock Organization")
                .country("Mock Country")
                .institutionName("Mock Institution") // Mandatory field
                .build();
        organization = organizationRepository.save(organization);

        // Mock group associated with the organization
        Group group = Group.builder()
                .name("Test Group")
                .organization(organization)
                .build();
        group = groupRepository.save(group);

        // Mock member associated with the group
        Member member = Member.builder()
                .name("John Doe")
                .age(30)
                .group(group)
                .build();
        Member savedMember = memberRepository.save(member);

        // Assertions
        assertNotNull(savedMember.getId());
        assertEquals("John Doe", savedMember.getName());
        assertEquals("Mock Organization", savedMember.getGroup().getOrganization().getName());
    }

}
