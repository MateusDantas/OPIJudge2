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
import com.opijudge.server.consume.SubmissionConsumes;
import com.opijudge.server.response.ListResponse;
import com.opijudge.server.response.SubmissionResponse;
import com.opijudge.server.response.UnauthorizedException;
import com.opijudge.server.util.SubmissionResponseUtil;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import static com.opijudge.controller.util.Constants.*;

/**
 */
@Path("/submission")
public class SubmissionServer {

	/**
	 * Method makeSubmission.
	 * 
	 * @param problemId
	 *            int
	 * @param username
	 *            String
	 * @param token
	 *            String
	 * @param inputStream
	 *            InputStream
	 * @param contentDispositionHeader
	 *            FormDataContentDisposition
	 * @return int
	 */
	@POST
	@Path("/makesubmission")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public int makeSubmission(
			@FormDataParam("problemid") int problemId,
			@FormDataParam("username") String username,
			@FormDataParam("token") String token,
			@FormDataParam("file") InputStream inputStream,
			@FormDataParam("file") FormDataContentDisposition contentDispositionHeader) {

		System.out.println("SUBMIT: " +  contentDispositionHeader.getFileName());
		System.out.println("USERNAME: " + username);
		if (!AuthTokenManager.isUserAuthentic(token, username))
			return UNAUTHORIZED;

		int statusCode = SubmissionController.makeSubmission(problemId,
				username, token, inputStream, contentDispositionHeader);

		return statusCode;
	}

	/**
	 * Method getSubmissionById.
	 * 
	 * @param submissionId
	 *            int
	 * @param username
	 *            String
	 * @param token
	 *            String
	 * @return SubmissionResponse
	 */
	@POST
	@Path("/getsubmissionbyid")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public SubmissionResponse getSubmissionById(
			SubmissionConsumes submissionConsumes) {

		int responseStatus;
		if (!AuthTokenManager.isUserAuthentic(submissionConsumes.getToken(),
				submissionConsumes.getUsername()))
			throw new UnauthorizedException();

		Submission submission = SubmissionController
				.getSubmissionById(submissionConsumes.getSubmissionid());

		if (submission == null)
			responseStatus = INVALID_SUBMISSION;
		else {
			int userId = submission.getUserId();
			User userQuery = UserController
					.getUserByUsername(submissionConsumes.getUsername());

			if (userQuery.getAccessLevel() != ADMIN_ACCESS_LEVEL
					&& userQuery.getId() != userId)
				responseStatus = UNAUTHORIZED;
			else
				responseStatus = OK;
		}

		return new SubmissionResponse(submission, responseStatus);
	}

	/**
	 * Method getSubmissionsByUser.
	 * 
	 * @param userId
	 *            int
	 * @param username
	 *            String
	 * @param token
	 *            String
	 * @return ListResponse<Submission>
	 */
	@POST
	@Path("/getsubmissionsbyuser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ListResponse<SubmissionResponse> getSubmissionsByUser(
			SubmissionConsumes submissionConsumes) {

		int responseStatus;

		User user = UserController.getUserByUsername(submissionConsumes
				.getUsername());

		if (user == null
				|| !AuthTokenManager.isUserAuthentic(
						submissionConsumes.getToken(),
						submissionConsumes.getUsername()))
			throw new UnauthorizedException();

		if (user.getAccessLevel() != ADMIN_ACCESS_LEVEL
				&& submissionConsumes.getUserid() != user.getId()) {
			responseStatus = UNAUTHORIZED;
			return new ListResponse<SubmissionResponse>(responseStatus);
		}

		List<Submission> list = SubmissionController
				.getSubmissionsByUser(submissionConsumes.getUserid());

		List<SubmissionResponse> listResponse = SubmissionResponseUtil
				.convertList(list);

		if (listResponse == null)
			responseStatus = INVALID_USER;
		else
			responseStatus = OK;

		return new ListResponse<SubmissionResponse>(listResponse,
				responseStatus);
	}

