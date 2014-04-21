package org.eclipse.ui.android.sorted.res.data;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.android.sorted.res.ImageProvider;

/**
 * @since 3.2
 *
 */
public class ResourcesNode extends AbstractNode {

	
	/**
	 *  used by the method bellow
	 */
	public static final String[] android_resources_folder = {"anim", "drawable", "color", "layout", "menu", "values"}; //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$//$NON-NLS-5$//$NON-NLS-6$
	
	/**
	 * @param name
	 * @return name is part of the android res
	 */
	public static boolean isAndroidResFolder(String name){
		for (int i = 0; i < android_resources_folder.length; i++)
			if(name.equals(android_resources_folder[i]))
				return true;
		return false;
	}

	/**
	 * @param project
	 */
	public ResourcesNode(IProject project) {
		super(project, null, "res_sorted"); //$NON-NLS-1$
	}

	@Override
	public List<AbstractNode> getChildren() {
		ArrayList<AbstractNode> list = new ArrayList<AbstractNode>();
		try {
			IResource[] members = getProject().getFolder("res").members(); //$NON-NLS-1$
			for (int i = 0; i < members.length; i++) {
				if (members[i] instanceof IFolder) {
					IFolder f = (IFolder) members[i];
					if(isAndroidResFolder(f.getName()))
						list.add(new FolderNode(getProject(), this, f.getName()));
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return list;
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
