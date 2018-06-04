package mila.tools.api;

public class MilaException extends RuntimeException {
	public MilaException(Throwable e) {
		super(e);
	}

	public MilaException(String s, Throwable e) {
		super(s, e);
	}

	private static final long serialVersionUID = 1L;

}
