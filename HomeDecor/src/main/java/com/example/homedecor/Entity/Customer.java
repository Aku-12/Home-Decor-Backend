package com.example.homedecor.Entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Table
@Entity
@Setter
@Getter

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;




    @Column
    private String username;
    @Column
    private String password;
//    @Column(
//            name = "Tel Number"
//    )
//    private Integer telNo;
@ManyToMany(fetch = FetchType.EAGER)
@JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
)
private List<Role> roles = new ArrayList<>();

}
