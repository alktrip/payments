-- payments.pago definition

CREATE TABLE `pago` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `concepto` varchar(100) NOT NULL,
  `cliente` varchar(300) NOT NULL,
  `vendedor` varchar(300) NOT NULL,
  `monto` double NOT NULL,
  `estatus_pago` varchar(100) NOT NULL,
  `cantidad_productos` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;