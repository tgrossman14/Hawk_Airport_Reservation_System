package controllers;

import io.javalin.http.Context;
import services.UserService;
import services.UserServiceImpl;

public class TestController {
	static UserService userService = new UserServiceImpl();
	
	public static void testCon(Context context) {
		System.out.println("I was hit!");
		userService.getOne();
	}
}
