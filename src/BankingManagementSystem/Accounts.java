package BankingManagementSystem;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.ResultSet;

public class Accounts {
    Connection connection;
    Scanner scanner;

    public Accounts(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }


    public long open_account(String email){
        if(!account_exists(email)){
            String query = "INSERT INTO accounts (account_number, full_name, email, balance, security_pin) VALUES(?,?,?,?,?)";
            System.out.println("Enter name: ");
            String fullName = scanner.next();
            System.out.println("Enter initial amount: ");
            double initialAmount = scanner.nextDouble();
            System.out.println("Enter security pin: ");
            String security_pin = scanner.next();
            try{
                long account_number = generate_account();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setLong(1, account_number);
                preparedStatement.setString(2, fullName);
                preparedStatement.setString(3, email);
                preparedStatement.setDouble(4, initialAmount);
                preparedStatement.setString(5, security_pin);
                int rowAffected = preparedStatement.executeUpdate();
                if(rowAffected > 0){
                    return account_number;
                }else{
                    throw new RuntimeException("Account creation failed!!");
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
        throw new RuntimeException("Account already exists!!");
    }


    public long generate_account(){
        try {
            Statement statement = connection.createStatement();
            String generate_query = "SELECT account_number FROM accounts ORDER BY account_number DESC LIMIT 1";
            ResultSet resultSet = statement.executeQuery(generate_query);
            if(resultSet.next()){
                long last_accountNumber = resultSet.getLong("account_number");
                return last_accountNumber+1;
            }else{
                return 10000100;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 10000100;
    }

    public long getAccountNumber(String email){
        String query = "SELECT account_number FROM accounts WHERE email=?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getLong("account_number");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        throw new RuntimeException("Account number doesn't exists!!");
    }


    public boolean account_exists(String email){
        String query = "SELECT account_number FROM accounts WHERE email = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
}