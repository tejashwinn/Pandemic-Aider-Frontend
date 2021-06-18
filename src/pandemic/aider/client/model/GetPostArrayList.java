package pandemic.aider.client.model;

import java.util.ArrayList;
import java.util.List;

public class GetPostArrayList extends PostDetails {
	
	private List<PostDetails> postsList = new ArrayList<>();
	
	public List<PostDetails> getPostsList() {
		return postsList;
	}
	
	public void setPostsList(List<PostDetails> postsList) {
		this.postsList = postsList;
	}
	
	public GetPostArrayList() {
		postsList.addAll(getList());
	}
	
	public List<PostDetails> getList() {
		List<PostDetails> temp = new ArrayList<>();
		PostDetails post;
		for (int i = 0; i < 100; i++) {
			post = new PostDetails();
			post.setContent("Content: " + i);
			post.setPincode("Num: " + i);
			post.setUserUniqueId("Username: " + i);
			post.setTime("Time: " + i);
			temp.add(post);
		}
		return temp;
	}
}
