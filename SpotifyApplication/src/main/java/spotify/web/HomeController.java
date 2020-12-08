package spotify.web;

import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import spotify.AuthorizationService;

@Controller
public class HomeController implements WebMvcConfigurer {
	@Autowired
	AuthorizationService authService;
	
	@GetMapping("/")
	public String setLoginPage(Model model) throws URISyntaxException {
		model.addAttribute("authUrl", authService.getAuthorizationCode());
		return "home";
	}
	

}