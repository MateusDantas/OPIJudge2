package com.opijudge.server;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
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
import com.opijudge.server.consume.ProblemConsumes;
import com.opijudge.server.response.ListResponse;
import com.opijudge.server.response.ProblemResponse;
import com.opijudge.server.response.SubmissionResponse;
import com.opijudge.server.response.UnauthorizedException;
import com.opijudge.server.util.ProblemResponseUtil;
import com.opijudge.server.util.UserServerValidate;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import static com.opijudge.controller.util.Constants.*;

/**
 */
@Path("/problem")
public class ProblemServer {

	/**
	 * Method createProblem.
	 * 
	 * @param username
	 *            String
	 * @param token
	 *            String
	 * @param problemName
	 *            String
	 * @param problemType
	 *            int
	 * @param testPlanStream
	 *            InputStream
	 * @param testPlanDetail
	 *            FormDataContentDisposition
	 * @param testCasesStream
	 *            InputStream
	 * @param testCasesDetail
	 *            FormDataContentDisposition
	 * @param statementStream
	 *            InputStream
	 * @param statementDetail
	 *            FormDataContentDisposition
	 * @return int
	 */
	@POST
	@Path("/createproblem")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public int createProblem(
			@FormDataParam("username") String username,
			@FormDataParam("token") String token,
			@FormDataParam("problemname") String problemName,
			@FormDataParam("problemtype") int problemType,
			@FormDataParam("testplan") InputStream testPlanStream,
			@FormDataParam("testplan") FormDataContentDisposition testPlanDetail,
			@FormDataParam("testcases") InputStream testCasesStream,
			@FormDataParam("testcases") FormDataContentDisposition testCasesDetail,
			@FormDataParam("statement") InputStream statementStream,
			@FormDataParam("statement") FormDataContentDisposition statementDetail) {

		System.out.println("CREATING PROBLEM " + problemName + " BY USERNAME "
				+ username);
		if (!AuthTokenManager.isUserAuthentic(token, username))
			throw new UnauthorizedException();

		if (!UserServerValidate.hasAdminPrivileges(token, username))
			return UNAUTHORIZED;

		System.out.println(username);

		return ProblemController.saveProblem(problemName, problemType,
				username, token, testPlanStream, testPlanDetail,
				testCasesStream, testCasesDetail, statementStream,
				statementDetail);
	}

	/**
	 * Method getStatement.
	 * 
	 * @param problemId
	 *            int
	 * @param username
	 *            String
	 * @param token
	 *            String
	 * @return Response
	 */
	@GET
	@Path("/getproblemstatement")
	@Produces("application/pdf")
	public Response getStatement(@QueryParam("token") String token,
			@QueryParam("username") String username,
			@QueryParam("problemid") int problemId) {

		if (!AuthTokenManager.isUserAuthentic(token, username))
			throw new UnauthorizedException();

		File file = ProblemController.getProblemStatement(problemId);

		if (file == null)
			return Response.status(INVALID_PROBLEM).build();

		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition",
				"attachment; filename=statement.pdf");

		return response.build();
	}

	/**
	 * Method getAllProblems.
	 * 
	 * @param username
	 *            String
	 * @param token
	 *            String
	 * @return ListResponse<Problem>
	 */
	@POST
	@Path("/getallproblems")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ListResponse<ProblemResponse> getAllProblems(
			ProblemConsumes problemConsumes) {

		if (!AuthTokenManager.isUserAuthentic(problemConsumes.getToken(),
				problemConsumes.getUsername()))
			throw new UnauthorizedException();

		List<Problem> list = ProblemController.getAllProblems();
		List<ProblemResponse> listResponse = ProblemResponseUtil
				.convertList(list);

		int responseStatus;

		if (listResponse == null)
			responseStatus = INVALID_PROBLEM;
		else
			responseStatus = OK;

		return new ListResponse<ProblemResponse>(listResponse, responseStatus);
	}
}
