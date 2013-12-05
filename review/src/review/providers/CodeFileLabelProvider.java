package review.providers;


import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import review.model.Project;
import review.model.CodeFile;


public class CodeFileLabelProvider extends LabelProvider {
  
  private static final Image FOLDER = getImage("projects.gif");
  private static final Image FILE = getImage("file.gif");
  
  
  @Override
  public String getText(Object element) {
    if (element instanceof Project) {
      Project project = (Project) element;
      return project.getName();
    }
    return ((CodeFile) element).getFilename();
  }

  @Override
  public Image getImage(Object element) {
    if (element instanceof Project) {
      return FOLDER;
    }
    return FILE;
  }

  // Helper Method to load the images
  private static Image getImage(String file) {
    Bundle bundle = FrameworkUtil.getBundle(CodeFileLabelProvider.class);
    URL url = FileLocator.find(bundle, new Path("icons/" + file), null);
    ImageDescriptor image = ImageDescriptor.createFromURL(url);
    return image.createImage();

  } 
} 