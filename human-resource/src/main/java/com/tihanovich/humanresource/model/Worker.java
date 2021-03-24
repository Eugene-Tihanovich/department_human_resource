package com.tihanovich.humanresource.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 30)
    @Column(length = 30)
    private String name;

    @NotBlank
    @Size(max = 30)
    @Column(length = 30)
    private String surname;

    @NotBlank
    @Size(max = 30)
    @Column(length = 30)
    private String middleName;

    @NotBlank
    @Size(max = 100)
    @Column(length = 100)
    private String position;

    @OneToMany(mappedBy = "worker")
    private List<Task> tasks;

}
