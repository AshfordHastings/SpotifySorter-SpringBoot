package spotify.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.wrapper.spotify.SpotifyApi;

import lombok.extern.slf4j.Slf4j;
import spotify.AuthorizationService;
import spotify.SortableAlbum;
import spotify.TemporaryHolder;
import spotify.UserPlaylist;

@Slf4j
@Controller
@RequestMapping("/mainpage")
public class PlaylistController {
	@Autowired
	AuthorizationService authorizationService;
	
	private String authorizationCode;
	private String authorizationState;
	
	@GetMapping
	public String getUserPlaylist(Model model) {
		model.addAttribute("playlist", new UserPlaylist());
		model.addAttribute("sortedAlbums", TemporaryHolder.sortedList);
		return "sort";
	}
	
	@PostMapping
	public String processPlaylist(UserPlaylist playlist) {
		try {
			playlist.pullPlaylist();
			
			List<SortableAlbum> albums = playlist.getPlaylistAlbums();
			Collections.sort(albums);
			TemporaryHolder.sortedList = albums;
			return "sort";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	@RequestMapping("/request")
	@ResponseBody
		@GetMapping
		public String getAuthorizationCode() throws URISyntaxException {
			System.out.println("Getting authorizationCode");
			//Document doc = Jsoup.connect(authorizationService.getAuthorizationCode().toString()).get();
			//doc
			String authCode = authorizationService.getAuthorizationCode();
			System.out.println("Auth Code is " + authCode);
			return authCode;
		}
	
	
	@RequestMapping("/response")
		@GetMapping
		public String processSpotifyResponse(@RequestParam(required = false) String code, @RequestParam(required = false) String state, @RequestParam(required = false) String error) {
			System.out.println("About to parse response");
			authorizationCode = code;
			authorizationState = state;
			authorizationService.getAuthorizationTokens(code, state);
			return "sort";
	}
	
	
}
