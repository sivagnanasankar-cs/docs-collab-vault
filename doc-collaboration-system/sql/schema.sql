-- Organizations
CREATE TABLE organizations (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    domain VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Users
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    org_id BIGINT NOT NULL REFERENCES organizations(id),
    email VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255),
    password_hash VARCHAR(255),
    status VARCHAR(50),
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Applications
CREATE TABLE applications (
    id BIGSERIAL PRIMARY KEY,
    org_id BIGINT NOT NULL REFERENCES organizations(id),
    name VARCHAR(255) NOT NULL
);

-- File Metadata
CREATE TABLE file_meta (
    id BIGSERIAL PRIMARY KEY,
    org_id BIGINT NOT NULL REFERENCES organizations(id),
    app_id BIGINT NOT NULL REFERENCES applications(id),
    owner_user_id BIGINT NOT NULL REFERENCES users(id),
    path VARCHAR(1024) NOT NULL,
    size BIGINT,
    mime VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    current_version_id BIGINT
);

-- File Versions
CREATE TABLE file_versions (
    id BIGSERIAL PRIMARY KEY,
    file_id BIGINT NOT NULL REFERENCES file_meta(id),
    version_no INT NOT NULL,
    storage_key VARCHAR(1024) NOT NULL,
    uploaded_by BIGINT NOT NULL REFERENCES users(id),
    uploaded_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50)
);

-- Roles
CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    org_id BIGINT REFERENCES organizations(id),
    name VARCHAR(255) NOT NULL
);

-- Permissions
CREATE TABLE permissions (
    id BIGSERIAL PRIMARY KEY,
    grantee_type VARCHAR(50) NOT NULL,
    grantee_id BIGINT NOT NULL,
    resource_type VARCHAR(50) NOT NULL,
    resource_id BIGINT NOT NULL,
    scopes TEXT NOT NULL
);

-- OAuth Clients
CREATE TABLE oauth_clients (
    id BIGSERIAL PRIMARY KEY,
    org_id BIGINT REFERENCES organizations(id),
    client_id VARCHAR(255) NOT NULL UNIQUE,
    client_secret_hash VARCHAR(255) NOT NULL,
    redirect_uris TEXT,
    scopes TEXT
);

-- Jobs
CREATE TABLE jobs (
    id BIGSERIAL PRIMARY KEY,
    type VARCHAR(255),
    payload TEXT,
    status VARCHAR(50),
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    started_at TIMESTAMP WITHOUT TIME ZONE,
    finished_at TIMESTAMP WITHOUT TIME ZONE
);
