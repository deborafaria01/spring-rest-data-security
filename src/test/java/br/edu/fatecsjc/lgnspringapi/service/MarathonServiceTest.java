package br.edu.fatecsjc.lgnspringapi.service;

import br.edu.fatecsjc.lgnspringapi.entity.Marathon;
import br.edu.fatecsjc.lgnspringapi.repository.MarathonRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class MarathonServiceTest {

    @Mock
    private MarathonRepository marathonRepository;

    @InjectMocks
    private MarathonService marathonService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Marathon marathon1 = Marathon.builder().id(1L).name("Marathon 1").weight(10.0).score(95.0).build();
        Marathon marathon2 = Marathon.builder().id(2L).name("Marathon 2").weight(8.0).score(80.0).build();
        when(marathonRepository.findAll()).thenReturn(Arrays.asList(marathon1, marathon2));

        List<Marathon> result = marathonService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Marathon 1", result.get(0).getName());
        assertEquals(10.0, result.get(0).getWeight());
        assertEquals(95.0, result.get(0).getScore());
    }

    @Test
    void testFindById() {
        Marathon marathon = Marathon.builder().id(1L).name("Marathon 1").weight(10.0).score(95.0).build();
        when(marathonRepository.findById(1L)).thenReturn(Optional.of(marathon));

        Optional<Marathon> result = marathonService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Marathon 1", result.get().getName());
        assertEquals(10.0, result.get().getWeight());
        assertEquals(95.0, result.get().getScore());
    }

    @Test
    void testSave() {
        Marathon marathon = Marathon.builder().name("Marathon 1").weight(10.0).score(95.0).build();
        when(marathonRepository.save(any(Marathon.class))).thenReturn(marathon);

        Marathon result = marathonService.save(marathon);

        assertNotNull(result);
        assertEquals("Marathon 1", result.getName());
        assertEquals(10.0, result.getWeight());
        assertEquals(95.0, result.getScore());
    }

    @Test
    void testUpdate() {
        Marathon updatedMarathon = Marathon.builder().id(1L).name("Updated Marathon").weight(12.0).score(98.0).build();

        when(marathonRepository.existsById(1L)).thenReturn(true);
        when(marathonRepository.save(any(Marathon.class))).thenReturn(updatedMarathon);

        Marathon result = marathonService.update(1L, updatedMarathon);

        assertNotNull(result);
        assertEquals("Updated Marathon", result.getName());
        assertEquals(12.0, result.getWeight());
        assertEquals(98.0, result.getScore());
    }

    @Test
    void testUpdateNonExistentMarathon() {
        Marathon updatedMarathon = Marathon.builder().id(1L).name("Updated Marathon").weight(12.0).score(98.0).build();

        when(marathonRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            marathonService.update(1L, updatedMarathon);
        });

        assertEquals("Marathon not found with ID: 1", exception.getMessage());
    }

    @Test
    void testDeleteById() {
        doNothing().when(marathonRepository).deleteById(1L);

        marathonService.deleteById(1L);

        verify(marathonRepository, times(1)).deleteById(1L);
    }
}
