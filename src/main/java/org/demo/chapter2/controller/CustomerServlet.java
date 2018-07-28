package org.demo.chapter2.controller;

import org.demo.chapter2.model.Customer;
import org.demo.chapter2.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/customer")
public class CustomerServlet extends HttpServlet {

    private CustomerService customerService;

    @Override
    public void init() throws ServletException {
        customerService = new CustomerService();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        List<Customer> list = customerService.getCustomerList();
        request.setAttribute("customerList", list);
        request.getRequestDispatcher("/WEB-INF/view/customer.jsp").forward(request, response);
    }
}
