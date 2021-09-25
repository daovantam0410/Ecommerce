package com.shopme.admin.security;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ShopmeUserDetails implements UserDetails {

    private User user;

    public ShopmeUserDetails(User user) {
        this.user = user;
    }

    /*
    **Return the authorities granted to the user
    */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //Get list role of user
        Set<Role> roles = user.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role: roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    /*
    **Returns the password used to authenticate the user
    */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /*
    **Returns the username used to authenticate the user
    */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /*
    **Returns
    * true: if the user's account is valid (ie non-expired)
    * false: if no longer valid (ie expired)
    */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /*
    **Returns
    * true: if the user is not locked
    * false: otherwise
    */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /*
    **Returns
    * true: if the user's credentials are valid (ie non-expired)
    * false: if no longer valid (ie expired)
    */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /*
    **Returns
    * true: if the user is enabled
    * false: otherwise
    */
    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
