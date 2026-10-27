# ATM Transaction Simulator (Java + JDBC + MySQL)

A menu-driven Java console application that simulates basic ATM operations. All account and transaction data is stored permanently in MySQL through JDBC. Built as an OOPS mini project. Only fictional data is used.

## Features
- Login with account number and PIN
- Check balance
- Deposit and withdraw money
- Insufficient balance check
- Mini statement (last 5 transactions)
- Balance update and transaction record saved together using `commit()` and `rollback()`

## Tech Stack
- Java (JDK 8 or higher)
- MySQL Server
- JDBC (MySQL Connector/J)

## Project Structure
| File | Purpose |
|---|---|
| `Main.java` | Menu, user input and output |
| `ATM.java` | All ATM operations and SQL logic |
| `Account.java` | Model class holding one account's data |
| `DBConnection.java` | Opens the MySQL connection |
| `schema.sql` | Creates the database, tables and sample accounts |

## OOP Concepts Used
- Classes and objects
- Encapsulation (private fields with getters in `Account`)
- Abstraction (`Main` calls `atm.withdraw()` without seeing the SQL)
- Static method (`DBConnection.getConnection()`)
- Exception handling (try-catch for `SQLException`)

## Database Design
- `accounts` (acc_no, name, pin, balance)
- `transactions` (id, acc_no, type, amount, txn_time)

## How to Run
1. Install the JDK and MySQL Server, and start MySQL.
2. Run `schema.sql` in MySQL.
3. Download the MySQL Connector/J jar and place it in the project folder.
4. In `DBConnection.java`, set your MySQL username and password.
5. Compile and run:

**Windows**
```
javac -cp ".;mysql-connector-j-x.x.x.jar" *.java
java -cp ".;mysql-connector-j-x.x.x.jar" Main
```

**Linux / macOS**
```
javac -cp ".:mysql-connector-j-x.x.x.jar" *.java
java -cp ".:mysql-connector-j-x.x.x.jar" Main
```

Replace `x.x.x` with your jar's version.

## Test Account
| Account No | PIN |
|---|---|
| 1001 | 1234 |

## Sample Output
Add your screenshots here (login, balance, deposit, withdraw, mini statement).

## Limitations
- Console only, no graphical interface
- No real ATM hardware, input is typed in
- DB password is written in code
- No PIN change or fund transfer

## Future Scope
PIN change, fund transfer, account lock after 3 wrong PINs, GUI using Swing.

## Author
[Your Name] | [Department] | [College]
