package com.doccollab.permissions.models;

import javax.persistence.*;

@Entity
@Table(name = "permissions")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "grantee_type", nullable = false)
    private String granteeType; // USER or ROLE

    @Column(name = "grantee_id", nullable = false)
    private Long granteeId;

    @Column(name = "resource_type", nullable = false)
    private String resourceType; // FILE or APP

    @Column(name = "resource_id", nullable = false)
    private Long resourceId;

    @Column(nullable = false)
    private String scopes; // Comma-separated list of scopes

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGranteeType() {
        return granteeType;
    }

    public void setGranteeType(String granteeType) {
        this.granteeType = granteeType;
    }

    public Long getGranteeId() {
        return granteeId;
    }

    public void setGranteeId(Long granteeId) {
        this.granteeId = granteeId;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getScopes() {
        return scopes;
    }

    public void setScopes(String scopes) {
        this.scopes = scopes;
    }
}