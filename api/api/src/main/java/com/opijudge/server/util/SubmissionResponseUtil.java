package com.opijudge.server.util;

import java.util.ArrayList;
import java.util.List;

import com.opijudge.server.response.SubmissionResponse;
import com.opijudge.models.Submission;

public class SubmissionResponseUtil {

	public static List<SubmissionResponse> convertList(List<Submission> list) {

		List<SubmissionResponse> listResponse = new ArrayList<SubmissionResponse>();

		if (list == null)
			listResponse = null;
		else {
			for (int i = 0; i < list.size(); i++) {
				listResponse.add(new SubmissionResponse(list.get(i), 0));
			}
		}

		return listResponse;
	}
}
