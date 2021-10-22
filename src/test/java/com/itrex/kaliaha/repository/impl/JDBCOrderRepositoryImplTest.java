package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Order;
import com.itrex.kaliaha.entity.User;
import com.itrex.kaliaha.entity.util.OrderStatus;
import com.itrex.kaliaha.repository.BaseRepositoryTest;
import com.itrex.kaliaha.repository.OrderRepository;
import com.itrex.kaliaha.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class JDBCOrderRepositoryImplTest extends BaseRepositoryTest {
    private final OrderRepository orderRepository;
    private List<Order> orders;
    private final List<User> users;

    public JDBCOrderRepositoryImplTest() {
        super();
        orderRepository = new JDBCOrderRepositoryImpl(getConnectionPool());

        users = new ArrayList<>();
        users.add(new User(1L, "Коляго", "Владислав", "kaliaha.vladzislav", "1111", "г.Витебск"));
        users.add(new User(2L, "Молочко", "Юрий", "molochko.urey", "2222", "г.Хойники"));
        users.add(new User(3L,"Рубанов", "Владислав", "rubanov", "3333", "г.Жлобин"));
        users.add(new User(4L, "Петров", "Сергей", "petrov", "4444", "г.Москва"));
    }

    @Before
    public void init() {
        orders = new ArrayList<>() {{
            add(new Order(1L, 1500, LocalDate.of(2021, 10, 21), "г. Минск", OrderStatus.Cooking, users.get(0)));
            add(new Order(2L, 2800, LocalDate.of(2021, 10, 22), "г. Минск", OrderStatus.Cooking, users.get(1)));
            add(new Order(3L, 1200, LocalDate.of(2021, 10, 23), "г. Витебск", OrderStatus.Cooking, users.get(0)));
            add(new Order(4L, 1500, LocalDate.of(2021, 11, 24), "г. Минск", OrderStatus.New, users.get(2)));
            add(new Order(5L, 2800, LocalDate.of(2021, 11, 25), "г. Минск", OrderStatus.New, users.get(3)));
            add(new Order(6L, 1200, LocalDate.of(2021, 12, 26), "г. Витебск", OrderStatus.New, users.get(0)));
        }};
    }

    @Test
    public void selectById_validData_shouldReturnExistOrderById() {
        //given
        Order expected = orders.get(0);
        //when
        Order actual = orderRepository.selectById(expected.getId());
        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void selectAll_validData_shouldReturnExistAllOrders() {
        //given
        List<Order> expected = orders;
        //when
        List<Order> actual = orderRepository.selectAll();
        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void add_validData_shouldAddOneOrder() {
        //given
        Order expected = new Order(7L, 1500, LocalDate.of(2021, 12, 27), "г. Минск", OrderStatus.New, users.get(0));
        //when
        Order actual = new Order(-1L, 1500, LocalDate.of(2021, 12, 27), "г. Минск", OrderStatus.New, users.get(0));
        orderRepository.add(actual);
        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addAll_validData_shouldAddAllOrders() {
        //given
        List<Order> expected = new ArrayList<>() {{
            add(new Order(7L, 1500, LocalDate.of(2021, 12, 27), "г. Минск", OrderStatus.New, users.get(0)));
            add(new Order(8L, 2800, LocalDate.of(2021, 12, 28), "г. Минск", OrderStatus.Cooking, users.get(1)));
        }};
        //when
        List<Order> actual = new ArrayList<>() {{
            add(new Order(1500, LocalDate.of(2021, 12, 27), "г. Минск", OrderStatus.New, users.get(0)));
            add(new Order(2800, LocalDate.of(2021, 12, 28), "г. Минск", OrderStatus.Cooking, users.get(1)));
        }};
        orderRepository.addAll(actual);
        //then
        assertEquals(expected, actual);
    }

    @Test
    public void update_validData_shouldUpdateOrder() {
        //given
        Order expected = new Order(1L, 1600, LocalDate.of(2021, 11, 1), "updated г. Минск", OrderStatus.Completed, users.get(0));
        Order actual = orders.get(0);
        //when
        actual.setPrice(1600);
        actual.setDate(LocalDate.of(2021, 11, 1));
        actual.setAddress("updated г. Минск");
        actual.setOrderStatus(OrderStatus.Completed);
        boolean result = orderRepository.update(actual);
        //then
        Assert.assertTrue(result);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void remove_validData_shouldRemoveOrder() {
        //given
        Order checkAddition = new Order(7L, 15, LocalDate.of(2021, 10, 20), "г. Минск", OrderStatus.New, users.get(0));
        Order newOrder = new Order(15, LocalDate.of(2021, 10, 20), "г. Минск", OrderStatus.New, users.get(0));
        orderRepository.add(newOrder);
        Assert.assertEquals(checkAddition, newOrder);
        //when
        boolean actual = orderRepository.remove(newOrder.getId());
        //then
        Assert.assertTrue(actual);
    }

    @Test
    public void findOrdersByUserId_validData_shouldReturnListUserOrders() {
        //given
        List<Order> expected = orders.stream().filter(order -> order.getUser().getId() == 1).collect(Collectors.toList());
        //when
        List<Order> actual = orderRepository.findOrdersByUserId(1L);
        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void removeOrdersByUserId_validData_shouldRemoveAllDishesInOrder() {
        //given
        User user = new User("Селиванов", "Михаил", "selivanov", "555", "г.Рига");
        UserRepository userRepository = new JDBCUserRepositoryImpl(getConnectionPool());
        userRepository.add(user);
        Assert.assertNotNull(user.getId());
        Order checkAddition = new Order(7L, 3000, LocalDate.of(2021, 12, 30), "г. Минск", OrderStatus.New, user);
        // when
        Order newOrder = new Order(3000, LocalDate.of(2021, 12, 30), "г. Минск", OrderStatus.New, user);
        orderRepository.add(newOrder);
        Assert.assertEquals(checkAddition, newOrder);
        Assert.assertTrue(orderRepository.removeOrdersByUserId(user.getId()));
    }

    @Test
    public void orderDish_validData_shouldAddDishToOrder() {
        //given && when && then
        boolean actual = orderRepository.orderDish(orders.get(1), 3L);
        Assert.assertTrue(actual);
    }

    @Test
    public void removeDishesByOrderId_validData_shouldRemoveAllDishesInOrder() {
        //given && when
        Order checkAddition = new Order(7L, 3000, LocalDate.of(2021, 12, 30), "г. Минск", OrderStatus.New, users.get(1));
        Order newOrder = new Order(3000, LocalDate.of(2021, 12, 30), "г. Минск", OrderStatus.New, users.get(1));
        orderRepository.add(newOrder);
        Assert.assertEquals(checkAddition, newOrder);

        Assert.assertTrue(orderRepository.orderDish(newOrder, 1L));
        Assert.assertTrue(orderRepository.orderDish(newOrder, 2L));
        Assert.assertTrue(orderRepository.orderDish(newOrder, 3L));
        Assert.assertTrue(orderRepository.removeDishesByOrderId(newOrder.getId()));
    }
}