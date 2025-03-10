import java.sql.*;
import java.util.Scanner;
public class BankApp {
    private static final String url="jdbc:mysql://localhost:3306/Banking_System";
    private static final String uname="root";
    private static final String pass="";
    public static void main(String[] args) throws ClassNotFoundException,SQLException{
        try{
            Class.forName("con.mysql.jdbc.Driver");
        }
        catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        try{
            Connection con=DriverManager.getConnection(url,uname,pass);
            Scanner in =new Scanner(System.in);
            User user = new User(con,in);
            Account account = new Account(con,in);
            AccountManager accountM= new AccountManager(con,in);
            while(true) {
                System.out.println("*** WELCOME TO NAVEEN BANKING SYSTEM ***");
                System.out.println();
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                int choice = in.nextInt();
                String email;
                long account_number;
                switch (choice){
                    case 1:
                        user.register();
                        break;
                    case 2:
                        email=user.login();
                        if(email!=null){
                            System.out.println();
                            System.out.println("User Logged In!");
                            if(!account.account_exist(email)) {
                                System.out.println();
                                System.out.println("1. Open a new Bank Account");
                                System.out.println("2. Exit");
                                if (in.nextInt() == 1) {
                                    account_number = account.openAccount(email);
                                    System.out.println("Account Created Successfully");
                                    System.out.println("Your Account Number is: " + account_number);
                                } else {
                                    break;
                                }
                            }
                            account_number = account.getAccountNumber(email);
                            int choice1 = 0;
                            while (choice1 != 5) {
                                System.out.println();
                                System.out.println("1. Debit Money");
                                System.out.println("2. Credit Money");
                                System.out.println("3. Transfer Money");
                                System.out.println("4. Check Balance");
                                System.out.println("5. Log Out");
                                System.out.println("Enter your choice: ");
                                choice1 = in.nextInt();
                                switch (choice1) {
                                    case 1:
                                        accountM.debit(account_number);
                                        break;
                                    case 2:
                                        accountM.credit(account_number);
                                        break;
                                    case 3:
                                        accountM.transfer(account_number);
                                        break;
                                    case 4:
                                        accountM.getBalance(account_number);
                                        break;
                                    case 5:
                                        break;
                                    default:
                                        System.out.println("Choose a Valid Option!!!");
                                        break;
                                }
                            }

                        }
                        else{
                            throw new RuntimeException("Incorrect Email or Password");
                        }
                    case 3:
                        System.out.println("THANK YOU!!!");
                        return;
                    default:
                        System.out.println(" Enter a Valid Choice ");
                        break;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
