package org.eclipse.ui.android.sorted.res.data;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.android.sorted.res.ImageProvider;

/**
 * @since 3.2
 *
 */
public class LocalizationNode extends AbstractNode implements IAdaptable {

	
	private IFile file;
	
	
	/**
	 * @param project - the project to which it is related
	 * @param parent - the parent node, set null if root
	 * @param file - file represented by this node
	 */
	public LocalizationNode(IProject project, AbstractNode parent, IFile file){
		super(project, parent, null);
		this.file = file;
	}
	
	@Override
	public String getTitle() {
		String localizationFolderName = file.getParent().getName();
		if(localizationFolderName.contains("-")) //$NON-NLS-1$
			return localizationFolderName.substring(localizationFolderName.indexOf("-") + 1); // please forgive me for this //$NON-NLS-1$
		
		return "everything-else"; //$NON-NLS-1$
	}

	/**
	 * @return Returns the file.
	 */
	public IFile getFile() {
		return file;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.examples.navigator.data.Node#getChildren()
	 */
	@Override
	public List<AbstractNode> getChildren() {
		return Collections.emptyList();
	}


	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
		return file.getAdapter(adapter);
	}


	/* (non-Javadoc)
	 * @see org.eclipse.ui.examples.navigator.data.Node#getOpenFile()
	 */
	@Override
	public IFile getOpenFile() {
		return file;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.examples.navigator.data.AbstractNode#getImage()
	 */
	@Override
	public Image getImage() {
		return ImageProvider.getImage("localization.png"); //$NON-NLS-1$
	}

}
