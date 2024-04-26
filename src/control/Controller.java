package control;

import model.Basket;
import view.View;


public class Controller {

    View view;
    Basket basket;

    public Controller(View view)
    {
        this.view = view;
        basket = new Basket();
    }

    public void run()
    {
    }
}
