package com.example.homeway.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "double not null")
    private Double price;

    @Column(columnDefinition = "varchar(20) ")
    private String status;

    @Column(columnDefinition = "datetime default current_timestamp")
    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "request_id", referencedColumnName = "id")
    @JsonIgnore
    private Request request;

}
