package review.perspective;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import review.utils.Common;
import review.views.CodeFileDetails;
import review.views.FileList;



public class Perspective implements IPerspectiveFactory {

       
        public void createInitialLayout(IPageLayout layout) {
        	   
               
                String editorArea = IPageLayout.ID_EDITOR_AREA;
                String packageExplorer = "org.eclipse.jdt.ui.PackageExplorer";
                                         
                //layout.addView(CodeReviewFiles.ID, IPageLayout.LEFT,0.27f, editorArea);
                layout.addView(FileList.ID, IPageLayout.LEFT,0.27f, editorArea);
                layout.addView(packageExplorer, IPageLayout.BOTTOM,0.27f, FileList.ID);
                layout.getViewLayout(FileList.ID).setCloseable(false);
                layout.getViewLayout(FileList.ID).setMoveable(false);
                
                layout.addView(CodeFileDetails.ID,IPageLayout.BOTTOM,0.73f, editorArea);
                 //layout.addView(TextView.ID,IPageLayout.RIGHT, 0.73f, FileList.ID);
                 //layout.addView(CodeFileView.ID,IPageLayout.BOTTOM,0.73f, TextView.ID);
                 
                 
               
                //IFolderLayout folder = layout.createFolder("views", IPageLayout.RIGHT, 0.73f, CodeFileView2.ID);
                //folder.addView(TextView.ID);
                //folder.addView(CodeFileView.ID);
                
                //folder.addView("org.eclipse.ui.console.ConsoleView");
              // folder.addView(IPageLayout.ID_PROP_SHEET);
                //folder.addView("org.eclipse.ui.views.CodeFileView");
               
                //layout.addView(IPageLayout.ID_OUTLINE, IPageLayout.RIGHT, 0.7f, "views");
                //layout.addView(CodeReviewFiles.ID, IPageLayout.BOTTOM, 0.68f, packageExplorer);
                
        }
}



