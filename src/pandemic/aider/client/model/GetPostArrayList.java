package pandemic.aider.client.model;

import java.util.ArrayList;

public class GetPostArrayList extends PostDetails {
	
	private ArrayList<PostDetails> postsList = new ArrayList<>();
	
	public ArrayList<PostDetails> getPostsList() {
		return postsList;
	}
	
	public void setPostsList(ArrayList<PostDetails> postsList) {
		this.postsList = postsList;
	}
}
