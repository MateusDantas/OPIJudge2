package com.opijudge.server;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.opijudge.controller.ProblemController;
import com.opijudge.controller.UserController;
import com.opijudge.controller.auth.AuthTokenManager;
import com.opijudge.controller.validate.UserValidate;
import com.opijudge.models.User;
import com.opijudge.server.consume.UserConsumes;
import com.opijudge.server.response.RankingResponse;
import com.opijudge.server.response.UnauthorizedException;
import com.opijudge.server.response.UserResponse;
import com.opijudge.server.util.UserServerValidate;

import static com.opijudge.controller.util.Constants.*;

/**
 */
@Path("/users")
public class UserServer {

	/**
	 * Method createUser.
	 * 
	 * @param name
	 *            String
	 * @param username
	 *            String
	 * @param password
	 *            String
	 * @param email
	 *            String
	 * @return int
	 */
	@Path("/createuser")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public int createUser(UserConsumes userConsumes) {

		int responseStatus = UserController.registerRegularUser(
				userConsumes.getName(), userConsumes.getUsername(),
				userConsumes.getPassword(), userConsumes.getEmail());

		return responseStatus;
	}

	/**
	 * Method loginUser.
	 * 
	 * @param username
	 *            String
	 * @param password
	 *            String
	 * @return UserResponse
	 */
	@Path("/loginuser")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public UserResponse loginUser(UserConsumes userConsumes) {

		int responseStatus = OK;

		User user = UserController
				.getUserByUsername(userConsumes.getUsername());

		if (user != null
				&& (user.getIsBlocked() == 1 && user.getAccessLevel() != ADMIN_ACCESS_LEVEL))
			return new UserResponse(null, null, USER_IS_BLOCKED);

		if (AuthTokenManager.getTokenUser(userConsumes.getUsername()) == null)
			responseStatus = UserController.loginUser(
					userConsumes.getUsername(), userConsumes.getPassword());

		String token = AuthTokenManager
				.getTokenUser(userConsumes.getUsername());

		if (user == null
				|| !UserValidate.checkPassword(userConsumes.getPassword(),
						user.getHashedPassword()))
			responseStatus = UNAUTHORIZED;

		if (responseStatus == UNAUTHORIZED) {
			responseStatus = UNAUTHORIZED;
			user = null;
			token = null;
		}

		UserResponse userResponse = new UserResponse(user, token,
				responseStatus);

		System.out.println(user);
		return userResponse;
	}

	/**
	 * Method logoutUser.
	 * 
	 * @param username
	 *            String
	 * @param token
	 *            String
	 * @return int
	 */
	@Path("/logoutuser")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public int logoutUser(UserConsumes userConsumes) {

		if (!AuthTokenManager.isUserAuthentic(userConsumes.getToken(),
				userConsumes.getUsername()))
			throw new UnauthorizedException();

		int responseStatus = UserController.logoutUser(
				userConsumes.getUsername(), userConsumes.getToken());

		return responseStatus;
	}

	/**
	 * Method changePassword.
	 * 
	 * @param username
	 *            String
	 * @param token
	 *            String
	 * @param newPassword
	 *            String
	 * @return int
	 */
	@Path("/changepassword")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public int changePassword(UserConsumes userConsumes) {

		if (!AuthTokenManager.isUserAuthentic(userConsumes.getToken(),
				userConsumes.getUsername()))
			throw new UnauthorizedException();

		int responseStatus = UserController.changePassword(
				userConsumes.getUsername(), userConsumes.getNew_password(),
				userConsumes.getToken());

		return responseStatus;
	}

	/**
	 * Method getUserAccessLevel.
	 * 
	 * @param username
	 *            String
	 * @param token
	 *            String
	 * @return int
	 */
	@Path("/getuseraccesslevel")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public int getUserAccessLevel(UserConsumes userConsumes) {

		if (!AuthTokenManager.isUserAuthentic(userConsumes.getToken(),
				userConsumes.getUsername()))
			throw new UnauthorizedException();

		return UserController.getUserAccessLevel(userConsumes.getUsername());
	}

