DO
$$
    BEGIN
        IF NOT EXISTS(SELECT 1 FROM pg_database WHERE datname = 'money-with-soul') THEN
            CREATE DATABASE "money-with-soul";
        END IF;
    END
$$;


DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS exchange_rates CASCADE;
DROP TABLE IF EXISTS transactions CASCADE;
DROP TABLE IF EXISTS budgets CASCADE;
DROP TABLE IF EXISTS cost_categories CASCADE;
DROP TABLE IF EXISTS earning_categories CASCADE;
DROP TABLE IF EXISTS costs CASCADE;
DROP TABLE IF EXISTS earnings CASCADE;
DROP TABLE IF EXISTS planning_costsplanning_costs CASCADE;
DROP TABLE IF EXISTS cost_earning CASCADE;

-- таблиця користувачів
DO
$$
    BEGIN
        IF
            NOT EXISTS(SELECT 1 FROM pg_catalog.pg_tables WHERE tablename = 'users' AND schemaname = 'public') THEN
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

-- таблиця курсів валют
DO
$$
    BEGIN
        IF
            NOT EXISTS(SELECT 1
                       FROM pg_catalog.pg_tables
                       WHERE tablename = 'exchange_rates'
                         AND schemaname = 'public') THEN
            CREATE TABLE exchange_rates
            (
                exchange_id   SERIAL PRIMARY KEY,
                name_currency VARCHAR(10) UNIQUE NOT NULL,
                rate          NUMERIC(10, 4)     NOT NULL CHECK (rate >= 0)
            );
        ELSE
            RAISE NOTICE 'Table exchange_rates already exists';
        END IF;
    END
$$;

-- таблиця транзакцій
DO
$$
    BEGIN
        IF
            NOT EXISTS(SELECT 1
                       FROM pg_catalog.pg_tables
                       WHERE tablename = 'transactions'
                         AND schemaname = 'public') THEN
            CREATE TABLE transactions
            (
                transaction_id     SERIAL PRIMARY KEY,
                user_id            INTEGER REFERENCES users (user_id),
                transaction_type   VARCHAR(50)    NOT NULL,
                transaction_date   TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
                transaction_amount NUMERIC(10, 2) NOT NULL CHECK (transaction_amount >= 0),
                description        VARCHAR(300),
                exchange_id        INTEGER REFERENCES exchange_rates (exchange_id),
                CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (user_id),
                CONSTRAINT fk_exchange_id  FOREIGN KEY (exchange_id) REFERENCES exchange_rates (exchange_id )
            );
        ELSE
            RAISE NOTICE 'Table transactions already exists';
        END IF;
    END
$$;

-- таблиця бюджетів
DO
$$
    BEGIN
        IF
            NOT EXISTS(SELECT 1 FROM pg_catalog.pg_tables WHERE tablename = 'budgets' AND schemaname = 'public') THEN
            CREATE TABLE budgets
            (
                budget_id  SERIAL PRIMARY KEY,
                user_id    INTEGER        NOT NULL REFERENCES users (user_id),
                name       VARCHAR(50)    NOT NULL,
                start_date DATE           NOT NULL CHECK (start_date <= end_date),
                end_date   DATE CHECK (end_date >= end_date),
                amount     NUMERIC(10, 2) NOT NULL CHECK (amount >= 0),
                CONSTRAINT unique_budget_name_user_id UNIQUE (name, user_id)
            );
        ELSE
            RAISE NOTICE 'Table budgets already exists';
        END IF;
    END
$$;

-- таблиця категорій витрат
DO
$$
    BEGIN
        IF
            NOT EXISTS(SELECT 1
                       FROM pg_catalog.pg_tables
                       WHERE tablename = 'cost_categories'
                         AND schemaname = 'public') THEN
            CREATE TABLE cost_categories
            (
                cost_category_id   SERIAL PRIMARY KEY,
                cost_category_name VARCHAR(100) NOT NULL
            );
        ELSE
            RAISE NOTICE 'Table cost_categories already exists';
        END IF;
    END
