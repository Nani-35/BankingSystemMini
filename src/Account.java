import java.sql.*;
import java.util.Scanner;
public class Account {
    private Connection con;
    private Scanner in;
    public Account(Connection con,Scanner in){
        this.con=con;
        this.in=in;
    }
    public long openAccount(String email) {
        if (!account_exist(email)) {
            String sql = "INSERT INTO Accounts(account_number, full_name, email, balance, security_pin) VALUES(?, ?, ?, ?, ?)";
            in.nextLine();
            System.out.print("Enter Full Name: ");
            String full_name = in.nextLine();
            System.out.print("Enter Initial Amount: ");
            double amount = in.nextDouble();
            double balance=in.nextDouble();
            System.out.print("Enter Security PIN: ");
            int pin = in.nextInt();
            try{
                long account_number = generateAccountNumber();
                PreparedStatement prepare = con.prepareStatement(sql);
                prepare.setLong(1,account_number);
                prepare.setString(2,full_name);
                prepare.setString(3,email);
                prepare.setDouble(4,balance);
                prepare.setInt(5,pin);
                int c = prepare.executeUpdate();
                if(c>0){
                    return account_number;
                }else{
                    System.out.println("Account Creation Failed..");
                }

            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        throw new RuntimeException("Account Already Existed..");
    }
    private long generateAccountNumber(){
        try{
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT account_number from Accounts ORDER BY account_number DESC LIMIT 1");
            if(rs.next()){
                long account_number = rs.getLong("account_number");
                return account_number+1;
            }else{
                return 200100;
            }
        }
        catch (SQLException e){
            e.printStackTrace();;
        }
        return 200100;
    }
    public long getAccountNumber(String email){
        String sql = "select account_number from accounts where email=?";
        try{
            PreparedStatement prepare = con.prepareStatement(sql);
            prepare.setString(1,email);
            ResultSet rs = prepare.executeQuery();
            if(rs.next()){
                long account_number = rs.getLong("account_number");
                return account_number;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        throw new RuntimeException("Account Number Doesnot Exist..");
    }
    public boolean account_exist(String email){
        String sql = "select email from accounts where email=?";
        try{
            PreparedStatement prepare = con.prepareStatement(sql);
            prepare.setString(1,email);
            ResultSet rs =prepare.executeQuery();
            if(rs.next()){
                return true;
            }else{
                return false;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
