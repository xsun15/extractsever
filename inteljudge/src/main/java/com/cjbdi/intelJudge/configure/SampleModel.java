package com.cjbdi.intelJudge.configure;

import java.util.List;

public class SampleModel {
	private List<String> keyword;
	private String filename;
	private int penalty = -1;

	public SampleModel(List<String> keyword, String filename) {
		this.keyword = keyword;
		this.filename = filename;
	}

	public List<String> getKeyword() {
		return keyword;
	}

	public void setKeyword(List<String> keyword) {
		this.keyword = keyword;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getPenalty() {
		return penalty;
	}

	public void setPenalty(int penalty) {
		this.penalty = penalty;
	}
}
