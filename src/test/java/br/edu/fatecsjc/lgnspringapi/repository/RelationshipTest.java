package br.edu.fatecsjc.lgnspringapi.repository;

import br.edu.fatecsjc.lgnspringapi.entity.Group;
import br.edu.fatecsjc.lgnspringapi.entity.Marathon;
import br.edu.fatecsjc.lgnspringapi.entity.Member;
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

    @Test
    public void testMemberGroupMarathonRelationship() {
        Group group = Group.builder().name("Group A").build();
        group = groupRepository.save(group);

        Marathon marathon = Marathon.builder()
                .name("Marathon 1")
                .weight(10.5)
                .score(95.0)
                .build();
        marathon = marathonRepository.save(marathon);

        Member member = Member.builder()
                .name("John Doe")
                .age(25)
                .group(group)
                .marathons(List.of(marathon))
                .build();
        member = memberRepository.save(member);

        assertNotNull(member.getId(), "Member ID should not be null");
        assertEquals(1, member.getMarathons().size(), "Member should have one associated marathon");
        assertEquals(group.getId(), member.getGroup().getId(), "Group ID should match the associated group");
    }
}
