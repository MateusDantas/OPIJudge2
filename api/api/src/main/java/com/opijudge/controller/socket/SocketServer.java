package com.opijudge.controller.socket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class SocketServer {

	private Socket socketServer = null;
	private String hostName;
	private int hostPort;
	private DataOutputStream outputStream = null;
	private DataInputStream inputStream = null;

	public SocketServer(String hostName, int hostPort) {
		this.setHostName(hostName);
		this.setHostPort(hostPort);
	}

	public boolean openSocket() {

		try {
			this.setSocketServer(new Socket(getHostName(), getHostPort()));
			this.setOutputStream(new DataOutputStream(getSocketServer()
					.getOutputStream()));
			this.setInputStream(new DataInputStream(getSocketServer()
					.getInputStream()));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean openSocket(Socket socketServer) {

		if (socketServer == null)
			return false;

		setSocketServer(socketServer);
		return openSocket();
	}

	public boolean closeSocket() {

		try {
			if (socketServer != null)
				socketServer.close();

			if (outputStream != null)
				outputStream.close();

			if (inputStream != null)
				inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean sendMessage(String message) {

		try {
			if (socketServer == null)
				return false;

			if (outputStream == null)
				return false;

			outputStream.writeBytes(message);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public Socket getSocketServer() {
		return socketServer;
	}

	public void setSocketServer(Socket socketServer) {
		this.socketServer = socketServer;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public int getHostPort() {
		return hostPort;
	}

	public void setHostPort(int hostPort) {
		this.hostPort = hostPort;
	}

	public DataOutputStream getOutputStream() {
		return outputStream;
	}

	public void setOutputStream(DataOutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public DataInputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(DataInputStream inputStream) {
		this.inputStream = inputStream;
	}
}
