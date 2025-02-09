package guru.sfg.brewery.models.security;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by jt on 6/21/20.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String username;
    private String password;

    @Singular // give us build patter a singular method for add authority
    @ManyToMany(cascade = CascadeType.MERGE) // ** ManyToMany
    // ** map table relationship
    @JoinTable(name = "users_authorities",
        joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "id")})
    private Set<Authority> authorities;

    @Builder.Default
    private Boolean accountNonExpired = true;
    @Builder.Default
    private Boolean accountNonLocked = true;
    @Builder.Default
    private Boolean credentialsNonExpired = true;
    @Builder.Default
    private Boolean enabled = true;


}