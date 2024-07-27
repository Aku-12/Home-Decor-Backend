package com.example.homedecor.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Table
@Entity
@Setter
@Getter
public class Payment {
    @Id
    private Integer id;
    @OneToOne
    private Orders order;
    @Column(
            name = "Type"
    )
    private String type;
    @Column(
            name = "Amount"
    )
    private String amount;
    @Column(
            name = "Date"
    )
    private LocalDate date;
}
