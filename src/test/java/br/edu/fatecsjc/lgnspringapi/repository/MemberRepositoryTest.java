package br.edu.fatecsjc.lgnspringapi.repository;

import br.edu.fatecsjc.lgnspringapi.entity.Group;
import br.edu.fatecsjc.lgnspringapi.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Test
    void testSaveAndFind() {
        Group group = Group.builder()
                .name("Test Group")
                .build();
        group = groupRepository.save(group);

        Member member = Member.builder()
                .name("John Doe")
                .age(30)
                .group(group)
                .build();

        Member savedMember = memberRepository.save(member);

        assertNotNull(savedMember.getId());
        assertEquals("John Doe", savedMember.getName());

        List<Member> members = memberRepository.findAll();
        assertEquals(1, members.size());
        assertEquals("Test Group", members.get(0).getGroup().getName());
    }

    @Test
    void testDeleteMembersByGroup() {
        Group group = Group.builder()
                .name("Test Group")
                .build();
        group = groupRepository.save(group);

        Member member = Member.builder()
                .name("John Doe")
                .age(30)
                .group(group)
                .build();
        memberRepository.save(member);

        memberRepository.deleteMembersByGroup(group);

        List<Member> members = memberRepository.findAll();
        assertTrue(members.isEmpty());
    }
}
