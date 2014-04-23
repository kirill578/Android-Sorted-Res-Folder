/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 ******************************************************************************/

package org.eclipse.ui.android.sorted.res;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.android.sorted.res.data.AbstractNode;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;

/**
 * @since 3.2
 *
 */
public class NodeActionProvider extends CommonActionProvider {

	/**
	 * Must be empty
	 */
	public NodeActionProvider() {
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.navigator.CommonActionProvider#init(org.eclipse.ui.navigator.ICommonActionExtensionSite)
	 */
	@Override
	public void init(ICommonActionExtensionSite aSite) {
		final ICommonViewerWorkbenchSite workbenchSite =  (ICommonViewerWorkbenchSite) aSite.getViewSite();
	    IDoubleClickListener doubleclick = new IDoubleClickListener() {
	        public void doubleClick(DoubleClickEvent event) {
	        	ISelection selection = workbenchSite.getSelectionProvider().getSelection();
	        	if(!selection.isEmpty()) {
	    			IStructuredSelection sSelection = (IStructuredSelection) selection;
	    			if(sSelection.size() == 1 && sSelection.getFirstElement() instanceof AbstractNode) {
	    				AbstractNode data = ((AbstractNode)sSelection.getFirstElement()); 				
	    				IFile file = data.getOpenFile();
	    				// not all nodes are open-able
	    				if(file != null)
							try {
								IDE.openEditor(workbenchSite.getPage(), file);
							} catch (PartInitException e) {
								e.printStackTrace();
							}
	    			}
	    		}
	        }
	    };
	    aSite.getStructuredViewer().addDoubleClickListener(doubleclick);
	}
	
}
