CREATE database cafeteria;
USE cafeteria;

CREATE TABLE productos(
codigo INT AUTO_INCREMENT PRIMARY KEY,
nombre VARCHAR(100),
precio DOUBLE,
inventario INT);

INSERT INTO productos values (1,"Manzanas",5000.0,25);
INSERT INTO productos values (2,"Limones",2300.0,15);
INSERT INTO productos values (3,"Peras",2700.0,33);
INSERT INTO productos values (4,"Arandanos",9300.0,5);
INSERT INTO productos values (5,"Tomates",2100.0,42);
INSERT INTO productos values (6,"Fresas",4100.0,3);
INSERT INTO productos values (7,"Helado",4500.0,41);
INSERT INTO productos values (8,"Galletas",500.0,8);
INSERT INTO productos values (9,"Chocolates",3500.0,80);
INSERT INTO productos values (10,"Jamon",15000.0,10);

select *
from productos