package travelleri;

import java.util.Scanner;
import travelleri.ui.ConsoleUI;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        new ConsoleUI(args, scan).run();
    }
}
