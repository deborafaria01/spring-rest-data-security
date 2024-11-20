package br.edu.fatecsjc.lgnspringapi.service;

import br.edu.fatecsjc.lgnspringapi.dto.MemberDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Group;
import br.edu.fatecsjc.lgnspringapi.entity.Member;
import br.edu.fatecsjc.lgnspringapi.repository.GroupRepository;
import br.edu.fatecsjc.lgnspringapi.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        List<Member> members = Arrays.asList(
                Member.builder().name("John").age(25).build(),
                Member.builder().name("Jane").age(30).build()
        );

        when(memberRepository.findAll()).thenReturn(members);

        List<Member> result = memberService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testSave() {
        MemberDTO memberDTO = MemberDTO.builder().name("John").age(25).groupId(1L).build();
        Group group = Group.builder().id(1L).name("Group A").build();

        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
        when(memberRepository.save(any(Member.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Member savedMember = memberService.save(memberDTO);

        assertNotNull(savedMember);
        assertEquals("John", savedMember.getName());
        assertEquals(25, savedMember.getAge());
        assertEquals(1L, savedMember.getGroup().getId());
    }

    @Test
    void testUpdate() {
        Member existingMember = Member.builder().id(1L).name("John").age(25).build();
        MemberDTO memberDTO = MemberDTO.builder().name("Updated Name").age(30).build();

        when(memberRepository.findById(1L)).thenReturn(Optional.of(existingMember));
        when(memberRepository.save(any(Member.class))).thenReturn(existingMember);

        Optional<Member> updatedMember = memberService.update(1L, memberDTO);

        assertTrue(updatedMember.isPresent());
        assertEquals("Updated Name", updatedMember.get().getName());
        assertEquals(30, updatedMember.get().getAge());
    }

    @Test
    void testDeleteById() {
        when(memberRepository.existsById(1L)).thenReturn(true);
        doNothing().when(memberRepository).deleteById(1L);

        boolean result = memberService.deleteById(1L);

        assertTrue(result);
        verify(memberRepository, times(1)).deleteById(1L);
    }
}
