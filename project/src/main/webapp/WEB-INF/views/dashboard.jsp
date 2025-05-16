<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>IMS Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;700&family=Source+Sans+Pro:wght@400;600&display=swap" rel="stylesheet">
    <style>
        :root {
            --primary-color: #1A73E8;
            --secondary-color: #5F6368;
            --success-color: #34A853;
            --warning-color: #FBBC04;
            --danger-color: #EA4335;
            --light-color: #F8F9FA;
            --dark-color: #202124;
        }
        
        body {
            font-family: 'Source Sans Pro', sans-serif;
            background-color: #f5f5f7;
            color: #333;
            line-height: 1.5;
        }
        
        h1, h2, h3, h4, h5, h6 {
            font-family: 'Montserrat', sans-serif;
            font-weight: 500;
            line-height: 1.2;
        }
        
        .navbar-brand {
            font-family: 'Montserrat', sans-serif;
            font-weight: 700;
        }
        
        .card {
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            margin-bottom: 16px;
        }
        
        .card:hover {
            transform: translateY(-4px);
            box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
        }
        
        .card-header {
            font-weight: 600;
            border-bottom: 1px solid rgba(0, 0, 0, 0.05);
        }
        
        .dashboard-stat {
            text-align: center;
            padding: 16px;
        }
        
        .dashboard-stat .stat-value {
            font-size: 2rem;
            font-weight: 700;
            margin-bottom: 8px;
        }
        
        .dashboard-stat .stat-label {
            font-size: 1rem;
            color: var(--secondary-color);
        }
        
        .bg-primary {
            background-color: var(--primary-color) !important;
        }
        
        .bg-success {
            background-color: var(--success-color) !important;
        }
        
        .bg-warning {
            background-color: var(--warning-color) !important;
        }
        
        .bg-danger {
            background-color: var(--danger-color) !important;
        }
        
        .sidebar {
            height: calc(100vh - 56px);
            position: sticky;
            top: 56px;
            background-color: white;
            box-shadow: 1px 0 10px rgba(0, 0, 0, 0.05);
        }
        
        .sidebar .nav-link {
            color: var(--secondary-color);
            padding: 8px 16px;
            margin: 4px 0;
            border-radius: 4px;
            transition: all 0.2s ease;
        }
        
        .sidebar .nav-link:hover {
            background-color: rgba(26, 115, 232, 0.1);
            color: var(--primary-color);
        }
        
        .sidebar .nav-link.active {
            background-color: var(--primary-color);
            color: white;
        }
        
        .sidebar .nav-link i {
            margin-right: 8px;
            width: 24px;
            text-align: center;
        }
        
        .avatar {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            background-color: var(--primary-color);
            color: white;
            display: flex;
            align-items: center;
            justify-content: center;
            font-weight: 600;
            font-size: 1.2rem;
        }
        
        @media (max-width: 767.98px) {
            .sidebar {
                height: auto;
                position: static;
                margin-bottom: 16px;
            }
        }
    </style>
