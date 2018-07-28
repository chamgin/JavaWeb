package org.demo.chapter2.service;

import org.demo.chapter2.helper.DatabaseHelper;
import org.demo.chapter2.model.Customer;
import org.demo.chapter2.util.PropsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);


    public List<Customer> getCustomerList() {
        //Connection conn = DatabaseHelper.getConnection();
        String sql = "SELECT * FROM customer";
        return DatabaseHelper.queryEntityList(Customer.class, sql);
    }
    public Customer getCustomer(long id) {
        String sql = "SELECT * FROM customer where id = ?";
        return DatabaseHelper.queryEntity(Customer.class, sql, id);
    }
    public boolean createCustomer(Map<String, Object> fieldMap) {
        return DatabaseHelper.insertEntity(Customer.class, fieldMap);
    }
    public boolean updateCustomer(long id, Map<String, Object> fieldMap) {
        return DatabaseHelper.updateEntity(Customer.class, id, fieldMap);
    }
    public boolean deleteCustomer(long id) {
        return DatabaseHelper.deleteEntity(Customer.class, id);
    }
}
