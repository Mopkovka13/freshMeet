package com.mopkovka.springboot.freshMeet.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    // Истек ли срок действия учетной записи?
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Заблокирован ли пользователь?
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Требуется ли обновление данных?
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Активирована ли учетная запись?
    @Override
    public boolean isEnabled() {
        return true;
    }
}
