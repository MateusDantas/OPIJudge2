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

import com.opijudge.controller.SubmissionController;
import com.opijudge.controller.UserController;
import com.opijudge.controller.auth.AuthTokenManager;
import com.opijudge.models.Submission;
import com.opijudge.models.User;
import com.opijudge.server.response.ListResponse;
import com.opijudge.server.response.SubmissionResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import static com.opijudge.controller.util.Constants.*;

@Path("/submission")
public class SubmissionServer {

	@POST
	@Path("/makesubmission")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public int makeSubmission(
			@QueryParam("problemid") int problemId,
			@QueryParam("username") String username,
			@QueryParam("token") String token,
			@FormDataParam("file") InputStream inputStream,
			@FormDataParam("file") FormDataContentDisposition contentDispositionHeader) {

		if (!AuthTokenManager.isUserAuthentic(token, username))
			return UNAUTHORIZED;

		int statusCode = SubmissionController.makeSubmission(problemId,
				username, token, inputStream, contentDispositionHeader);

		return statusCode;
	}

	@POST
	@Path("/getsubmissionbyid")
	@Produces(MediaType.APPLICATION_JSON)
	public SubmissionResponse getSubmissionById(
			@QueryParam("submissionid") int submissionId,
			@QueryParam("username") String username,
			@QueryParam("token") String token) {

		int responseStatus;
		if (!AuthTokenManager.isUserAuthentic(token, username)) {
			responseStatus = UNAUTHORIZED;
			return new SubmissionResponse(null, responseStatus);
		}

		Submission submission = SubmissionController
				.getSubmissionById(submissionId);

		if (submission == null)
			responseStatus = INVALID_SUBMISSION;
		else {
			int userId = submission.getUserId();
			User userQuery = UserController.getUserByUsername(username);

			if (userQuery.getAccessLevel() != ADMIN_ACCESS_LEVEL
					&& userQuery.getId() != userId)
				responseStatus = UNAUTHORIZED;
			else
				responseStatus = OK;
		}

		return new SubmissionResponse(submission, responseStatus);
	}

	@POST
	@Path("/getsubmissionsbyuser")
	@Produces(MediaType.APPLICATION_JSON)
	public ListResponse<Submission> getSubmissionsByUser(
			@QueryParam("userid") int userId,
			@QueryParam("username") String username,
			@QueryParam("token") String token) {

		int responseStatus;

		User user = UserController.getUserByUsername(username);

		if (!AuthTokenManager.isUserAuthentic(token, username)
				|| (user.getAccessLevel() != ADMIN_ACCESS_LEVEL && userId != user
						.getId())) {
			responseStatus = UNAUTHORIZED;
			return new ListResponse<Submission>(responseStatus);
		}

		List<Submission> list = SubmissionController
				.getSubmissionsByUser(userId);

		if (list == null)
			responseStatus = INVALID_USER;
		else
			responseStatus = OK;

		return new ListResponse<Submission>(list, responseStatus);
	}

	@POST
	@Path("/getsubmissionsbyproblem")
	@Produces(MediaType.APPLICATION_JSON)
	public ListResponse<Submission> getSubmissionsByProblem(
			@QueryParam("problemid") int problemId,
			@QueryParam("username") String username,
			@QueryParam("token") String token) {

		int responseStatus;

		User user = UserController.getUserByUsername(username);

		if (!AuthTokenManager.isUserAuthentic(token, username)
				|| user.getAccessLevel() != ADMIN_ACCESS_LEVEL) {

			responseStatus = UNAUTHORIZED;
			return new ListResponse<Submission>(responseStatus);
		}

		List<Submission> list = SubmissionController
				.getSubmissionsByProblem(problemId);

		if (list == null)
			responseStatus = INVALID_PROBLEM;
		else
			responseStatus = OK;

		return new ListResponse<Submission>(list, responseStatus);
	}

	@POST
	@Path("/getsubmissionsbyuser_in_problem")
	@Produces(MediaType.APPLICATION_JSON)
	public ListResponse<Submission> getSubmissionsByUserInProblem(
			@QueryParam("userid") int userId,
			@QueryParam("problemid") int problemId,
			@QueryParam("username") String username,
			@QueryParam("token") String token) {

		int responseStatus;
		User user = UserController.getUserByUsername(username);

		if (!AuthTokenManager.isUserAuthentic(token, username)
				|| (user.getAccessLevel() != ADMIN_ACCESS_LEVEL && user.getId() != userId)) {

			responseStatus = UNAUTHORIZED;
			return new ListResponse<Submission>(responseStatus);
		}

		List<Submission> list = SubmissionController
				.getSubmissionsByUserInProblem(userId, problemId);

		if (list == null)
			responseStatus = INVALID_SUBMISSION;
		else
			responseStatus = OK;

		return new ListResponse<Submission>(list, responseStatus);
	}

	@GET
	@Path("/getusercode")
	@Produces("text/plain")
	public Response getUserCode(@QueryParam("submissionid") int submissionId,
			@QueryParam("username") String username,
			@QueryParam("token") String token) {

		if (!AuthTokenManager.isUserAuthentic(token, username))
			return Response.status(Status.UNAUTHORIZED).build();
		
		Submission submission = SubmissionController.getSubmissionById(submissionId);
		User user = UserController.getUserByUsername(username);
		if (user.getAccessLevel() != ADMIN_ACCESS_LEVEL && user.getId() != submission.getUserId())
			return Response.status(Status.UNAUTHORIZED).build();
		
		File file = SubmissionController.getSubmissionCode(submissionId);
		
		if (file == null)
			return Response.status(INVALID_SUBMISSION).build();
		
		ResponseBuilder response = Response.ok((Object) file);
		
		response.header("Content-Disposition", "attachment; filename=code.txt");
		
		return response.build();
	}

}
