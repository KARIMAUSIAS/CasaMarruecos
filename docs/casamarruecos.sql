-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 15-02-2023 a las 00:03:20
-- Versión del servidor: 8.0.23
-- Versión de PHP: 8.0.3

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
  `id` bigint NOT NULL,
  `descripcion` varchar(400) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `fecha` date NOT NULL,
  `id_incidencia` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `accion`
--

INSERT INTO `accion` (`id`, `descripcion`, `fecha`, `id_incidencia`) VALUES
(1, 'manifestacion', '2011-05-28', 3),
(2, 'denuncia judicial', '2012-03-29', 2),
(3, 'manifestacion', '2012-06-15', 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `evento`
--

CREATE TABLE `evento` (
  `id` bigint NOT NULL,
  `descripcion` varchar(400) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `fecha` date NOT NULL,
  `imagen` varchar(500) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `evento`
--

INSERT INTO `evento` (`id`, `descripcion`, `fecha`, `imagen`) VALUES
(1, 'manifestacion', '2011-10-26', 'https://media.discordapp.net/attachments/1028346785829245169/1075195184360476783/image.png?width=641&height=468'),
(2, 'festividad', '2022-06-24', 'https://lh3.googleusercontent.com/p/AF1QipMLozFcmXsj_4wf6WLWdRt1_4e6JQARxOmoF_MD=s680-w680-h510'),
(3, 'festividad', '2021-11-19', 'https://media.discordapp.net/attachments/1028346785829245169/1075197697943928953/cela-696x462.png'),
(4, 'charla', '2020-06-27', 'https://www.savener.es/wp-content/uploads/2020/09/Salon-de-Actos-Factoria-de-Tablada-Airbus-2.jpg'),
(5, 'charla', '2010-11-03', 'https://www.savener.es/wp-content/uploads/2020/09/Salon-de-Actos-Factoria-de-Tablada-Airbus-2.jpg'),
(6, 'manifestacion', '2010-08-24', 'https://media.discordapp.net/attachments/1028346785829245169/1075195908490264616/ajuntament_11.png?width=702&height=468'),
(7, 'festividad', '2019-07-11', 'https://definanzas.com/wp-content/uploads/2017/04/que-es-el-ramadan-600x400.jpg'),
(8, 'manifestacion', '2017-01-26', 'https://media.discordapp.net/attachments/1028346785829245169/1075195908490264616/ajuntament_11.png?width=702&height=468'),
(9, 'festividad', '2021-06-30', 'https://media.discordapp.net/attachments/1028346785829245169/1075198460611014758/fiestacordero-9-7-22.png?width=831&height=467'),
(10, 'festividad', '2014-01-15', 'https://lh3.googleusercontent.com/p/AF1QipMLozFcmXsj_4wf6WLWdRt1_4e6JQARxOmoF_MD=s680-w680-h510'),
(11, 'charla', '2010-08-05', 'https://www.savener.es/wp-content/uploads/2020/09/Salon-de-Actos-Factoria-de-Tablada-Airbus-2.jpg'),
(12, 'charla', '2015-04-13', 'https://www.savener.es/wp-content/uploads/2020/09/Salon-de-Actos-Factoria-de-Tablada-Airbus-2.jpg'),
(13, 'manifestacion', '2012-10-03', 'https://media.discordapp.net/attachments/1028346785829245169/1075195908490264616/ajuntament_11.png?width=702&height=468');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `incidencia`
--

CREATE TABLE `incidencia` (
  `id` bigint NOT NULL,
  `lugar` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `fecha` date NOT NULL,
  `descripcion` varchar(400) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `id_usuario` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `incidencia`
--

INSERT INTO `incidencia` (`id`, `lugar`, `fecha`, `descripcion`, `id_usuario`) VALUES
(1, 'Valencia', '2022-08-20', 'acto vandalico a mezquita', 2),
(2, 'Granada', '2022-10-02', 'amenazas en la calle', 3),
(3, 'Madrid', '2022-09-11', 'cantos racistas a unos vecinos', 4),
(4, 'Valencia', '2022-08-10', 'agresión a hombre marroqui', 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `multimedia`
--

CREATE TABLE `multimedia` (
  `id` bigint NOT NULL,
  `archivo` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `id_evento` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `multimedia`
--

INSERT INTO `multimedia` (`id`, `archivo`, `id_evento`) VALUES
(1, 'https://getuikit.com/v2/docs/images/placeholder_600x400.svg', 1),
(2, 'https://getuikit.com/v2/docs/images/placeholder_600x400.svg', 1),
(3, 'https://getuikit.com/v2/docs/images/placeholder_600x400.svg', 3),
(4, 'https://getuikit.com/v2/docs/images/placeholder_600x400.svg', 3),
(5, 'https://getuikit.com/v2/docs/images/placeholder_600x400.svg', 3),
(6, 'https://getuikit.com/v2/docs/images/placeholder_600x400.svg', 2),
(7, 'https://getuikit.com/v2/docs/images/placeholder_600x400.svg', 2),
(8, 'https://getuikit.com/v2/docs/images/placeholder_600x400.svg', 1),
(9, 'https://getuikit.com/v2/docs/images/placeholder_600x400.svg', 3),
(10, 'https://getuikit.com/v2/docs/images/placeholder_600x400.svg', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `participacion`
--

CREATE TABLE `participacion` (
  `id` bigint NOT NULL,
  `id_usuario` bigint NOT NULL,
  `id_evento` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `participacion`
--

INSERT INTO `participacion` (`id`, `id_usuario`, `id_evento`) VALUES
(5, 64, 1),
(6, 67, 5),
(7, 65, 5),
(8, 68, 8),
(9, 66, 12),
(10, 7, 6),
(11, 5, 13),
(12, 68, 11),
(13, 4, 8),
(14, 8, 4),
(15, 7, 1),
(16, 69, 8),
(17, 72, 4),
(18, 8, 12),
(19, 5, 12),
(20, 65, 6),
(22, 5, 2),
(23, 69, 5),
(24, 71, 11),
(25, 5, 10),
(26, 3, 5),
(27, 65, 12),
(28, 7, 8),
(29, 1, 6),
(31, 70, 9),
(32, 6, 13),
(33, 10, 12),
(34, 65, 3),
(43, 1, 4),
(52, 1, 3),
(57, 1, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipousuario`
--

CREATE TABLE `tipousuario` (
  `id` bigint NOT NULL,
  `nombre` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL
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
  `id` bigint NOT NULL,
  `nombre` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `apellido1` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `apellido2` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `id_tipousuario` bigint NOT NULL,
  `usuario` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `contraseña` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `nombre`, `apellido1`, `apellido2`, `email`, `id_tipousuario`, `usuario`, `contraseña`) VALUES
(1, 'Karim', 'Rezgaoui', 'Mourad', 'email@email.com', 1, 'kare', 'be59d545b4c6ee5c9f2da6bbf6ce667097a7e38bd75ade5dde564234fb891c88'),
(2, 'Kevin', 'Blanco', 'Bañuls', 'email@email.com', 4, 'kebla', 'be59d545b4c6ee5c9f2da6bbf6ce667097a7e38bd75ade5dde564234fb891c88'),
(3, 'Jose', 'Rosales', '', 'email@email.com', 2, 'jora', 'be59d545b4c6ee5c9f2da6bbf6ce667097a7e38bd75ade5dde564234fb891c88'),
(4, 'Carlos', 'Lazaro', '', 'email@email.com', 2, 'carla', 'be59d545b4c6ee5c9f2da6bbf6ce667097a7e38bd75ade5dde564234fb891c88'),
(5, 'Iris', 'Suay', '', 'email@email.com', 2, 'isu', 'be59d545b4c6ee5c9f2da6bbf6ce667097a7e38bd75ade5dde564234fb891c88'),
(6, 'Nerea', 'Soler', '', 'email@email.com', 2, 'neso', 'be59d545b4c6ee5c9f2da6bbf6ce667097a7e38bd75ade5dde564234fb891c88'),
(7, 'Mario', 'Tomas', '', 'email@email.com', 2, 'mato', 'be59d545b4c6ee5c9f2da6bbf6ce667097a7e38bd75ade5dde564234fb891c88'),
(8, 'Ruben', 'Perez', '', 'email@email.com', 2, 'rupe', 'be59d545b4c6ee5c9f2da6bbf6ce667097a7e38bd75ade5dde564234fb891c88'),
(9, 'Alvaro', 'Talaya', '', 'email@email.com', 2, 'alta', 'be59d545b4c6ee5c9f2da6bbf6ce667097a7e38bd75ade5dde564234fb891c88'),
(10, 'Cris', 'Carascosa', '', 'email@email.com', 2, 'crisca', 'be59d545b4c6ee5c9f2da6bbf6ce667097a7e38bd75ade5dde564234fb891c88'),
(63, 'Kevin', 'Perez', 'Luz', 'kevin_perez@casamarruecos.com', 2, 'Kevin_Perez', 'be59d545b4c6ee5c9f2da6bbf6ce667097a7e38bd75ade5dde564234fb891c88'),
(64, 'Cristina', 'Luque', 'Sanchis', 'luque_cristina@casamarruecos.com', 2, 'Cristina_Luque', 'be59d545b4c6ee5c9f2da6bbf6ce667097a7e38bd75ade5dde564234fb891c88'),
(65, 'Estefania', 'Blanco', 'Benavent', 'estefania_blanco@casamarruecos.com', 2, 'Estefania_Blanco', 'be59d545b4c6ee5c9f2da6bbf6ce667097a7e38bd75ade5dde564234fb891c88'),
(66, 'Mario', 'Rosales', 'Benavent', 'mario_rosales@casamarruecos.com', 2, 'Mario_Rosales', 'be59d545b4c6ee5c9f2da6bbf6ce667097a7e38bd75ade5dde564234fb891c88'),
(67, 'Jose Maria', 'Tomas', 'Morera', 'tomas_jose maria@casamarruecos.com', 2, 'Jose Maria_Tomas', 'be59d545b4c6ee5c9f2da6bbf6ce667097a7e38bd75ade5dde564234fb891c88'),
(68, 'Estefania', 'Talaya', 'Patricio', 'estefania_talaya@casamarruecos.com', 1, 'Estefania_Talaya', 'be59d545b4c6ee5c9f2da6bbf6ce667097a7e38bd75ade5dde564234fb891c88'),
(69, 'Iris', 'Tomas', 'Sanchis', 'tomas_iris@casamarruecos.com', 1, 'Iris_Tomas', 'be59d545b4c6ee5c9f2da6bbf6ce667097a7e38bd75ade5dde564234fb891c88'),
(70, 'Jose Maria', 'Benito', 'Sanchis', 'jose maria_benito@casamarruecos.com', 1, 'Jose Maria_Benito', 'be59d545b4c6ee5c9f2da6bbf6ce667097a7e38bd75ade5dde564234fb891c88'),
(71, 'Elliot', 'Carrascosa', 'Benavent', 'elliot_carrascosa@casamarruecos.com', 2, 'Elliot_Carrascosa', 'be59d545b4c6ee5c9f2da6bbf6ce667097a7e38bd75ade5dde564234fb891c88'),
(72, 'Iris', 'Perez', 'Morera', 'iris_perez@casamarruecos.com', 1, 'Iris_Perez', 'be59d545b4c6ee5c9f2da6bbf6ce667097a7e38bd75ade5dde564234fb891c88');

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
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `evento`
--
ALTER TABLE `evento`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `incidencia`
--
ALTER TABLE `incidencia`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `multimedia`
--
ALTER TABLE `multimedia`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `participacion`
--
ALTER TABLE `participacion`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=58;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=73;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
