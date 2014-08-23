package com.opijudge.server.response;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

public class FileStreamingResponse implements StreamingOutput {

	private File file;
	
	public FileStreamingResponse(File file) {
		this.file = file;
	}
	
	public void write(OutputStream output) throws IOException,
			WebApplicationException {
		
		FileInputStream input = new FileInputStream(this.file);
		
		try {
			
			int bytes;
			while ((bytes = input.read()) != -1) {
				output.write(bytes);
			}
		} catch (Exception e) {
			throw new WebApplicationException(e);
		} finally {
			
			if (output != null)
				output.close();
			if (input != null)
				input.close();
		}
		
	}

}
