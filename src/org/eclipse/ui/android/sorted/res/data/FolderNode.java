package org.eclipse.ui.android.sorted.res.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.android.sorted.res.ImageProvider;

/**
 * @since 3.2
 *
 */
public class FolderNode extends AbstractNode {

	private static final String ANDROID_RES_FOLDER = "res/"; //$NON-NLS-1$
	
	/**
	 * @param project - the project to which it is related
	 * @param parent - the parent node, set null if root
	 * @param title - the title of the node
	 */
	public FolderNode(IProject project, AbstractNode parent, String title){
		super(project, parent, title);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.examples.navigator.data.Node#getChildren()
	 */
	@Override
	public List<AbstractNode> getChildren() throws CoreException {
		ArrayList<AbstractNode> children = new ArrayList<AbstractNode>();
		IResource[] rMembers = getProject().getFolder(ANDROID_RES_FOLDER + getTitle()).members();
		HashMap<String, ArrayList<IFile>> folderSortedFiles = new HashMap<String, ArrayList<IFile>>(); 
		for (int i = 0; i < rMembers.length; i++) {
			if (rMembers[i] instanceof IFile) {
				IFile file = (IFile) rMembers[i];
				if(file.getName().contains(VirtualFolderNode.VIRTUAL_FILE_SEPERATOR)){
					String folderName = file.getName().split(VirtualFolderNode.VIRTUAL_FILE_SEPERATOR)[0];
					if(folderSortedFiles.containsKey(folderName) == false)
						folderSortedFiles.put(folderName, new ArrayList<IFile>());
					folderSortedFiles.get(folderName).add(file);
				} else {
					children.add(new FileNode(getProject(), this, file));
				}
			}
		}

		Iterator<Entry<String, ArrayList<IFile>>> it = folderSortedFiles.entrySet().iterator();
		while (it.hasNext()) {
	        Map.Entry<String, ArrayList<IFile>> pairs = it.next();
	        children.add(new VirtualFolderNode(getProject(), this, pairs.getValue(), pairs.getKey()));
	        it.remove(); // avoids a ConcurrentModificationException
	    }
		
		return children;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.examples.navigator.data.Node#getOpenFile()
	 */
	@Override
	public IFile getOpenFile() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.examples.navigator.data.AbstractNode#getImage()
	 */
	@Override
	public Image getImage() {
		return ImageProvider.getImage("res_sorted.png"); //$NON-NLS-1$
	}

}
