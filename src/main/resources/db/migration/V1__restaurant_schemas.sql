CREATE TABLE
    restaurant_tables (
        id BIGSERIAL PRIMARY KEY,
        table_number INTEGER NOT NULL UNIQUE,
        description VARCHAR(100),
        capacity INTEGER NOT NULL DEFAULT 4,
        status VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE',
        CONSTRAINT chk_table_status CHECK (
            status IN ('AVAILABLE', 'OCCUPIED', 'RESERVED', 'INACTIVE')
        )
    );

CREATE TABLE
    product_categories (
        id BIGSERIAL PRIMARY KEY,
        name VARCHAR(100) NOT NULL UNIQUE,
        is_active BOOLEAN NOT NULL DEFAULT TRUE
    );

CREATE TABLE
    products (
        id BIGSERIAL PRIMARY KEY,
        category_id BIGINT NOT NULL REFERENCES product_categories (id),
        name VARCHAR(150) NOT NULL,
        description TEXT,
        price NUMERIC(10, 2) NOT NULL CONSTRAINT chk_product_price CHECK (price >= 0),
        is_available BOOLEAN NOT NULL DEFAULT TRUE,
        preparation_time_minutes INTEGER,
        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP
    );

CREATE INDEX idx_products_name ON products (name);

CREATE INDEX idx_products_category ON products (category_id);

CREATE TABLE
    orders (
        id BIGSERIAL PRIMARY KEY,
        table_id BIGINT NOT NULL REFERENCES restaurant_tables (id),
        opening_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        closing_date TIMESTAMP,
        status VARCHAR(30) NOT NULL DEFAULT 'OPEN',
        note TEXT,
        CONSTRAINT chk_order_status CHECK (
            status IN (
                'OPEN',
                'CLOSED',
                'IN_PREPARATION',
                'DONE',
                'DELIVERED',
                'CANCELED'
            )
        )
    );

CREATE INDEX idx_orders_table ON orders (table_id);

CREATE INDEX idx_orders_status ON orders (status);

CREATE INDEX idx_orders_opening_date ON orders (opening_date);

CREATE TABLE
    order_items (
        id BIGSERIAL PRIMARY KEY,
        order_id BIGINT NOT NULL REFERENCES orders (id),
        product_id BIGINT NOT NULL REFERENCES products (id),
        quantity INTEGER NOT NULL CONSTRAINT chk_item_quantity CHECK (quantity > 0),
        unit_price NUMERIC(10, 2) NOT NULL CONSTRAINT chk_item_price CHECK (unit_price >= 0),
        note TEXT,
        status VARCHAR(30) NOT NULL DEFAULT 'PENDING',
        CONSTRAINT chk_item_status CHECK (
            status IN (
                'PENDING',
                'IN_PREPARATION',
                'DONE',
                'DELIVERED',
                'CANCELED'
            )
        )
    );

CREATE INDEX idx_order_items_order ON order_items (order_id);

CREATE INDEX idx_order_items_product ON order_items (product_id);

CREATE INDEX idx_order_items_status ON order_items (status);

CREATE TABLE
    payments (
        id BIGSERIAL PRIMARY KEY,
        order_id BIGINT NOT NULL REFERENCES orders (id),
        amount NUMERIC(10, 2) NOT NULL CONSTRAINT chk_payment_amount CHECK (amount >= 0),
        payment_method VARCHAR(30) NOT NULL,
        status VARCHAR(30) NOT NULL DEFAULT 'PENDING',
        external_transaction_code VARCHAR(100),
        payment_date TIMESTAMP,
        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        CONSTRAINT chk_payment_method CHECK (
            payment_method IN ('CASH', 'CREDIT_CARD', 'DEBIT_CARD', 'PIX')
        ),
        CONSTRAINT chk_payment_status CHECK (
            status IN ('PENDING', 'APPROVED', 'DECLINED', 'CANCELED')
        )
    );

CREATE INDEX idx_payments_order ON payments (order_id);

CREATE INDEX idx_payments_status ON payments (status);

CREATE TABLE
    account_closures (
        id BIGSERIAL PRIMARY KEY,
        order_id BIGINT NOT NULL UNIQUE REFERENCES orders (id), -- Um fechamento único por ordem
        subtotal NUMERIC(10, 2) NOT NULL CONSTRAINT chk_closure_subtotal CHECK (subtotal >= 0),
        service_tax NUMERIC(10, 2) NOT NULL DEFAULT 0 CONSTRAINT chk_closure_tax CHECK (service_tax >= 0),
        discount NUMERIC(10, 2) NOT NULL DEFAULT 0 CONSTRAINT chk_closure_discount CHECK (discount >= 0),
        total_amount NUMERIC(10, 2) NOT NULL CONSTRAINT chk_closure_total CHECK (total_amount >= 0),
        closing_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );