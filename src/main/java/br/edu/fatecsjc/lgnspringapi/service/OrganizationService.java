package br.edu.fatecsjc.lgnspringapi.service;

import br.edu.fatecsjc.lgnspringapi.dto.OrganizationDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Organization;
import br.edu.fatecsjc.lgnspringapi.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    public List<OrganizationDTO> getAll() {
        return organizationRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<OrganizationDTO> findById(Long id) {
        return organizationRepository.findById(id).map(this::convertToDTO);
    }

    public OrganizationDTO save(OrganizationDTO body) {
        Organization organization = convertToEntity(body);
        return convertToDTO(organizationRepository.save(organization));
    }

    public OrganizationDTO save(Long id, OrganizationDTO body) {
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organization not found with id: " + id));
        organization.setName(body.getName());
        organization.setInstitutionName(body.getInstitutionName());
        organization.setCountry(body.getCountry());
        organization.setStreet(body.getStreet());
        organization.setNumber(body.getNumber());
        organization.setNeighborhood(body.getNeighborhood());
        organization.setZipCode(body.getZipCode());
        organization.setCity(body.getCity());
        organization.setState(body.getState());
        return convertToDTO(organizationRepository.save(organization));
    }

    public void deleteById(Long id) {
        organizationRepository.deleteById(id);
    }

    private OrganizationDTO convertToDTO(Organization organization) {
        return OrganizationDTO.builder()
                .name(organization.getName())
                .institutionName(organization.getInstitutionName())
                .country(organization.getCountry())
                .street(organization.getStreet())
                .number(organization.getNumber())
                .neighborhood(organization.getNeighborhood())
                .zipCode(organization.getZipCode())
                .city(organization.getCity())
                .state(organization.getState())
                .build();
    }

    private Organization convertToEntity(OrganizationDTO dto) {
        return Organization.builder()
                .name(dto.getName())
                .institutionName(dto.getInstitutionName())
                .country(dto.getCountry())
                .street(dto.getStreet())
                .number(dto.getNumber())
                .neighborhood(dto.getNeighborhood())
                .zipCode(dto.getZipCode())
                .city(dto.getCity())
                .state(dto.getState())
                .build();
    }
}
