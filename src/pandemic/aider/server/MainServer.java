package pandemic.aider.server;

import pandemic.aider.server.service.ServerSidePostService;
import pandemic.aider.server.service.ServerSideUserServer;

public class MainServer {
	public static void main(String[] args) {
		
		ServerSideUserServer.runUserService();
		ServerSidePostService.runServerPost();
	}
}
