import java.sql.*;
import java.util.Scanner;
public class User {
    private Connection con;
    private Scanner in;
    public User(Connection con,Scanner in){
        this.con=con;
        this.in=in;
    }
    public void register(){
        in.nextLine();
        System.out.print("Enter Full Name: ");
        String full_name=in.nextLine();
        System.out.print("Enter Your Email Id: ");
        String email=in.nextLine();
        System.out.print("Enter your Password: ");
        String pass=in.nextLine();
        if(user_exists(email)){
            System.out.println("User already exists");
            return;
        }
        String sql="insert into user(full_name,email,password) values(?,?,?)";
        try(PreparedStatement registration=con.prepareStatement(sql)) {
            registration.setString(1,full_name);
            registration.setString(2,email);
            registration.setString(3,pass);
            int affectedRows = registration.executeUpdate();
            if(affectedRows>0){
                System.out.println("Registration is Successfully!!!");
            }else{
                System.out.println("Registration Failed...");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public String login(){
        in.nextLine();
        System.out.print("Enter Email Id: ");
        String email=in.nextLine();
        System.out.print("Enter password: ");
        String pass=in.nextLine();
        String sql = "Select * from user where email=? and password=?";
        try{
            PreparedStatement log=con.prepareStatement(sql);
            log.setString(1,email);
            log.setString(2,pass);
            ResultSet rs = log.executeQuery();
            if(rs.next()){
                return email;
            }
            else{
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
    public boolean user_exists(String email){
        String sql = "select * from user where email=?";
        try{
            PreparedStatement exist = con.prepareStatement(sql);
            exist.setString(1,"email");
            ResultSet rs = exist.executeQuery();
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
