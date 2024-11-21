package br.edu.fatecsjc.lgnspringapi.resource;

import br.edu.fatecsjc.lgnspringapi.entity.Marathon;
import br.edu.fatecsjc.lgnspringapi.service.MarathonService;
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

class MarathonResourceTest {

    @Mock
    private MarathonService marathonService;

    @InjectMocks
    private MarathonResource marathonResource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings("deprecation")
    @Test
    void testGetAllMarathons() {
        Marathon marathon1 = Marathon.builder()
                .id(1L)
                .name("Marathon 1")
                .weight(10.0)
                .score(95.0)
                .build();

        Marathon marathon2 = Marathon.builder()
                .id(2L)
                .name("Marathon 2")
                .weight(8.0)
                .score(85.0)
                .build();

        when(marathonService.findAll()).thenReturn(Arrays.asList(marathon1, marathon2));

        ResponseEntity<List<Marathon>> response = marathonResource.getAllMarathons();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Marathon 1", response.getBody().get(0).getName());
        verify(marathonService, times(1)).findAll();
    }

    @SuppressWarnings("deprecation")
    @Test
    void testGetMarathonById() {
        Marathon marathon = Marathon.builder()
                .id(1L)
                .name("Marathon 1")
                .weight(10.0)
                .score(95.0)
                .build();

        when(marathonService.findById(1L)).thenReturn(Optional.of(marathon));

        ResponseEntity<Marathon> response = marathonResource.getMarathonById(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Marathon 1", response.getBody().getName());
        verify(marathonService, times(1)).findById(1L);
    }

    @SuppressWarnings("deprecation")
    @Test
    void testGetMarathonById_NotFound() {
        when(marathonService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Marathon> response = marathonResource.getMarathonById(1L);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        verify(marathonService, times(1)).findById(1L);
    }

    @SuppressWarnings("deprecation")
    @Test
    void testCreateMarathon() {
        Marathon marathon = Marathon.builder()
                .name("Marathon 1")
                .weight(10.0)
                .score(95.0)
                .build();

        when(marathonService.save(any(Marathon.class))).thenReturn(marathon);

        ResponseEntity<Marathon> response = marathonResource.createMarathon(marathon);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Marathon 1", response.getBody().getName());
        verify(marathonService, times(1)).save(any(Marathon.class));
    }

    @SuppressWarnings("deprecation")
    @Test
    void testUpdateMarathon() {
        Marathon updatedMarathon = Marathon.builder()
                .id(1L)
                .name("Updated Marathon")
                .weight(12.0)
                .score(90.0)
                .build();

        when(marathonService.update(eq(1L), any(Marathon.class))).thenReturn(updatedMarathon);

        ResponseEntity<Marathon> response = marathonResource.updateMarathon(1L, updatedMarathon);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Updated Marathon", response.getBody().getName());
        verify(marathonService, times(1)).update(eq(1L), any(Marathon.class));
    }

    @SuppressWarnings("deprecation")
    @Test
    void testDeleteMarathon() {
        doNothing().when(marathonService).deleteById(1L);

        ResponseEntity<Void> response = marathonResource.deleteMarathon(1L);

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(marathonService, times(1)).deleteById(1L);
    }
}
