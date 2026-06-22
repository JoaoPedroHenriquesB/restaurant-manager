INSERT INTO
    restaurant_tables (table_number, description, capacity, status)
VALUES
    (1, 'Mesa perto da janela', 2, 'AVAILABLE'),
    (2, 'Mesa central salão principal', 4, 'OCCUPIED'),
    (3, 'Mesa externa - Varanda', 6, 'RESERVED'),
    (4, 'Mesa VIP - Reservada', 4, 'INACTIVE');

INSERT INTO
    product_categories (name, is_active)
VALUES
    ('Bebidas', true),
    ('Entradas', true),
    ('Pratos Principais', true),
    ('Sobremesas', true);

INSERT INTO
    products (
        category_id,
        name,
        description,
        price,
        is_available,
        preparation_time_minutes
    )
VALUES
    (
        (
            SELECT
                id
            FROM
                product_categories
            WHERE
                name = 'Bebidas'
        ),
        'Suco Natural de Laranja',
        'Suco natural da fruta 300ml',
        9.50,
        true,
        5
    ),
    (
        (
            SELECT
                id
            FROM
                product_categories
            WHERE
                name = 'Bebidas'
        ),
        'Cerveja Artesanal IPA',
        'Garrafa 500ml de fabricação local',
        18.00,
        true,
        2
    ),
    (
        (
            SELECT
                id
            FROM
                product_categories
            WHERE
                name = 'Entradas'
        ),
        'Batata Frita com Queijo',
        'Porção de batatas com cheddar e bacon',
        28.90,
        true,
        12
    ),
    (
        (
            SELECT
                id
            FROM
                product_categories
            WHERE
                name = 'Pratos Principais'
        ),
        'Filé à Parmegiana',
        'Acompanha arroz e batata frita',
        45.00,
        true,
        25
    ),
    (
        (
            SELECT
                id
            FROM
                product_categories
            WHERE
                name = 'Sobremesas'
        ),
        'Pudim de Leite',
        'Fatia de pudim de leite condensado tradicional',
        12.00,
        true,
        3
    );

INSERT INTO
    orders (table_id, status, note)
VALUES
    (
        (
            SELECT
                id
            FROM
                restaurant_tables
            WHERE
                table_number = 2
        ),
        'OPEN',
        'Cliente pediu para caprichar no queijo'
    );

INSERT INTO
    order_items (
        order_id,
        product_id,
        quantity,
        unit_price,
        status,
        note
    )
VALUES
    (
        (
            SELECT
                id
            FROM
                orders
            WHERE
                table_id = (
                    SELECT
                        id
                    FROM
                        restaurant_tables
                    WHERE
                        table_number = 2
                )
                AND status = 'OPEN'
        ),
        (
            SELECT
                id
            FROM
                products
            WHERE
                name = 'Batata Frita com Queijo'
        ),
        1,
        28.90,
        'DELIVERED',
        NULL
    ),
    (
        (
            SELECT
                id
            FROM
                orders
            WHERE
                table_id = (
                    SELECT
                        id
                    FROM
                        restaurant_tables
                    WHERE
                        table_number = 2
                )
                AND status = 'OPEN'
        ),
        (
            SELECT
                id
            FROM
                products
            WHERE
                name = 'Cerveja Artesanal IPA'
        ),
        2,
        18.00,
        'DELIVERED',
        'Bem gelada'
    );

INSERT INTO
    payments (
        order_id,
        amount,
        payment_method,
        status,
        payment_date
    )
VALUES
    (
        (
            SELECT
                id
            FROM
                orders
            WHERE
                table_id = (
                    SELECT
                        id
                    FROM
                        restaurant_tables
                    WHERE
                        table_number = 2
                )
                AND status = 'OPEN'
        ),
        64.90, -- (1x 28.90 + 2x 18.00)
        'PIX',
        'APPROVED',
        CURRENT_TIMESTAMP
    );

INSERT INTO
    account_closures (
        order_id,
        subtotal,
        service_tax,
        discount,
        total_amount
    )
VALUES
    (
        (
            SELECT
                id
            FROM
                orders
            WHERE
                table_id = (
                    SELECT
                        id
                    FROM
                        restaurant_tables
                    WHERE
                        table_number = 2
                )
                AND status = 'OPEN'
        ),
        64.90,
        6.49,
        5.00,
        66.39
    );