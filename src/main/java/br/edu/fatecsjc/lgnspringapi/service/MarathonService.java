package br.edu.fatecsjc.lgnspringapi.service;

import br.edu.fatecsjc.lgnspringapi.entity.Marathon;
import br.edu.fatecsjc.lgnspringapi.repository.MarathonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarathonService {

    @Autowired
    private MarathonRepository marathonRepository;

    public List<Marathon> findAll() {
        return marathonRepository.findAll();
    }

    public Optional<Marathon> findById(Long id) {
        return marathonRepository.findById(id);
    }

    public Marathon save(Marathon marathon) {
        return marathonRepository.save(marathon);
    }

    public Marathon update(Long id, Marathon marathon) {
        if (marathonRepository.existsById(id)) {
            marathon.setId(id);
            return marathonRepository.save(marathon);
        }
        throw new RuntimeException("Marathon not found with ID: " + id);
    }

    public void deleteById(Long id) {
        marathonRepository.deleteById(id);
    }
}
