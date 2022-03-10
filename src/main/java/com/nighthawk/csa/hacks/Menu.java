package com.nighthawk.csa.hacks;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    String title;
    Runnable action;

    public Menu(String title, Runnable action) {
        this.title = title;
        this.action = action;
    }

    public String getTitle() {
        return this.title;
    }

    public Runnable getAction() {
        return this.action;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Map<Integer, Menu> menu = new HashMap<>();

        menu.put(1, new Menu("Swap", () -> IntByReference.main(null) ) );
        menu.put(2, new Menu("Matrix", () -> Matrix.main(null) ) );

        System.out.println("Menu:");
        for (Map.Entry<Integer, Menu> pair : menu.entrySet()) {
            System.out.println(pair.getKey() + " ==> " + pair.getValue().getTitle());
        }

        int input = sc.nextInt();
        Menu m = menu.get(input);
        m.getAction().run();
    }
}
