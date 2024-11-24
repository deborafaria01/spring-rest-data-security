package br.edu.fatecsjc.lgnspringapi.repository;

import br.edu.fatecsjc.lgnspringapi.entity.Group;
import br.edu.fatecsjc.lgnspringapi.entity.Marathon;
import br.edu.fatecsjc.lgnspringapi.entity.Member;
import br.edu.fatecsjc.lgnspringapi.entity.Organization;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import jakarta.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class RelationshipTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private MarathonRepository marathonRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Test
    public void testMemberGroupMarathonRelationship() {
        // Mock organization
        Organization organization = Organization.builder()
                .name("Relationship Organization")
                .country("Test Country")
                .institutionName("Test Institution") // Mandatory field
                .build();
        organization = organizationRepository.save(organization);

        // Mock group associated with the organization
        Group group = Group.builder()
                .name("Group A")
                .organization(organization)
                .build();
        group = groupRepository.save(group);

        // Mock marathon
        Marathon marathon = Marathon.builder()
                .name("Marathon 1")
                .weight(10.5)
                .score(95.0)
                .build();
        marathon = marathonRepository.save(marathon);

        // Mock member associated with the group and marathon
        Member member = Member.builder()
                .name("John Doe")
                .age(25)
                .group(group)
                .marathons(List.of(marathon))
                .build();
        member = memberRepository.save(member);

        // Assertions
        assertNotNull(member.getId(), "Member ID should not be null");
        assertEquals(1, member.getMarathons().size(), "Member should have one associated marathon");
        assertEquals(group.getId(), member.getGroup().getId(), "Group ID should match the associated group");
    }
}
