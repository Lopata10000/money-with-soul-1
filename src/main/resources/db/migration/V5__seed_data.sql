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
                user_status   VARCHAR(20)                         NOT NULL
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
        IF NOT EXISTS(SELECT 1 FROM pg_catalog.pg_tables WHERE tablename = 'exchange_rates' AND schemaname = 'public') THEN
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
       ('Jane', 'Doe', 'janedoeй@gmail.com', 'password456', '2022-01-02 12:00:00', 'active'),
       ('Bob', 'Smith', 'bobsmith@yahoo.com', 'password789', '2022-01-03 15:00:00', 'admin'),
       ('John', 'Doe', 'john.doe@example.com', 'password_hash_1', '2022-01-04 15:00:00', 'active'),
       ('Jane', 'Smith', 'jane.smith@example.com', 'password_hash_2', '2023-01-03 15:00:00', 'active'),
       ('David', 'Johnson', 'david.johnson@example.com', 'password_hash_3', '2002-09-01 10:00:00', 'active'),
       ('Sarah', 'Johnson', 'sarah.johnson@example.com', 'password_hash_4', '2012-08-02 18:00:00', 'active'),
       ('Michael', 'Brown', 'michael.brown@example.com', 'password_hash_5', '2010-06-02 18:03:00', 'inactive'),
       ('Emily', 'Davis', 'emily.davis@example.com', 'password_hash_6', '2011-01-02 21:03:00', 'active'),
       ('Sophia', 'Harris', 'sophia.harris@example.com', 'password_hash_10', '2023-01-03 19:00:00', 'active'),
       ('Liam', 'Clark', 'liam.clark@example.com', 'password_hash_11', '2020-02-03 17:00:00', 'inactive'),
       ('Ava', 'Lewis', 'ava.lewis@example.com', 'password_hash_12', '2017-11-03 10:00:00', 'active');
INSERT INTO exchange_rates (name_currency, rate)
VALUES ('USD', 1.00),
       ('EUR', 1.20),
       ('GBP', 1.40),
       ('JPY', 0.009),
       ('CAD', 0.90),
       ('AUD', 0.70);
INSERT INTO transactions (user_id, transaction_type, transaction_date, transaction_amount, description, exchange_id)
VALUES (1, 'expense', '2022-01-05 10:00:00', 50.00, 'Groceries', 1),
       (2, 'income', '2022-01-06 12:00:00', 1000.00, 'Salary', 2),
       (3, 'expense', '2022-01-07 15:00:00', 25.00, 'Coffee', 3),
       (4, 'income', '2022-01-08 17:00:00', 500.00, 'Freelance work', 4),
       (5, 'expense', CURRENT_TIMESTAMP, 100.00, 'Groceries', 1),
       (6, 'income', CURRENT_TIMESTAMP, 500.00, 'Salary', 1),
       (7, 'expense', CURRENT_TIMESTAMP, 50.00, 'Dinner', 2),
       (4, 'expense', CURRENT_TIMESTAMP, 80.00, 'Internet bill', 5),
       (5, 'income', CURRENT_TIMESTAMP, 1000.00, 'Bonus', 5),
       (6, 'expense', CURRENT_TIMESTAMP, 40.00, 'Coffee', 4),
       (7, 'expense', CURRENT_TIMESTAMP, 120.00, 'Dinner', 1),
       (8, 'income', CURRENT_TIMESTAMP, 800.00, 'Freelance payment', 2),
       (9, 'expense', CURRENT_TIMESTAMP, 60.00, 'Gym membership', 2),
       (1, 'expense', CURRENT_TIMESTAMP, 150.00, 'Medical expenses', 6),
       (2, 'income', CURRENT_TIMESTAMP, 1200.00, 'Sales revenue', 5),
       (3, 'expense', CURRENT_TIMESTAMP, 80.00, 'Dinner with friends', 5);
