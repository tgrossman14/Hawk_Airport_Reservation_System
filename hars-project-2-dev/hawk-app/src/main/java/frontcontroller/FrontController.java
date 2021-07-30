package frontcontroller;

import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * FrontController class to perform middleware and define a dispatcher
 * */
public class FrontController {
	Javalin app;
	Dispatcher dispatcher;
	
	public FrontController(Javalin app) {
		this.app = app;
		
		
		this.app.before("/api/*", FrontController::checkAllRequests);
		
		this.dispatcher = new Dispatcher(app);
	}
	
	
	public static void checkAllRequests(Context context) {
		System.out.println("Middleware has been hit");
	}

}
