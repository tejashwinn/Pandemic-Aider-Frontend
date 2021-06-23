package pandemic.aider;

import pandemic.aider.client.ui.main.Main;
import pandemic.aider.server.service.ServerSidePostService;
import pandemic.aider.server.service.UserServer;

public class MasterMain {
	public static void main(String[] args) {
		UserServer.runUserService();
		ServerSidePostService.runServerPost();
		Main.main(args);
		
	}
}
