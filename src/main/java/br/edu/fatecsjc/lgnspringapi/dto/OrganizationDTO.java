package br.edu.fatecsjc.lgnspringapi.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrganizationDTO {
    private String name;
    private String institutionName;
    private String country;
    private String street;
    private String number;
    private String neighborhood;
    private String zipCode;
    private String city;
    private String state;
}
