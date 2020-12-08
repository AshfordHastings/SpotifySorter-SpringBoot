package spotify.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import spotify.AuthConfigs;
import spotify.AuthorizationService;

public class AuthController {
	@Autowired
	AuthorizationService authService;

	@RequestMapping("/authorize")
	public String handleAuthenticationCode(
			@RequestParam(required = false) String code, 
			@RequestParam(required = false) String state, 
			@RequestParam(required = false) String error,
			Model model,HttpSession session) {
		
		authService.setCode(code, state);
		if(authService.getAuthorizationTokens() == null) {
			System.out.println("There has been an error in retrieving the token.");
		}
		
		return "main";
	}
}
