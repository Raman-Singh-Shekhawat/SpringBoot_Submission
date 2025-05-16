<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Inventory Management System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
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
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        
        h1, h2, h3, h4, h5, h6 {
            font-family: 'Montserrat', sans-serif;
            font-weight: 500;
        }
        
        .login-container {
            max-width: 400px;
            width: 100%;
            padding: 40px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
        }
        
        .login-header {
            text-align: center;
            margin-bottom: 30px;
        }
        
        .login-header h2 {
            font-weight: 700;
            color: var(--primary-color);
        }
        
        .login-header p {
            color: var(--secondary-color);
        }
        
        .form-floating {
            margin-bottom: 16px;
        }
        
        .btn-primary {
            background-color: var(--primary-color);
            border-color: var(--primary-color);
            padding: 10px 16px;
            font-weight: 600;
            transition: all 0.3s ease;
        }
        
        .btn-primary:hover {
            background-color: #1557b8;
            border-color: #1557b8;
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(26, 115, 232, 0.3);
        }
        
        .alert {
            border-radius: 4px;
            padding: 12px 16px;
            margin-bottom: 20px;
        }
        
        .system-logo {
            width: 80px;
            height: 80px;
            margin: 0 auto 20px;
            background-color: var(--primary-color);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-size: 40px;
            font-weight: 700;
            box-shadow: 0 4px 10px rgba(26, 115, 232, 0.3);
        }
    </style>
</head>
<body>
    <div class="login-container">
        <div class="login-header">
            <div class="system-logo">IMS</div>
            <h2>Welcome Back</h2>
            <p>Sign in to continue to Inventory Management System</p>
        </div>
        
        <c:if test="${param.error != null}">
            <div class="alert alert-danger" role="alert">
                Invalid username or password.
            </div>
        </c:if>
        
        <c:if test="${param.logout != null}">
            <div class="alert alert-success" role="alert">
                You have been logged out successfully.
            </div>
        </c:if>
        
        <form action="/login" method="post">
            <div class="form-floating">
                <input type="text" class="form-control" id="username" name="username" placeholder="Username" required>
                <label for="username">Username</label>
            </div>
            
            <div class="form-floating">
                <input type="password" class="form-control" id="password" name="password" placeholder="Password" required>
                <label for="password">Password</label>
            </div>
            
            <div class="d-flex justify-content-between align-items-center mb-4">
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="" id="rememberMe">
                    <label class="form-check-label" for="rememberMe">
                        Remember me
                    </label>
                </div>
                <a href="#" class="text-decoration-none text-primary">Forgot password?</a>
            </div>
            
            <button type="submit" class="btn btn-primary w-100">Sign In</button>
            
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        </form>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>