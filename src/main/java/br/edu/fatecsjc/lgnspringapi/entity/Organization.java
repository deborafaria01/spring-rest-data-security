package br.edu.fatecsjc.lgnspringapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "organizations")
public class Organization {
    @Id
    @SequenceGenerator(initialValue = 1, allocationSize = 1, name = "organizationsidgen", sequenceName = "organizations_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "organizationsidgen")
    private Long id;

    @Column(nullable = false) // The name of the organization cannot be null
    private String name;

    @Column(nullable = false) // Ensures the institution name is mandatory
    private String institutionName;

    private String country;

    // Address composition fields
    private String street;
    private String number;
    private String neighborhood;
    private String zipCode;
    private String city;
    private String state;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Group> groups;
}
