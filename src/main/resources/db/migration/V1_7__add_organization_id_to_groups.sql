-- Add organization_id to groups table

ALTER TABLE groups
ADD COLUMN organization_id BIGINT NOT NULL,
ADD CONSTRAINT fk_groups_organization
    FOREIGN KEY (organization_id)
    REFERENCES organizations (id)
    ON DELETE CASCADE;
