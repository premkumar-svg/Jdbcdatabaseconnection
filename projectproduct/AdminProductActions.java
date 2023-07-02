package projectproduct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AdminProductActions {
    private static final String DB_URL = "jdbc:mysql://localhost:8888/crudapp";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123456789";

    public void admindetails() {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Admin Product Actions:");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("0. Logout");
            System.out.println("___________________________");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    viewProducts();
                    break;
                case 0:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public void addProduct() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter productID: ");
        String productID = sc.nextLine();
        System.out.print("Enter productname: ");
        String productname = sc.nextLine();
        System.out.print("Enter productprice: ");
        int price = sc.nextInt();
        System.out.print("Enter product quantity: ");
        double quantity = sc.nextDouble();

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = getConnection();

            String query = "INSERT INTO admin (productID,productname,price,quantity) VALUES (?,?,?,?)";
            ps = con.prepareStatement(query);
            ps.setString(1, productID);
            ps.setString(2, productname);
            ps.setInt(3,price);
            ps.setDouble(4, quantity);

            int rowsInserted = ps.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Product added successfully.");
                System.out.println("______________________________");
            } else {
                System.out.println("Failed to add product.");
                System.out.println("______________________________");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void viewProducts() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = getConnection();

            String query = "SELECT * FROM admin";
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                String productID = rs.getString("productID");
                String productname = rs.getString("productname");
                int price = rs.getInt("price");
                Double quantity = rs.getDouble("quantity");

                System.out.println("Product ID: " + productID);
                System.out.println("Product Name: " + productname);
                System.out.println("Product Price: " + price);
                System.out.println("Product quantity: " + quantity);
                System.out.println("________________________________");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
