package com.opijudge.controller.util;

import java.util.UUID;

public class RandomGenerator {

	public String randomString() {
		
		return UUID.randomUUID().toString();
	}
}
