package br.edu.fatecsjc.lgnspringapi.service;

import br.edu.fatecsjc.lgnspringapi.dto.OrganizationDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Organization;
import br.edu.fatecsjc.lgnspringapi.repository.OrganizationRepository;
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
public class OrganizationServiceTest {

    @Mock
    private OrganizationRepository organizationRepository;

    @InjectMocks
    private OrganizationService organizationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        Organization organization = Organization.builder()
                .name("Test Organization")
                .build();

        when(organizationRepository.findAll()).thenReturn(Arrays.asList(organization));

        List<OrganizationDTO> result = organizationService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Organization", result.get(0).getName());
    }

    @Test
    void testFindById() {
        Organization organization = Organization.builder()
                .name("Test Organization")
                .build();

        when(organizationRepository.findById(1L)).thenReturn(Optional.of(organization));

        Optional<OrganizationDTO> result = organizationService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Test Organization", result.get().getName());
    }

    @Test
    void testSave() {
        OrganizationDTO organizationDTO = OrganizationDTO.builder()
                .name("Test Organization")
                .build();

        Organization organization = Organization.builder()
                .name("Test Organization")
                .build();

        when(organizationRepository.save(any(Organization.class))).thenReturn(organization);

        OrganizationDTO result = organizationService.save(organizationDTO);

        assertNotNull(result);
        assertEquals("Test Organization", result.getName());
    }

    @Test
    void testUpdate() {
        OrganizationDTO organizationDTO = OrganizationDTO.builder()
                .name("Updated Organization")
                .build();

        Organization existingOrganization = Organization.builder()
                .id(1L)
                .name("Test Organization")
                .build();

        when(organizationRepository.findById(1L)).thenReturn(Optional.of(existingOrganization));
        when(organizationRepository.save(any(Organization.class))).thenReturn(existingOrganization);

        OrganizationDTO result = organizationService.save(1L, organizationDTO);

        assertNotNull(result);
        assertEquals("Updated Organization", result.getName());
    }

    @Test
    void testDeleteById() {
        doNothing().when(organizationRepository).deleteById(1L);

        organizationService.deleteById(1L);

        verify(organizationRepository, times(1)).deleteById(1L);
    }
}
