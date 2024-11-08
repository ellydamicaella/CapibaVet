package br.com.start.meupet.domain.factory;

import br.com.start.meupet.domain.entities.User;
import br.com.start.meupet.domain.valueobjects.Email;
import br.com.start.meupet.domain.valueobjects.PhoneNumber;

import java.util.Map;

public final class UserFactory {

    public static User create(Map<String, Object> lista) {
        User user = new User();

        if (lista == null || lista.isEmpty()) {
            return user;
        }

        user.setId((Long) lista.get("id"));
        user.setName((String) lista.get("nome_completo"));
        user.setEmail(new Email((String) lista.get("email")));
        user.setPassword((String) lista.get("senha"));
        user.setPhoneNumber(new PhoneNumber((String) lista.get("telefone")));

        return user;

    }
}
