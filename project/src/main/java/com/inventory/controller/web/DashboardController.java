package com.inventory.controller.web;

import com.inventory.model.PurchaseOrder;
import com.inventory.model.SalesInvoice;
import com.inventory.service.ProductService;
import com.inventory.service.PurchaseOrderService;
import com.inventory.service.SalesInvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {
    
    private final ProductService productService;
    private final PurchaseOrderService purchaseOrderService;
    private final SalesInvoiceService salesInvoiceService;
    
    @GetMapping("/")
    public String index() {
        return "redirect:/dashboard";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Add dashboard data to model
        model.addAttribute("totalProducts", productService.getAllProducts().size());
        model.addAttribute("lowStockProducts", productService.getProductsBelowReorderLevel());
        model.addAttribute("pendingOrders", purchaseOrderService.getPurchaseOrdersByStatus(PurchaseOrder.PurchaseOrderStatus.PENDING));
        model.addAttribute("recentPurchaseOrders", purchaseOrderService.getAllPurchaseOrders());
        model.addAttribute("recentSalesInvoices", salesInvoiceService.getAllSalesInvoices());
        model.addAttribute("overdueSalesInvoices", salesInvoiceService.getOverdueSalesInvoices());
        
        return "dashboard";
    }
}