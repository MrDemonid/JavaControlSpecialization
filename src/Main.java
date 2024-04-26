import model.fileio.FileBase;
import model.fileio.TextFile;
import view.AnsiView;
import view.View;

import java.util.List;

public class Main {
    public static void main(String[] args)
    {
        View view = new AnsiView();
        view.output("Loading file 'input.txt' to buffer on 1024 bytes\n");

        FileBase file = new TextFile("assets/input.txt");
        try {
            List<String> lst = file.load();
            view.output(lst);
        } catch (Exception e)
        {
            view.error(e.getMessage());
        }
    }
}