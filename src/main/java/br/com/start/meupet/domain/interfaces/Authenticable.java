package br.com.start.meupet.domain.interfaces;

import br.com.start.meupet.domain.valueobjects.Email;

public interface Authenticable {
	
	Number getId();
	
	String getName();

	Email getEmail();

	String getSenha();

}
