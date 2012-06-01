package mereditor.interfaz.editor;

import mereditor.interfaz.factory.FabricaEditPartMER;
import mereditor.modelo.Diagrama;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.editparts.FreeformGraphicalRootEditPart;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.gef.ui.parts.GraphicalEditorWithPalette;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;

/**
 * Vista principal
 */
public class MERGraphicalEditor extends GraphicalEditorWithPalette {

	/**
	 * The default tool on the diagram that does not do anything. Otherwise the
	 * selection tool is used and it looks like you can select nodes with the
	 * selection tool when you actually cannot for this example.
	 */
	public class DoNothingTool extends AbstractTool {

		// FIXME

		@Override
		protected String getCommandName() {
			return null;
		}
	}

	/**
	 * Constructor for a Digraph1GraphicalEditor.
	 */
	public MERGraphicalEditor() {
		setEditDomain(new DefaultEditDomain(this));
		getEditDomain().setActiveTool(new DoNothingTool());
	}

	/*
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#configureGraphicalViewer()
	 */
	@Override
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		GraphicalViewer viewer = getGraphicalViewer();

		viewer.setRootEditPart(new FreeformGraphicalRootEditPart());
		viewer.setEditPartFactory(new FabricaEditPartMER());
	}

	/*
	 * @see org.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.
	 * IProgressMonitor)
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {
		/* not implemented */
	}

	/*
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#initializeGraphicalViewer()
	 */
	@Override
	protected void initializeGraphicalViewer() {
		getGraphicalViewer().setContents(new Diagrama());
	}

	/*
	 * @see org.eclipse.ui.part.EditorPart#setInput(org.eclipse.ui.IEditorInput)
	 */
	@Override
	protected void setInput(IEditorInput input) {
		super.setInput(input);
		if (input instanceof IFileEditorInput) {
			setPartName(((IFileEditorInput) input).getName());
		}
	}

	@Override
	protected PaletteRoot getPaletteRoot() {
		// TODO Auto-generated method stub
		return null;
	}
}
