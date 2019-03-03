package app;

import app.graph.Interface.Vertex;
import app.route_map.TrainStop;
import app.view.MenuView;

import java.util.function.Supplier;

interface Test<T extends Vertex> {
    void test(String abc, Supplier<T> s);
}

public class Main<T extends Vertex> implements Test<T> {

    public static void main (String[] args) {


//        Main m = new Main();
//
//        m.test("", TrainStop::new);
//
//        var menu = new MenuView();
//
//        menu.show();

    }


    @Override
    public void test(String abc, Supplier<T> s) {

    }
}
