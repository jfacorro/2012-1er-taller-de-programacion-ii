package mereditor.interfaz.rcp;

import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

/**
 * A Workbench Advisor for the Directed Graph Example Editor as an RCP
 * application.
 * 
 * @author Anthony Hunter
 */
public class Digraph1WorkbenchAdvisor extends WorkbenchAdvisor {

	/*
	 * @see
	 * org.eclipse.ui.application.WorkbenchAdvisor#createWorkbenchWindowAdvisor
	 * (org.eclipse.ui.application.IWorkbenchWindowConfigurer)
	 */
	@Override
	public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(
			IWorkbenchWindowConfigurer configurer) {
		return new Digraph1WorkbenchWindowAdvisor(configurer);
	}

	/*
	 * @see
	 * org.eclipse.ui.application.WorkbenchAdvisor#getInitialWindowPerspectiveId
	 * ()
	 */
	@Override
	public String getInitialWindowPerspectiveId() {
		return "mereditor"; //$NON-NLS-1$
	}

}
