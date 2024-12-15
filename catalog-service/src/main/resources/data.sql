INSERT IGNORE INTO catalog_db.products (id, created_at, is_deleted, updated_at, product_code, description, has_stock, name, price, unlimeted)
VALUES
    (UNHEX(REPLACE(UUID(), '-', '')), NOW(), 0, NULL, 'P001', 'High-performance laptop', 1, 'Laptop', 1500.00, 0),
    (UNHEX(REPLACE(UUID(), '-', '')), NOW(), 0, NULL, 'P002', 'Gaming mouse with RGB lights', 1, 'Mouse', 50.00, 0),
    (UNHEX(REPLACE(UUID(), '-', '')), NOW(), 0, NULL, 'P003', 'Ergonomic office chair', 1, 'Chair', 200.00, 0),
    (UNHEX(REPLACE(UUID(), '-', '')), NOW(), 0, NULL, 'P004', 'Mechanical keyboard', 1, 'Keyboard', 120.00, 0),
    (UNHEX(REPLACE(UUID(), '-', '')), NOW(), 0, NULL, 'P006', 'Mouse Pad', 1, 'Mouse Pad', 5.00, 1),
    (UNHEX(REPLACE(UUID(), '-', '')), NOW(), 0, NULL, 'P005', '27-inch 4K monitor', 1, 'Monitor', 300.00, 0);