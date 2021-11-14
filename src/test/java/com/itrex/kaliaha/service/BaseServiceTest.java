package com.itrex.kaliaha.service;

import com.itrex.kaliaha.config.TestServiceConfiguration;
import com.itrex.kaliaha.converters.DishConverter;
import com.itrex.kaliaha.converters.OrderConverter;
import com.itrex.kaliaha.converters.RoleConverter;
import com.itrex.kaliaha.converters.UserConverter;
import com.itrex.kaliaha.dto.*;
import com.itrex.kaliaha.entity.*;
import com.itrex.kaliaha.enums.DishGroup;
import com.itrex.kaliaha.enums.Measurement;
import com.itrex.kaliaha.enums.OrderStatus;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestServiceConfiguration.class)
public abstract class BaseServiceTest {
    public List<User> getUsers() {
        return new ArrayList<>() {{
            add(User.builder().id(1L).lastName("Коляго").firstName("Владислав").login("kaliaha.vladzislav").password("1111").address("г.Витебск").build());
            add(User.builder().id(2L).lastName("Петров").firstName("Александр").login("petrov.Alessandro").password("2222").address("г.Минск").build());
        }};
    }

    public List<UserListDTO> getListUserListDTO() {
        return new ArrayList<>() {{
            add(UserConverter.toListDTO(getUsers().get(0)));
            add(UserConverter.toListDTO(getUsers().get(1)));
        }};
    }

    public List<Role> getRoles() {
        return new ArrayList<>() {{
            add(Role.builder().id(1L).roleName("admin").build());
            add(Role.builder().id(2L).roleName("user").build());
            add(Role.builder().id(3L).roleName("cook").build());
        }};
    }

    public List<RoleDTO> getListRoleDTO() {
        return new ArrayList<>() {{
            add(RoleConverter.toDTO(getRoles().get(0)));
            add(RoleConverter.toDTO(getRoles().get(1)));
            add(RoleConverter.toDTO(getRoles().get(2)));
        }};
    }

    public List<Order> getOrders() {
        return new ArrayList<>() {{
            add(Order.builder().id(1L).orderStatus(OrderStatus.NEW).address("a").price(150).user(getUsers().get(0)).build());
            add(Order.builder().id(2L).orderStatus(OrderStatus.COOKING).address("b").price(250).user(getUsers().get(0)).build());
            add(Order.builder().id(3L).orderStatus(OrderStatus.COMPLETED).address("c").price(350).user(getUsers().get(1)).build());
            add(Order.builder().id(4L).orderStatus(OrderStatus.ACCEPTED).address("d").price(450).user(getUsers().get(1)).build());
            add(Order.builder().id(5L).orderStatus(OrderStatus.NEW).address("e").price(550).user(getUsers().get(1)).build());
        }};
    }

    public List<OrderListDTO> getListOrderListDTO() {
        return new ArrayList<>() {{
            add(OrderConverter.toListDTO(getOrders().get(0)));
            add(OrderConverter.toListDTO(getOrders().get(1)));
            add(OrderConverter.toListDTO(getOrders().get(2)));
            add(OrderConverter.toListDTO(getOrders().get(3)));
            add(OrderConverter.toListDTO(getOrders().get(4)));
        }};
    }

    public List<Dish> getDishes() {
        return new ArrayList<>() {{
            add(Dish.builder().id(1L).dishName("Картошка с грибами").price(2).dishGroup(DishGroup.HOT).dishDescription("Очень вкусно").imagePath("photo.img").build());
            add(Dish.builder().id(2L).dishName("Салат по-французски").price(7).dishGroup(DishGroup.SALAD).dishDescription("Невкусно").imagePath("photo1.img").build());
            add(Dish.builder().id(3L).dishName("Макароны по-европейски").price(11).dishGroup(DishGroup.DRINK).dishDescription("Невероятно").imagePath("photo2.img").build());
        }};
    }

    public List<DishListDTO> getListDishListDTO() {
        return new ArrayList<>() {{
            add(DishConverter.toDishListDTO(getDishes().get(0)));
            add(DishConverter.toDishListDTO(getDishes().get(1)));
            add(DishConverter.toDishListDTO(getDishes().get(2)));
        }};
    }

    public List<Ingredient> getIngredients() {
        return new ArrayList<>() {{
            add(Ingredient.builder().id(1L).ingredientName("Мясо").price(800).remainder(1500).measurement(Measurement.KILOGRAM).build());
            add(Ingredient.builder().id(2L).ingredientName("Картошка").price(300).remainder(777).measurement(Measurement.KILOGRAM).build());
            add(Ingredient.builder().id(3L).ingredientName("Рис").price(350).remainder(1111).measurement(Measurement.KILOGRAM).build());
            add(Ingredient.builder().id(4L).ingredientName("Чеснок").price(15).remainder(500).measurement(Measurement.GRAM).build());
            add(Ingredient.builder().id(5L).ingredientName("Помидор").price(13).remainder(500).measurement(Measurement.GRAM).build());
        }};
    }

    public List<IngredientDTO> getIngredientsDTO() {
        return new ArrayList<>() {{
            add(IngredientDTO.builder().id(1L).ingredientName("Мясо").price(800).remainder(1500).measurement(Measurement.KILOGRAM).build());
            add(IngredientDTO.builder().id(2L).ingredientName("Картошка").price(300).remainder(777).measurement(Measurement.KILOGRAM).build());
            add(IngredientDTO.builder().id(3L).ingredientName("Рис").price(350).remainder(1111).measurement(Measurement.KILOGRAM).build());
            add(IngredientDTO.builder().id(4L).ingredientName("Чеснок").price(15).remainder(500).measurement(Measurement.GRAM).build());
            add(IngredientDTO.builder().id(5L).ingredientName("Помидор").price(13).remainder(500).measurement(Measurement.GRAM).build());
        }};
    }
}