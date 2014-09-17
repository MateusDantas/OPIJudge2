package com.opijudge.server;

import static com.opijudge.controller.util.Constants.ADMIN_ACCESS_LEVEL;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.opijudge.controller.ClarificationController;
import com.opijudge.controller.ServerController;
import com.opijudge.controller.UserController;
import com.opijudge.controller.auth.AuthTokenManager;
import com.opijudge.controller.util.Constants;
import com.opijudge.models.Clarification;
import com.opijudge.models.User;
import com.opijudge.server.consume.ClarificationConsumes;
import com.opijudge.server.response.UnauthorizedException;
import com.opijudge.server.util.UserServerValidate;

import static com.opijudge.controller.util.Constants.*;

@Path("/clarification")
public class ClarificationServer {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public int createClarification(ClarificationConsumes clarification) {

		if (!AuthTokenManager.isUserAuthentic(clarification.getToken(),
				clarification.getUsername()))
			throw new UnauthorizedException();

		if (ServerController.getServerStatus() == 0
				&& !UserServerValidate.hasAdminPrivileges(
						clarification.getToken(), clarification.getUsername()))
			throw new UnauthorizedException();

		int type = PRIVATE_TYPE;

		if (UserServerValidate.hasAdminPrivileges(clarification.getToken(),
				clarification.getUsername())
				&& clarification.getType() == GLOBAL_TYPE)
			type = GLOBAL_TYPE;

		User user = UserController.getUserByUsername(clarification.getUsername());
		
		if (user == null)
			return INVALID_USER;
		
		int userId = user.getId();
		
		return ClarificationController.createClarification(userId,
				clarification.getProblemid(),
				clarification.getQuestionmessage(), type);
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public int changeClarification(ClarificationConsumes clarification) {

		return 0;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Clarification> getClarifications(
			@QueryParam("username") String username,
			@QueryParam("token") String userToken) {

		return null;
	}

	@GET
	@Path("/unanswered")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Clarification> getClarifications() {

		return null;
	}
}
