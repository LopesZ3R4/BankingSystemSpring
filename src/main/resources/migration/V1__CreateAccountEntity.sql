CREATE TABLE Accounts (
                         AccountID BINARY(16) PRIMARY KEY,
                         ChavePix VARCHAR(20) UNIQUE NOT NULL,
                         AccountHolderName VARCHAR(100) NOT NULL,
                         Balance DECIMAL(15, 2) NOT NULL,
                         Credit_limit DECIMAL(15, 2) NOT NULL,
                         CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         UpdatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
