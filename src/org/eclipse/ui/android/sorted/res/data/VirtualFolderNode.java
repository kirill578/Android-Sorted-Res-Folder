package org.eclipse.ui.android.sorted.res.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.android.sorted.res.ImageProvider;

/**
 * @since 3.2
 *
 */
public class VirtualFolderNode extends AbstractNode {

	/**
	 * This string will separate file into virtual folders
	 */
	public static final String VIRTUAL_FILE_SEPERATOR = "__"; //$NON-NLS-1$
	
	private ArrayList<IFile> matching;
	
	/**
	 * @param project - 
	 * @param parent
	 * @param matchingFiles
	 * @param title
	 */
	public VirtualFolderNode(IProject project, AbstractNode parent, ArrayList<IFile> matchingFiles, String title){
		super(project, parent, title);
		this.matching = matchingFiles;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.examples.navigator.data.Node#getChildren()
	 */
	@Override
	public List<AbstractNode> getChildren() {
		ArrayList<AbstractNode> children = new ArrayList<AbstractNode>();
		HashMap<String, ArrayList<IFile>> folderSortedFiles = new HashMap<String, ArrayList<IFile>>();
		for (IFile file : matching) {
			if(file.getName().substring(getTitle().length() + VIRTUAL_FILE_SEPERATOR.length()).contains(VIRTUAL_FILE_SEPERATOR)){
				// create virtual folders
				String folderName = file.getName().substring(getTitle().length() + VIRTUAL_FILE_SEPERATOR.length()).split(VIRTUAL_FILE_SEPERATOR)[0];
				if(folderSortedFiles.containsKey(folderName) == false)
					folderSortedFiles.put(folderName, new ArrayList<IFile>());
				folderSortedFiles.get(folderName).add(file);
			} else {
				// add file nodes
				children.add(new FileNode(getProject(), this, file));
			}
		}
		
		// iterate and insert the virtual folders
		Iterator<Entry<String, ArrayList<IFile>>> it = folderSortedFiles.entrySet().iterator();
		while (it.hasNext()) {
	        Map.Entry<String, ArrayList<IFile>> pairs = it.next();
	        children.add(new VirtualFolderNode(getProject(), this, pairs.getValue(), getTitle() + VIRTUAL_FILE_SEPERATOR + pairs.getKey()));
	        it.remove();
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
