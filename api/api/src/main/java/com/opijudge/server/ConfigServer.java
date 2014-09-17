package com.opijudge.server;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.opijudge.controller.ServerController;
import com.opijudge.controller.UserController;
import com.opijudge.controller.auth.AuthTokenManager;
import com.opijudge.server.consume.UserConsumes;
import com.opijudge.server.response.UnauthorizedException;

import static com.opijudge.controller.util.Constants.*;

@Path("/config")
public class ConfigServer {

	@POST
	@Path("/lockserver")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public int lockServer(UserConsumes userConsumes) {

		if (!AuthTokenManager.isUserAuthentic(userConsumes.getToken(),
				userConsumes.getUsername()))
			throw new UnauthorizedException();
		
		if (UserController.getUserAccessLevel(userConsumes.getUsername()) != ADMIN_ACCESS_LEVEL)
			throw new UnauthorizedException();
		
		ServerController.blockServer();
		
		return OK;
	}
	
	@POST
	@Path("/unlockserver")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public int unlockServer(UserConsumes userConsumes) {

		if (!AuthTokenManager.isUserAuthentic(userConsumes.getToken(),
				userConsumes.getUsername()))
			throw new UnauthorizedException();
		
		if (UserController.getUserAccessLevel(userConsumes.getUsername()) != ADMIN_ACCESS_LEVEL)
			throw new UnauthorizedException();
		
		ServerController.unblockServer();
		
		return OK;
	}
	
	@POST
	@Path("/serverstatus")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public int serverStatus(UserConsumes userConsumes) {

		if (!AuthTokenManager.isUserAuthentic(userConsumes.getToken(),
				userConsumes.getUsername()))
			throw new UnauthorizedException();
		
		if (UserController.getUserAccessLevel(userConsumes.getUsername()) != ADMIN_ACCESS_LEVEL)
			throw new UnauthorizedException();
		
		return ServerController.getServerStatus();
	}
}
