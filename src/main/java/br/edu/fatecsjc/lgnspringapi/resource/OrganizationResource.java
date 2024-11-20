package br.edu.fatecsjc.lgnspringapi.resource;

import br.edu.fatecsjc.lgnspringapi.dto.OrganizationDTO;
import br.edu.fatecsjc.lgnspringapi.service.OrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organization")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Organization")
@SecurityRequirement(name = "bearerAuth")
public class OrganizationResource {

    @Autowired
    private OrganizationService organizationService;

    @GetMapping
    @Operation(
            description = "Get all organizations and their groups",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized/Invalid token", responseCode = "403"),
                    @ApiResponse(description = "Unknown error", responseCode = "400"),
            }
    )
    public ResponseEntity<List<OrganizationDTO>> getAllOrganizations() {
        return ResponseEntity.ok(organizationService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(
            description = "Get an organization by ID",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized/Invalid token", responseCode = "403"),
                    @ApiResponse(description = "Unknown error", responseCode = "400"),
            }
    )
    public ResponseEntity<OrganizationDTO> getOrganizationById(@PathVariable Long id) {
        return organizationService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    @Operation(
            description = "Update an organization by ID",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "201"),
                    @ApiResponse(description = "Unauthorized/Invalid token", responseCode = "403"),
                    @ApiResponse(description = "Unknown error", responseCode = "400"),
            }
    )
    public ResponseEntity<OrganizationDTO> update(@PathVariable Long id, @RequestBody OrganizationDTO body) {
        return ResponseEntity.status(HttpStatusCode.valueOf(201))
                .body(organizationService.save(id, body));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    @Operation(
            description = "Register a new organization",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "201"),
                    @ApiResponse(description = "Unauthorized/Invalid token", responseCode = "403"),
                    @ApiResponse(description = "Unknown error", responseCode = "400"),
            }
    )
    public ResponseEntity<OrganizationDTO> register(@RequestBody OrganizationDTO body) {
        return ResponseEntity.status(HttpStatusCode.valueOf(201))
                .body(organizationService.save(body));
    }

    @DeleteMapping("/{id}")
    @Operation(
            description = "Delete an organization by ID",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "204"),
                    @ApiResponse(description = "Unauthorized/Invalid token", responseCode = "403"),
                    @ApiResponse(description = "Unknown error", responseCode = "400"),
            }
    )
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        organizationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
