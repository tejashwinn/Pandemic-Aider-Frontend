package pandemic.aider.server;

/**
 * todo updated @14:30 10-06-21 @author tejashwin
 * this package is gonna consist of all the new features which includes the client side and server side
 * todo:
 * client side consists:
 * * * controller: Contains all the controller for the java fx
 * * * pandemic.oldfx.dao: data transfer between the ui and the pandemic.oldfx.service
 * * * pandemic.oldfx.model: consists of all the pandemic.oldfx.model(Classes) required for the client side
 * * * resources: consists of resources for the ui eg.: picture
 * * * pandemic.oldfx.service: connection between the client and the server
 * * * ui: the java fx file such as fxml and css  for the ui
 * <p>
 * todo:
 * server side consists:
 * * * pandemic.oldfx.dao: to access database and its contents
 * * * pandemic.oldfx.model: consists the classes for the pandemic.oldfx.service
 * * * pandemic.oldfx.service: this will help us to connect to the clients
 * <p>
 * !For server use multithreading and each thread will handle different request
 * !1st thread: Check for username and user exists
 * !2st thread: add credentials
 * !3rd thread: search posts
 * !4th thread: view according to pincode
 * !5th thread: delete post or user
 */