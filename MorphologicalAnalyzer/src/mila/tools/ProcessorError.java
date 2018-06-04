package mila.tools;

class ProcessorError {
	private Exception e;

	private String filename;
	private ToolType tool;

	public ProcessorError(Exception e, String filename, ToolType tool) {
		super();
		this.e = e;
		this.filename = filename;
		this.tool = tool;
	}

	public Exception getE() {
		return e;
	}

	public String getFilename() {
		return filename;
	}

	public ToolType getTool() {
		return tool;
	}

	public void setE(Exception e) {
		this.e = e;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setTool(ToolType tool) {
		this.tool = tool;
	}
}