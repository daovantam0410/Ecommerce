package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //Để run the test on the real database(dữ liệu thực)
@Rollback(false) //Hibernate will commit changes to underlying database after test
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    /*
    This class is provided by Spring Data JPA for Unit testing with repository
    */
    @Autowired
    private TestEntityManager testEntityManager;

    /*
    Add user with one role
    */
    @Test
    public void testCreateUserWithOneRole(){
        Role roleAdmin = testEntityManager.find(Role.class, 1);
        User user = new User("tamdv@gmail.com","123456","Tâm","Đào Văn");
        user.addRole(roleAdmin);

        User saveUser = userRepository.save(user);

        assertThat(saveUser.getId()).isGreaterThan(0);
    }

    /*
    Add user with two role
    */
    @Test
    public void testCreateUserWithTwoRole(){
        User user = new User("hungcv@gmail.com","hung2021","Hưng","Cam Việt");
        Role roleEditor = new Role(3);
        Role roleAssistant = new Role(5);

        user.addRole(roleEditor);
        user.addRole(roleAssistant);

        User saveUser = userRepository.save(user);
        //Assertion API from lib org.assertj.core.api.Assertions.assertThat
        assertThat(saveUser.getId()).isGreaterThan(0);
    }

    /*
    Get list all user
    */
    @Test
    public void testListAllUser(){
        Iterable<User> listUsers = userRepository.findAll();
        listUsers.forEach(user -> System.out.println(user));

    }

    /*
    Get user by id
    */
    @Test
    public void testGetUserByID(){
        User user = userRepository.findById(1).get();
        System.out.println(user);
        assertThat(user).isNotNull();
    }

    /*
    update user by id
    */
    @Test
    public void testUpdateUserDetails(){
        User user = userRepository.findById(1).get();
        user.setEnabled(true);
        user.setEmail("daovantam0410@gmail.com");

        userRepository.save(user);
    }

    /*
    update role for user
    */
    @Test
    public void testUpdateUserRoles(){
        User user = userRepository.findById(2).get();
        Role roleEditor = new Role(3);
        Role roleSalesPerson = new Role(2);

        user.getRoles().remove(roleEditor);
        user.addRole(roleSalesPerson);

        userRepository.save(user);
    }

    /*
    delete user
    */
    @Test
    public void testDeleteUser(){
        Integer userId = 2;
        userRepository.deleteById(userId);
    }

    @Test
    public void testGetUserByEmail(){
        String email = "daututhietbi@gmail.com";
        User user = userRepository.getUserByEmail(email);

        assertThat(user).isNotNull();
    }

    @Test
    public void testCountById(){
        Integer id = 1;
        Long countById = userRepository.countById(id);

        assertThat(countById).isNotNull().isGreaterThan(0);
    }

    /*
    update disabled user
    */
    @Test
    public void testDisabledUser(){
        Integer id = 1;
        userRepository.updateEnabledStatus(id, false);
    }

    /*
    update enabled user
    */
    @Test
    public void testEnabledUser(){
        Integer id = 3;
        userRepository.updateEnabledStatus(id, true);
    }

    @Test
    public void testListFirstPage(){
        int pageNumber = 0;
        int pageSize = 4;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page =  userRepository.findAll(pageable);

        List<User> users = page.getContent();
        users.forEach(user -> System.out.println(user));

        assertThat(users.size()).isEqualTo(pageSize);
    }

}
