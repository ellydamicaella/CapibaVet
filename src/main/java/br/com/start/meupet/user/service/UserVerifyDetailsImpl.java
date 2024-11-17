package br.com.start.meupet.common.service;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.start.meupet.common.interfaces.Authenticable;

@Getter
public class UserVerifyDetailsImpl implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String name;

    private final String email;

    private final String password;

    private final String phoneNumber;

    public static UserVerifyDetailsImpl build(Authenticable user) {
        return new UserVerifyDetailsImpl(
                user.getName(),
                user.getEmail().toString(),
                user.getPassword(),
                user.getPhoneNumber().toString());
    }

    public UserVerifyDetailsImpl(String name, String email, String password, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
