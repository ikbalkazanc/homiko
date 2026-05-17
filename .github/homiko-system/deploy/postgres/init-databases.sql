-- Create multiple databases for Homiko
CREATE DATABASE "homiko-stage";
CREATE DATABASE "homiko-prod";

-- Grant privileges (optional, since the default user already has access)
GRANT ALL PRIVILEGES ON DATABASE "homiko-stage" TO homiko;
GRANT ALL PRIVILEGES ON DATABASE "homiko-prod" TO homiko;
