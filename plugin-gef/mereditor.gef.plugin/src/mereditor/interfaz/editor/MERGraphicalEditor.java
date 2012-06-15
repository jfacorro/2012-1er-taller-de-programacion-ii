package mereditor.interfaz.editor;

import mereditor.interfaz.factory.FabricaEditPartMER;
import mereditor.modelo.Diagrama;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.SharedImages;
import org.eclipse.gef.editparts.FreeformGraphicalRootEditPart;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.CreationToolEntry;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.SelectionToolEntry;
import org.eclipse.gef.requests.SimpleFactory;
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
		PaletteRoot root = new PaletteRoot();
		root.add(new SelectionToolEntry());
		root.add(new MarqueeToolEntry());
		PaletteGroup group = new PaletteGroup("Elementos"); //$NON-NLS-1$
		root.add(group);
		CreationToolEntry createClassEntry = new CreationToolEntry(
				"classe", //$NON-NLS-1$
				"permet de cr er une nouvelle classe", new SimpleFactory( //$NON-NLS-1$
						ClassModel.class), SharedImages.DESC_SELECTION_TOOL_16,
				SharedImages.DESC_SELECTION_TOOL_16);
		ConnectionCreationToolEntry connectionToolEntry = new ConnectionCreationToolEntry(
				"hritage", "permet de cr er une nouvelle relation d'hritage", //$NON-NLS-2$
				new SimpleFactory(InheritanceModel.class),
				SharedImages.DESC_SELECTION_TOOL_16,
				SharedImages.DESC_SELECTION_TOOL_16);
		group.add(0, createClassEntry);
		group.add(1, connectionToolEntry);
		return root;
	}
}
