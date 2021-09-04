package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //Để run the test on the real database(dữ liệu thực)
@Rollback(false) //Hibernate will commit changes to underlying database after test
public class RoleRepositoryTests {
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testCreateFirstRole(){
        Role roleAdmin = new Role("Admin", "manage everything");
        Role saveRepo = roleRepository.save(roleAdmin);

        assertThat(saveRepo.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateRestRole(){
        Role roleSalesPerson = new Role("Sales person","manage product price, customer, shipping, orders and sales report");
        Role roleEditor = new Role("Editor","manage categories, brands, products, articles and menu");
        Role roleShipper = new Role("Shipper","view products, view orders, and update order status");
        Role roleAssistant = new Role("Assistant","manage question and reviews");

        List<Role> list = Stream.of(roleSalesPerson,roleEditor, roleShipper, roleAssistant).collect(Collectors.toList());
        roleRepository.saveAll(list);
    }
}