INSERT INTO budgets (user_id, name, start_date, end_date, amount)
VALUES (1, 'January Budget', '2022-01-01', '2022-01-31', 1000.00),
       (2, 'February Budget', '2022-02-01', '2022-02-28', 1500.00),
       (3, 'March Budget', '2022-03-01', '2022-03-31', 2000.00),
       (4, 'April Budget', '2022-04-01', '2022-04-30', 2500.00),
       (1, 'Monthly Budget', '2023-01-01', '2023-01-31', 1000.00),
       (2, 'Weekly Budget', '2023-05-01', '2023-05-07', 500.00),
       (3, 'Yearly Budget', '2023-01-01', '2023-12-31', 5000.00),
       (4, 'Monthly Budget', '2023-05-01', '2023-05-31', 1500.00),
       (5, 'Weekly Budget', '2023-05-01', '2023-05-07', 800.00),
       (6, 'Yearly Budget', '2023-01-01', '2023-12-31', 6000.00),
       (7, 'Monthly Budget', '2023-05-01', '2023-05-31', 2000.00),
       (8, 'Weekly Budget', '2023-05-01', '2023-05-07', 1000.00),
       (9, 'Yearly Budget', '2023-01-01', '2023-12-31', 8000.00),
       (1, 'Monthly Budget', '2023-05-01', '2023-05-31', 2500.00),
       (2, 'Weekly Budget', '2023-05-01', '2023-05-07', 1500.00),
       (8, 'Yearly Budget', '2023-01-01', '2023-12-31', 10000.00);
INSERT INTO cost_categories (cost_category_name)
VALUES ('Food'),
       ('Housing'),
       ('Transportation'),
       ('Entertainment'),
       ('Rent'),
       ('Utilities'),
       ('Clothing'),
       ('Healthcare'),
       ('Education'),
       ('Travel'),
       ('Home Maintenance'),
       ('Hobbies'),
       ('Charity');
INSERT INTO earning_categories (earning_category_name)
VALUES ('Salary'),
       ('Freelance work'),
       ('Investment'),
       ('Bonus'),
       ('Rental Income'),
       ('Dividends'),
       ('Commissions'),
       ('Royalties'),
       ('Gifts'),
       ('Consulting'),
       ('Online Sales'),
       ('Rentals');
INSERT INTO costs (user_id, cost_category_id, budget_id, transaction_id, cost_date, cost_amount, cost_description)
VALUES (1, 1, 1, 1, CURRENT_TIMESTAMP, 50.00, 'Lunch'),
       (2, 2, 2, 2, CURRENT_TIMESTAMP, 20.00, 'Bus fare'),
       (3, 3, 3, 3, CURRENT_TIMESTAMP, 30.00, 'Movie ticket'),
       (4, 1, 7, 6, CURRENT_TIMESTAMP, 70.00, 'Electricity bill'),
       (5, 2, 8, 7, CURRENT_TIMESTAMP, 30.00, 'Lunch'),
       (6, 3, 9, 8, CURRENT_TIMESTAMP, 20.00, 'Books'),
       (7, 1, 3, 12, CURRENT_TIMESTAMP, 90.00, 'Utility bill'),
       (8, 2, 4, 13, CURRENT_TIMESTAMP, 40.00, 'Transportation'),
       (9, 3, 5, 4, CURRENT_TIMESTAMP, 30.00, 'Movie tickets'),
       (1, 1, 9, 8, CURRENT_TIMESTAMP, 120.00, 'Medicine'),
       (1, 2, 10, 10, CURRENT_TIMESTAMP, 60.00, 'Tuition fee'),
       (2, 3, 1, 10, CURRENT_TIMESTAMP, 40.00, 'Flight ticket');
INSERT INTO earnings (user_id, earning_category_id, earning_date, earning_amount, transaction_id, budget_id)
VALUES (1, 1, CURRENT_TIMESTAMP, 2000.00, 4, 1),
       (2, 2, CURRENT_TIMESTAMP, 1000.00, 5, 3),
       (7, 4, CURRENT_TIMESTAMP, 600.00, 15, 13),
       (8, 5, CURRENT_TIMESTAMP, 2500.00, 16, 14),
       (9, 6, CURRENT_TIMESTAMP, 400.00, 10, 15),
       (10, 4, CURRENT_TIMESTAMP, 800.00, 11, 9),
       (11, 5, CURRENT_TIMESTAMP, 3000.00, 2, 10),
       (12, 6, CURRENT_TIMESTAMP, 200.00, 3, 1);
INSERT INTO planning_costs (user_id, cost_category_id, planning_cost_date, budget_id, planned_amount)
VALUES (1, 1, '2024-07-01', 1, 100.00),
       (2, 2, '2024-07-15', 2, 50.00),
       (3, 3, '2024-07-01', 3, 200.00),
       (5, 2, '2024-07-15', 8, 50.00),
       (6, 3, '2024-07-01', 9, 100.00),
       (7, 1, '2024-06-01', 13, 150.00),
       (8, 2, '2024-06-15', 14, 70.00),
       (9, 3, '2024-07-01', 15, 200.00),
       (10, 1, '2024-06-01', 9, 200.00),
       (11, 2, '2024-05-15', 10, 100.00),
       (12, 3, '2024-07-01', 2, 150.00);


