package review.providers;

import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import review.model.Project;
import review.model.CodeFile;

public class StyledCodeFileLabelProvider extends StyledCellLabelProvider {
  @Override
  public void update(ViewerCell cell) {
    Object element = cell.getElement();
    StyledString text = new StyledString();

    if (element instanceof Project) {
      Project project = (Project) element;
      text.append(project.getName());
      cell.setImage(PlatformUI.getWorkbench().getSharedImages()
          .getImage(ISharedImages.IMG_OBJ_FOLDER));
      text.append(" (" +project.getCodeFiles().size() + ") ", StyledString.COUNTER_STYLER);
    } else {
      CodeFile codefile = (CodeFile) element;
      text.append(codefile.getFilename());
      cell.setImage(PlatformUI.getWorkbench().getSharedImages()
          .getImage(ISharedImages.IMG_OBJ_FILE));
    }
    cell.setText(text.toString());
    cell.setStyleRanges(text.getStyleRanges());
    super.update(cell);
  }
} 