CREATE TABLE Transactions (
                              TransactionID BINARY(16) PRIMARY KEY,
                              AccountID BINARY(16) NOT NULL,
                              TransactionType ENUM('deposit', 'withdraw', 'transfer') NOT NULL,
                              Amount DECIMAL(15, 2) NOT NULL,
                              DestinationAccountID BINARY(16),
                              TransactionDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              FOREIGN KEY (AccountID) REFERENCES Accounts(AccountID),
                              FOREIGN KEY (DestinationAccountID) REFERENCES Accounts(AccountID)
);
