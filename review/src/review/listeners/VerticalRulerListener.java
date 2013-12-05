package review.listeners;


import org.eclipse.jface.text.source.IVerticalRulerListener;
import org.eclipse.jface.text.source.VerticalRulerEvent;
import org.eclipse.swt.widgets.Menu;

public class VerticalRulerListener implements IVerticalRulerListener {
	public VerticalRulerListener() {
		System.out.println("VerticalRulerListener.VerticalRulerListener()");
	}
	
	@Override
	public void annotationContextMenuAboutToShow(VerticalRulerEvent arg0,
			Menu arg1) {
		System.out.println("VerticalRulerListener.annotationContextMenuAboutToShow()");
		// TODO Auto-generated method stub
		
	}
	@Override
	public void annotationDefaultSelected(VerticalRulerEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("VerticalRulerListener.annotationDefaultSelected()");
	
		
	}
	@Override
	public void annotationSelected(VerticalRulerEvent arg0) {
		System.out.println("VerticalRulerListener.annotationSelected()");
		// TODO Auto-generated method stub
		
	}
}
