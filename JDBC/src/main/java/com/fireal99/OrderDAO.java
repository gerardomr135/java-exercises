package com.fireal99;

import com.fireal99.util.DataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends DataAccessObject<Order> {
    private static final String GET_BY_ID = "SELECT" +
            "  o.order_id, o.creation_date, o.total_due, o.status," +
            "  c.customer_id, c.first_name customer_first_name, c.last_name customer_last_name, c.email customer_email, c.phone customer_phone," +
            "  c.address customer_address, c.city customer_city, c.state customer_state, c.zipcode customer_zipcode," +
            "  s.salesperson_id, s.first_name salesperson_first_name, s.last_name salesperson_last_name, s.email salesperson_email, s.phone salesperson_phone," +
            "  s.address salesperson_address, s.city salesperson_city, s.state salesperson_state, s.zipcode salesperson_zipcode " +
            "FROM orders o " +
            "INNER JOIN customer c ON o.customer_id = c.customer_id " +
            "INNER JOIN salesperson s ON o.salesperson_id = s.salesperson_id " +
            "WHERE o.order_id = ?";

    private static final String GET_FOR_CUST = "SELECT * FROM get_orders_by_customer(?)";

    public OrderDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Order findById(long id) {
        Order order = new Order();

        try(PreparedStatement statement = connection.prepareStatement(GET_BY_ID)) {
            statement.setLong(1, id);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                order.setId(rs.getLong("order_id"));
                order.setCreationDate(rs.getDate("creation_date").toLocalDate());
                order.setTotalDue(rs.getBigDecimal("total_due"));
                order.setStatus(rs.getString("status"));

                Customer customer = new Customer();
                customer.setId(rs.getLong("customer_id"));
                customer.setFirstName(rs.getString("customer_first_name"));
                customer.setLastName(rs.getString("customer_last_name"));
                customer.setEmail(rs.getString("customer_email"));
                customer.setPhone(rs.getString("customer_phone"));
                customer.setAddress(rs.getString("customer_address"));
                customer.setCity(rs.getString("customer_city"));
                customer.setState(rs.getString("customer_state"));
                customer.setZipCode(rs.getString("customer_zipcode"));
                order.setCustomer(customer);

                SalesPerson salesPerson = new SalesPerson();
                salesPerson.setId(rs.getLong("salesperson_id"));
                salesPerson.setFirstName(rs.getString("salesperson_first_name"));
                salesPerson.setLastName(rs.getString("salesperson_last_name"));
                salesPerson.setEmail(rs.getString("salesperson_email"));
                salesPerson.setPhone(rs.getString("salesperson_phone"));
                salesPerson.setAddress(rs.getString("salesperson_address"));
                salesPerson.setCity(rs.getString("salesperson_city"));
                salesPerson.setState(rs.getString("salesperson_state"));
                salesPerson.setZipCode(rs.getString("salesperson_zipcode"));
                order.setSalesPerson(salesPerson);
            }
        } catch(SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return order;
    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public Order update(Order dto) {
        return null;
    }

    @Override
    public Order create(Order dto) {
        return null;
    }

    @Override
    public void delete(long id) {
    }

    public List<Order> findOrdersByCustomerId(long customerId) {
        List<Order> orders = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(GET_FOR_CUST)) {
            statement.setLong(1, customerId);

            ResultSet rs = statement.executeQuery();

            long orderId = 0;
            Order order = null;

            while (rs.next()) {
                long localOrderId = rs.getLong(4);

                if (orderId !=  localOrderId) {
                    order = new Order();
                    orders.add(order);
                    order.setId(localOrderId);
                    order.setCreationDate(rs.getDate(5).toLocalDate());
                    order.setTotalDue(rs.getBigDecimal(6));
                    order.setStatus(rs.getString(7));
                    orderId = localOrderId;

                    Customer customer = new Customer();
                    customer.setFirstName(rs.getString(1));
                    customer.setLastName(rs.getString(2));
                    customer.setEmail(rs.getString(3));
                    order.setCustomer(customer);

                    SalesPerson salesPerson = new SalesPerson();
                    salesPerson.setFirstName(rs.getString(8));
                    salesPerson.setLastName(rs.getString(9));
                    salesPerson.setEmail(rs.getString(10));
                    order.setSalesPerson(salesPerson);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return  orders;
    }
}