	/**
	 * Method getUserById.
	 * 
	 * @param username
	 *            String
	 * @param token
	 *            String
	 * @param userId
	 *            int
	 * @return UserResponse
	 */
	@Path("/getuserbyid")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public UserResponse getUserById(UserConsumes userConsumes) {

		if (!AuthTokenManager.isUserAuthentic(userConsumes.getToken(),
				userConsumes.getUsername()))
			throw new UnauthorizedException();

		int responseStatus;
		User user = new User();

		if (!UserServerValidate.hasAdminPrivileges(userConsumes.getToken(),
				userConsumes.getUsername()))
			responseStatus = UNAUTHORIZED;
		else {

			responseStatus = OK;
			user = UserController.getUserById(userConsumes.getUser_id());
		}

		UserResponse userResponse = new UserResponse(user, "", responseStatus);

		return userResponse;
	}

	/**
	 * Method getUserByUsername.
	 * 
	 * @param username
	 *            String
	 * @param token
	 *            String
	 * @param userUsername
	 *            String
	 * @return UserResponse
	 */
	@Path("/getuserbyusername")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public UserResponse getUserByUsername(UserConsumes userConsumes) {

		if (!AuthTokenManager.isUserAuthentic(userConsumes.getToken(),
				userConsumes.getUsername()))
			throw new UnauthorizedException();

		int responseStatus;
		User user = new User();

		if (!UserServerValidate.hasAdminPrivileges(userConsumes.getToken(),
				userConsumes.getUsername()))
			responseStatus = UNAUTHORIZED;
		else {

			responseStatus = OK;
			user = UserController.getUserByUsername(userConsumes
					.getUser_username());
		}

		UserResponse userResponse = new UserResponse(user, "", responseStatus);

		return userResponse;
	}

	/**
	 * Method getUserByEmail.
	 * 
	 * @param username
	 *            String
	 * @param token
	 *            String
	 * @param userEmail
	 *            String
	 * @return UserResponse
	 */
	@Path("/getuserbyemail")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public UserResponse getUserByEmail(UserConsumes userConsumes) {

		if (!AuthTokenManager.isUserAuthentic(userConsumes.getToken(),
				userConsumes.getUsername()))
			throw new UnauthorizedException();

		int responseStatus;
		User user = new User();

		if (!UserServerValidate.hasAdminPrivileges(userConsumes.getToken(),
				userConsumes.getUsername()))
			responseStatus = UNAUTHORIZED;
		else {

			responseStatus = OK;
			user = UserController.getUserByEmail(userConsumes.getUser_email());
		}

		UserResponse userResponse = new UserResponse(user, "", responseStatus);

		return userResponse;
	}

	@Path("/stateusers")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public int blockAllUsers(UserConsumes userConsumes) {

		if (!AuthTokenManager.isUserAuthentic(userConsumes.getToken(),
				userConsumes.getUsername()))
			throw new UnauthorizedException();

		if (!UserServerValidate.hasAdminPrivileges(userConsumes.getToken(),
				userConsumes.getUsername()))
			return UNAUTHORIZED;

		List<User> listUsers = UserController.getAllUsers();

		if (listUsers == null)
			return INTERNAL_ERROR;

		for (User user : listUsers) {
			if (user.getAccessLevel() == ADMIN_ACCESS_LEVEL)
				continue;
			user.setIsBlocked(userConsumes.getUserState());
			user.saveToDatabase();
		}

		return OK;
	}

	@Path("/rankingusers")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<RankingResponse> getRankingUsers(UserConsumes userConsumes) {

		if (!AuthTokenManager.isUserAuthentic(userConsumes.getToken(),
				userConsumes.getUsername()))
			throw new UnauthorizedException();

		if (!UserServerValidate.hasAdminPrivileges(userConsumes.getToken(),
				userConsumes.getUsername()))
			throw new UnauthorizedException();

		List<RankingResponse> list = new ArrayList<RankingResponse>();

		List<User> listUser = UserController.getAllUsers();

		for (User user : listUser) {
			RankingResponse ranking = new RankingResponse(user.getId(),
					user.getName(),
					ProblemController.getUserScore(user.getId()));
			list.add(ranking);
		}

		return list;
	}

}