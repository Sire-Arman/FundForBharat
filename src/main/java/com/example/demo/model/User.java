package com.example.demo.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String fullName;

    @Column(name = "address")
    private String address;

    @Column(name = "doc_id")
    private Long Doc_id;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    //testing
//    @Enumerated(EnumType.STRING)
//    private Role role;


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == null) {
            System.out.println("Role is null");
            return List.of(); // or an empty list
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (role.isRoleUser()) {
            authorities.add(new SimpleGrantedAuthority("USER"));
        }
        if (role.isRoleCampaignAdmin()) {
            authorities.add(new SimpleGrantedAuthority("CAMPAIGN_ADMIN"));
        }
        if (role.isRoleDocumentAdmin()) {
            authorities.add(new SimpleGrantedAuthority("DOCUMENT_ADMIN"));
        }
        if (role.isRolePaymentAdmin()) {
            authorities.add(new SimpleGrantedAuthority("PAYMENT_ADMIN"));
        }
        if (role.isRoleSuperAdmin()) {
            authorities.add(new SimpleGrantedAuthority("SUPER_ADMIN"));
        }
        return authorities;
    }





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
    }
}
