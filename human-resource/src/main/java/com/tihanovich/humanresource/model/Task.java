package com.tihanovich.humanresource.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 80)
    @Column(length = 80)
    private String name;

    private Integer workTime;

    private Date startDate;

    private Date endDate;

    @NotBlank
    @Size(max = 16)
    @Column(length = 16)
    private String status;

    @ManyToOne
    private Project project;

    @ManyToOne
    private Worker worker;

}
