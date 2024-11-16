package br.edu.fatecsjc.lgnspringapi.resource;

import br.edu.fatecsjc.lgnspringapi.entity.Marathon;
import br.edu.fatecsjc.lgnspringapi.service.MarathonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/marathon")
@Tag(name = "Marathon")
public class MarathonResource {

    @Autowired
    private MarathonService marathonService;

    @GetMapping
    @Operation(description = "List all marathons")
    public ResponseEntity<List<Marathon>> getAllMarathons() {
        return ResponseEntity.ok(marathonService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(description = "Get a marathon by ID")
    public ResponseEntity<Marathon> getMarathonById(@PathVariable Long id) {
        return marathonService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

        @PostMapping
    @Operation(description = "Create a new marathon")
    public ResponseEntity<Marathon> createMarathon(@RequestBody Marathon marathon) {
        return ResponseEntity.status(201).body(marathonService.save(marathon));
    }

    @PutMapping("/{id}")
    @Operation(description = "Update a marathon by ID")
    public ResponseEntity<Marathon> updateMarathon(@PathVariable Long id, @RequestBody Marathon marathon) {
        return ResponseEntity.ok(marathonService.update(id, marathon));
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete a marathon by ID")
    public ResponseEntity<Void> deleteMarathon(@PathVariable Long id) {
        marathonService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
