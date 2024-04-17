CREATE TABLE `books` (
    `id` binary(16) NOT NULL,
    `author` varchar(80) NOT NULL,
    `launch_date` datetime(6) NOT NULL,
    `price` decimal(11,2) NOT NULL,
    `title` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;