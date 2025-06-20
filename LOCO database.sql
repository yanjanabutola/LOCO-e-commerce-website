
-- Create the database
CREATE DATABASE IF NOT EXISTS loco;
USE loco;

-- Table: user_roles
CREATE TABLE IF NOT EXISTS user_roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO user_roles (role_name) VALUES ('admin'), ('customer'), ('vendor');

-- Table: users
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone_number VARCHAR(15),
    password VARCHAR(255) NOT NULL,
    role_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (role_id) REFERENCES user_roles(id)
);

-- Table: categories
CREATE TABLE IF NOT EXISTS categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL,
    url_slug VARCHAR(100) NOT NULL UNIQUE,
    parent_cat_id INT,
    status ENUM('active', 'inactive') DEFAULT 'active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Table: products
CREATE TABLE IF NOT EXISTS products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    url_slug VARCHAR(255) NOT NULL UNIQUE,
    cat_id INT,
    description TEXT,
    price FLOAT(10,2) NOT NULL,
    stock_quantity INT,
    status ENUM('active', 'inactive') DEFAULT 'active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (cat_id) REFERENCES categories(id)
);

-- Table: product_variants
CREATE TABLE IF NOT EXISTS product_variants (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT,
    colour VARCHAR(50),
    size VARCHAR(50),
    price FLOAT(10,2),
    stock_quantity INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Table: carts
CREATE TABLE IF NOT EXISTS carts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    product_id INT,
    product_variant_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (product_variant_id) REFERENCES product_variants(id)
);

-- Table: orders
CREATE TABLE IF NOT EXISTS orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_number VARCHAR(100) UNIQUE NOT NULL,
    user_id INT,
    total_amount FLOAT,
    discount_amount FLOAT,
    gross_amount FLOAT,
    shipping_amount FLOAT,
    net_amount FLOAT,
    status ENUM('placed', 'processing', 'shipping', 'delivered') DEFAULT 'placed',
    payment_status VARCHAR(50),
    payment_type VARCHAR(50),
    payment_transaction_id VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Table: order_items
CREATE TABLE IF NOT EXISTS order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    product_id INT,
    product_variant_id INT,
    product_name VARCHAR(255),
    colour VARCHAR(50),
    size VARCHAR(50),
    price FLOAT,
    quantity INT DEFAULT 1,
    total_amount FLOAT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (product_variant_id) REFERENCES product_variants(id)
);

-- Table: order_shipping_address
CREATE TABLE IF NOT EXISTS order_shipping_address (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    shipping_address_id INT,
    full_address TEXT,
    state VARCHAR(100),
    city VARCHAR(100),
    zip_code VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(id)
);

-- Table: wishlist
CREATE TABLE IF NOT EXISTS wishlist (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    product_id INT,
    product_variant_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (product_variant_id) REFERENCES product_variants(id)
);

-- Table: offers
CREATE TABLE IF NOT EXISTS offers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    coupon_code VARCHAR(50) NOT NULL,
    discount_type ENUM('fixed', 'rate') NOT NULL,
    discount_value FLOAT,
    start_date DATE,
    end_date DATE,
    description TEXT,
    status ENUM('active', 'inactive') DEFAULT 'active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
INSERT INTO user_roles (role_name) VALUES
('Customer'),
('Admin'),
('Vendor');

INSERT INTO users (full_name, email, phone_number, password, role_id) VALUES
('John Doe', 'john@example.com', '1234567890', 'hashedpassword1', 1),
('Jane Admin', 'admin@example.com', '0987654321', 'hashedpassword2', 2);

INSERT INTO categories (category_name, url_slug, parent_cat_id, status) VALUES
('Electronics', 'electronics', NULL, 'active'),
('Clothing', 'clothing', NULL, 'active');

INSERT INTO products (product_name, url_slug, cat_id, description, price, stock_quantity, status) VALUES
('Smartphone X', 'smartphone-x', 1, 'Latest smartphone with great features', 699.99, 50, 'active'),
('Jeans Classic', 'jeans-classic', 2, 'Comfortable and stylish jeans', 49.99, 100, 'active');

INSERT INTO product_variants (product_id, colour, size, price, stock_quantity) VALUES
(1, 'Black', '128GB', 699.99, 20),
(2, 'Blue', '32', 49.99, 30);

INSERT INTO wishlist (user_id, product_id, product_variant_id) VALUES
(1, 1, 1),
(1, 2, 2);

INSERT INTO offers (coupon_code, discount_type, discount_value, start_date, end_date, description, status) VALUES
('NEWYEAR2025', 'rate', 15.00, '2025-01-01', '2025-01-31', 'New Year Discount', 'active');
