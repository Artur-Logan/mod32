package com.arturlogan.projeto_mod32.exceptions;

public class EntityException extends Exception{
    private static final long serialVersionUID = 7054379063290825137L;

	public EntityException(String msg, Exception ex) {
        super(msg, ex);
    }
}
