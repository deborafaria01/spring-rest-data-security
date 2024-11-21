package br.edu.fatecsjc.lgnspringapi.repository;

import br.edu.fatecsjc.lgnspringapi.entity.Group;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class GroupRepositoryTest {

    @Autowired
    private GroupRepository groupRepository;

    @Test
    void testSaveAndFind() {
        Group group = Group.builder()
                .name("Test Group")
                .build();

        Group savedGroup = groupRepository.save(group);

        assertNotNull(savedGroup.getId());
        assertEquals("Test Group", savedGroup.getName());

        Optional<Group> foundGroup = groupRepository.findById(savedGroup.getId());
        assertTrue(foundGroup.isPresent());
        assertEquals("Test Group", foundGroup.get().getName());
    }

    @Test
    void testDelete() {
        Group group = Group.builder()
                .name("Delete Test")
                .build();

        Group savedGroup = groupRepository.save(group);

        groupRepository.deleteById(savedGroup.getId());

        Optional<Group> foundGroup = groupRepository.findById(savedGroup.getId());
        assertFalse(foundGroup.isPresent());
    }
}
