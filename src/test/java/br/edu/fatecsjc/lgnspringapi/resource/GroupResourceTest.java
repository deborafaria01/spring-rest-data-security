package br.edu.fatecsjc.lgnspringapi.resource;

import br.edu.fatecsjc.lgnspringapi.dto.GroupDTO;
import br.edu.fatecsjc.lgnspringapi.dto.MemberDTO;
import br.edu.fatecsjc.lgnspringapi.service.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GroupResourceTest {

    @Mock
    private GroupService groupService;

    @InjectMocks
    private GroupResource groupResource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings("deprecation")
    @Test
    void testGetAllGroups() {
        GroupDTO group1 = GroupDTO.builder()
                .id(1L)
                .name("Group 1")
                .members(Arrays.asList(
                        MemberDTO.builder().id(1L).name("Member 1").build(),
                        MemberDTO.builder().id(2L).name("Member 2").build()
                ))
                .build();

        GroupDTO group2 = GroupDTO.builder()
                .id(2L)
                .name("Group 2")
                .members(Arrays.asList(
                        MemberDTO.builder().id(3L).name("Member 3").build()
                ))
                .build();

        when(groupService.getAll()).thenReturn(Arrays.asList(group1, group2));

        ResponseEntity<List<GroupDTO>> response = groupResource.getAllGroups();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Group 1", response.getBody().get(0).getName());
        verify(groupService, times(1)).getAll();
    }

    @SuppressWarnings("deprecation")
    @Test
    void testGetGroupById() {
        GroupDTO group = GroupDTO.builder()
                .id(1L)
                .name("Group 1")
                .members(Arrays.asList(
                        MemberDTO.builder().id(1L).name("Member 1").build()
                ))
                .build();

        when(groupService.findById(1L)).thenReturn(group);

        ResponseEntity<GroupDTO> response = groupResource.getGroupById(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Group 1", response.getBody().getName());
        verify(groupService, times(1)).findById(1L);
    }

    @SuppressWarnings("deprecation")
    @Test
    void testRegisterGroup() {
        GroupDTO group = GroupDTO.builder()
                .name("Group 1")
                .members(Arrays.asList(
                        MemberDTO.builder().id(1L).name("Member 1").build()
                ))
                .build();

        when(groupService.save(group)).thenReturn(group);

        ResponseEntity<GroupDTO> response = groupResource.register(group);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Group 1", response.getBody().getName());
        verify(groupService, times(1)).save(group);
    }

    @SuppressWarnings("deprecation")
    @Test
    void testUpdateGroup() {
        GroupDTO updatedGroup = GroupDTO.builder()
                .id(1L)
                .name("Updated Group")
                .members(Arrays.asList(
                        MemberDTO.builder().id(1L).name("Updated Member").build()
                ))
                .build();

        when(groupService.save(eq(1L), any(GroupDTO.class))).thenReturn(updatedGroup);

        ResponseEntity<GroupDTO> response = groupResource.update(1L, updatedGroup);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Updated Group", response.getBody().getName());
        verify(groupService, times(1)).save(eq(1L), any(GroupDTO.class));
    }

    @SuppressWarnings("deprecation")
    @Test
    void testDeleteGroup() {
        doNothing().when(groupService).delete(1L);

        ResponseEntity<Void> response = groupResource.update(1L);

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(groupService, times(1)).delete(1L);
    }
}
