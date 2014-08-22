package com.opijudge.server.util;

import java.util.ArrayList;
import java.util.List;

import com.opijudge.server.response.ProblemResponse;
import com.opijudge.models.Problem;

public class ProblemResponseUtil {

	public static List<ProblemResponse> convertList(List<Problem> list) {
		
		List <ProblemResponse> listResponse = new ArrayList<ProblemResponse>();
		if (list == null) {
			listResponse = null;
		} else {
			for (int i = 0; i < list.size(); i++)
				listResponse.add(new ProblemResponse(list.get(i)));
		}
		
		return listResponse;
	}
}
