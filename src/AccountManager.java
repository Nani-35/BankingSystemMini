import java.sql.*;
import java.util.Scanner;
public class AccountManager {
    private Connection con;
    private Scanner in;
    public AccountManager(Connection con,Scanner in){
        this.con=con;
        this.in=in;
    }
    public void debit(long account_number) throws SQLException{
        in.nextLine();
        System.out.print("Enter Debit Amount: ");
        long deb = in.nextLong();
        in.nextLine();
        System.out.print("Enter PIN: ");
        String pin = in.next();
        try {
            con.setAutoCommit(false);
            if (account_number != 0) {
                PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM Accounts WHERE account_number = ? and security_pin = ? ");
                preparedStatement.setLong(1, account_number);
                preparedStatement.setString(2, pin);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    double current_balance = resultSet.getDouble("balance");
                    if (deb <= current_balance) {
                        String debit_query = "UPDATE Accounts SET balance = balance - ? WHERE account_number = ?";
                        PreparedStatement preparedStatement1 = con.prepareStatement(debit_query);
                        preparedStatement1.setDouble(1, deb);
                        preparedStatement1.setLong(2, account_number);
                        int rowsAffected = preparedStatement1.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Rs." + deb + " debited Successfully");
                            con.commit();
                            con.setAutoCommit(true);
                            return;
                        } else {
                            System.out.println("Transaction Failed!");
                            con.rollback();
                            con.setAutoCommit(true);
                        }
                    } else {
                        System.out.println("Insufficient Balance!");
                    }
                } else {
                    System.out.println("Invalid Pin!");
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        con.setAutoCommit(true);
    }
    public void credit(long account_number)throws SQLException {
        in.nextLine();
        System.out.print("Enter Amount: ");
        double amount = in.nextDouble();
        in.nextLine();
        System.out.print("Enter Security Pin: ");
        String security_pin = in.nextLine();

        try {
            con.setAutoCommit(false);
            if(account_number != 0) {
                PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM Accounts WHERE account_number = ? and security_pin = ? ");
                preparedStatement.setLong(1, account_number);
                preparedStatement.setString(2, security_pin);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String credit_query = "UPDATE Accounts SET balance = balance + ? WHERE account_number = ?";
                    PreparedStatement preparedStatement1 = con.prepareStatement(credit_query);
                    preparedStatement1.setDouble(1, amount);
                    preparedStatement1.setLong(2, account_number);
                    int rowsAffected = preparedStatement1.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Rs."+amount+" credited Successfully");
                        con.commit();
                        con.setAutoCommit(true);
                        return;
                    } else {
                        System.out.println("Transaction Failed!");
                        con.rollback();
                        con.setAutoCommit(true);
                    }
                }else{
                    System.out.println("Invalid Security Pin!");
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        con.setAutoCommit(true);
    }
    public void transfer(long sender_account_number) throws SQLException {
        in.nextLine();
        System.out.print("Enter Receiver Account Number: ");
        long receiver_account_number = in.nextLong();
        System.out.print("Enter Amount: ");
        double amount = in.nextDouble();
        in.nextLine();
        System.out.print("Enter Security Pin: ");
        String security_pin = in.nextLine();
        try{
            con.setAutoCommit(false);
            if(sender_account_number!=0 && receiver_account_number!=0){
                PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM Accounts WHERE account_number = ? AND security_pin = ? ");
                preparedStatement.setLong(1, sender_account_number);
                preparedStatement.setString(2, security_pin);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    double current_balance = resultSet.getDouble("balance");
                    if (amount<=current_balance){

                        // Write debit and credit queries
                        String debit_query = "UPDATE Accounts SET balance = balance - ? WHERE account_number = ?";
                        String credit_query = "UPDATE Accounts SET balance = balance + ? WHERE account_number = ?";

                        // Debit and Credit prepared Statements
                        PreparedStatement creditPreparedStatement = con.prepareStatement(credit_query);
                        PreparedStatement debitPreparedStatement = con.prepareStatement(debit_query);

                        // Set Values for debit and credit prepared statements
                        creditPreparedStatement.setDouble(1, amount);
                        creditPreparedStatement.setLong(2, receiver_account_number);
                        debitPreparedStatement.setDouble(1, amount);
                        debitPreparedStatement.setLong(2, sender_account_number);
                        int rowsAffected1 = debitPreparedStatement.executeUpdate();
                        int rowsAffected2 = creditPreparedStatement.executeUpdate();
                        if (rowsAffected1 > 0 && rowsAffected2 > 0) {
                            System.out.println("Transaction Successful!");
                            System.out.println("Rs."+amount+" Transferred Successfully");
                            con.commit();
                            con.setAutoCommit(true);
                            return;
                        } else {
                            System.out.println("Transaction Failed");
                            con.rollback();
                            con.setAutoCommit(true);
                        }
                    }else{
                        System.out.println("Insufficient Balance!");
                    }
                }else{
                    System.out.println("Invalid Security Pin!");
                }
            }else{
                System.out.println("Invalid account number");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        con.setAutoCommit(true);
    }

    public void getBalance(long account_number){
        in.nextLine();
        System.out.print("Enter Security Pin: ");
        String security_pin = in.nextLine();
        try{
            PreparedStatement preparedStatement = con.prepareStatement("SELECT balance FROM Accounts WHERE account_number = ? AND security_pin = ?");
            preparedStatement.setLong(1, account_number);
            preparedStatement.setString(2, security_pin);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                double balance = resultSet.getDouble("balance");
                System.out.println("Balance: "+balance);
            }else{
                System.out.println("Invalid Pin!");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
