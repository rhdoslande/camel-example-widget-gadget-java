CREATE TABLE `david_schema`.`orders` (
  `customer_id` INT NOT NULL,
  `product_type` VARCHAR(45) NOT NULL,
  `amount` INT NOT NULL,
  PRIMARY KEY (`customer_id`));
