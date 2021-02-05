package com.lsandoval9.springmedia.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lsandoval9.springmedia.security.auth.Roles;
import com.lsandoval9.springmedia.security.auth.SecurityUser;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long $id;

    @Column(unique = true, nullable = false)
    @NonNull
    private String username;

    @Column(unique = true, nullable = false)
    @NonNull
    private String email;

    @Column(unique = true, nullable = false)
    @NonNull
    private String password;

    @Column(unique = false, nullable = false)
    @NonNull
    private String firstname;

    @Column(unique = false, nullable = false)
    @NonNull
    private String lastname;

    @Column(nullable = false, unique = false)
    @NonNull
    private Boolean isUserEnabled;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<RoleEntity> roles;

    @JsonIgnore
    public SecurityUser getSecurityUser() {

        Set<Roles> grantedRoles = this.roles
                .stream()
                .map(RoleEntity::getRole)
                .collect(Collectors.toSet());

        SecurityUser securityUser = new SecurityUser(
                this.username,
                this.email,
                this.password,
                this.firstname,
                this.lastname,
                grantedRoles,
                this.isUserEnabled
        );

        return securityUser;
    }


    @Override
    public String toString() {
        return "UserEntity{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", isUserEnabled=" + isUserEnabled +
                ", roles=" + roles +
                '}';
    }
}