	/**
	 * Method getSubmissionsByProblem.
	 * 
	 * @param problemId
	 *            int
	 * @param username
	 *            String
	 * @param token
	 *            String
	 * @return ListResponse<Submission>
	 */
	@POST
	@Path("/getsubmissionsbyproblem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ListResponse<SubmissionResponse> getSubmissionsByProblem(
			SubmissionConsumes submissionConsumes) {

		int responseStatus;

		User user = UserController.getUserByUsername(submissionConsumes
				.getUsername());

		if (user == null
				|| !AuthTokenManager.isUserAuthentic(
						submissionConsumes.getToken(),
						submissionConsumes.getUsername()))
			throw new UnauthorizedException();

		if (user.getAccessLevel() != ADMIN_ACCESS_LEVEL) {

			responseStatus = UNAUTHORIZED;
			return new ListResponse<SubmissionResponse>(responseStatus);
		}

		List<Submission> list = SubmissionController
				.getSubmissionsByProblem(submissionConsumes.getProblemid());

		List<SubmissionResponse> listResponse = SubmissionResponseUtil
				.convertList(list);

		if (listResponse == null)
			responseStatus = INVALID_PROBLEM;
		else
			responseStatus = OK;

		return new ListResponse<SubmissionResponse>(listResponse,
				responseStatus);
	}

	/**
	 * Method getSubmissionsByUserInProblem.
	 * 
	 * @param userId
	 *            int
	 * @param problemId
	 *            int
	 * @param username
	 *            String
	 * @param token
	 *            String
	 * @return ListResponse<Submission>
	 */
	@POST
	@Path("/getsubmissionsbyuser_in_problem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ListResponse<SubmissionResponse> getSubmissionsByUserInProblem(
			SubmissionConsumes submissionConsumes) {

		int responseStatus;
		User user = UserController.getUserByUsername(submissionConsumes
				.getUsername());

		if (user == null
				|| !AuthTokenManager.isUserAuthentic(
						submissionConsumes.getToken(),
						submissionConsumes.getUsername()))
			throw new UnauthorizedException();

		if (user.getAccessLevel() != ADMIN_ACCESS_LEVEL
				&& user.getId() != submissionConsumes.getUserid()) {

			responseStatus = UNAUTHORIZED;
			return new ListResponse<SubmissionResponse>(responseStatus);
		}

		System.out.println(submissionConsumes.getUserid());
		List<Submission> list = SubmissionController
				.getSubmissionsByUserInProblem(submissionConsumes.getUserid(),
						submissionConsumes.getProblemid());
		System.out.println(list.size());

		List<SubmissionResponse> listResponse = SubmissionResponseUtil
				.convertList(list);

		if (listResponse == null)
			responseStatus = INVALID_SUBMISSION;
		else
			responseStatus = OK;

		return new ListResponse<SubmissionResponse>(listResponse,
				responseStatus);
	}

	/**
	 * Method getUserCode.
	 * 
	 * @param submissionId
	 *            int
	 * @param username
	 *            String
	 * @param token
	 *            String
	 * @return Response
	 */
	@GET
	@Path("/getusercode")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("text/plain")
	public Response getUserCode(SubmissionConsumes submissionConsumes) {

		if (!AuthTokenManager.isUserAuthentic(submissionConsumes.getToken(),
				submissionConsumes.getUsername()))
			throw new UnauthorizedException();

		Submission submission = SubmissionController
				.getSubmissionById(submissionConsumes.getSubmissionid());
		User user = UserController.getUserByUsername(submissionConsumes
				.getUsername());
		if (user.getAccessLevel() != ADMIN_ACCESS_LEVEL
				&& user.getId() != submission.getUserId())
			return Response.status(Status.UNAUTHORIZED).build();

		File file = SubmissionController.getSubmissionCode(submissionConsumes
				.getSubmissionid());

		if (file == null)
			return Response.status(INVALID_SUBMISSION).build();

		ResponseBuilder response = Response.ok((Object) file);

		response.header("Content-Disposition", "attachment; filename=code.txt");

		return response.build();
	}

}
