-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 15-12-2022 a las 18:31:11
-- Versión del servidor: 10.4.25-MariaDB
-- Versión de PHP: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `casamarruecos`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `accion`
--

CREATE TABLE `accion` (
  `id` bigint(20) NOT NULL,
  `descripcion` varchar(400) COLLATE utf8_unicode_ci NOT NULL,
  `fecha` date NOT NULL,
  `id_incidencia` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `evento`
--

CREATE TABLE `evento` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `descripcion` varchar(400) COLLATE utf8_unicode_ci NOT NULL,
  `fecha` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `incidencia`
--

CREATE TABLE `incidencia` (
  `id` bigint(20) NOT NULL,
  `lugar` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `fecha` date NOT NULL,
  `descripcion` varchar(400) COLLATE utf8_unicode_ci NOT NULL,
  `id_usuario` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `multimedia`
--

CREATE TABLE `multimedia` (
  `id` bigint(20) NOT NULL,
  `archivo` blob NOT NULL,
  `id_evento` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `participacion`
--

CREATE TABLE `participacion` (
  `id` bigint(20) NOT NULL,
  `id_usuario` bigint(20) NOT NULL,
  `id_evento` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipousuario`
--

CREATE TABLE `tipousuario` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `tipousuario`
--

INSERT INTO `tipousuario` (`id`, `nombre`) VALUES
(1, 'ADMIN'),
(2, 'USER'),
(3, 'SOCIO'),
(4, 'VOLUNTARIO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `apellido1` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `apellido2` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `id_tipousuario` bigint(20) NOT NULL,
  `usuario` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `contraseña` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `localidad` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `domicilio` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `telefono` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `dni` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fechaNacimiento` date DEFAULT NULL,
  `camara` tinyint(1) DEFAULT NULL,
  `carnetConducir` tinyint(1) DEFAULT NULL,
  `coche` tinyint(1) DEFAULT NULL,
  `accesoInternet` tinyint(1) DEFAULT NULL,
  `facebook` tinyint(1) DEFAULT NULL,
  `telefonoFijo` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ocupacionActual` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `nombre`, `apellido1`, `apellido2`, `email`, `id_tipousuario`, `usuario`, `contraseña`, `localidad`, `domicilio`, `telefono`, `dni`, `fechaNacimiento`, `camara`, `carnetConducir`, `coche`, `accesoInternet`, `facebook`, `telefonoFijo`, `ocupacionActual`) VALUES
(1, 'Karim', 'Rezgaoui', 'Mourad', 'email@email.com', 1, 'kare', 'admin', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(2, 'Kevin', 'Blanco', 'Bañuls', 'email@email.com', 2, 'kebla', '73ec8dee81ea4c9e7515d63c9e5bbb707c7bc4789363c5afa401d3aa780630f6', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(3, 'Jose', 'Rosales', '', 'email@email.com', 2, 'jora', '73ec8dee81ea4c9e7515d63c9e5bbb707c7bc4789363c5afa401d3aa780630f6', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(4, 'Carlos', 'Lazaro', '', 'email@email.com', 2, 'carla', '73ec8dee81ea4c9e7515d63c9e5bbb707c7bc4789363c5afa401d3aa780630f6', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(5, 'Iris', 'Suay', '', 'email@email.com', 2, 'isu', '73ec8dee81ea4c9e7515d63c9e5bbb707c7bc4789363c5afa401d3aa780630f6', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(6, 'Nerea', 'Soler', '', 'email@email.com', 2, 'neso', '73ec8dee81ea4c9e7515d63c9e5bbb707c7bc4789363c5afa401d3aa780630f6', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(7, 'Mario', 'Tomas', '', 'email@email.com', 2, 'mato', '73ec8dee81ea4c9e7515d63c9e5bbb707c7bc4789363c5afa401d3aa780630f6', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(8, 'Ruben', 'Perez', '', 'email@email.com', 2, 'rupe', '73ec8dee81ea4c9e7515d63c9e5bbb707c7bc4789363c5afa401d3aa780630f6', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(9, 'Alvaro', 'Talaya', '', 'email@email.com', 2, 'alta', '73ec8dee81ea4c9e7515d63c9e5bbb707c7bc4789363c5afa401d3aa780630f6', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(10, 'Cristina', 'Carascosa', '', 'email@email.com', 2, 'crisca', '73ec8dee81ea4c9e7515d63c9e5bbb707c7bc4789363c5afa401d3aa780630f6', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `accion`
--
ALTER TABLE `accion`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `evento`
--
ALTER TABLE `evento`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `incidencia`
--
ALTER TABLE `incidencia`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `multimedia`
--
ALTER TABLE `multimedia`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `participacion`
--
ALTER TABLE `participacion`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `tipousuario`
--
ALTER TABLE `tipousuario`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `accion`
--
ALTER TABLE `accion`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `evento`
--
ALTER TABLE `evento`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `incidencia`
--
ALTER TABLE `incidencia`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `multimedia`
--
ALTER TABLE `multimedia`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `participacion`
--
ALTER TABLE `participacion`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;