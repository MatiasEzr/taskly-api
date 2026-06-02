package com.matias.taskly.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails {

    public User(String nickname, String email, String password, LocalDate dateOfBirth) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname; // antes se llamaba username, era un nombre visible

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    // LocalDate is used because we only need the date, not the time
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    // Automatically set in @PrePersist, never comes from the client
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Task> tasks;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    private String role; // "USER", "ADMIN", etc.

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email; // identificador único real de Usuario, Spring Security lo usa para el login
    }

    @Override
    public boolean isAccountNonExpired() {

        // true means the account is still valid.
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        // true means the account is not locked.
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        // true means the password credentials are still valid.
        return true;
    }

    @Override
    public boolean isEnabled() {

        // true means the user is enabled and can authenticate.
        return true;
    }
}
