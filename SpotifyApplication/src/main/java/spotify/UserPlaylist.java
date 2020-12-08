package spotify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Playlist;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;

import lombok.Data;

@Data
public class UserPlaylist {
	private String playlistId;
	private Playlist spotifyPlaylist;
	
	   public Playlist pullPlaylist() throws Exception {
		   //changes this value
//		    if(request.getClientToken() == null) {
		 //   	request.getClientAuthorization();
	//	    } //request.getClientAuthorization();
		   
		   String cutFromInput = new String("spotify:playlist:");
			   if(playlistId.substring(0, cutFromInput.length()).equals(cutFromInput)) {
		    		playlistId = playlistId.substring(cutFromInput.length());
		    	}
			   
		    try {
		    	return request.getSpotifyPlaylist(playlistId);
		    }
		    catch (Exception e){
		    	throw new Exception("PlaylistInputError");
		    }
	   }
	   
	public List<SortableAlbum> getPlaylistAlbums() {
		List<PlaylistTrack> playlistTracks = convertToTracklist();
			
		ArrayList<SortableAlbum> albumList = new ArrayList<>();
		for(int i = 0; i < playlistTracks.size(); i++) {
			SortableAlbum albumAtIndex = SortableAlbum.convertFrom(playlistTracks.get(i));
			if(!albumList.contains(albumAtIndex)) {
				albumList.add(albumAtIndex);
			}
		}
		return albumList;
	}
	
	   public List<PlaylistTrack> convertToTracklist() {
		   List<PlaylistTrack> trackList = new ArrayList<PlaylistTrack>();
		   int trackIndex = 0;
		   Paging<PlaylistTrack> trackPages = spotifyPlaylist.getTracks();
		   while(trackIndex < trackPages.getTotal()) {
			   PlaylistTrack[] tempTrackList = trackPages.getItems();
			   trackList.addAll(Arrays.asList(tempTrackList));
			   trackIndex = trackIndex + trackPages.getLimit();
			   	try {
				   trackPages = request.getSpotifyApi()
			   			.getPlaylistsItems(spotifyPlaylist.getId())
			   			.offset(trackIndex)
			   			.build().execute();
			   	}
			   	catch(Exception e) {
					   break;
				}
			   
		   }
		   
		  // trackList.addAll(Arrays.asList(trackPages.getItems()));
		   return trackList;
		   
	   }
	   
	   
}
