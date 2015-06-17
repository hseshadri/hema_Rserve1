package com.pivotalservices.java2r;

import org.rosuda.REngine.REngine;
import org.rosuda.REngine.REngineStdOutput;

class RStdOutputListner extends REngineStdOutput {

	private final R r;

	private RLogger logger;

	RStdOutputListner(R r) {
		this.r = r;
	}

	@Override
	public void RWriteConsole(REngine engine, String text, int oType) {
		if (r.getLogger().isDebugEnabled()) {
			super.RWriteConsole(engine, text, oType);
		}

		if (logger != null) {
			logger.append(oType, text);
		}
	}

	void registerLogger(RLogger logger) {
		this.logger = logger;
	}

	void unregisterLogger(RLogger logger) {
		if (this.logger == logger) {
			if (this.logger != null) {
				this.logger.flush();
			}
			this.logger = null;
		}
	}
}
