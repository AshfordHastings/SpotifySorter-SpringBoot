package spotify;

import java.net.URI;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.model_objects.AbstractModelObject;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.Album;
import com.wrapper.spotify.model_objects.specification.AlbumSimplified;
import com.wrapper.spotify.model_objects.specification.Playlist;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import com.wrapper.spotify.requests.data.albums.GetAlbumRequest;
import com.wrapper.spotify.requests.data.playlists.GetPlaylistRequest;
import com.wrapper.spotify.requests.data.tracks.GetTrackRequest;


public class request {
	
	private final static String clientID = "99a639e0fbac44249faa37cfeb8d0250";
	private final static String clientSecret = "6cf771eed4a047428057edcef8653ce8";
	private final static URI redirectURI = SpotifyHttpManager.makeUri("https://en.wikipedia.org/wiki/Throbbing_Gristle");
	
	private final static SpotifyApi spotifyApi = new SpotifyApi.Builder()
	.setClientId(clientID)
	.setClientSecret(clientSecret)
	.build();
	
	
	/**	Obtains authorization through the Client Credentials Flow.
	 * Cannot access user resources or refresh tokens, but does require secret key.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		System.out.println("Hello world!");
	}
	
	public static SpotifyApi getClientAuthorization() {
		SpotifyApi spotifyApi = new SpotifyApi.Builder()
				.setClientId(clientID)
				.setClientSecret(clientSecret)
				.build();
				
		
		//Gets an access token from Spotify
		 ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
				    .build();
		    try {
		    	// Sets access token for client credentials 
		        ClientCredentials clientCredentials = clientCredentialsRequest.execute();
		        String clientAccessToken = clientCredentials.getAccessToken();
		    	System.out.println("Authorization through Client Credentials successful.");
		        System.out.println("Expires in: " + clientCredentials.getExpiresIn());
		        return spotifyApi;
		      } catch (Exception e) {
		    	 System.out.println(e.toString());
		    	 return null;
		      }
	}
	
	
	public static Playlist getSpotifyPlaylist(String playlistId) throws Exception {
		SpotifyApi spotifyApii = request.getClientAuthorization();
		
		/// test
		GetTrackRequest request = spotifyApii.getTrack("6WuRo5MdVrpKCl6lkKIAlp").build();
		try {
			request.execute();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		/// test
		GetPlaylistRequest playlistRequest = spotifyApii.getPlaylist(playlistId).build();

		try {
			Playlist playlist = playlistRequest.execute();
			return playlist;
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception thrown in getPlaylist.");
			throw new Exception("PlaylistException");
		}
		
	}
	
	public static Album getSpotifyAlbum(String albumId) throws Exception {
		GetAlbumRequest albumRequest = spotifyApi.getAlbum(albumId).build();
		try {
			Album album = albumRequest.execute();
			return album;
		}
		catch (Exception e) {
			e.toString();
			System.out.println("Exception thrown in getSpotifyAlbum.");
			throw new Exception("AlbumException");
		}
		
	}
	
	public static SpotifyApi getSpotifyApi() {
		return spotifyApi;
	}

	public static SpotifyApi setUserToken(String accessToken) {
		spotifyApi.setAccessToken(accessToken);
		return spotifyApi;
	}
}
