package org.eclipse.ui.android.sorted.res.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;

/**
 * @since 3.2
 *
 */
public class FileNode extends AbstractNode implements IAdaptable {

	private IFile file;
	
	/**
	 * @param project - the project to which it is related
	 * @param parent - the parent node, set null if root
	 * @param file - file represented by this node 
	 */
	public FileNode(IProject project, AbstractNode parent, IFile file){
		super(project, parent, file.getName());
		this.file = file;
	}
	

	@Override
	public List<AbstractNode> getChildren() throws CoreException {
		ArrayList<AbstractNode> children = new ArrayList<AbstractNode>();
		
		String folderName = file.getParent().getName();
		String fileName = file.getName();
		
		IResource[] siblingsOfFolder = file.getParent().getParent().members();
		for (int i = 0; i < siblingsOfFolder.length; i++) {
			if (siblingsOfFolder[i] instanceof IFolder) {
				IFolder folder = (IFolder) siblingsOfFolder[i];
				if(folder.getName().startsWith(folderName)){
					IFile siblingFile = folder.getFile(fileName);
					if(siblingFile.exists())
						children.add(new LocalizationNode(getProject(), this, siblingFile));
				}
			}
		}
		
		// if there is only one localization
		// it will be final node
		if(children.size() == 1)
			return Collections.emptyList();
		
		return children;
	}


	@Override
	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class adapter) {
		if (hasChildren())
			return file.getAdapter(adapter);
		return null;
	}


	/* (non-Javadoc)
	 * @see org.eclipse.ui.examples.navigator.data.Node#getOpenFile()
	 */
	@Override
	public IFile getOpenFile() {
		try {
			if(getChildren().size() == 0)
				return file;
		} catch (CoreException e){
		}
		return null;
	}


	/**
	 * @return icon
	 */
	@Override
	public Image getImage() {
		return PlatformUI.getWorkbench().getEditorRegistry().getImageDescriptor(file.getName()).createImage();
	}

}
