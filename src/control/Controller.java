package control;

import ex.BarReadLineException;
import ex.NeverFileException;
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
        try {
            basket.load("input.txt");

            /*
                Задание №1
             */
            int numWords = basket.getCount();
            view.printf("В файле 'input.txt' находится %d слов %n", numWords);

            /*
                Задание №2
             */
            view.printf("Продукты с самым длинным названием: '%s' \n", basket.getLongerNames());

            /*
                Задание №3
             */
            view.printf("Частота слов: \n%s\n", basket.getFreqList());

        } catch (NeverFileException | BarReadLineException e) {
            view.error(e.getMessage());
        }
    }
}
