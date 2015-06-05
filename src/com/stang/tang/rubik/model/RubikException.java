package com.stang.tang.rubik.model;

public class RubikException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public RubikException() {
		super();
	}

	public RubikException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public RubikException(String arg0) {
		super(arg0);
	}

	public RubikException(Throwable arg0) {
		super(arg0);
	}

}
