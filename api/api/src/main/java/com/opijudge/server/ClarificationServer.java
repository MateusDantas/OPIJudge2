package com.opijudge.server;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.opijudge.server.consume.ClarificationConsumes;

@Path("/clarification")
public class ClarificationServer {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public int createClarification(ClarificationConsumes clarification) {

		return 0;
	}
}
