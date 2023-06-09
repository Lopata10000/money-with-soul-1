DROP TABLE IF EXISTS costs CASCADE;
DROP TABLE IF EXISTS earnings CASCADE;
DROP TABLE IF EXISTS budgets CASCADE;
DROP TABLE IF EXISTS cost_categories CASCADE;
DROP TABLE IF EXISTS earning_categories CASCADE;
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

--- таблиця категорій витрат
DO
$$
BEGIN
        IF NOT EXISTS(SELECT 1 FROM pg_catalog.pg_tables WHERE tablename = 'cost_categories' AND schemaname = 'public') THEN
CREATE TABLE cost_categories
(
    cost_category_id   SERIAL PRIMARY KEY,
    cost_category_name VARCHAR(100) NOT NULL,
    user_id            INTEGER REFERENCES users(user_id) NOT NULL ON DELETE CASCADE ON UPDATE CASCADE,
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
    earning_category_name VARCHAR(100) NOT NULL,
    user_id               INTEGER REFERENCES users(user_id) NOT NULL ON DELETE CASCADE ON UPDATE CASCADE,
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
                budget_id           INTEGER        NOT NULL REFERENCES budgets (budget_id) ON DELETE CASCADE ON UPDATE CASCADE
            );
        ELSE
            RAISE NOTICE 'Table earnings already exists';
        END IF;
    END
$$;

-- таблиця планованих витрат


-- створюємо індекси для підвищення швидкодії запитів
CREATE INDEX IF NOT EXISTS idx_cost_user ON costs (user_id);
CREATE INDEX IF NOT EXISTS idx_email_user ON users (email);
CREATE INDEX IF NOT EXISTS idx_cost_budget ON costs (budget_id);
CREATE INDEX IF NOT EXISTS idx_earning_user ON earnings (user_id);
CREATE INDEX IF NOT EXISTS idx_earning_budget ON earnings (budget_id);


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
INSERT INTO cost_categories (cost_category_name, user_id)
VALUES ('Food', 1),
       ('Housing', 2),
       ('Transportation', 3),
       ('Entertainment', 4),
       ('Rent', 5),
       ('Utilities', 6),
       ('Clothing', 7),
       ('Healthcare',8 ),
       ('Education', 9),
       ('Travel', 10),
       ('Home Maintenance', 1),
       ('Hobbies', 1),
       ('Charity', 11);
INSERT INTO earning_categories (earning_category_name, user_id)
VALUES ('Salary', 1),
       ('Freelance work', 2),
       ('Investment', 3),
       ('Bonus', 4),
       ('Rental Income', 5),
       ('Dividends', 6),
       ('Commissions', 7),
       ('Royalties', 8),
       ('Gifts', 9),
       ('Consulting', 10),
       ('Online Sales', 11),
       ('Rentals', 1);
INSERT INTO costs (user_id, cost_category_id, budget_id, cost_date, cost_amount, cost_description)
VALUES (1, 1, 1, CURRENT_TIMESTAMP, 50.00, 'Lunch'),
       (2, 2, 2, CURRENT_TIMESTAMP, 20.00, 'Bus fare'),
       (3, 3, 3, CURRENT_TIMESTAMP, 30.00, 'Movie ticket'),
       (4, 1, 7, CURRENT_TIMESTAMP, 70.00, 'Electricity bill'),
       (5, 2, 8, CURRENT_TIMESTAMP, 30.00, 'Lunch'),
       (6, 3, 9, CURRENT_TIMESTAMP, 20.00, 'Books'),
       (7, 1, 3, CURRENT_TIMESTAMP, 90.00, 'Utility bill'),
       (8, 2, 4, CURRENT_TIMESTAMP, 40.00, 'Transportation'),
       (9, 3, 5, CURRENT_TIMESTAMP, 30.00, 'Movie tickets'),
       (1, 1, 9, CURRENT_TIMESTAMP, 120.00, 'Medicine'),
       (1, 2, 10, CURRENT_TIMESTAMP, 60.00, 'Tuition fee'),
       (2, 3, 1, CURRENT_TIMESTAMP, 40.00, 'Flight ticket');
INSERT INTO earnings (user_id, earning_category_id, earning_date, earning_amount, budget_id)
VALUES (1, 1, CURRENT_TIMESTAMP, 2000.00, 1),
       (2, 2, CURRENT_TIMESTAMP, 1000.00, 3),
       (7, 4, CURRENT_TIMESTAMP, 600.00, 13),
       (8, 5, CURRENT_TIMESTAMP, 2500.00, 14),
       (9, 6, CURRENT_TIMESTAMP, 400.00, 15),
       (10, 4, CURRENT_TIMESTAMP, 800.00, 9),
       (11, 5, CURRENT_TIMESTAMP, 3000.00, 10),
       (12, 6, CURRENT_TIMESTAMP, 200.00, 1);

