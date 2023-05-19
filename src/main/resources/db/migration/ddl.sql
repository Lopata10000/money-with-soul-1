DO
$$
    BEGIN
        IF NOT EXISTS(SELECT 1 FROM pg_database WHERE datname = 'money-with-soul') THEN
            CREATE DATABASE "money-with-soul";
        END IF;
    END
$$;
DROP TABLE IF EXISTS costs CASCADE;
DROP TABLE IF EXISTS earnings CASCADE;
DROP TABLE IF EXISTS planning_costs CASCADE;
DROP TABLE IF EXISTS transactions CASCADE;
DROP TABLE IF EXISTS budgets CASCADE;
DROP TABLE IF EXISTS cost_categories CASCADE;
DROP TABLE IF EXISTS earning_categories CASCADE;
DROP TABLE IF EXISTS exchange_rates CASCADE;
DROP TABLE IF EXISTS users CASCADE;

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
                password_hash VARCHAR(100)                        NOT NULL,
                registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                user_status   VARCHAR(20)                         NOT NULL,
                CONSTRAINT chk_user_status CHECK (user_status IN ('active', 'inactive', 'admin'))
            );
            ALTER SEQUENCE users_user_id_seq RESTART WITH 1;
        ELSE
            RAISE NOTICE 'Table users already exists';
        END IF;
    END
$$;

-- таблиця курсів валют
DO
$$
    BEGIN
        IF NOT EXISTS(SELECT 1 FROM pg_catalog.pg_tables WHERE tablename = 'exchange_rates' AND schemaname = 'public') THEN
            CREATE TABLE exchange_rates
            (
                exchange_id   SERIAL PRIMARY KEY,
                name_currency VARCHAR(10) UNIQUE NOT NULL,
                rate          NUMERIC(10, 4)     NOT NULL CHECK (rate >= 0)
            );
            ALTER SEQUENCE exchange_rates_exchange_id_seq RESTART WITH 1;
        ELSE
            RAISE NOTICE 'Table exchange_rates already exists';
        END IF;
    END
$$;

