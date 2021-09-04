package com.shopme.admin.user;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordEncoderTest {
    @Test
    public void testEncodePassword(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "tam0410";
        //Password đã được mã hóa
        String encodePassword = passwordEncoder.encode(rawPassword);

        System.out.println(encodePassword); //$2a$10$mLI.KnOMKU2HjAhZoHUKl.8MuGiAqt0AobB3QpAB0ZEt8chMh6n7q

        boolean matches =  passwordEncoder.matches(rawPassword, encodePassword);

        assertThat(matches).isTrue();
    }
}
