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

-- Create notifications table if it doesn't exist
CREATE TABLE IF NOT EXISTS notifications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    status VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    message VARCHAR(255) NOT NULL,  -- ✅ Ensure 'message' column exists
    timestamp DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP -- ✅ Ensure 'timestamp' is set correctly
);

-- Insert sample data for notifications
INSERT INTO notifications (order_id, status, message, timestamp) VALUES (1, 'Order Confirmed', 'Your order has been successfully placed.', NOW());
