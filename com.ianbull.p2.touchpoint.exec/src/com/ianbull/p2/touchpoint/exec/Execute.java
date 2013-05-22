package com.ianbull.p2.touchpoint.exec;

import java.io.IOException;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.equinox.internal.p2.touchpoint.eclipse.Util;
import org.eclipse.equinox.internal.p2.touchpoint.eclipse.actions.ActionConstants;
import org.eclipse.equinox.p2.engine.spi.ProvisioningAction;

public class Execute extends ProvisioningAction {
	
	public static final String ARTIFACT = "artifact";
	public static final String PROGRAM = "program";

	public Execute() {
	}

	@Override
	public IStatus execute(Map<String, Object> parameters) {
		String artifact = null;
		if ( parameters.containsKey(ARTIFACT) ) {
			artifact = (String) parameters.get(ARTIFACT);
			if (artifact.equals(ActionConstants.PARM_AT_ARTIFACT)) {
				try {
					artifact = Util.resolveArtifactParam(parameters);
				} catch (CoreException e) {
					return e.getStatus();
				}
			}
		}
		String program = (String)parameters.get(PROGRAM);
		String fullPath = computeProgramPath(artifact, program);
		try {
			Runtime.getRuntime().exec(fullPath);
		} catch (IOException e) {
			e.printStackTrace();
			return new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Cannot execute " + fullPath);
		}
		return Status.OK_STATUS;
	}
		
	/**
	 * Computes the path to the program to run
	 */
	private String computeProgramPath(String artifact, String programPath) {
		if ( artifact != null ) {
			return artifact + "/" + programPath;
		} 
		return programPath;
	}

	@Override
	public IStatus undo(Map<String, Object> parameters) {
		return Status.OK_STATUS;
	}

}
