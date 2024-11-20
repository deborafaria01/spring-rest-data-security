package br.edu.fatecsjc.lgnspringapi.service;

import br.edu.fatecsjc.lgnspringapi.converter.GroupConverter;
import br.edu.fatecsjc.lgnspringapi.dto.GroupDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Group;
import br.edu.fatecsjc.lgnspringapi.repository.GroupRepository;
import br.edu.fatecsjc.lgnspringapi.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private GroupConverter groupConverter;

    @InjectMocks
    private GroupService groupService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        List<Group> groups = new ArrayList<>();
        groups.add(Group.builder().id(1L).name("Group A").build());
        groups.add(Group.builder().id(2L).name("Group B").build());

        List<GroupDTO> groupDTOs = new ArrayList<>();
        groupDTOs.add(GroupDTO.builder().id(1L).name("Group A").build());
        groupDTOs.add(GroupDTO.builder().id(2L).name("Group B").build());

        when(groupRepository.findAll()).thenReturn(groups);
        when(groupConverter.convertToDto(groups)).thenReturn(groupDTOs);

        List<GroupDTO> result = groupService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Group A", result.get(0).getName());
    }

    @Test
    void testFindById() {
        Group group = Group.builder().id(1L).name("Group A").build();
        GroupDTO groupDTO = GroupDTO.builder().id(1L).name("Group A").build();

        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
        when(groupConverter.convertToDto(group)).thenReturn(groupDTO);

        GroupDTO result = groupService.findById(1L);

        assertNotNull(result);
        assertEquals("Group A", result.getName());
    }

    @Test
    void testSaveExistingGroup() {
        Group existingGroup = Group.builder()
                .id(1L)
                .name("Existing Group")
                .members(new ArrayList<>())
                .build();

        GroupDTO dto = GroupDTO.builder().name("Updated Group").build();

        Group groupToSave = Group.builder()
                .id(1L)
                .name("Updated Group")
                .members(new ArrayList<>())
                .build();

        when(groupRepository.findById(1L)).thenReturn(Optional.of(existingGroup));
        when(groupConverter.convertToEntity(dto, existingGroup)).thenReturn(groupToSave);
        when(groupRepository.save(groupToSave)).thenReturn(groupToSave);
        when(groupConverter.convertToDto(groupToSave)).thenReturn(dto);

        GroupDTO result = groupService.save(1L, dto);

        assertNotNull(result);
        assertEquals("Updated Group", result.getName());
        verify(groupRepository, times(1)).save(groupToSave);
    }

    @Test
    void testSaveNewGroup() {
        Group group = Group.builder().id(1L).name("New Group").build();
        GroupDTO dto = GroupDTO.builder().name("New Group").build();

        when(groupConverter.convertToEntity(dto)).thenReturn(group);
        when(groupRepository.save(group)).thenReturn(group);
        when(groupConverter.convertToDto(group)).thenReturn(dto);

        GroupDTO result = groupService.save(dto);

        assertNotNull(result);
        assertEquals("New Group", result.getName());
    }

    @Test
    void testDelete() {
        doNothing().when(groupRepository).deleteById(1L);

        groupService.delete(1L);

        verify(groupRepository, times(1)).deleteById(1L);
    }
}


