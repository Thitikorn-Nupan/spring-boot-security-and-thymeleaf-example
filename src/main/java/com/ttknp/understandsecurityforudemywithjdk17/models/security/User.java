package com.ttknp.understandsecurityforudemywithjdk17.models.security;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;
import java.util.stream.Collectors;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "users")
// if you get error A UserDetailsService must be set
// when authenticate user with UserDetailsService
// don't forget add UserDetails to your user entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String email;

    @Singular
    @ManyToMany(cascade = {CascadeType.MERGE},fetch = FetchType.EAGER)//(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<Role> roles;

    // @Transient Specifies that the property or field is not persistent.
    // It is used to annotate a property or field of an entity class, mapped superclass, or embeddable class.
    @Transient
    public Set<GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(Role::getAuthorities)
                .flatMap(Set::stream)
                .map(authority -> {
                    return new SimpleGrantedAuthority(authority.getPermission());
                })
                .collect(Collectors.toSet());
    }

    /**
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;

    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }*/

}