-- таблиця транзакцій
DO
$$
    BEGIN
        IF NOT EXISTS(SELECT 1 FROM pg_catalog.pg_tables WHERE tablename = 'transactions' AND schemaname = 'public') THEN
            CREATE TABLE transactions
            (
                transaction_id     SERIAL PRIMARY KEY,
                user_id            INTEGER REFERENCES users (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
                transaction_type   VARCHAR(50)    NOT NULL,
                transaction_date   TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
                transaction_amount NUMERIC(10, 2) NOT NULL CHECK (transaction_amount >= 0),
                description        VARCHAR(300),
                exchange_id        INTEGER REFERENCES exchange_rates (exchange_id) ON DELETE SET NULL ON UPDATE CASCADE
            );
            ALTER SEQUENCE transactions_transaction_id_seq RESTART WITH 1;
        ELSE
            RAISE NOTICE 'Table transactions already exists';
        END IF;
    END
$$;

-- таблиця бюджетів
DO
$$
    BEGIN
        IF NOT EXISTS(SELECT 1 FROM pg_catalog.pg_tables WHERE tablename = 'budgets' AND schemaname = 'public') THEN
            CREATE TABLE budgets
            (
                budget_id  SERIAL PRIMARY KEY,
                user_id    INTEGER        NOT NULL REFERENCES users (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
                name       VARCHAR(50)    NOT NULL,
                start_date DATE           NOT NULL CHECK (start_date <= end_date),
                end_date   DATE CHECK (end_date >= start_date),
                amount     NUMERIC(10, 2) NOT NULL CHECK (amount >= 0)
            );
            ALTER SEQUENCE budgets_budget_id_seq RESTART WITH 1;
        ELSE
            RAISE NOTICE 'Table budgets already exists';
        END IF;
    END
$$;

-- таблиця категорій витрат
DO
$$
    BEGIN
        IF NOT EXISTS(SELECT 1 FROM pg_catalog.pg_tables WHERE tablename = 'cost_categories' AND schemaname = 'public') THEN
            CREATE TABLE cost_categories
            (
                cost_category_id   SERIAL PRIMARY KEY,
                cost_category_name VARCHAR(100) NOT NULL
            );
            ALTER SEQUENCE cost_categories_cost_category_id_seq RESTART WITH 1;
        ELSE
            RAISE NOTICE 'Table cost_categories already exists';
        END IF;
    END
$$;

-- таблиця категорій доходів
DO
$$
    BEGIN
        IF NOT EXISTS(SELECT 1 FROM pg_catalog.pg_tables WHERE tablename = 'earning_categories' AND schemaname = 'public') THEN
            CREATE TABLE earning_categories
            (
                earning_category_id   SERIAL PRIMARY KEY,
                earning_category_name VARCHAR(100) NOT NULL
            );
            ALTER SEQUENCE earning_categories_earning_category_id_seq RESTART WITH 1;
        ELSE
            RAISE NOTICE 'Table earning_categories already exists';
        END IF;
    END
$$;

-- таблиця витрат
DO
$$
    BEGIN
        IF NOT EXISTS(SELECT 1 FROM pg_catalog.pg_tables WHERE tablename = 'costs' AND schemaname = 'public') THEN
            CREATE TABLE costs
            (
                cost_id          SERIAL PRIMARY KEY,
                user_id          INTEGER        NOT NULL REFERENCES users (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
                cost_category_id INTEGER        NOT NULL REFERENCES cost_categories (cost_category_id) ON DELETE CASCADE ON UPDATE CASCADE,
                budget_id        INTEGER        NOT NULL REFERENCES budgets (budget_id) ON DELETE CASCADE ON UPDATE CASCADE,
                transaction_id   INTEGER        NOT NULL REFERENCES transactions (transaction_id) ON DELETE CASCADE ON UPDATE CASCADE,
                cost_date        TIMESTAMP      NOT NULL,
                cost_amount      NUMERIC(10, 2) NOT NULL CHECK (cost_amount >= 0),
                cost_description VARCHAR(300)   NOT NULL
            );
            ALTER SEQUENCE costs_cost_id_seq RESTART WITH 1;
        ELSE
            RAISE NOTICE 'Table costs already exists';
        END IF;
    END
$$;

-- таблиця прибутку
DO
$$
    BEGIN
        IF NOT EXISTS(SELECT 1 FROM pg_catalog.pg_tables WHERE tablename = 'earnings' AND schemaname = 'public') THEN
            CREATE TABLE earnings
            (
                earning_id          SERIAL PRIMARY KEY,
                user_id             INTEGER        NOT NULL REFERENCES users (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
                earning_category_id INTEGER        NOT NULL REFERENCES earning_categories (earning_category_id) ON DELETE CASCADE ON UPDATE CASCADE,
                earning_date        TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
                earning_amount      NUMERIC(10, 2) NOT NULL CHECK (earning_amount >= 0),
                transaction_id      INTEGER        NOT NULL REFERENCES transactions (transaction_id) ON DELETE CASCADE ON UPDATE CASCADE,
                budget_id           INTEGER        NOT NULL REFERENCES budgets (budget_id) ON DELETE CASCADE ON UPDATE CASCADE
            );
            ALTER SEQUENCE earnings_earning_id_seq RESTART WITH 1;
        ELSE
            RAISE NOTICE 'Table earnings already exists';
        END IF;
    END
$$;

-- таблиця планованих витрат
DO
$$
    BEGIN
        IF NOT EXISTS(SELECT 1 FROM pg_catalog.pg_tables WHERE tablename = 'planning_costs' AND schemaname = 'public') THEN
            CREATE TABLE planning_costs
            (
                planning_cost_id   SERIAL PRIMARY KEY,
                user_id            INTEGER        NOT NULL REFERENCES users (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
                cost_category_id   INTEGER        NOT NULL REFERENCES cost_categories (cost_category_id) ON DELETE CASCADE ON UPDATE CASCADE,
                planning_cost_date TIMESTAMP      NOT NULL CHECK (planning_cost_date >= CURRENT_TIMESTAMP),
                budget_id          INTEGER        NOT NULL REFERENCES budgets (budget_id) ON DELETE CASCADE ON UPDATE CASCADE,
                planned_amount     NUMERIC(10, 2) NOT NULL CHECK (planned_amount >= 0)
            );
            ALTER SEQUENCE planning_costs_planning_cost_id_seq RESTART WITH 1;
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
