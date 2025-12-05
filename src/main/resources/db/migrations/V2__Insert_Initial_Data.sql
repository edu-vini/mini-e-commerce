-- DADOS CATEGORIES
INSERT INTO categories (id, name, created_at, updated_at) VALUES
    (1, 'Informática', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    (2, 'Acessórios', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    (3, 'Gamer', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

-- DADOS CATEGORIES (FILHAS)
INSERT INTO categories (id, name, parent_id, created_at, updated_at) VALUES
    (4, 'Periféricos', 1, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    (5, 'Monitores', 1, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    (6, 'Mouse e Teclado', 4, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    (7, 'Headsets', 3, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());


-- DADOS PRODUTOS
INSERT INTO products (id, name, description, sku, price, cost_price, category_id, stock_quantity, active, created_at, updated_at) VALUES
    (1, 'Mouse Gamer RGB', 'Mouse com sensor de alta precisão.', 'MG001', 120.00, 80.00, 4, 15, TRUE, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    (2, 'Teclado Mecânico Blue Switch', 'Teclado com switches azuis.', 'TM002', 250.00, 180.00, 4, 15, TRUE, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    (3, 'Mousepad Grande', 'Mousepad extra grande para periféricos.', 'MP003', 45.00, 25.00, 4, 15, TRUE, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    (4, 'Monitor 24 Full HD', 'Monitor de 24 polegadas, 60Hz.', 'M24FHD', 950.00, 750.00, 5, 8, TRUE, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    (5, 'Monitor 27 Curvo', 'Monitor curvo de 27 polegadas, 144Hz.', 'M27CUR', 1450.00, 1100.00, 5, 8, TRUE, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    (6, 'Cadeira Gamer Reclinável', 'Cadeira com suporte lombar e reclínio 180º.', 'CG006', 850.00, 600.00, 3, 5, TRUE, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    (7, 'Headset Surround 7.1', 'Headset para áudio posicional 7.1.', 'HS707', 180.00, 100.00, 7, 12, TRUE, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    (8, 'Headset Wireless Pro', 'Headset sem fio com baixa latência.', 'HWP008', 380.00, 280.00, 7, 12, TRUE, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    (9, 'HD SSD 480GB', 'Disco sólido de 480GB de capacidade.', 'SSD480', 220.00, 150.00, 1, 20, TRUE, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
    (10, 'Memória RAM 16GB DDR4', 'Módulo de memória 16GB, 3200Mhz.', 'MR16D4', 320.00, 250.00, 1, 20, TRUE, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

-- DADOS CARTS
INSERT INTO carts (id, user_id, status) VALUES
                                            (1, 10, 'OPEN'),
                                            (2, 20, 'OPEN'),
                                            (3, 30, 'OPEN');

-- DADOS CART ITEMS
INSERT INTO cart_items (id, cart_id, product_id, quantity, price_snapshot) VALUES
    (1, 1, 1, 1, 120.00), (2, 1, 2, 1, 250.00), (3, 1, 3, 2, 45.00),
    (4, 2, 4, 1, 950.00), (5, 2, 5, 1, 1450.00), (6, 2, 6, 1, 850.00),
    (7, 3, 7, 1, 180.00), (8, 3, 8, 1, 380.00), (9, 3, 9, 1, 220.00), (10, 3, 10, 2, 320.00);

-- DADOS USUARIOS
INSERT INTO usuarios (id, login, senha, role) VALUES
    (1, 'admin_teste', '$2a$12$UxL.sHRqPbzCfGwwep.THuzF/wh8iGUFU5wBrBtntopbga4OPgQyu', 'ROLE_ADMIN'), -- senhaadmin
    (2, 'user_teste', '$2a$12$eTu3/e3CdYjJ/fABfFpwHOwqEsLoweCNAVxdw/hPbz6WmMLYPpbbW', 'ROLE_USER'); -- senhauser

ALTER TABLE categories ALTER COLUMN id RESTART WITH 8;
ALTER TABLE products ALTER COLUMN id RESTART WITH 11;
ALTER TABLE carts ALTER COLUMN id RESTART WITH 4;
ALTER TABLE cart_items ALTER COLUMN id RESTART WITH 11;
ALTER TABLE usuarios ALTER COLUMN id RESTART WITH 3;