package API;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Jdbc1 {
    private static final String url = "jdbc:mysql://localhost:3306/student_manage";
    private static final String uname = "root";
    private static final String pass = "database@2025";

    public static void main(String[] args){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        try {
            Scanner sc = new Scanner(System.in);
            Connection con = DriverManager.getConnection(url, uname, pass);
            String query = "INSERT INTO student(name, phone, city) VALUES (?,?,?)";
            String query2 = "DELETE FROM student WHERE studentId = ?";
            PreparedStatement st = con.prepareStatement(query);
            PreparedStatement st2 = con.prepareStatement(query2);
            System.out.print("Enter id: ");
            int id = sc.nextInt();
            st2.setInt(1, id);
            int rowAffected = st2.executeUpdate();
            if(rowAffected > 0){
                System.out.println(rowAffected+" row(s) affected!");
            }else{
                System.out.println("Deletion Failed!!");
            }

            while(true){
                System.out.print("Enter name: ");
                String name = sc.next();
                System.out.print("Enter phone: ");
                String phone = sc.next();
                System.out.print("Enter city: ");
                String city = sc.next();
                st.setString(1, name);
                st.setString(2, phone);
                st.setString(3, city);
                st.addBatch();

                System.out.print("Do you want to insert more data ?(Y/N): ");
                String choice = sc.next();

                if(choice.equalsIgnoreCase("N")){
                    System.out.println("Insertion Done Successfully...");
                    break;
                }else{
                    System.out.println("Enter new data below---->");
                }
            }
            int[] arr = st.executeBatch();

            for(int i=0; i<arr.length; i++){
                if(arr[i]==0){
                    System.out.println("Query "+i+" not executed Successfully..!");
                }else{
                    System.out.println("All indices executed Successfully..!");
                }
            }

            con.close();
            st.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
