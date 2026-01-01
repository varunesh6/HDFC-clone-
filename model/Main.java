package model;

import controller.Main_Controller;

public class Main {
    public static void main(String[] args) {

        Main_Controller controller = new Main_Controller();

        while (true) {
            controller.startMainMenu();
        }
    }
}
