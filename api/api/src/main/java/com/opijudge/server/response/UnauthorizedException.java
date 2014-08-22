package com.opijudge.server.response;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class UnauthorizedException extends WebApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3275077923750819748L;

	public UnauthorizedException() {
		this("Authentication required!", "OPI Judge Web Service");
	}

	public UnauthorizedException(String message, String realm) {
		super(Response
				.status(Status.UNAUTHORIZED)
				.header(HttpHeaders.WWW_AUTHENTICATE,
						"Basic realm=\"" + realm + "\"").entity(message)
				.build());
	}
}
