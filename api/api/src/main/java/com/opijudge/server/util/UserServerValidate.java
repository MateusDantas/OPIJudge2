package com.opijudge.server.util;

import static com.opijudge.controller.util.Constants.ADMIN_ACCESS_LEVEL;

import com.opijudge.controller.UserController;
import com.opijudge.controller.auth.AuthTokenManager;

public class UserServerValidate {

	public static boolean hasAdminPrivileges(String token, String username) {
		
		if (!AuthTokenManager.isUserAuthentic(token, username)
				|| UserController.getUserAccessLevel(username) != ADMIN_ACCESS_LEVEL)
			return false;
		return true;
	}
}
