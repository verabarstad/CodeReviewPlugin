
package review.editors;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationAccess;
import org.eclipse.jface.text.source.IAnnotationAccessExtension;
import org.eclipse.jface.text.source.ImageUtilities;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;

//santa's little helper
public class AnnotationMarkerAccess implements IAnnotationAccess,
		IAnnotationAccessExtension {
	public Object getType(Annotation annotation) {
		return annotation.getType();
	}

	public boolean isMultiLine(Annotation annotation) {
		return true;
	}

	public boolean isTemporary(Annotation annotation) {
		return !annotation.isPersistent();
	}

	

	public boolean isSubtype(Object annotationType, Object potentialSupertype) {
		if (annotationType.equals(potentialSupertype))
			return true;

		return false;

	}

	public Object[] getSupertypes(Object annotationType) {
		return new Object[0];
	}

	@Override
	public int getLayer(Annotation arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getTypeLabel(Annotation arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPaintable(Annotation arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void paint(Annotation arg0, GC arg1, Canvas arg2, Rectangle arg3) {
		// TODO Auto-generated method stub
		
	}
}