package com.opijudge.models;

import java.io.File;

public class SubmissionDAOImpl implements SubmissionDAO {

	private File code = null;
	private Submission submission = null;
	private String basePath;

	public SubmissionDAOImpl(Submission submission, String basePath, File code) {
		this.setSubmission(submission);
		this.setBasePath(basePath);
		this.setCode(code);
	}

	public SubmissionDAOImpl(File code, Submission submission) {
		this.setCode(code);
		this.setSubmission(submission);
	}

	public SubmissionDAOImpl(Submission submission, String basePath) {
		this.setSubmission(submission);
		this.setBasePath(basePath);
	}
	
	public boolean saveCode() {

		return saveFile(code, getTotalPath());
	}

	public boolean saveFile(File file, String totalPath) {

		try {
			if (file == null)
				return false;

			if (!file.canWrite())
				return false;

			if (totalPath == null)
				return false;


			if (file.renameTo(new File(totalPath)))
				return true;
			else
				return false;
		} catch (Exception e) {

			return false;
		}
	}

	public boolean loadCode() {

		File file = loadFile(getTotalPath());
		if (file == null)
			return false;
		this.code = file;
		return true;
	}

	public File loadFile(String totalPath) {

		File file = null;
		try {
			file = new File(totalPath);
			if (!file.isFile())
				return null;
			if (!file.canRead())
				return null;
		} catch (Exception e) {
			return null;
		}
		return file;
	}

	public String getTotalPath() {

		if (submission == null)
			return null;

		int curId = submission.getId();
		if (curId < 1)
			return null;

		String extension = submission.getLanguage();
		if (extension == "")
			return null;

		String path = basePath;
		if (path.endsWith("/"))
			path += "." + String.valueOf(curId) + extension;
		else
			path += "/" + String.valueOf(curId) + "." + extension;

		return path;
	}

	public File getCode() {
		return code;
	}

	public void setCode(File code) {
		this.code = code;
	}

	public Submission getSubmission() {
		return submission;
	}

	public void setSubmission(Submission submission) {
		this.submission = submission;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

}
