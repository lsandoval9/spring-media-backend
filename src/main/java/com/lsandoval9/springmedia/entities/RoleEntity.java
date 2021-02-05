package com.lsandoval9.springmedia.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lsandoval9.springmedia.security.auth.Roles;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long $id;

    @Enumerated(EnumType.STRING)
    @NonNull
    private Roles role;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false, unique = false)
    private UserEntity user;
}
