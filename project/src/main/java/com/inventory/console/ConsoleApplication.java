package com.inventory.console;

import com.inventory.model.*;
import com.inventory.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@Profile("console")
public class ConsoleApplication implements CommandLineRunner {
    
    private final ProductService productService;
    private final VendorService vendorService;
    private final CustomerService customerService;
    private final PurchaseOrderService purchaseOrderService;
    private final SalesInvoiceService salesInvoiceService;
    
    private final Scanner scanner = new Scanner(System.in);
    
    @Autowired
    public ConsoleApplication(ProductService productService, 
                              VendorService vendorService,
                              CustomerService customerService,
                              PurchaseOrderService purchaseOrderService,
                              SalesInvoiceService salesInvoiceService) {
        this.productService = productService;
        this.vendorService = vendorService;
        this.customerService = customerService;
        this.purchaseOrderService = purchaseOrderService;
        this.salesInvoiceService = salesInvoiceService;
    }
    
    @Override
    public void run(String... args) {
        boolean exit = false;
        
        System.out.println("===============================================");
        System.out.println("   INVENTORY MANAGEMENT SYSTEM - CONSOLE MODE  ");
        System.out.println("===============================================");
        
        while (!exit) {
            printMainMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    handleProductManagement();
                    break;
                case 2:
                    handlePurchaseOrderManagement();
                    break;
                case 3:
                    handleVendorManagement();
                    break;
                case 4:
                    handleCustomerManagement();
                    break;
                case 5:
                    handleSalesManagement();
                    break;
                case 6:
                    handleReportGeneration();
                    break;
                case 7:
                    exit = true;
                    System.out.println("Exiting the Inventory Management System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void printMainMenu() {
        System.out.println("\nMAIN MENU");
        System.out.println("1. Product Management");
        System.out.println("2. Purchase Order Management");
        System.out.println("3. Vendor Management");
        System.out.println("4. Customer Management");
        System.out.println("5. Sales Management");
        System.out.println("6. Reports");
        System.out.println("7. Exit");
    }
    
    private void handleProductManagement() {
        boolean back = false;
        
        while (!back) {
            System.out.println("\nPRODUCT MANAGEMENT");
            System.out.println("1. List All Products");
            System.out.println("2. Add New Product");
            System.out.println("3. Update Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Search Products");
            System.out.println("6. View Low Stock Products");
            System.out.println("7. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    System.out.println("\nList of All Products:");
                    productService.getAllProducts().forEach(this::displayProduct);
                    break;
                case 2:
                    // Add new product logic
                    System.out.println("Add New Product functionality will be implemented here.");
                    break;
                case 3:
                    // Update product logic
                    System.out.println("Update Product functionality will be implemented here.");
                    break;
                case 4:
                    // Delete product logic
                    System.out.println("Delete Product functionality will be implemented here.");
                    break;
                case 5:
                    // Search products logic
                    System.out.println("Search Products functionality will be implemented here.");
                    break;
                case 6:
                    System.out.println("\nLow Stock Products:");
                    productService.getProductsBelowReorderLevel().forEach(this::displayProduct);
                    break;
                case 7:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void handlePurchaseOrderManagement() {
        boolean back = false;
        
        while (!back) {
            System.out.println("\nPURCHASE ORDER MANAGEMENT");
            System.out.println("1. List All Purchase Orders");
            System.out.println("2. Create New Purchase Order");
            System.out.println("3. Update Purchase Order");
            System.out.println("4. Cancel Purchase Order");
            System.out.println("5. Close Purchase Order");
            System.out.println("6. View Open Purchase Orders");
            System.out.println("7. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    System.out.println("\nList of All Purchase Orders:");
                    purchaseOrderService.getAllPurchaseOrders().forEach(this::displayPurchaseOrder);
                    break;
                case 2:
                    // Create new purchase order logic
                    System.out.println("Create New Purchase Order functionality will be implemented here.");
                    break;
                case 3:
                    // Update purchase order logic
                    System.out.println("Update Purchase Order functionality will be implemented here.");
                    break;
                case 4:
                    // Cancel purchase order logic
                    System.out.println("Cancel Purchase Order functionality will be implemented here.");
                    break;
                case 5:
                    // Close purchase order logic
                    System.out.println("Close Purchase Order functionality will be implemented here.");
                    break;
                case 6:
                    System.out.println("\nOpen Purchase Orders:");
                    purchaseOrderService.getOpenPurchaseOrders().forEach(this::displayPurchaseOrder);
                    break;
                case 7:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void handleVendorManagement() {
        boolean back = false;
        
        while (!back) {
            System.out.println("\nVENDOR MANAGEMENT");
            System.out.println("1. List All Vendors");
            System.out.println("2. Add New Vendor");
            System.out.println("3. Update Vendor");
            System.out.println("4. Delete Vendor");
            System.out.println("5. Search Vendors");
            System.out.println("6. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    System.out.println("\nList of All Vendors:");
                    vendorService.getAllVendors().forEach(this::displayVendor);
                    break;
                case 2:
                    // Add new vendor logic
                    System.out.println("Add New Vendor functionality will be implemented here.");
                    break;
                case 3:
                    // Update vendor logic
                    System.out.println("Update Vendor functionality will be implemented here.");
                    break;
                case 4:
                    // Delete vendor logic
                    System.out.println("Delete Vendor functionality will be implemented here.");
                    break;
                case 5:
                    // Search vendors logic
                    System.out.println("Search Vendors functionality will be implemented here.");
                    break;
                case 6:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void handleCustomerManagement() {
        boolean back = false;
        
        while (!back) {
            System.out.println("\nCUSTOMER MANAGEMENT");
            System.out.println("1. List All Customers");
            System.out.println("2. Add New Customer");
            System.out.println("3. Update Customer");
            System.out.println("4. Delete Customer");
            System.out.println("5. Search Customers");
            System.out.println("6. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    System.out.println("\nList of All Customers:");
                    customerService.getAllCustomers().forEach(this::displayCustomer);
                    break;
                case 2:
                    // Add new customer logic
                    System.out.println("Add New Customer functionality will be implemented here.");
                    break;
                case 3:
                    // Update customer logic
                    System.out.println("Update Customer functionality will be implemented here.");
                    break;
                case 4:
                    // Delete customer logic
                    System.out.println("Delete Customer functionality will be implemented here.");
                    break;
                case 5:
                    // Search customers logic
                    System.out.println("Search Customers functionality will be implemented here.");
                    break;
                case 6:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void handleSalesManagement() {
        boolean back = false;
        
        while (!back) {
            System.out.println("\nSALES MANAGEMENT");
            System.out.println("1. List All Sales Invoices");
            System.out.println("2. Create New Sales Invoice");
            System.out.println("3. Update Sales Invoice");
            System.out.println("4. Record Payment");
            System.out.println("5. View Overdue Invoices");
            System.out.println("6. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    System.out.println("\nList of All Sales Invoices:");
                    salesInvoiceService.getAllSalesInvoices().forEach(this::displaySalesInvoice);
                    break;
                case 2:
                    // Create new sales invoice logic
                    System.out.println("Create New Sales Invoice functionality will be implemented here.");
                    break;
                case 3:
                    // Update sales invoice logic
                    System.out.println("Update Sales Invoice functionality will be implemented here.");
                    break;
                case 4:
                    // Record payment logic
                    System.out.println("Record Payment functionality will be implemented here.");
                    break;
                case 5:
                    System.out.println("\nOverdue Sales Invoices:");
                    salesInvoiceService.getOverdueSalesInvoices().forEach(this::displaySalesInvoice);
                    break;
                case 6:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void handleReportGeneration() {
        boolean back = false;
        
        while (!back) {
            System.out.println("\nREPORTS");
            System.out.println("1. Inventory Status Report");
            System.out.println("2. Purchase Order Report");
            System.out.println("3. Sales Report");
            System.out.println("4. Outstanding Payments Report");
            System.out.println("5. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    // Inventory status report logic
                    System.out.println("Inventory Status Report functionality will be implemented here.");
                    break;
                case 2:
                    // Purchase order report logic
                    System.out.println("Purchase Order Report functionality will be implemented here.");
                    break;
                case 3:
                    // Sales report logic
                    System.out.println("Sales Report functionality will be implemented here.");
                    break;
                case 4:
                    // Outstanding payments report logic
                    System.out.println("Outstanding Payments Report functionality will be implemented here.");
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    // Helper methods for displaying entities
    private void displayProduct(Product product) {
        System.out.println("Product: " + product.getProductCode() + " - " + product.getName() + 
                           " | Stock: " + product.getCurrentQuantity() + 
                           " | Reorder Level: " + product.getReorderLevel() + 
                           " | Price: $" + product.getSellingPrice());
    }
    
    private void displayPurchaseOrder(PurchaseOrder po) {
        System.out.println("PO: " + po.getOrderNumber() + 
                           " | Vendor: " + po.getVendor().getCompanyName() + 
                           " | Date: " + po.getOrderDate() + 
                           " | Status: " + po.getStatus() + 
                           " | Total: $" + po.getTotalAmount());
    }
    
    private void displayVendor(Vendor vendor) {
        System.out.println("Vendor: " + vendor.getVendorCode() + " - " + vendor.getCompanyName() + 
                           " | Contact: " + vendor.getContactPerson() + 
                           " | Phone: " + vendor.getPhone() + 
                           " | Email: " + vendor.getEmail());
    }
    
    private void displayCustomer(Customer customer) {
        System.out.println("Customer: " + customer.getCustomerCode() + " - " + customer.getCompanyName() + 
                           " | Contact: " + customer.getContactPerson() + 
                           " | Phone: " + customer.getPhone() + 
                           " | Email: " + customer.getEmail());
    }
    
    private void displaySalesInvoice(SalesInvoice invoice) {
        System.out.println("Invoice: " + invoice.getInvoiceNumber() + 
                           " | Customer: " + invoice.getCustomer().getCompanyName() + 
                           " | Date: " + invoice.getInvoiceDate() + 
                           " | Status: " + invoice.getPaymentStatus() + 
                           " | Total: $" + invoice.getTotalAmount());
    }
    
    // Helper methods for user input
    private int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid number.");
            scanner.next();
            System.out.print(prompt);
        }
        int input = scanner.nextInt();
        scanner.nextLine(); // Consume the newline
        return input;
    }
    
    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}