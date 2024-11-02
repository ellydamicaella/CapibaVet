package br.com.start.meupet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.start.meupet.dto.AccessDTO;
import br.com.start.meupet.dto.AuthenticationDTO;
import br.com.start.meupet.security.jwt.JwtUtils;

@Service
public class AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtils jwtUtils;

	public AccessDTO login(AuthenticationDTO authDTO) {
		
		try {
			// Cria mecanismo de credencial para o Spring
			UsernamePasswordAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(authDTO.email(),
					authDTO.password());

			// Prepara mecanismo para autenticacao
			Authentication authentication = authenticationManager.authenticate(userAuth);

			// Busca usuario logado
			UserDetailsImpl userAuthenticate = (UserDetailsImpl) authentication.getPrincipal();
			
			String token = jwtUtils.generateTokenFromUserDetailsImpl(userAuthenticate);
			
			AccessDTO accessDTO = new AccessDTO(token);
			return accessDTO;
		} catch (BadCredentialsException e) {
			// TODO: handle exception
		}
		
		return null;	
	}
}
