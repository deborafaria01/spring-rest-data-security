package br.edu.fatecsjc.lgnspringapi.repository;

import br.edu.fatecsjc.lgnspringapi.entity.Marathon;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class MarathonRepositoryTest {

    @Autowired
    private MarathonRepository marathonRepository;

    @Test
    void testSaveAndFind() {
        Marathon marathon = Marathon.builder()
                .name("Test Marathon")
                .weight(10.5)
                .score(95.0)
                .build();

        Marathon savedMarathon = marathonRepository.save(marathon);

        assertNotNull(savedMarathon.getId());
        assertEquals("Test Marathon", savedMarathon.getName());

        Optional<Marathon> foundMarathon = marathonRepository.findById(savedMarathon.getId());
        assertTrue(foundMarathon.isPresent());
        assertEquals(95.0, foundMarathon.get().getScore());
    }

    @Test
    void testDelete() {
        Marathon marathon = Marathon.builder()
                .name("Delete Test")
                .weight(8.0)
                .score(80.0)
                .build();

        Marathon savedMarathon = marathonRepository.save(marathon);

        marathonRepository.deleteById(savedMarathon.getId());

        Optional<Marathon> foundMarathon = marathonRepository.findById(savedMarathon.getId());
        assertFalse(foundMarathon.isPresent());
    }
}
