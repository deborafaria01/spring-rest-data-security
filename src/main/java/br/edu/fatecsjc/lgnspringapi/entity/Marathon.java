package br.edu.fatecsjc.lgnspringapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double weight;
    private Double score;

    @ManyToMany(mappedBy = "marathons")
    @JsonIgnore
    @JsonBackReference
    private List<Member> members;
}
