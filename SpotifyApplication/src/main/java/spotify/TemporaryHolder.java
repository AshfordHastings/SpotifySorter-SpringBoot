package spotify;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class TemporaryHolder {
	public static List<SortableAlbum> sortedList = new ArrayList<SortableAlbum>();
}
