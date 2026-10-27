import java.sql.*;

public class ATM {

    public Account login(int accNo, int pin) {
        String sql = "SELECT name, balance FROM accounts WHERE acc_no=? AND pin=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, accNo);
            ps.setInt(2, pin);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return new Account(accNo, rs.getString("name"), rs.getDouble("balance"));
        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
        }
        return null;
    }

    public double checkBalance(int accNo) {
        String sql = "SELECT balance FROM accounts WHERE acc_no=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, accNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble("balance");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return 0;
    }

    public void deposit(int accNo, double amount) {
        if (amount <= 0) { System.out.println("Invalid amount."); return; }
        updateBalance(accNo, amount, "DEPOSIT");
    }

    public void withdraw(int accNo, double amount) {
        if (amount <= 0) { System.out.println("Invalid amount."); return; }
        if (amount > checkBalance(accNo)) {
            System.out.println("Insufficient balance!");
            return;
        }
        updateBalance(accNo, -amount, "WITHDRAW");
    }

    private void updateBalance(int accNo, double change, String type) {
        String upd = "UPDATE accounts SET balance = balance + ? WHERE acc_no=?";
        String ins = "INSERT INTO transactions(acc_no, type, amount) VALUES (?,?,?)";
        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);
            try (PreparedStatement p1 = con.prepareStatement(upd);
                 PreparedStatement p2 = con.prepareStatement(ins)) {
                p1.setDouble(1, change);
                p1.setInt(2, accNo);
                p1.executeUpdate();

                p2.setInt(1, accNo);
                p2.setString(2, type);
                p2.setDouble(3, Math.abs(change));
                p2.executeUpdate();

                con.commit();
                System.out.println(type + " successful. New balance: " + checkBalance(accNo));
            } catch (SQLException e) {
                con.rollback();
                System.out.println("Transaction failed, rolled back.");
            }
        } catch (SQLException e) {
            System.out.println("DB error: " + e.getMessage());
        }
    }

    public void miniStatement(int accNo) {
        String sql = "SELECT type, amount, txn_time FROM transactions " +
                     "WHERE acc_no=? ORDER BY id DESC LIMIT 5";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, accNo);
            ResultSet rs = ps.executeQuery();
            System.out.println("\n--- Mini Statement ---");
            boolean any = false;
            while (rs.next()) {
                any = true;
                System.out.println(rs.getTimestamp("txn_time") + "  " +
                                   rs.getString("type") + "  " + rs.getDouble("amount"));
            }
            if (!any) System.out.println("No transactions yet.");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}