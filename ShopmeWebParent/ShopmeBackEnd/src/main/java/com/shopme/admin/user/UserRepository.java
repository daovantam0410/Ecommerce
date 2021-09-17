package com.shopme.admin.user;

import com.shopme.common.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

    /*
    Check email là duy nhất
    */
    @Query("SELECT u FROM User u WHERE u.email = :email")
    public User getUserByEmail(@Param("email") String email);

    public Long countById(Integer id);

    /*
    Update user enabled status
    */
    @Query("UPDATE User u SET u.enabled = ?2 WHERE  u.id = ?1")
    @Modifying
    public void updateEnabledStatus(Integer id, boolean enabled);

    @Query("SELECT u FROM User u WHERE CONCAT(u.id, ' ', u.email, ' ', u.firstName, ' ',"+" u.lastName) LIKE %?1%")
    public Page<User> findAll(String keyword, Pageable pageable);
}
