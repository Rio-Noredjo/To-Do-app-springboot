CREATE USER 'todoapp'@'localhost' IDENTIFIED BY 'todoapp';

GRANT ALL PRIVILEGES ON * . * TO 'todoapp'@'localhost';

ALTER USER 'todoapp'@'localhost' IDENTIFIED WITH mysql_native_password BY 'todoapppassword';