$$;

-- таблиця категорій доходів
DO
$$
    BEGIN
        IF
            NOT EXISTS(SELECT 1
                       FROM pg_catalog.pg_tables
                       WHERE tablename = 'earning_categories'
                         AND schemaname = 'public') THEN
            CREATE TABLE earning_categories
            (
                earning_category_id   SERIAL PRIMARY KEY,
                earning_category_name VARCHAR(100) NOT NULL
            );
        ELSE
            RAISE NOTICE 'Table earning_categories already exists';
        END IF;
    END
$$;

-- таблиця витрат
DO
$$
    BEGIN
        IF
            NOT EXISTS(SELECT 1 FROM pg_catalog.pg_tables WHERE tablename = 'costs' AND schemaname = 'public') THEN
            CREATE TABLE costs
            (
                cost_id          SERIAL PRIMARY KEY,
                user_id          INTEGER        NOT NULL REFERENCES users (user_id),
                cost_category_id INTEGER        NOT NULL REFERENCES cost_categories (cost_category_id),
                budget_id        INTEGER        NOT NULL REFERENCES budgets (budget_id),
                transaction_id   INTEGER        NOT NULL REFERENCES transactions (transaction_id),
                cost_date        TIMESTAMP      NOT NULL,
                cost_amount      NUMERIC(10, 2) NOT NULL CHECK (cost_amount >= 0),
                cost_description VARCHAR(300)   NOT NULL,
                CONSTRAINT fk_cost_user FOREIGN KEY (user_id) REFERENCES users (user_id) ON UPDATE CASCADE ON DELETE CASCADE,
                CONSTRAINT fk_cost_category FOREIGN KEY (cost_category_id) REFERENCES cost_categories (cost_category_id) ON UPDATE CASCADE ON DELETE CASCADE,
                CONSTRAINT fk_cost_budget FOREIGN KEY (budget_id) REFERENCES budgets (budget_id) ON UPDATE CASCADE ON DELETE CASCADE,
                CONSTRAINT fk_cost_transaction FOREIGN KEY (transaction_id) REFERENCES transactions (transaction_id) ON UPDATE CASCADE ON DELETE CASCADE
            );
        ELSE
            RAISE NOTICE 'Table costs already exists';
        END IF;
    END
$$;

-- таблиця прибутку
DO
$$
    BEGIN
        IF
            NOT EXISTS(SELECT 1 FROM pg_catalog.pg_tables WHERE tablename = 'earnings' AND schemaname = 'public') THEN
            CREATE TABLE earnings
            (
                earning_id          SERIAL PRIMARY KEY,
                user_id             INTEGER        NOT NULL REFERENCES users (user_id),
                earning_category_id INTEGER        NOT NULL REFERENCES earning_categories (earning_category_id),
                earning_date        TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
                earning_amount      NUMERIC(10, 2) NOT NULL CHECK (earning_amount >= 0),
                transaction_id      INTEGER        NOT NULL REFERENCES transactions (transaction_id),
                budget_id           INTEGER        NOT NULL REFERENCES budgets (budget_id),
                CONSTRAINT fk_earning_user FOREIGN KEY (user_id) REFERENCES users (user_id) ON UPDATE CASCADE ON DELETE CASCADE,
                CONSTRAINT fk_earning_category FOREIGN KEY (earning_category_id) REFERENCES earning_categories (earning_category_id) ON UPDATE CASCADE ON DELETE CASCADE,
                CONSTRAINT fk_earning_transaction FOREIGN KEY (transaction_id) REFERENCES transactions (transaction_id) ON UPDATE CASCADE ON DELETE CASCADE,
                CONSTRAINT fk_earning_budget FOREIGN KEY (budget_id) REFERENCES budgets (budget_id) ON UPDATE CASCADE ON DELETE CASCADE
            );
        ELSE
            RAISE NOTICE 'Table earnings already exists';
        END IF;
    END
