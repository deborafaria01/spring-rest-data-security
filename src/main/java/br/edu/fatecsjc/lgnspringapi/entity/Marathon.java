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
@Table(name = "marathons")
public class Marathon {
    @Id
    @SequenceGenerator(initialValue = 1, allocationSize = 1, name = "marathonsidgen", sequenceName = "marathons_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "marathonsidgen")
    private Long id;

    private String name;

    private Double weight;

    private Double score;

    @ManyToMany(mappedBy = "marathons")
    private List<Member> members; 
}
