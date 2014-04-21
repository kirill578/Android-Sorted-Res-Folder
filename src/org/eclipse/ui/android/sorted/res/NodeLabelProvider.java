/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation.
 * Licensed Material - Property of IBM. All rights reserved.
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp.
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.android.sorted.res;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.android.sorted.res.data.AbstractNode;
import org.eclipse.ui.android.sorted.res.data.VirtualFolderNode;
import org.eclipse.ui.navigator.IDescriptionProvider;
import org.w3c.dom.Node;

/**
 * Provides a label and icon for objects of type {@link Node}.
 * 
 * @since 3.2
 *
 */
public class NodeLabelProvider extends LabelProvider implements
		ILabelProvider, IDescriptionProvider {
  

	public Image getImage(Object element) {
		if (element instanceof AbstractNode) {
			AbstractNode data = (AbstractNode) element;
			return data.getImage();
		}
		return null;
	}

	public String getText(Object element) {
		if (element instanceof AbstractNode) {
			AbstractNode data = (AbstractNode) element;
			String[] list = data.getTitle().split(VirtualFolderNode.VIRTUAL_FILE_SEPERATOR);
			return list[list.length-1];
		}  
		return null;
	}

	public String getDescription(Object anElement) {
		if (anElement instanceof AbstractNode) {
			AbstractNode data = (AbstractNode) anElement;
			IFile openFile = data.getOpenFile();
			if(openFile == null)
				return data.getTitle();
			return openFile.getLocation().toString().substring(data.getProject().getLocation().toString().length());
		}
		return null;
	}
  
}
