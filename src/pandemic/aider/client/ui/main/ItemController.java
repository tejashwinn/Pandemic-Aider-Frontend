package pandemic.aider.client.ui.main;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import pandemic.aider.client.model.PostDetails;

public class ItemController {
	
	@FXML
	private Label usernameLabelView;
	
	@FXML
	private Label pincodeLabel;
	
	@FXML
	private Label postLabel;
	
	@FXML
	private Label timeLabel;
	
	private PostDetails post;
	
	public void setData(PostDetails post) {
		usernameLabelView.setText(post.getUserUniqueId());
		pincodeLabel.setText(post.getPincode());
		postLabel.setText(post.getContent());
		timeLabel.setText(post.getTime());
	}
}
