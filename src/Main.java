import control.Controller;
import model.fileio.FileBase;
import model.fileio.TextFile;
import view.AnsiView;
import view.View;

import java.util.List;

public class Main {
    public static void main(String[] args)
    {
        Controller controller = new Controller(new AnsiView());
        controller.run();
    }
}