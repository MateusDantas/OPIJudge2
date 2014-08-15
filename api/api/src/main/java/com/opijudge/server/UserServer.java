package com.opijudge.server;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.opijudge.controller.UserController;
import com.opijudge.controller.auth.AuthTokenManager;
import com.opijudge.models.User;
import com.opijudge.server.response.UserResponse;

import static com.opijudge.controller.util.Constants.*;

@Path("/users")
public class UserServer {

	@Path("/createuser")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public int createUser(@QueryParam("name") String name,
			@QueryParam("username") String username,
			@QueryParam("password") String password,
			@QueryParam("email") String email) {

		int responseStatus = UserController.registerRegularUser(name, username,
				password, email);

		return responseStatus;
	}

	@Path("/loginuser")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public UserResponse loginUser(@QueryParam("username") String username,
			@QueryParam("password") String password) {

		int responseStatus = UserController.loginUser(username, password);
		User user = UserController.getUserByUsername(username);
		String token = AuthTokenManager.getTokenUser(username);

		UserResponse userResponse = new UserResponse(user, token,
				responseStatus);

		return userResponse;
	}

	@Path("/logoutuser")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public int logoutUser(@QueryParam("username") String username,
			@QueryParam("token") String token) {

		int responseStatus = UserController.logoutUser(username, token);

		return responseStatus;
	}

	@Path("/changepassword")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public int changePassword(@QueryParam("username") String username,
			@QueryParam("token") String token,
			@QueryParam("new_password") String newPassword) {

		int responseStatus = UserController.changePassword(username, newPassword, token);
		
		return responseStatus;
	}

	@Path("/getuserbyid")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public UserResponse getUserById(@QueryParam("username") String username,
			@QueryParam("token") String token, @QueryParam("user_id") int userId) {

		int responseStatus;
		User user = new User();

		if (!AuthTokenManager.isUserAuthentic(token, username))
			responseStatus = UNAUTHORIZED;
		else {

			responseStatus = OK;
			user = UserController.getUserById(userId);
		}

		UserResponse userResponse = new UserResponse(user, "", responseStatus);

		return userResponse;
	}

	@Path("/getuserbyusername")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public UserResponse getUserByUsername(
			@QueryParam("username") String username,
			@QueryParam("token") String token,
			@QueryParam("user_username") String userUsername) {

		int responseStatus;
		User user = new User();

		if (!AuthTokenManager.isUserAuthentic(token, username))
			responseStatus = UNAUTHORIZED;
		else {

			responseStatus = OK;
			user = UserController.getUserByUsername(userUsername);
		}

		UserResponse userResponse = new UserResponse(user, "", responseStatus);

		return userResponse;
	}

	@Path("/getuserbyemail")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public UserResponse getUserByEmail(@QueryParam("username") String username,
			@QueryParam("token") String token,
			@QueryParam("user_email") String userEmail) {

		int responseStatus;
		User user = new User();

		if (!AuthTokenManager.isUserAuthentic(token, username))
			responseStatus = UNAUTHORIZED;
		else {

			responseStatus = OK;
			user = UserController.getUserByEmail(userEmail);
		}

		UserResponse userResponse = new UserResponse(user, "", responseStatus);

		return userResponse;
	}

}
