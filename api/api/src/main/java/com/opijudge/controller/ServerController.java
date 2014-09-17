package com.opijudge.controller;

import static com.opijudge.controller.util.Constants.*;

import com.opijudge.controller.socket.SocketServer;

public class ServerController {
	
	public static int SERVER_STATUS = 1;
	
	public synchronized static void blockServer() {
		SERVER_STATUS = 0;
	}
	
	public synchronized static void unblockServer() {
		SERVER_STATUS = 1;
	}
	
	public synchronized static int getServerStatus() {
		return SERVER_STATUS;
	}
	
	public static boolean singleSubmission(int submissionId) {
				
		return sendMessage("judge:" + String.valueOf(submissionId) + ":" + SERVER_SECRET_KEY);
	}
	
	public static boolean rejudgeSubmission(int submissionId) {
		
		return sendMessage("rejudge:sub:" + String.valueOf(submissionId) + ":" + SERVER_SECRET_KEY);
	}
	
	public static boolean rejudgeAll() {
		
		return sendMessage("rejudge:sub:all:" + SERVER_SECRET_KEY);
	}
	
	public static boolean rejudgeAllByProblem(int problemId) {
		
		return sendMessage("rejudge:prob:" + String.valueOf(problemId) + ":" + SERVER_SECRET_KEY);
	}
	
	public static boolean sendMessage(String message) {
		
		SocketServer socketServer = getSocketServer(JUDGE_HOSTNAME, JUDGE_PORT);
		
		if (!socketServer.openSocket())
			return false;
		
		if (!socketServer.sendMessage(message))
			return false;
		
		if (!socketServer.closeSocket())
			return false;
		
		return true;
	}
	
	public static SocketServer getSocketServer(String HOSTNAME, int PORT) {
		
		return new SocketServer(HOSTNAME, PORT);
	}
}
