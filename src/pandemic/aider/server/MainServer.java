package pandemic.aider.server;

import pandemic.aider.server.service.ServerSidePostService;
import pandemic.aider.server.service.UserServer;

public class MainServer {
	public static void main(String[] args) {
		
		UserServer.runUserService();
		ServerSidePostService.runServerPost();
	}
}
