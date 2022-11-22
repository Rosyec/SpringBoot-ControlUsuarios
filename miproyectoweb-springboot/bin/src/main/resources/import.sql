INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Ceysor', 'Parrado', 'chechomens@gmail.com', '2022-10-12', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Colin', 'Parrado', 'colinparrado@gmail.com', '2022-10-10', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Jim', 'Parrado', 'jim@gmail.com', '2022-04-12', '');
INSERT INTO productos (nombre, precio, create_at) VALUES('TV Panasonic', 150000, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Camara Sony', 39000, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Xbox Series S', 43600, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Xbox Series X', 55000, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Smartphone Samsung Galaxy A52S', 122000, NOW());

INSERT INTO facturas (descripcion, observacion, create_at, cliente_id) VALUES('Factura de equipos de oficina', 'Efectivo', NOW(), 1);
INSERT INTO item_facturas (cantidad, producto_id, factura_id) VALUES(1, 1, 1);
INSERT INTO item_facturas (cantidad, producto_id, factura_id) VALUES(1, 2, 1);

INSERT INTO facturas (descripcion, observacion, create_at, cliente_id) VALUES('Factura de video juegos', 'Tarjeta', NOW(), 2);
INSERT INTO item_facturas (cantidad, producto_id, factura_id) VALUES(1, 3, 2);