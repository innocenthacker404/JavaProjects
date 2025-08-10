package API;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;

public class Jdbc2 {
    private static final String url = "jdbc:mysql://localhost:3306/student_manage";

    private static final String username = "root";

    private static final String password = "database@2025";

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        try{
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println();
            System.out.println("Connection Established Successfully!!");
            String query = "select * from student";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                int id = rs.getInt("studentId");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String city = rs.getString("city");

                System.out.println(id+" -> "+name+" -> "+phone+" -> "+city);
            }

            con.close();
            st.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
