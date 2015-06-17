package com.pivotalservices.java2r;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;

public class REnvironment {
	private final R r;
	private final REXP env;

	REnvironment(R r, REXP env) throws RException {
		this.r = r;
		this.env = env;
	}

	public void eval(String expr) throws RException {
		this.eval(expr, null);
	}

	public double evalDouble(String expr) throws RException {
		return this.evalDouble(expr, null);
	}

	public String[] evalStrings(String expr) throws RException {
		return this.evalStrings(expr, null);
	}

	public double[] evalDoubles(String expr) throws RException {
		return this.evalDoubles(expr, null);
	}

	public void eval(String expr, RLogger logger) throws RException {
		r.eval(expr, env, false, logger);
	}

	public double evalDouble(String expr, RLogger logger) throws RException {
		try {
			return r.eval(expr, env, true, logger).asDouble();
		} catch (REXPMismatchException e) {
			throw new RException(e);
		}
	}

	public String[] evalStrings(String expr, RLogger logger) throws RException {
		try {
			return r.eval(expr, env, true, logger).asStrings();
		} catch (REXPMismatchException e) {
			throw new RException(e);
		}
	}

	public double[] evalDoubles(String expr, RLogger logger) throws RException {
		try {
			return r.eval(expr, env, true, logger).asDoubles();
		} catch (REXPMismatchException e) {
			throw new RException(e);
		}
	}

}
