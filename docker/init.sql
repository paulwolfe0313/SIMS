CREATE DATABASE IF NOT EXISTS inventory;
USE inventory;

-- Create products table if it doesn't exist
CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    stock INT NOT NULL,
    price DOUBLE NOT NULL
);

-- Insert sample data into products
INSERT INTO products (name, stock, price) VALUES ('Laptop', 10, 999.99);
INSERT INTO products (name, stock, price) VALUES ('Smartphone', 15, 699.99);
INSERT INTO products (name, stock, price) VALUES ('Gaming Monitor', 20, 299.99);

-- Create orders table if it doesn't exist
CREATE TABLE IF NOT EXISTS orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'Pending',
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- Insert sample data into orders
INSERT INTO orders (product_id, quantity, status) VALUES (1, 2, 'Pending');
INSERT INTO orders (product_id, quantity, status) VALUES (2, 1, 'Completed');
