package br.edu.fatecsjc.lgnspringapi.resource;

import br.edu.fatecsjc.lgnspringapi.dto.OrganizationDTO;
import br.edu.fatecsjc.lgnspringapi.service.OrganizationService;
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

class OrganizationResourceTest {

    @Mock
    private OrganizationService organizationService;

    @InjectMocks
    private OrganizationResource organizationResource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings("deprecation")
    @Test
    void testGetAllOrganizations() {
        // Mocking data
        OrganizationDTO organization1 = OrganizationDTO.builder()
                .name("Org 1")
                .institutionName("Institution 1")
                .country("USA")
                .street("Street 1")
                .number("123")
                .neighborhood("Neighborhood 1")
                .zipCode("00000-000")
                .city("City 1")
                .state("State 1")
                .build();

        OrganizationDTO organization2 = OrganizationDTO.builder()
                .name("Org 2")
                .institutionName("Institution 2")
                .country("Brazil")
                .street("Street 2")
                .number("456")
                .neighborhood("Neighborhood 2")
                .zipCode("11111-111")
                .city("City 2")
                .state("State 2")
                .build();

        when(organizationService.getAll()).thenReturn(Arrays.asList(organization1, organization2));

        // Test method
        ResponseEntity<List<OrganizationDTO>> response = organizationResource.getAllOrganizations();

        // Assertions
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Org 1", response.getBody().get(0).getName());
        assertEquals("USA", response.getBody().get(0).getCountry());
        verify(organizationService, times(1)).getAll();
    }

    @SuppressWarnings("deprecation")
    @Test
    void testGetOrganizationById() {
        // Mocking data
        OrganizationDTO organization = OrganizationDTO.builder()
                .name("Org 1")
                .institutionName("Institution 1")
                .country("USA")
                .street("Street 1")
                .number("123")
                .neighborhood("Neighborhood 1")
                .zipCode("00000-000")
                .city("City 1")
                .state("State 1")
                .build();

        when(organizationService.findById(1L)).thenReturn(Optional.of(organization));

        // Test method
        ResponseEntity<OrganizationDTO> response = organizationResource.getOrganizationById(1L);

        // Assertions
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Org 1", response.getBody().getName());
        assertEquals("USA", response.getBody().getCountry());
        verify(organizationService, times(1)).findById(1L);
    }

    @SuppressWarnings("deprecation")
    @Test
    void testGetOrganizationByIdNotFound() {
        when(organizationService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<OrganizationDTO> response = organizationResource.getOrganizationById(1L);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(organizationService, times(1)).findById(1L);
    }

    @SuppressWarnings("deprecation")
    @Test
    void testRegister() {
        // Mocking data
        OrganizationDTO organization = OrganizationDTO.builder()
                .name("Org 1")
                .institutionName("Institution 1")
                .country("USA")
                .street("Street 1")
                .number("123")
                .neighborhood("Neighborhood 1")
                .zipCode("00000-000")
                .city("City 1")
                .state("State 1")
                .build();

        when(organizationService.save(organization)).thenReturn(organization);

        ResponseEntity<OrganizationDTO> response = organizationResource.register(organization);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Org 1", response.getBody().getName());
        assertEquals("USA", response.getBody().getCountry());
        verify(organizationService, times(1)).save(organization);
    }

    @SuppressWarnings("deprecation")
    @Test
    void testUpdate() {
        OrganizationDTO updatedOrganization = OrganizationDTO.builder()
                .name("Updated Org")
                .institutionName("Updated Institution")
                .country("Canada")
                .street("Street Updated")
                .number("789")
                .neighborhood("Updated Neighborhood")
                .zipCode("22222-222")
                .city("Updated City")
                .state("Updated State")
                .build();

        when(organizationService.save(eq(1L), any(OrganizationDTO.class))).thenReturn(updatedOrganization);

        ResponseEntity<OrganizationDTO> response = organizationResource.update(1L, updatedOrganization);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Updated Org", response.getBody().getName());
        assertEquals("Canada", response.getBody().getCountry());
        verify(organizationService, times(1)).save(eq(1L), any(OrganizationDTO.class));
    }

    @SuppressWarnings("deprecation")
    @Test
    void testDelete() {
        doNothing().when(organizationService).deleteById(1L);

        ResponseEntity<Void> response = organizationResource.delete(1L);

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(organizationService, times(1)).deleteById(1L);
    }
}
