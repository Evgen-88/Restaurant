package com.itrex.kaliaha.util;

import com.itrex.kaliaha.dto.*;
import com.itrex.kaliaha.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DTOParser {
    public static DishDTO toDTO(Dish dish) {
        return DishDTO.builder()
                .id(dish.getId())
                .dishName(dish.getDishName())
                .price(dish.getPrice())
                .dishGroup(dish.getDishGroup())
                .dishDescription(dish.getDishDescription())
                .imagePath(dish.getImagePath())
                .build();
    }

    public static List<DishIngredientDTO> toIngredientDTO(List<Ingredient> ingredients, List<Composition> compositions) {
        List<DishIngredientDTO> dishIngredientDTOList = new ArrayList<>();
        for (int index = 0; index < compositions.size(); index++) {
            Ingredient ingredient = ingredients.get(index);
            Composition composition = compositions.get(index);
            DishIngredientDTO dishIngredientDTO = toDTO(ingredient, composition);
            dishIngredientDTO.setPrice(calculatePriceIngredient(ingredient, composition));
            dishIngredientDTOList.add(dishIngredientDTO);
        }
        return dishIngredientDTOList;
    }

    private static int calculatePriceIngredient(Ingredient ingredient, Composition composition) {
        int partMeasurementPrice = ingredient.getPrice() * composition.getQuantity();
        return partMeasurementPrice / ingredient.getMeasurement().getValue() ;
    }

    public static DishIngredientDTO toDTO(Ingredient ingredient, Composition composition) {
        return DishIngredientDTO.builder()
                .ingredientId(ingredient.getId())
                .ingredientName(ingredient.getIngredientName())
                .measurement(ingredient.getMeasurement())
                .compositionId(composition.getId())
                .quantity(composition.getQuantity())
                .build();
    }

    public static DishListDTO toDishListDTO(Dish dish) {
        return DishListDTO.builder()
                .id(dish.getId())
                .dishName(dish.getDishName())
                .price(dish.getPrice())
                .build();
    }

    public static Dish fromDTO(DishSaveDTO dishSaveDTO) {
        Dish dish = new Dish();
        dish.setId(dishSaveDTO.getId());
        dish.setDishName(dishSaveDTO.getDishName());
        dish.setPrice(dishSaveDTO.getPrice());
        dish.setDishDescription(dishSaveDTO.getDishDescription());
        dish.setDishGroup(dishSaveDTO.getDishGroup());
        dish.setImagePath(dishSaveDTO.getImagePath());
        return dish;
    }

    public static Ingredient fromDTO(IngredientSaveDTO ingredientSaveDTO) {
        Ingredient ingredient = new Ingredient();
        ingredient.setIngredientName(ingredientSaveDTO.getIngredientName());
        ingredient.setRemainder(ingredient.getRemainder());
        ingredient.setPrice(ingredient.getPrice());
        ingredient.setMeasurement(ingredientSaveDTO.getMeasurement());
        return ingredient;
    }

    public static IngredientSaveDTO toDTO(Ingredient ingredient) {
        return IngredientSaveDTO.builder()
                .id(ingredient.getId())
                .ingredientName(ingredient.getIngredientName())
                .measurement(ingredient.getMeasurement())
                .price(ingredient.getPrice())
                .remainder(ingredient.getRemainder())
                .build();
    }

    public static List<IngredientSaveDTO> toIngredientListDTO(List<Ingredient> ingredients) {
        return ingredients.stream().map(DTOParser::toDTO).collect(Collectors.toList());
    }

    public static RoleDTO toDTO(Role role) {
        return RoleDTO.builder()
                .id(role.getId())
                .roleName(role.getRoleName())
                .build();
    }

    public static OrderUserDTO toDTO(Order order, List<Dish> dishes) {
        return OrderUserDTO.builder()
                .userId(order.getUser().getId())
                .login(order.getUser().getLogin())
                .orderId(order.getId())
                .price(order.getPrice())
                .date(order.getDate())
                .address(order.getAddress())
                .orderStatus(order.getOrderStatus())
                .orderedDishes(toDishListDTO(dishes))
                .build();
    }

    private static List<DishListDTO> toDishListDTO(List<Dish> dishes) {
        return dishes.stream()
                .map(DTOParser::toDishListDTO)
                .collect(Collectors.toList());
    }

    public static OrderListDTO toDTO(Order order) {
        return OrderListDTO.builder()
                .userId(order.getUser().getId())
                .orderId(order.getId())
                .price(order.getPrice())
                .date(order.getDate())
                .address(order.getAddress())
                .orderStatus(order.getOrderStatus())
                .build();
    }

    public static List<OrderListDTO> toOrderListDTO(List<Order> orders) {
        return orders.stream()
                .map(DTOParser::toDTO)
                .collect(Collectors.toList());
    }

    public static Order fromDTO(OrderUserDTO orderDTO) {
        Order order = new Order();
        order.setId(orderDTO.getOrderId());
        order.setDate(orderDTO.getDate());
        order.setOrderStatus(orderDTO.getOrderStatus());
        order.setAddress(orderDTO.getAddress());
        order.setPrice(orderDTO.getPrice());
        order.setUser(new User(orderDTO.getUserId()));
        return order;
    }

    public static List<UserListDTO> toUserListDTO(List<User> users) {
        return users.stream().map(user ->
                UserListDTO.builder()
                        .id(user.getId())
                        .lastName(user.getLastName())
                        .firstName(user.getFirstName())
                        .login(user.getLogin())
                        .address(user.getAddress())
                        .build()
        ).collect(Collectors.toList());
    }

    public static UserDTO toDTO(User user, List<Role> roles, List<Order> orders) {
        return UserDTO.builder()
                .id(user.getId())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .login(user.getLogin())
                .password(user.getPassword())
                .address(user.getAddress())
                .roles(toRoleListDTO(roles))
                .orders(toOrderListDTO(orders))
                .build();
    }

    public static List<RoleDTO> toRoleListDTO(List<Role> roles) {
        return roles.stream()
                .map(DTOParser::toDTO)
                .collect(Collectors.toList());
    }

    public static Role fromDTO(RoleDTO roleDTO) {
        Role role = new Role();
        role.setId(roleDTO.getId());
        role.setRoleName(roleDTO.getRoleName());
        return role;
    }

    public static List<Role> fromRoleListDTO(List<RoleDTO> roles) {
        return roles.stream()
                .map(DTOParser::fromDTO)
                .collect(Collectors.toList());
    }

    public static Role fromDTO(Long id) {
        Role role = new Role();
        role.setId(id);
        return role;
    }

    public static List<Role> fromRoleListIdDTO(List<Long> rolesId) {
        return rolesId.stream()
                .map(DTOParser::fromDTO)
                .collect(Collectors.toList());
    }

    public static User fromDTO(UserSaveDTO userSaveDTO) {
        User user = new User();
        user.setFirstName(userSaveDTO.getFirstName());
        user.setLastName(userSaveDTO.getLastName());
        user.setLogin(userSaveDTO.getLogin());
        user.setPassword(userSaveDTO.getPassword());
        user.setAddress(userSaveDTO.getAddress());
        return user;
    }
}