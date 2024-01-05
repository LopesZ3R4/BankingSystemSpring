CREATE TABLE Account (
                         AccountID BIGINT PRIMARY KEY AUTO_INCREMENT,
                         AccountNumber VARCHAR(20) UNIQUE NOT NULL,
                         AccountHolderName VARCHAR(100) NOT NULL,
                         Balance DECIMAL(15, 2) NOT NULL,
                         CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         UpdatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
