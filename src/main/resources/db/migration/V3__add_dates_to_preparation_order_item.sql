ALTER TABLE order_items
ADD COLUMN preparation_date TIMESTAMP,
ADD COLUMN completion_date TIMESTAMP,
ADD COLUMN delivery_date TIMESTAMP;