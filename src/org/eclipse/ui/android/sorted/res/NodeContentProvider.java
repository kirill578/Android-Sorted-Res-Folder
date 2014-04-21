package org.eclipse.ui.android.sorted.res;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.android.sorted.res.data.AbstractNode;
import org.eclipse.ui.android.sorted.res.data.ResourcesNode;
import org.eclipse.ui.progress.UIJob;

/**
 * @since 3.2
 *
 */
public class NodeContentProvider implements ITreeContentProvider, IResourceChangeListener, IResourceDeltaVisitor {

	private StructuredViewer viewer;

	/**
	 * must be empty
	 */
	public NodeContentProvider() {
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this, IResourceChangeEvent.POST_CHANGE);
	}

	public Object[] getChildren(Object parentElement) {
		if(parentElement instanceof IProject){
			return new Object[]{new ResourcesNode((IProject) parentElement)};
		} else if (parentElement instanceof AbstractNode){
			AbstractNode node = (AbstractNode) parentElement;
			List<AbstractNode> list;
			try {
				list = node.getChildren();
				return list.toArray(new Object[list.size()]);
			} catch (CoreException e) {
				e.printStackTrace();
				return new Object[0];
			}
		}
		return new Object[0];
	}  

	public Object getParent(Object element) {
		if (element instanceof AbstractNode) {
			AbstractNode data = (AbstractNode) element;
			return data.getParent();
		} 
		return null;
	}

	public boolean hasChildren(Object element) {		
		return getChildren(element).length != 0;
	}

	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this); 
	}

	public void inputChanged(Viewer aViewer, Object oldInput, Object newInput) {
		viewer = (StructuredViewer) aViewer;
	}

	public void resourceChanged(IResourceChangeEvent event) {
		IResourceDelta delta = event.getDelta();
		try {
			delta.accept(this);
		} catch (CoreException e) { 
			e.printStackTrace();
		} 
	}

	public boolean visit(IResourceDelta delta) {
		IResource source = delta.getResource();
		switch (source.getType()) {
		case IResource.ROOT:
			return true;
		case IResource.PROJECT:
			final IProject file = (IProject) source;
			new UIJob("Update Properties Model in CommonViewer") {  //$NON-NLS-1$
				public IStatus runInUIThread(IProgressMonitor monitor) {
					if (viewer != null && !viewer.getControl().isDisposed())
						viewer.refresh(file);
					return Status.OK_STATUS;						
				}
			}.schedule();
			return false;
		case IResource.FOLDER:
		case IResource.FILE:
		}
		return false;
	} 
}
