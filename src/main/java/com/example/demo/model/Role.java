package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

//add a getall roles method to access roles for a user
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity(name="roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Setter
    @Getter
    @Column(name = "role_user")
    private boolean roleUser;

    @Getter
    @Column(name = "role_campaign_admin")
    private boolean roleCampaignAdmin;

    @Column(name = "role_document_admin")
    private boolean roleDocumentAdmin;

    @Column(name = "role_payment_admin")
    private boolean rolePaymentAdmin;

    @Column(name = "role_super_admin")
    private boolean roleSuperAdmin;


    public List<String> getAllRoles() {
        List<String> roles = new ArrayList<>();

        if (this.isRoleUser()) {
            roles.add("ROLE_USER");
        }
        if (this.isRoleCampaignAdmin()) {
            roles.add("ROLE_CAMPAIGN_ADMIN");
        }
        if (this.isRoleDocumentAdmin()) {
            roles.add("ROLE_DOCUMENT_ADMIN");
        }
        if (this.isRolePaymentAdmin()) {
            roles.add("ROLE_PAYMENT_ADMIN");
        }
        if (this.isRoleSuperAdmin()) {
            roles.add("ROLE_SUPER_ADMIN");
        }

        return roles;
    }
}
