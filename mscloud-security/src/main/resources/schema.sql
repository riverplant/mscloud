CREATE TABLE IF NOT EXISTS authors (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255)
);


CREATE TABLE IF NOT EXISTS book (
    id BIGINT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author_id BIGINT NOT NULL,
    publish_time TIMESTAMP NOT NULL
);


-- 权限表 t_perm
CREATE TABLE IF NOT EXISTS t_perm (
    id BIGSERIAL PRIMARY KEY,
    value VARCHAR(100) NOT NULL,
    uri VARCHAR(255),
    description VARCHAR(255),
    create_time TIMESTAMP NOT NULL,
    update_time TIMESTAMP
);

-- 角色表 t_roles
CREATE TABLE IF NOT EXISTS t_roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    value VARCHAR(100) NOT NULL,
    create_time TIMESTAMP NOT NULL,
    update_time TIMESTAMP
);

-- 用户表 t_user
CREATE TABLE IF NOT EXISTS t_user (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    create_time TIMESTAMP NOT NULL,
    update_time TIMESTAMP
);

-- 用户-角色关系表 t_user_role
CREATE TABLE IF NOT EXISTS t_user_role (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    create_time TIMESTAMP NOT NULL,
    update_time TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES t_user(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES t_roles(id) ON DELETE CASCADE
);

-- 角色-权限关系表 t_role_perm
CREATE TABLE IF NOT EXISTS t_role_perm (
    id BIGSERIAL PRIMARY KEY,
    role_id BIGINT NOT NULL,
    perm_id BIGINT NOT NULL,
    create_time TIMESTAMP NOT NULL,
    update_time TIMESTAMP,
    FOREIGN KEY (role_id) REFERENCES t_roles(id) ON DELETE CASCADE,
    FOREIGN KEY (perm_id) REFERENCES t_perm(id) ON DELETE CASCADE
);
