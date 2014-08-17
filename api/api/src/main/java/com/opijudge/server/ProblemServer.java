package com.opijudge.server;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.opijudge.controller.ProblemController;
import com.opijudge.controller.SubmissionController;
import com.opijudge.controller.UserController;
import com.opijudge.controller.auth.AuthTokenManager;
import com.opijudge.models.Problem;
import com.opijudge.models.Submission;
import com.opijudge.models.User;
import com.opijudge.server.response.ListResponse;
import com.opijudge.server.response.SubmissionResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import static com.opijudge.controller.util.Constants.*;

@Path("/problem")
public class ProblemServer {

	@POST
	@Path("/createproblem")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public int createProblem(
			@QueryParam("username") String username,
			@QueryParam("token") String token,
			@QueryParam("problemname") String problemName,
			@QueryParam("problemtype") int problemType,
			@FormDataParam("testplan") InputStream testPlanStream,
			@FormDataParam("testplan") FormDataContentDisposition testPlanDetail,
			@FormDataParam("testcases") InputStream testCasesStream,
			@FormDataParam("testcases") FormDataContentDisposition testCasesDetail,
			@FormDataParam("statement") InputStream statementStream,
			@FormDataParam("file") FormDataContentDisposition statementDetail) {

		return ProblemController.saveProblem(problemName, problemType,
				username, token, testPlanStream, testPlanDetail,
				testCasesStream, testCasesDetail, statementStream,
				statementDetail);
	}

	@GET
	@Path("/getproblemstatement")
	@Produces("application/pdf")
	public Response getStatement(@QueryParam("problemid") int problemId,
			@QueryParam("username") String username,
			@QueryParam("token") String token) {

		if (!AuthTokenManager.isUserAuthentic(token, username))
			return Response.status(Status.UNAUTHORIZED).build();

		File file = ProblemController.getProblemStatement(problemId);
		if (file == null)
			return Response.status(INVALID_PROBLEM).build();

		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition",
				"attachment; filename=statement.pdf");

		return response.build();
	}

	@POST
	@Path("/getallproblems")
	@Produces(MediaType.APPLICATION_JSON)
	public ListResponse<Problem> getAllProblems(
			@QueryParam("username") String username,
			@QueryParam("token") String token) {

		if (!AuthTokenManager.isUserAuthentic(token, username))
			return new ListResponse<Problem>(UNAUTHORIZED);

		List<Problem> list = ProblemController.getProblemsByName("*");

		int responseStatus;
		if (list == null)
			responseStatus = INVALID_PROBLEM;
		else
			responseStatus = OK;

		return new ListResponse<Problem>(list, responseStatus);
	}
}