</head>
<body>
    <!-- Navigation Bar -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">
                Inventory Management System
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle d-flex align-items-center" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            <div class="avatar me-2">A</div>
                            Admin
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                            <li><a class="dropdown-item" href="#"><i class="bi bi-person"></i> Profile</a></li>
                            <li><a class="dropdown-item" href="#"><i class="bi bi-gear"></i> Settings</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="#"><i class="bi bi-box-arrow-right"></i> Logout</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container-fluid">
        <div class="row">
            <!-- Sidebar -->
            <div class="col-md-3 col-lg-2 d-md-block sidebar collapse">
                <div class="position-sticky pt-3">
                    <ul class="nav flex-column">
                        <li class="nav-item">
                            <a class="nav-link active" href="#">
                                <i class="bi bi-speedometer2"></i> Dashboard
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">
                                <i class="bi bi-box-seam"></i> Products
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">
                                <i class="bi bi-cart3"></i> Purchase Orders
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">
                                <i class="bi bi-receipt"></i> Sales Invoices
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">
                                <i class="bi bi-shop"></i> Vendors
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">
                                <i class="bi bi-people"></i> Customers
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">
                                <i class="bi bi-file-text"></i> Reports
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">
                                <i class="bi bi-gear"></i> Settings
                            </a>
                        </li>
                    </ul>
                </div>
            </div>

            <!-- Main Content -->
            <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 py-4">
                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                    <h1 class="h2">Dashboard</h1>
                    <div class="btn-toolbar mb-2 mb-md-0">
                        <div class="btn-group me-2">
                            <button type="button" class="btn btn-sm btn-outline-secondary">Export</button>
                            <button type="button" class="btn btn-sm btn-outline-secondary">Print</button>
                        </div>
                        <button type="button" class="btn btn-sm btn-primary">
                            <i class="bi bi-calendar"></i> Today
                        </button>
                    </div>
                </div>

                <!-- Quick Stats -->
                <div class="row">
                    <div class="col-md-3">
                        <div class="card">
                            <div class="card-body dashboard-stat">
                                <div class="stat-value text-primary">254</div>
                                <div class="stat-label">Total Products</div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="card">
                            <div class="card-body dashboard-stat">
                                <div class="stat-value text-success">$24,500</div>
                                <div class="stat-label">Monthly Sales</div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="card">
                            <div class="card-body dashboard-stat">
                                <div class="stat-value text-warning">18</div>
                                <div class="stat-label">Pending Orders</div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="card">
                            <div class="card-body dashboard-stat">
                                <div class="stat-value text-danger">6</div>
                                <div class="stat-label">Low Stock Items</div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Recent Activity -->
                <div class="row mt-4">
                    <div class="col-md-6">
                        <div class="card">
                            <div class="card-header">
                                Recent Purchase Orders
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-striped table-sm">
                                        <thead>
                                            <tr>
                                                <th>Order #</th>
                                                <th>Vendor</th>
                                                <th>Date</th>
                                                <th>Amount</th>
                                                <th>Status</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>PO-10234</td>
                                                <td>ABC Supplies</td>
                                                <td>2023-06-15</td>
                                                <td>$1,240.00</td>
                                                <td><span class="badge bg-warning">Pending</span></td>
                                            </tr>
                                            <tr>
                                                <td>PO-10233</td>
                                                <td>XYZ Electronics</td>
                                                <td>2023-06-14</td>
                                                <td>$3,580.00</td>
                                                <td><span class="badge bg-success">Approved</span></td>
                                            </tr>
                                            <tr>
                                                <td>PO-10232</td>
                                                <td>Global Trading</td>
                                                <td>2023-06-12</td>
                                                <td>$690.50</td>
                                                <td><span class="badge bg-primary">Received</span></td>
                                            </tr>
                                            <tr>
                                                <td>PO-10231</td>
                                                <td>City Wholesalers</td>
                                                <td>2023-06-10</td>
                                                <td>$1,825.75</td>
                                                <td><span class="badge bg-secondary">Closed</span></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <a href="#" class="btn btn-sm btn-primary mt-2">View All</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="card">
                            <div class="card-header">
                                Recent Sales Invoices
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-striped table-sm">
                                        <thead>
                                            <tr>
                                                <th>Invoice #</th>
                                                <th>Customer</th>
                                                <th>Date</th>
                                                <th>Amount</th>
                                                <th>Status</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>INV-20345</td>
                                                <td>Acme Corporation</td>
                                                <td>2023-06-15</td>
                                                <td>$2,450.00</td>
                                                <td><span class="badge bg-warning">Pending</span></td>
                                            </tr>
                                            <tr>
                                                <td>INV-20344</td>
                                                <td>Tech Solutions</td>
                                                <td>2023-06-14</td>
                                                <td>$1,890.00</td>
                                                <td><span class="badge bg-success">Paid</span></td>
                                            </tr>
                                            <tr>
                                                <td>INV-20343</td>
                                                <td>Mega Retailers</td>
                                                <td>2023-06-12</td>
                                                <td>$3,740.25</td>
                                                <td><span class="badge bg-danger">Overdue</span></td>
                                            </tr>
                                            <tr>
                                                <td>INV-20342</td>
                                                <td>Global Mart</td>
                                                <td>2023-06-10</td>
                                                <td>$1,025.50</td>
                                                <td><span class="badge bg-success">Paid</span></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <a href="#" class="btn btn-sm btn-primary mt-2">View All</a>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Low Stock Alert -->
                <div class="row mt-4">
                    <div class="col-md-12">
                        <div class="card">
                            <div class="card-header">
                                Low Stock Alert
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-striped table-sm">
                                        <thead>
                                            <tr>
                                                <th>Product Code</th>
                                                <th>Product Name</th>
                                                <th>Current Stock</th>
                                                <th>Reorder Level</th>
                                                <th>Supplier</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>PRD-001</td>
                                                <td>Premium Notebook</td>
                                                <td><span class="text-danger">5</span></td>
                                                <td>20</td>
                                                <td>Office Supplies Inc.</td>
                                                <td><button class="btn btn-sm btn-primary">Reorder</button></td>
                                            </tr>
                                            <tr>
                                                <td>PRD-042</td>
                                                <td>Wireless Mouse</td>
                                                <td><span class="text-danger">8</span></td>
                                                <td>15</td>
                                                <td>Tech Gadgets Ltd</td>
                                                <td><button class="btn btn-sm btn-primary">Reorder</button></td>
                                            </tr>
                                            <tr>
                                                <td>PRD-108</td>
                                                <td>Desk Lamp</td>
                                                <td><span class="text-danger">3</span></td>
                                                <td>10</td>
                                                <td>Bright Lights Co.</td>
                                                <td><button class="btn btn-sm btn-primary">Reorder</button></td>
                                            </tr>
                                            <tr>
                                                <td>PRD-156</td>
                                                <td>Coffee Mug</td>
                                                <td><span class="text-danger">12</span></td>
                                                <td>25</td>
                                                <td>Kitchen Essentials</td>
                                                <td><button class="btn btn-sm btn-primary">Reorder</button></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Footer -->
                <footer class="pt-5 d-flex justify-content-between">
                    <span>Copyright © 2023 Inventory Management System</span>
                    <ul class="nav m-0">
                        <li class="nav-item">
                            <a class="nav-link text-secondary" href="#">Privacy Policy</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link text-secondary" href="#">Terms of Use</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link text-secondary" href="#">Contact</a>
                        </li>
                    </ul>
                </footer>
            </main>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Add smooth animations for dashboard cards
        document.addEventListener('DOMContentLoaded', function() {
            const cards = document.querySelectorAll('.card');
            cards.forEach((card, index) => {
                setTimeout(() => {
                    card.style.opacity = '1';
                    card.style.transform = 'translateY(0)';
                }, 100 * index);
            });
        });
    </script>
</body>
</html>