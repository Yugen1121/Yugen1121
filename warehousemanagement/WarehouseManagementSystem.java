// Main application class
package warehousemanagement;

import java.sql.*;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Logger;
import java.util.logging.Level;

public class WarehouseManagementSystem {
    private static final Logger LOGGER = Logger.getLogger(WarehouseManagementSystem.class.getName());
    private static final String DB_URL = "jdbc:sqlite:warehouse.db";
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        try {
            initializeDatabase();
            displayMainMenu();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error: " + e.getMessage(), e);
            System.out.println("A database error occurred. Please check the logs for details.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error: " + e.getMessage(), e);
            System.out.println("An unexpected error occurred. Please check the logs for details.");
        } finally {
            scanner.close();
        }
    }
    
    private static void initializeDatabase() throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            
            // Create Inventory table
            stmt.execute("CREATE TABLE IF NOT EXISTS inventory (" +
                         "item_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                         "item_name TEXT NOT NULL," +
                         "quantity INTEGER NOT NULL," +
                         "storage_location TEXT NOT NULL)");
            
            // Create Orders table
            stmt.execute("CREATE TABLE IF NOT EXISTS orders (" +
                         "order_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                         "order_date TEXT NOT NULL," +
                         "customer_name TEXT NOT NULL," +
                         "status TEXT NOT NULL)");
            
            // Create Shipments table
            stmt.execute("CREATE TABLE IF NOT EXISTS shipments (" +
                         "shipment_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                         "destination TEXT NOT NULL," +
                         "shipment_date TEXT NOT NULL," +
                         "status TEXT NOT NULL)");
            
            LOGGER.info("Database initialized successfully");
        }
    }
    
    private static void displayMainMenu() {
        boolean exit = false;
        
        while (!exit) {
            System.out.println("\n===== WAREHOUSE MANAGEMENT SYSTEM =====");
            System.out.println("1. Manage Inventory");
            System.out.println("2. Process Orders");
            System.out.println("3. Track Shipments");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                
                switch (choice) {
                    case 1:
                        manageInventory();
                        break;
                    case 2:
                        processOrders();
                        break;
                    case 3:
                        trackShipments();
                        break;
                    case 0:
                        exit = true;
                        System.out.println("Exiting the system. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Database error: " + e.getMessage(), e);
                System.out.println("A database error occurred. Please check the logs for details.");
            }
        }
    }
    
    // ===== INVENTORY MANAGEMENT =====
    private static void manageInventory() throws SQLException {
        boolean back = false;
        
        while (!back) {
            System.out.println("\n===== INVENTORY MANAGEMENT =====");
            System.out.println("1. Add New Item");
            System.out.println("2. View All Items");
            System.out.println("3. Search Item");
            System.out.println("4. Update Item");
            System.out.println("5. Delete Item");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                
                switch (choice) {
                    case 1:
                        addInventoryItem();
                        break;
                    case 2:
                        viewAllInventoryItems();
                        break;
                    case 3:
                        searchInventoryItem();
                        break;
                    case 4:
                        updateInventoryItem();
                        break;
                    case 5:
                        deleteInventoryItem();
                        break;
                    case 0:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
    
    private static void addInventoryItem() throws SQLException {
        System.out.println("\n----- Add New Inventory Item -----");
        
        System.out.print("Enter item name: ");
        String itemName = scanner.nextLine().trim();
        
        int quantity = getValidIntInput("Enter quantity: ");
        
        System.out.print("Enter storage location: ");
        String storageLocation = scanner.nextLine().trim();
        
        if (itemName.isEmpty() || storageLocation.isEmpty()) {
            System.out.println("Item name and storage location cannot be empty.");
            return;
        }
        
        String sql = "INSERT INTO inventory (item_name, quantity, storage_location) VALUES (?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, itemName);
            pstmt.setInt(2, quantity);
            pstmt.setString(3, storageLocation);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Inventory item added successfully!");
            } else {
                System.out.println("Failed to add inventory item.");
            }
        }
    }
    
    private static void viewAllInventoryItems() throws SQLException {
        System.out.println("\n----- All Inventory Items -----");
        
        String sql = "SELECT * FROM inventory";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            boolean hasItems = false;
            
            System.out.println("ID | Item Name | Quantity | Storage Location");
            System.out.println("------------------------------------------");
            
            while (rs.next()) {
                hasItems = true;
                System.out.printf("%-3d | %-9s | %-8d | %s%n",
                                 rs.getInt("item_id"),
                                 rs.getString("item_name"),
                                 rs.getInt("quantity"),
                                 rs.getString("storage_location"));
            }
            
            if (!hasItems) {
                System.out.println("No inventory items found.");
            }
        }
    }
    
    private static void searchInventoryItem() throws SQLException {
        System.out.println("\n----- Search Inventory Item -----");
        System.out.println("1. Search by ID");
        System.out.println("2. Search by Name");
        System.out.print("Enter your choice: ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            
            String sql;
            String searchTerm;
            
            switch (choice) {
                case 1:
                    int itemId = getValidIntInput("Enter item ID: ");
                    sql = "SELECT * FROM inventory WHERE item_id = ?";
                    
                    try (Connection conn = DriverManager.getConnection(DB_URL);
                         PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        
                        pstmt.setInt(1, itemId);
                        
                        try (ResultSet rs = pstmt.executeQuery()) {
                            displayInventorySearchResults(rs);
                        }
                    }
                    break;
                    
                case 2:
                    System.out.print("Enter item name: ");
                    searchTerm = scanner.nextLine().trim();
                    sql = "SELECT * FROM inventory WHERE item_name LIKE ?";
                    
                    try (Connection conn = DriverManager.getConnection(DB_URL);
                         PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        
                        pstmt.setString(1, "%" + searchTerm + "%");
                        
                        try (ResultSet rs = pstmt.executeQuery()) {
                            displayInventorySearchResults(rs);
                        }
                    }
                    break;
                    
                default:
                    System.out.println("Invalid option.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }
    
    private static void displayInventorySearchResults(ResultSet rs) throws SQLException {
        boolean found = false;
        
        System.out.println("ID | Item Name | Quantity | Storage Location");
        System.out.println("------------------------------------------");
        
        while (rs.next()) {
            found = true;
            System.out.printf("%-3d | %-9s | %-8d | %s%n",
                             rs.getInt("item_id"),
                             rs.getString("item_name"),
                             rs.getInt("quantity"),
                             rs.getString("storage_location"));
        }
        
        if (!found) {
            System.out.println("No matching inventory items found.");
        }
    }
    
    private static void updateInventoryItem() throws SQLException {
        System.out.println("\n----- Update Inventory Item -----");
        
        int itemId = getValidIntInput("Enter item ID to update: ");
        
        String checkSql = "SELECT * FROM inventory WHERE item_id = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(checkSql)) {
            
            pstmt.setInt(1, itemId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Current details:");
                    System.out.printf("Item Name: %s%n", rs.getString("item_name"));
                    System.out.printf("Quantity: %d%n", rs.getInt("quantity"));
                    System.out.printf("Storage Location: %s%n", rs.getString("storage_location"));
                    
                    System.out.println("\nEnter new details (leave blank to keep current value):");
                    
                    System.out.print("Enter new item name: ");
                    String newItemName = scanner.nextLine().trim();
                    if (newItemName.isEmpty()) {
                        newItemName = rs.getString("item_name");
                    }
                    
                    System.out.print("Enter new quantity: ");
                    String quantityStr = scanner.nextLine().trim();
                    int newQuantity = quantityStr.isEmpty() ? rs.getInt("quantity") : Integer.parseInt(quantityStr);
                    
                    System.out.print("Enter new storage location: ");
                    String newLocation = scanner.nextLine().trim();
                    if (newLocation.isEmpty()) {
                        newLocation = rs.getString("storage_location");
                    }
                    
                    String updateSql = "UPDATE inventory SET item_name = ?, quantity = ?, storage_location = ? WHERE item_id = ?";
                    
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setString(1, newItemName);
                        updateStmt.setInt(2, newQuantity);
                        updateStmt.setString(3, newLocation);
                        updateStmt.setInt(4, itemId);
                        
                        int rowsAffected = updateStmt.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Inventory item updated successfully!");
                        } else {
                            System.out.println("Failed to update inventory item.");
                        }
                    }
                } else {
                    System.out.println("No inventory item found with ID: " + itemId);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid quantity. Update canceled.");
            }
        }
    }
    
    private static void deleteInventoryItem() throws SQLException {
        System.out.println("\n----- Delete Inventory Item -----");
        
        int itemId = getValidIntInput("Enter item ID to delete: ");
        
        String checkSql = "SELECT * FROM inventory WHERE item_id = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(checkSql)) {
            
            pstmt.setInt(1, itemId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Item to delete:");
                    System.out.printf("ID: %d%n", rs.getInt("item_id"));
                    System.out.printf("Item Name: %s%n", rs.getString("item_name"));
                    System.out.printf("Quantity: %d%n", rs.getInt("quantity"));
                    System.out.printf("Storage Location: %s%n", rs.getString("storage_location"));
                    
                    System.out.print("Are you sure you want to delete this item? (y/n): ");
                    String confirmation = scanner.nextLine().trim().toLowerCase();
                    
                    if (confirmation.equals("y")) {
                        String deleteSql = "DELETE FROM inventory WHERE item_id = ?";
                        
                        try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                            deleteStmt.setInt(1, itemId);
                            
                            int rowsAffected = deleteStmt.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("Inventory item deleted successfully!");
                            } else {
                                System.out.println("Failed to delete inventory item.");
                            }
                        }
                    } else {
                        System.out.println("Deletion canceled.");
                    }
                } else {
                    System.out.println("No inventory item found with ID: " + itemId);
                }
            }
        }
    }
    
    // ===== ORDER PROCESSING =====
    private static void processOrders() throws SQLException {
        boolean back = false;
        
        while (!back) {
            System.out.println("\n===== ORDER PROCESSING =====");
            System.out.println("1. Create New Order");
            System.out.println("2. View All Orders");
            System.out.println("3. Search Order");
            System.out.println("4. Update Order");
            System.out.println("5. Delete Order");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                
                switch (choice) {
                    case 1:
                        createOrder();
                        break;
                    case 2:
                        viewAllOrders();
                        break;
                    case 3:
                        searchOrder();
                        break;
                    case 4:
                        updateOrder();
                        break;
                    case 5:
                        deleteOrder();
                        break;
                    case 0:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
    
    private static void createOrder() throws SQLException {
        System.out.println("\n----- Create New Order -----");
        
        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine().trim();
        
        if (customerName.isEmpty()) {
            System.out.println("Customer name cannot be empty.");
            return;
        }
        
        LocalDate orderDate = LocalDate.now();
        String status = "Pending";
        
        String sql = "INSERT INTO orders (order_date, customer_name, status) VALUES (?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, orderDate.toString());
            pstmt.setString(2, customerName);
            pstmt.setString(3, status);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Order created successfully!");
            } else {
                System.out.println("Failed to create order.");
            }
        }
    }
    
    private static void viewAllOrders() throws SQLException {
        System.out.println("\n----- All Orders -----");
        
        String sql = "SELECT * FROM orders";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            boolean hasOrders = false;
            
            System.out.println("ID | Order Date | Customer Name | Status");
            System.out.println("------------------------------------------");
            
            while (rs.next()) {
                hasOrders = true;
                System.out.printf("%-3d | %-10s | %-13s | %s%n",
                                 rs.getInt("order_id"),
                                 rs.getString("order_date"),
                                 rs.getString("customer_name"),
                                 rs.getString("status"));
            }
            
            if (!hasOrders) {
                System.out.println("No orders found.");
            }
        }
    }
    
    private static void searchOrder() throws SQLException {
        System.out.println("\n----- Search Order -----");
        System.out.println("1. Search by ID");
        System.out.println("2. Search by Customer Name");
        System.out.print("Enter your choice: ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            
            String sql;
            String searchTerm;
            
            switch (choice) {
                case 1:
                    int orderId = getValidIntInput("Enter order ID: ");
                    sql = "SELECT * FROM orders WHERE order_id = ?";
                    
                    try (Connection conn = DriverManager.getConnection(DB_URL);
                         PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        
                        pstmt.setInt(1, orderId);
                        
                        try (ResultSet rs = pstmt.executeQuery()) {
                            displayOrderSearchResults(rs);
                        }
                    }
                    break;
                    
                case 2:
                    System.out.print("Enter customer name: ");
                    searchTerm = scanner.nextLine().trim();
                    sql = "SELECT * FROM orders WHERE customer_name LIKE ?";
                    
                    try (Connection conn = DriverManager.getConnection(DB_URL);
                         PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        
                        pstmt.setString(1, "%" + searchTerm + "%");
                        
                        try (ResultSet rs = pstmt.executeQuery()) {
                            displayOrderSearchResults(rs);
                        }
                    }
                    break;
                    
                default:
                    System.out.println("Invalid option.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }
    
    private static void displayOrderSearchResults(ResultSet rs) throws SQLException {
        boolean found = false;
        
        System.out.println("ID | Order Date | Customer Name | Status");
        System.out.println("------------------------------------------");
        
        while (rs.next()) {
            found = true;
            System.out.printf("%-3d | %-10s | %-13s | %s%n",
                             rs.getInt("order_id"),
                             rs.getString("order_date"),
                             rs.getString("customer_name"),
                             rs.getString("status"));
        }
        
        if (!found) {
            System.out.println("No matching orders found.");
        }
    }
    
    private static void updateOrder() throws SQLException {
        System.out.println("\n----- Update Order -----");
        
        int orderId = getValidIntInput("Enter order ID to update: ");
        
        String checkSql = "SELECT * FROM orders WHERE order_id = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(checkSql)) {
            
            pstmt.setInt(1, orderId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Current details:");
                    System.out.printf("Order Date: %s%n", rs.getString("order_date"));
                    System.out.printf("Customer Name: %s%n", rs.getString("customer_name"));
                    System.out.printf("Status: %s%n", rs.getString("status"));
                    
                    System.out.println("\nEnter new details (leave blank to keep current value):");
                    
                    System.out.print("Enter new customer name: ");
                    String newCustomerName = scanner.nextLine().trim();
                    if (newCustomerName.isEmpty()) {
                        newCustomerName = rs.getString("customer_name");
                    }
                    
                    System.out.println("Available statuses: Pending, Processing, Shipped, Delivered, Cancelled");
                    System.out.print("Enter new status: ");
                    String newStatus = scanner.nextLine().trim();
                    if (newStatus.isEmpty()) {
                        newStatus = rs.getString("status");
                    }
                    
                    String updateSql = "UPDATE orders SET customer_name = ?, status = ? WHERE order_id = ?";
                    
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setString(1, newCustomerName);
                        updateStmt.setString(2, newStatus);
                        updateStmt.setInt(3, orderId);
                        
                        int rowsAffected = updateStmt.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Order updated successfully!");
                        } else {
                            System.out.println("Failed to update order.");
                        }
                    }
                } else {
                    System.out.println("No order found with ID: " + orderId);
                }
            }
        }
    }
    
    private static void deleteOrder() throws SQLException {
        System.out.println("\n----- Delete Order -----");
        
        int orderId = getValidIntInput("Enter order ID to delete: ");
        
        String checkSql = "SELECT * FROM orders WHERE order_id = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(checkSql)) {
            
            pstmt.setInt(1, orderId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Order to delete:");
                    System.out.printf("ID: %d%n", rs.getInt("order_id"));
                    System.out.printf("Order Date: %s%n", rs.getString("order_date"));
                    System.out.printf("Customer Name: %s%n", rs.getString("customer_name"));
                    System.out.printf("Status: %s%n", rs.getString("status"));
                    
                    System.out.print("Are you sure you want to delete this order? (y/n): ");
                    String confirmation = scanner.nextLine().trim().toLowerCase();
                    
                    if (confirmation.equals("y")) {
                        String deleteSql = "DELETE FROM orders WHERE order_id = ?";
                        
                        try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                            deleteStmt.setInt(1, orderId);
                            
                            int rowsAffected = deleteStmt.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("Order deleted successfully!");
                            } else {
                                System.out.println("Failed to delete order.");
                            }
                        }
                    } else {
                        System.out.println("Deletion canceled.");
                    }
                } else {
                    System.out.println("No order found with ID: " + orderId);
                }
            }
        }
    }
    
    // ===== SHIPMENT TRACKING =====
    private static void trackShipments() throws SQLException {
        boolean back = false;
        
        while (!back) {
            System.out.println("\n===== SHIPMENT TRACKING =====");
            System.out.println("1. Create New Shipment");
            System.out.println("2. View All Shipments");
            System.out.println("3. Search Shipment");
            System.out.println("4. Update Shipment");
            System.out.println("5. Delete Shipment");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                
                switch (choice) {
                    case 1:
                        createShipment();
                        break;
                    case 2:
                        viewAllShipments();
                        break;
                    case 3:
                        searchShipment();
                        break;
                    case 4:
                        updateShipment();
                        break;
                    case 5:
                        deleteShipment();
                        break;
                    case 0:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
    
    private static void createShipment() throws SQLException {
        System.out.println("\n----- Create New Shipment -----");
        
        System.out.print("Enter destination: ");
        String destination = scanner.nextLine().trim();
        
        if (destination.isEmpty()) {
            System.out.println("Destination cannot be empty.");
            return;
        }
        
        LocalDate shipmentDate = LocalDate.now();
        String status = "Pending";
        
        String sql = "INSERT INTO shipments (destination, shipment_date, status) VALUES (?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, destination);
            pstmt.setString(2, shipmentDate.toString());
            pstmt.setString(3, status);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Shipment created successfully!");
            } else {
                System.out.println("Failed to create shipment.");
            }
        }
    }
    
    private static void viewAllShipments() throws SQLException {
        System.out.println("\n----- All Shipments -----");
        
        String sql = "SELECT * FROM shipments";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            boolean hasShipments = false;
            
            System.out.println("ID | Destination | Shipment Date | Status");
            System.out.println("------------------------------------------");
            
            while (rs.next()) {
                hasShipments = true;
                System.out.printf("%-3d | %-11s | %-13s | %s%n",
                                 rs.getInt("shipment_id"),
                                 rs.getString("destination"),
                                 rs.getString("shipment_date"),
                                 rs.getString("status"));
            }
            
            if (!hasShipments) {
                System.out.println("No shipments found.");
            }
        }
    }
    
    private static void searchShipment() throws SQLException {
        System.out.println("\n----- Search Shipment -----");
        System.out.println("1. Search by ID");
        System.out.println("2. Search by Destination");
        System.out.println("3. Search by Status");
        System.out.print("Enter your choice: ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            
            String sql;
            String searchTerm;
            
            switch (choice) {
                case 1:
                    int shipmentId = getValidIntInput("Enter shipment ID: ");
                    sql = "SELECT * FROM shipments WHERE shipment_id = ?";
                    
                    try (Connection conn = DriverManager.getConnection(DB_URL);
                         PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        
                        pstmt.setInt(1, shipmentId);
                        
                        try (ResultSet rs = pstmt.executeQuery()) {
                            displayShipmentSearchResults(rs);
                        }
                    }
                    break;
                    
                case 2:
                    System.out.print("Enter destination: ");
                    searchTerm = scanner.nextLine().trim();
                    sql = "SELECT * FROM shipments WHERE destination LIKE ?";
                    
                    try (Connection conn = DriverManager.getConnection(DB_URL);
                         PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        
                        pstmt.setString(1, "%" + searchTerm + "%");
                        
                        try (ResultSet rs = pstmt.executeQuery()) {
                            displayShipmentSearchResults(rs);
                        }
                    }
                    break;
                    
                case 3:
                    System.out.print("Enter status: ");
                    searchTerm = scanner.nextLine().trim();
                    sql = "SELECT * FROM shipments WHERE status LIKE ?";
                    
                    try (Connection conn = DriverManager.getConnection(DB_URL);
                         PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        
                        pstmt.setString(1, "%" + searchTerm + "%");
                        
                        try (ResultSet rs = pstmt.executeQuery()) {
                            displayShipmentSearchResults(rs);
                        }
                    }
                    break;
                    
                default:
                    System.out.println("Invalid option.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }
    
    private static void displayShipmentSearchResults(ResultSet rs) throws SQLException {
        boolean found = false;
        
        System.out.println("ID | Destination | Shipment Date | Status");
        System.out.println("------------------------------------------");
        
        while (rs.next()) {
            found = true;
            System.out.printf("%-3d | %-11s | %-13s | %s%n",
                             rs.getInt("shipment_id"),
                             rs.getString("destination"),
                             rs.getString("shipment_date"),
                             rs.getString("status"));
        }
        
        if (!found) {
            System.out.println("No matching shipments found.");
        }
    }
    
    private static void updateShipment() throws SQLException {
        System.out.println("\n----- Update Shipment -----");
        
        int shipmentId = getValidIntInput("Enter shipment ID to update: ");
        
        String checkSql = "SELECT * FROM shipments WHERE shipment_id = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(checkSql)) {
            
            pstmt.setInt(1, shipmentId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Current details:");
                    System.out.printf("Destination: %s%n", rs.getString("destination"));
                    System.out.printf("Shipment Date: %s%n", rs.getString("shipment_date"));
                    System.out.printf("Status: %s%n", rs.getString("status"));
                    
                    System.out.println("\nEnter new details (leave blank to keep current value):");
                    
                    System.out.print("Enter new destination: ");
                    String newDestination = scanner.nextLine().trim();
                    if (newDestination.isEmpty()) {
                        newDestination = rs.getString("destination");
                    }
                    
                    System.out.println("Available statuses: Pending, In Transit, Delivered, Delayed, Returned");
                    System.out.print("Enter new status: ");
                    String newStatus = scanner.nextLine().trim();
                    if (newStatus.isEmpty()) {
                        newStatus = rs.getString("status");
                    }
                    
                    String updateSql = "UPDATE shipments SET destination = ?, status = ? WHERE shipment_id = ?";
                    
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setString(1, newDestination);
                        updateStmt.setString(2, newStatus);
                        updateStmt.setInt(3, shipmentId);
                        
                        int rowsAffected = updateStmt.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Shipment updated successfully!");
                        } else {
                            System.out.println("Failed to update shipment.");
                        }
                    }
                } else {
                    System.out.println("No shipment found with ID: " + shipmentId);
                }
            }
        }
    }
    
    private static void deleteShipment() throws SQLException {
        System.out.println("\n----- Delete Shipment -----");
        
        int shipmentId = getValidIntInput("Enter shipment ID to delete: ");
        
        String checkSql = "SELECT * FROM shipments WHERE shipment_id = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(checkSql)) {
            
            pstmt.setInt(1, shipmentId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Shipment to delete:");
                    System.out.printf("ID: %d%n", rs.getInt("shipment_id"));
                    System.out.printf("Destination: %s%n", rs.getString("destination"));
                    System.out.printf("Shipment Date: %s%n", rs.getString("shipment_date"));
                    System.out.printf("Status: %s%n", rs.getString("status"));
                    
                    System.out.print("Are you sure you want to delete this shipment? (y/n): ");
                    String confirmation = scanner.nextLine().trim().toLowerCase();
                    
                    if (confirmation.equals("y")) {
                        String deleteSql = "DELETE FROM shipments WHERE shipment_id = ?";
                        
                        try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                            deleteStmt.setInt(1, shipmentId);
                            
                            int rowsAffected = deleteStmt.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("Shipment deleted successfully!");
                            } else {
                                System.out.println("Failed to delete shipment.");
                            }
                        }
                    } else {
                        System.out.println("Deletion canceled.");
                    }
                } else {
                    System.out.println("No shipment found with ID: " + shipmentId);
                }
            }
        }
    }
    
    // ===== HELPER METHODS =====
    private static int getValidIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
    
    private static LocalDate getValidDateInput(String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        while (true) {
            System.out.print(prompt + " (YYYY-MM-DD): ");
            String input = scanner.nextLine().trim();
            
            try {
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD format.");
            }
        }
    }
}