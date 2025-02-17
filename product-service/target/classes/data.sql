-- Ensure the `products` table exists
CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    stock INT NOT NULL,
    price DOUBLE NOT NULL
);

-- Insert sample products
INSERT INTO products (name, stock, price) VALUES ('Laptop', 10, 999.99);
INSERT INTO products (name, stock, price) VALUES ('Smartphone', 15, 699.99);
INSERT INTO products (name, stock, price) VALUES ('Gaming Monitor', 20, 299.99);
