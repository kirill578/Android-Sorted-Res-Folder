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

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;

/**
 * @since 3.2
 *
 */
public class ImageProvider {
	
	private static ImageRegistry registry = new ImageRegistry();
	
	/**
	 * @param name of the file inside the images folder
	 * @return image
	 */
	public static Image getImage(String name){
		Image image = registry.get(name);
		if(image == null){
			try {
				Bundle bundle = Platform.getBundle("org.eclipse.ui.android.sorted.res"); //$NON-NLS-1$
				URL fullPathString = FileLocator.find(bundle, new Path("images/" + name), null); //$NON-NLS-1$
				ImageDescriptor imageDesc = ImageDescriptor.createFromURL(fullPathString);
				image = imageDesc.createImage();
			} catch (Exception e){
			}
		}
		return image;
	}

}
