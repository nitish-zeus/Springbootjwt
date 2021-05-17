package com.nitish.jwt.model;

import lombok.Data;
import lombok.Generated;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name="usrtab")
public class User {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private  String username;
    private String password;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name="rolestab",
            joinColumns=@JoinColumn(name="id")
    )
    @Column(name="role")
    private Set<String> roles;

}
