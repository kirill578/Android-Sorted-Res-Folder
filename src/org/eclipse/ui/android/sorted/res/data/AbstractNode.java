package org.eclipse.ui.android.sorted.res.data;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.graphics.Image;

/**
 * @since 3.2
 *
 */
public abstract class AbstractNode {

	private String title;
	private IProject project;
	private AbstractNode parent;

	/**
	 * @param project - the project to which it is related
	 * @param parent - the parent node, set null if root
	 * @param title - the title of the node
	 */
	public AbstractNode(IProject project, AbstractNode parent, String title){
		this.parent = parent;
		this.project = project;
		this.title = title;
	}
	
	/**
	 * @return project to which the nodes are related
	 */
	public IProject getProject() {
		return project;
	}

	/**
	 * @return parent node instance, null if root
	 */
	public AbstractNode getParent() {
		return parent;
	}

	/**
	 * @return title of the node
	 */
	public String getTitle() {
		return title;
	}

	
	/**
	 * @return checks if the current node has children
	 */
	public boolean hasChildren(){
		try {
			return getChildren().size() > 0;
		} catch (CoreException e){
			return false;
		}
	}
	
	/**
	 * @return a list of all children nodes
	 * @throws CoreException
	 */
	public abstract List<AbstractNode> getChildren()
			throws CoreException;

	/**
	 * if this node can be opened with a double click, return the file
	 * that should be opened
	 * @return the file that will be opened
	 */
	public abstract IFile getOpenFile();
	
	/**
	 * @return - image that will be along side the title
	 */
	public abstract Image getImage();

}
