package com.ianbull.p2.touchpoint.exec;

import java.io.IOException;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.equinox.internal.p2.touchpoint.eclipse.EclipseTouchpoint;
import org.eclipse.equinox.p2.engine.spi.ProvisioningAction;

public class Execute extends ProvisioningAction {

	public Execute() {
	}

	@Override
	public IStatus execute(Map<String, Object> parameters) {
		String program = (String)parameters.get("program");
		try {
			Runtime.getRuntime().exec(program);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Status.OK_STATUS;
	}

	@Override
	public IStatus undo(Map<String, Object> parameters) {
		return Status.OK_STATUS;
	}

}
