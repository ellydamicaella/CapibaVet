package br.com.start.meupet.domain.factory;

import java.util.Map;

import br.com.start.meupet.domain.entities.Usuario;
import br.com.start.meupet.domain.valueobjects.Email;
import br.com.start.meupet.domain.valueobjects.Telefone;

public final class UsuarioFactory {

	public static Usuario create(Map<String, Object> lista) {
		Usuario user = new Usuario();

		if (lista == null || lista.isEmpty()) {
			return user;
		}

		user.setId((Long) lista.get("id"));
		user.setNomeCompleto((String) lista.get("nome_completo"));
		user.setEmail((Email) lista.get("email"));
		user.setSenha((String) lista.get("senha"));
		user.setTelefone((Telefone) lista.get("telefone"));

		return user;

	}
}
