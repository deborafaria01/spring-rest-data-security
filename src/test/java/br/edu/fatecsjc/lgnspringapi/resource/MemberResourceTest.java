package br.edu.fatecsjc.lgnspringapi.resource;

import br.edu.fatecsjc.lgnspringapi.entity.Member;
import br.edu.fatecsjc.lgnspringapi.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MemberResourceTest {

    @Mock
    private MemberService memberService;

    @InjectMocks
    private MemberResource memberResource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings("deprecation")
    @Test
    void testGetAllMembers() {
        Member member1 = Member.builder().id(1L).name("John Doe").age(30).build();
        Member member2 = Member.builder().id(2L).name("Jane Doe").age(25).build();

        when(memberService.findAll()).thenReturn(Arrays.asList(member1, member2));

        ResponseEntity<List<Member>> response = memberResource.getAllMembers();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        assertEquals("John Doe", response.getBody().get(0).getName());
        assertEquals(30, response.getBody().get(0).getAge());
        verify(memberService, times(1)).findAll();
    }

    @SuppressWarnings("deprecation")
    @Test
    void testGetMemberById() {
        Member member = Member.builder().id(1L).name("John Doe").age(30).build();

        when(memberService.findById(1L)).thenReturn(Optional.of(member));

        ResponseEntity<Member> response = memberResource.getMemberById(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("John Doe", response.getBody().getName());
        assertEquals(30, response.getBody().getAge());
        verify(memberService, times(1)).findById(1L);
    }

    @SuppressWarnings("deprecation")
    @Test
    void testGetMemberByIdNotFound() {
        when(memberService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Member> response = memberResource.getMemberById(1L);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        verify(memberService, times(1)).findById(1L);
    }

    @SuppressWarnings("deprecation")
    @Test
    void testCreateMember() {
        Member member = Member.builder().id(1L).name("John Doe").age(30).build();

        when(memberService.save(any())).thenReturn(member);

        ResponseEntity<Member> response = memberResource.createMember(null);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("John Doe", response.getBody().getName());
        assertEquals(30, response.getBody().getAge());
        verify(memberService, times(1)).save(any());
    }

    @SuppressWarnings("deprecation")
    @Test
    void testUpdateMember() {
        Member member = Member.builder().id(1L).name("John Doe").age(30).build();

        when(memberService.update(eq(1L), any())).thenReturn(Optional.of(member));

        ResponseEntity<Member> response = memberResource.updateMember(1L, null);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("John Doe", response.getBody().getName());
        assertEquals(30, response.getBody().getAge());
        verify(memberService, times(1)).update(eq(1L), any());
    }

    @SuppressWarnings("deprecation")
    @Test
    void testUpdateMemberNotFound() {
        when(memberService.update(eq(1L), any())).thenReturn(Optional.empty());

        ResponseEntity<Member> response = memberResource.updateMember(1L, null);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        verify(memberService, times(1)).update(eq(1L), any());
    }

    @SuppressWarnings("deprecation")
    @Test
    void testDeleteMember() {
        when(memberService.deleteById(1L)).thenReturn(true);

        ResponseEntity<Void> response = memberResource.deleteMember(1L);

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(memberService, times(1)).deleteById(1L);
    }

    @SuppressWarnings("deprecation")
    @Test
    void testDeleteMemberNotFound() {
        when(memberService.deleteById(1L)).thenReturn(false);

        ResponseEntity<Void> response = memberResource.deleteMember(1L);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        verify(memberService, times(1)).deleteById(1L);
    }
}
