package BL;


import DL.*;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static DL.DataSource.*;
import static java.util.stream.Collectors.*;

public class BL implements IBL {
    @Override
    public Product getProductById(long productId) {
        return allProducts.stream().filter(l -> l.getProductId() == productId).findFirst().orElse(null);
    }

    @Override
    public Order getOrderById(long orderId) {
        return allOrders.stream().filter(l -> l.getOrderId() == orderId).findFirst().orElse(null);
    }

    @Override
    public Customer getCustomerById(long customerId) {
        return allCustomers.stream().filter(l -> l.getId() == customerId).findFirst().orElse(null);
    }


    @Override
    public List<Product> getProducts(ProductCategory cat, double price) {
        return allProducts.stream().filter(l -> l.getPrice() <= price && l.getCategory() == cat)
                .sorted(Comparator.comparing(Product::getProductId)).collect(toList());
    }

    @Override
    public List<Customer> popularCustomers() {
        List<Long> customId = allOrders.stream().collect(groupingBy(Order::getCustomrId, counting()))
                .entrySet().stream().filter(l -> l.getValue() > 10).map(Map.Entry::getKey).collect(toList());
        return allCustomers.stream().filter(l -> l.getTier() == 3 && customId.contains(l.getId())).collect(toList());
    }

    @Override
    public List<Order> getCustomerOrders(long customerId) {
        return allOrders.stream().filter(l -> l.getCustomrId() == customerId).collect(toList());
    }

    @Override
    public long numberOfProductInOrder(long orderId) {
        return allOrderProducts.stream().filter(l -> l.getOrderId() == orderId).count();
    }

    @Override
    public List<Product> getPopularOrderedProduct(int orderedtimes) {
        List<Long> prodId = allOrderProducts.stream().collect(groupingBy(OrderProduct::getProductId , counting()))
                .entrySet().stream().filter(l -> l.getValue() >= orderedtimes).map(Map.Entry::getKey).collect(toList());
        return allProducts.stream().filter(l -> prodId.contains(l.getProductId())).collect(toList());
    }

    @Override
    public List<Product> getOrderProducts(long orderId) {
        List<Long> prodId = allOrderProducts.stream().filter(l -> l.getOrderId() == orderId)
                .map(OrderProduct::getProductId).collect(toList());
        return allProducts.stream().filter(l -> prodId.contains(l.getProductId())).collect(toList());
    }

    @Override
    public List<Customer> getCustomersWhoOrderedProduct(long productId) {
        List<Long> orderId = allOrderProducts.stream().filter(l->l.getProductId() == productId)
                .map(OrderProduct::getOrderId).collect(toList());
        List<Long> customId = allOrders.stream().filter(l->orderId.contains(l.getOrderId()))
                .map(Order::getCustomrId).collect(toList());
        return allCustomers.stream().filter(l -> customId.contains(l.getId())).collect(toList());
    }

    @Override
    public Product getMaxOrderedProduct() {
        Product result = null;
        Long prodId = allOrderProducts.stream().collect(Collectors.groupingBy(OrderProduct::getProductId,
                        counting())).entrySet().stream()
                .max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(null);
        if (prodId != null) {
            result = allProducts.stream().filter(l -> l.getProductId() == prodId).findFirst().orElse(null);
        }
        return result;
    }
    @Override
    public double sumOfOrder(long orderID) {
        Map<Long, Long> sumOrder = allOrderProducts.stream().filter(l -> l.getOrderId() == orderID)
                .collect(Collectors.groupingBy(OrderProduct::getProductId,
                        Collectors.summingLong(OrderProduct::getQuantity)));

        return getOrderProducts(orderID).stream().map(l -> l.getPrice() * sumOrder.get(l.getProductId()))
                .reduce(0.0, Double::sum);
    }

    @Override
    public List<Order> getExpensiveOrders(double price) {
        return allOrders.stream().filter(l -> sumOfOrder(l.getOrderId()) > price)
                .sorted(Comparator.comparing(Order::getOrderId)).collect(toList());
    }

    @Override
    public List<Customer> ThreeTierCustomerWithMaxOrders() {
        List<Long> customer3 = allCustomers.stream().filter(l->l.getTier() == 3).map(Customer::getId).collect(toList());
        Long maxOrder3 = allOrders.stream().filter(l->customer3.contains(l.getCustomrId()))
                .collect(groupingBy(Order::getCustomrId, counting())).entrySet().stream()
                .max(Map.Entry.comparingByValue()).map(Map.Entry::getValue).orElse(null);

        List<Long> customersId = allOrders.stream().filter(l->customer3.contains(l.getCustomrId()))
                .collect(groupingBy(Order::getCustomrId, counting())).entrySet().stream()
                .filter(l->l.getValue() == maxOrder3).map(Map.Entry::getKey).collect(toList());
        return allCustomers.stream().filter(l->customersId.contains(l.getId()))
                .sorted(Comparator.comparing(Customer::getId)).collect(toList());
    }

}