$$;

-- таблиця планованих витрат
DO
$$
    BEGIN
        IF
            NOT EXISTS(SELECT 1
                       FROM pg_catalog.pg_tables
                       WHERE tablename = 'planning_costs'
                         AND schemaname = 'public') THEN
            CREATE TABLE planning_costs
            (
                planning_cost_id   SERIAL PRIMARY KEY,
                user_id            INTEGER        NOT NULL REFERENCES users (user_id),
                cost_category_id   INTEGER        NOT NULL REFERENCES cost_categories (cost_category_id),
                planning_cost_date TIMESTAMP      NOT NULL CHECK (planning_cost_date >= CURRENT_TIMESTAMP),
                budget_id          INTEGER        NOT NULL REFERENCES budgets (budget_id),
                planned_amount     NUMERIC(10, 2) NOT NULL CHECK (planned_amount >= 0),
                CONSTRAINT fk_planning_cost_user FOREIGN KEY (user_id) REFERENCES users (user_id) ON UPDATE CASCADE ON DELETE CASCADE,
                CONSTRAINT fk_planning_cost_category FOREIGN KEY (cost_category_id) REFERENCES cost_categories (cost_category_id) ON UPDATE CASCADE ON DELETE CASCADE,
                CONSTRAINT fk_planning_cost_budget FOREIGN KEY (budget_id) REFERENCES budgets (budget_id) ON UPDATE CASCADE ON DELETE CASCADE
            );
        ELSE
            RAISE NOTICE 'Table planning_costs already exists';
        END IF;
    END
$$;

-- створюємо індекси для підвищення швидкодії запитів
CREATE INDEX IF NOT EXISTS idx_cost_user ON costs (user_id);
CREATE INDEX IF NOT EXISTS idx_cost_budget ON costs (budget_id);
CREATE INDEX IF NOT EXISTS idx_earning_user ON earnings (user_id);
CREATE INDEX IF NOT EXISTS idx_earning_budget ON earnings (budget_id);
CREATE INDEX IF NOT EXISTS idx_planning_cost_user ON planning_costs (user_id);
CREATE INDEX IF NOT EXISTS idx_planning_cost_budget ON planning_costs (budget_id);


INSERT INTO users (first_name, last_name, email, password_hash, registered_at, user_status)
VALUES ('John', 'Doe', 'johndoe@gmail.com', 'password123', '2022-01-01 10:00:00', 'active'),
       ('Jane', 'Doe', 'janedoeй@gmail.com', 'password456', '2022-01-02 12:00:00', 'inactive'),
       ('Bob', 'Smith', 'bobsmith@yahoo.com', 'password789', '2022-01-03 15:00:00', 'admin'),
       ('Alice', 'Johnson', 'alicejohnson@hotmail.com', 'passwordabc', '2022-01-04 17:00:00', 'active');
INSERT INTO exchange_rates (name_currency, rate)
VALUES ('USD', 1.00),
       ('EUR', 1.20),
       ('GBP', 1.40),
       ('JPY', 0.009);
INSERT INTO budgets (user_id, name, start_date, end_date, amount)
VALUES (1, 'January Budget', '2022-01-01', '2022-01-31', 1000.00),
       (2, 'February Budget', '2022-02-01', '2022-02-28', 1500.00),
       (3, 'March Budget', '2022-03-01', '2022-03-31', 2000.00),
       (4, 'April Budget', '2022-04-01', '2022-04-30', 2500.00);
INSERT INTO cost_categories (cost_category_name)
VALUES ('Food'),
       ('Housing'),
       ('Transportation'),
       ('Entertainment');
INSERT INTO earning_categories (earning_category_name)
VALUES ('Salary'),
       ('Freelance work'),
       ('Investment');





