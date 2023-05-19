-- таблиця користувачів
DO
$$
    BEGIN
        IF NOT EXISTS(SELECT 1 FROM pg_catalog.pg_tables WHERE tablename = 'users' AND schemaname = 'public') THEN
            CREATE TABLE users
            (
                user_id       SERIAL PRIMARY KEY,
                 first_name    VARCHAR(50)                         NOT NULL,
                last_name     VARCHAR(50)                         NOT NULL,
                email         VARCHAR(100) UNIQUE                 NOT NULL,
                password_hash VARCHAR(100)                        NOT NULL,-- зберігати хеш пароля замість самого пароля
                registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                user_status   VARCHAR(20)                         NOT NULL,
                CONSTRAINT chk_user_status CHECK (user_status IN ('active', 'inactive', 'admin')) -- додати обмеження на значення user_status
            );
        ELSE
            RAISE NOTICE 'Table users already exists';
        END IF;
    END
$$;



