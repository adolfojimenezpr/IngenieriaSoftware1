-- MySQL Script generated by MySQL Workbench
-- dom 21 ene 2018 16:57:24 CET
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema infamousmusic
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema infamousmusic
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `infamousmusic` DEFAULT CHARACTER SET utf8 ;
USE `infamousmusic` ;

-- -----------------------------------------------------
-- Table `infamousmusic`.`almacen`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `infamousmusic`.`almacen` (
  `idAlmacen` INT(11) NOT NULL AUTO_INCREMENT,
  `idTienda` INT(11) NOT NULL,
  `nombreAlmacen` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idAlmacen`),
  UNIQUE INDEX `nombreAlmacen_UNIQUE` (`nombreAlmacen` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `infamousmusic`.`cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `infamousmusic`.`cliente` (
  `idDNICliente` INT(11) NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `apellidos` VARCHAR(45) NOT NULL,
  `telefono` INT(11) NOT NULL,
  `email` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`idDNICliente`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `infamousmusic`.`cupon`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `infamousmusic`.`cupon` (
  `idCupon` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `descuento` INT(11) NOT NULL,
  `duracion` INT(11) NOT NULL,
  `fechaInicio` DATE NOT NULL,
  PRIMARY KEY (`idCupon`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `infamousmusic`.`detallesproducto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `infamousmusic`.`detallesproducto` (
  `idDetallesProducto` INT(11) NOT NULL AUTO_INCREMENT,
  `artista` VARCHAR(45) NOT NULL,
  `album` VARCHAR(45) NOT NULL,
  `selloDiscografico` VARCHAR(45) NOT NULL,
  `proveedor` VARCHAR(45) NOT NULL,
  `estiloMusical` VARCHAR(45) NULL DEFAULT NULL,
  `numeroVentas` INT(11) NULL DEFAULT NULL,
  `costeProducto` FLOAT NOT NULL,
  `precioVentaProducto` FLOAT NOT NULL,
  `fechaLanzamiento` DATE NULL DEFAULT NULL,
  `fechaLlegadaTienda` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`idDetallesProducto`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `infamousmusic`.`producto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `infamousmusic`.`producto` (
  `idProducto` INT(11) NOT NULL AUTO_INCREMENT,
  `cantidadDisponible` VARCHAR(45) NOT NULL,
  `cantidadVendida` VARCHAR(45) NOT NULL,
  `cantidadReservada` VARCHAR(45) NOT NULL,
  `idDetallesProducto` INT(11) NOT NULL,
  `idAlmacen` INT(11) NOT NULL,
  PRIMARY KEY (`idProducto`),
  INDEX `fk_producto_detallesproducto1_idx` (`idDetallesProducto` ASC),
  INDEX `fk_producto_almacen1_idx` (`idAlmacen` ASC),
  CONSTRAINT `fk_producto_detallesproducto1`
    FOREIGN KEY (`idDetallesProducto`)
    REFERENCES `infamousmusic`.`detallesproducto` (`idDetallesProducto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_producto_almacen1`
    FOREIGN KEY (`idAlmacen`)
    REFERENCES `infamousmusic`.`almacen` (`idAlmacen`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `infamousmusic`.`pago`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `infamousmusic`.`pago` (
  `idPago` INT(11) NOT NULL AUTO_INCREMENT,
  `importeVenta` FLOAT NOT NULL,
  `importeEntregado` FLOAT NOT NULL,
  `importeVuelta` FLOAT NOT NULL,
  `fechaPago` DATE NOT NULL,
  PRIMARY KEY (`idPago`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `infamousmusic`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `infamousmusic`.`usuario` (
  `idDNIUsuario` INT(11) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `apellidos` VARCHAR(45) NOT NULL,
  `telefono` INT(11) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `puesto` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idDNIUsuario`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `infamousmusic`.`venta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `infamousmusic`.`venta` (
  `idVenta` INT(11) NOT NULL AUTO_INCREMENT,
  `precioTotal` FLOAT NULL DEFAULT NULL,
  `idPago` INT(11) NOT NULL,
  `idDNIUsuario` INT(11) NOT NULL,
  `idDNICliente` INT(11) NOT NULL,
  `idCupon` INT(11) NOT NULL,
  PRIMARY KEY (`idVenta`),
  INDEX `fk_venta_pago1_idx` (`idPago` ASC),
  INDEX `fk_venta_usuario1_idx` (`idDNIUsuario` ASC),
  INDEX `fk_venta_cliente1_idx` (`idDNICliente` ASC),
  INDEX `fk_venta_cupon1_idx` (`idCupon` ASC),
  CONSTRAINT `fk_venta_pago1`
    FOREIGN KEY (`idPago`)
    REFERENCES `infamousmusic`.`pago` (`idPago`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_venta_usuario1`
    FOREIGN KEY (`idDNIUsuario`)
    REFERENCES `infamousmusic`.`usuario` (`idDNIUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_venta_cliente1`
    FOREIGN KEY (`idDNICliente`)
    REFERENCES `infamousmusic`.`cliente` (`idDNICliente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_venta_cupon1`
    FOREIGN KEY (`idCupon`)
    REFERENCES `infamousmusic`.`cupon` (`idCupon`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `infamousmusic`.`devolucion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `infamousmusic`.`devolucion` (
  `idDevolucion` INT(11) NOT NULL AUTO_INCREMENT,
  `idProducto` INT(11) NOT NULL,
  `idVenta` INT(11) NOT NULL,
  PRIMARY KEY (`idDevolucion`),
  INDEX `fk_devolucion_producto1_idx` (`idProducto` ASC),
  INDEX `fk_devolucion_venta1_idx` (`idVenta` ASC),
  CONSTRAINT `fk_devolucion_producto1`
    FOREIGN KEY (`idProducto`)
    REFERENCES `infamousmusic`.`producto` (`idProducto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_devolucion_venta1`
    FOREIGN KEY (`idVenta`)
    REFERENCES `infamousmusic`.`venta` (`idVenta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `infamousmusic`.`lineaventa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `infamousmusic`.`lineaventa` (
  `idLineaVenta` INT(11) NOT NULL AUTO_INCREMENT,
  `idProducto` INT(11) NOT NULL,
  `cantidad` INT(11) NOT NULL,
  `cantidadVuelta` INT(11) NOT NULL DEFAULT '0',
  `idVenta` INT(11) NOT NULL,
  PRIMARY KEY (`idLineaVenta`),
  INDEX `fk_lineaventa_venta1_idx` (`idVenta` ASC),
  CONSTRAINT `fk_lineaventa_venta1`
    FOREIGN KEY (`idVenta`)
    REFERENCES `infamousmusic`.`venta` (`idVenta`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `infamousmusic`.`reserva`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `infamousmusic`.`reserva` (
  `idReserva` INT(11) NOT NULL AUTO_INCREMENT,
  `cantidad` INT(11) NOT NULL,
  `fechaReserva` DATE NOT NULL,
  `idDNICliente` INT(11) NOT NULL,
  `idProducto` INT(11) NOT NULL,
  PRIMARY KEY (`idReserva`),
  INDEX `fk_reserva_cliente1_idx` (`idDNICliente` ASC),
  INDEX `fk_reserva_producto1_idx` (`idProducto` ASC),
  CONSTRAINT `fk_reserva_cliente1`
    FOREIGN KEY (`idDNICliente`)
    REFERENCES `infamousmusic`.`cliente` (`idDNICliente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_reserva_producto1`
    FOREIGN KEY (`idProducto`)
    REFERENCES `infamousmusic`.`producto` (`idProducto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;