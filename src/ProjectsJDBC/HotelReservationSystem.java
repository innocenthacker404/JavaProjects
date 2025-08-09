package ProjectsJDBC;

import java.sql.*;
import java.util.Scanner;


public class HotelReservationSystem {

    private static final String url = "jdbc:mysql://localhost:3306/hotel_db";

    private static final String username = "root";

    private static final String password = "database@2025";



    public static void main(String[] args) throws InterruptedException, SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println();
            System.out.println("Connection Established With Database....");
            while (true) {
                System.out.println();
                Scanner scanner = new Scanner(System.in);
                System.out.println("WELCOME TO SASARAM HOTEL RESERVATION SYSTEM!!");
                System.out.println();
                System.out.println("1. Reserve a Room");
                System.out.println("2. View Reservations");
                System.out.println("3. Get Room_Number");
                System.out.println("4. Update Reservation");
                System.out.println("5. Delete Reservation");
                System.out.println("0. For Exit");
                System.out.println();

                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        reserveRoom(connection, scanner);
                        break;
                    case 2:
                        viewReservation(connection);
                        break;
                    case 3:
                        getRoom(connection, scanner);
                        break;

                    case 4:
                        updateReservation(connection, scanner);
                        break;

                    case 5:
                        deleteReservation(connection, scanner);
                        break;

                    case 0:
                        exit();
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid input!!");
                }
            }
        } catch (SQLException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void reserveRoom(Connection connection, Scanner scanner){

            System.out.print("Enter guest name: ");
            String guestName = scanner.next();
            scanner.nextLine();
            System.out.print("Enter room number: ");
            int roomNumber = scanner.nextInt();
            System.out.print("Enter contact number: ");
            String contactNumber = scanner.next();
            String query = "INSERT INTO reservations(guest_name, room_number, contact_number)" +
                    "VALUES ('"+guestName+"', " +roomNumber+ ", '"+contactNumber+"')";
            try(Statement statement = connection.createStatement()){
                int affectedRows = statement.executeUpdate(query);

                    if (affectedRows > 0) {
                        System.out.println("Reservation Successfully!!");
                    } else {
                        System.out.println("Reservation Failed!!");
                    }

            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
    }

    public static void viewReservation(Connection connection){
        String query = "SELECT reservation_id, guest_name, room_number, contact_number, reservation_date FROM reservations";
        try(Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery(query);

            System.out.println("Current Reservations:");
            System.out.println("+----------------+-----------------------+----------------+-----------------------------+-------------------------------+");
            System.out.println("| Reservation ID | Guest Name            | Room Number    | Contact Number              | Reservation Date              |");
            System.out.println("+----------------+-----------------------+----------------+-----------------------------+-------------------------------+");

            while(rs.next()){
                int reservationId = rs.getInt("reservation_id");
                String guestName = rs.getString("guest_name");
                int roomNumber = rs.getInt("room_number");
                String contactNumber = rs.getString("contact_number");
                String reservationDate = rs.getTimestamp("reservation_date").toString();

                // Format and display the reservation data in a table-like format
                System.out.printf("| %14d | %21s | %14d  | %24s    | %25s    |\n",
                        reservationId, guestName, roomNumber, contactNumber, reservationDate);
            }
            System.out.println("+----------------------+---------------------+--------------------+---------------------+-------------------------------+");

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void getRoom(Connection connection, Scanner scanner){
        try{
            System.out.print("Enter Reservation ID: ");
            int reservationId = scanner.nextInt();
            System.out.print("Enter Guest Name: ");
            String guestName = scanner.next();

            String query = "SELECT * FROM reservations "+
                    "WHERE reservation_id = "+reservationId+" AND guest_name = '"+ guestName+"'";

            try(Statement statement = connection.createStatement()){
                ResultSet rs = statement.executeQuery(query);
                if(rs.next()){
                    int roomNumber = rs.getInt("room_number");
                    System.out.println("Room number for reservation ID '"+reservationId+"' and Guest '"+guestName+"' is '"+roomNumber+"'");
                }else{
                    System.out.println("Reservation not found for given data!!");
                }
            }
        }catch(SQLException e){
            System.out.println();
        }
    }

    public static void updateReservation(Connection connection, Scanner scanner){
        try{
            System.out.print("Enter reservation ID: ");
            int reservationId = scanner.nextInt();
            System.out.print("Enter new name: ");
            String guestName = scanner.next();
            System.out.print("Enter new room number: ");
            int roomNumber = scanner.nextInt();
            System.out.print("Enter new contact number: ");
            String contactNumber = scanner.next();

            String query = "UPDATE reservations SET guest_name = '"+ guestName +"', "+"room_number = "+roomNumber+", "+"contact_number = '"+contactNumber+"' "+" WHERE reservation_id = "+reservationId;
            try(Statement statement = connection.createStatement()){
                int rowsAffect = statement.executeUpdate(query);
                if(rowsAffect>0){
                    System.out.println("Update successfully!!");
                }else{
                    System.out.println("Update operation Failed!!");
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void deleteReservation(Connection connection, Scanner scanner){
        try{
            System.out.print("Enter reservation id: ");
            int reservationId = scanner.nextInt();

            if(!reservationExists(connection, reservationId)){
                System.out.println("Reservation Not found for the given ID.");
                return;
            }

            String query = "DELETE FROM reservations WHERE reservation_id = "+reservationId;
            try(Statement statement = connection.createStatement()){
                int affectedRows = statement.executeUpdate(query);
                if(affectedRows>0){
                    System.out.println("Delete Reservation Successfully!!");
                }else{
                    System.out.println("Deletion Failed!!");
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static boolean reservationExists(Connection connection, int reservationId){
        try{
            String query = "SELECT reservation_id FROM reservations WHERE reservation_id = "+reservationId;
            try(Statement statement = connection.createStatement()){
                ResultSet resultSet = statement.executeQuery(query);
                return resultSet.next();
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static void exit() throws InterruptedException{
        System.out.print("Exiting System");
        int i=5;
        while(i>0){
            System.out.print(". ");
            Thread.sleep(500);
            i--;
        }
        System.out.println();
        System.out.println("Thank You For Using Sasaram Hotel Reservation System!!");
    }
}