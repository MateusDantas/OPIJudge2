package com.opijudge.controller.util;

import com.opijudge.controller.auth.AuthTokenManager;

public class RandomToken {

	public static String generateToken() {
		
		while (true) {
			String nowToken = (new RandomGenerator()).randomString();
			if (AuthTokenManager.getAuthToken(nowToken) == null)
				return nowToken;
		}
	}
}
