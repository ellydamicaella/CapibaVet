package br.com.start.meupet.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.start.meupet.domain.entities.User;
import br.com.start.meupet.domain.interfaces.Authenticable;
import br.com.start.meupet.domain.valueobjects.Email;

public class UserDetailsImpl implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Number id;

	private String name;

	private Email email;

	private String password;

	public static UserDetailsImpl build(Authenticable usuario) {
		return new UserDetailsImpl(usuario.getId(), usuario.getName(), usuario.getEmail(), new ArrayList<>());
	}
	
	public UserDetailsImpl(Number id, String name, Email email, Collection<? extends GrantedAuthority> authorithies) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.authorithies = authorithies;
	}

	private Collection<? extends GrantedAuthority> authorithies;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorithies;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.email.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
