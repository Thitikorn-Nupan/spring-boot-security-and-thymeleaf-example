package com.ttknp.understandsecurityforudemywithjdk17.models.security;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    // You can see some amazing features if you connect database with tool's intellij
    // *** ctrl + click to value you set for join table *** will go ahead to real column!
    @Singular
    // *** error :  detached entity passed to persist thrown by JPA and Hibernate
    // fix by (cascade = CascadeType.MERGE) instead (cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @ManyToMany(cascade = CascadeType.MERGE) //(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "roles_authorities",
        joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "id")})
    private Set<Authority> authorities;

}