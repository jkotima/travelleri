package travelleri.ui;

// import travelleri.domain.NaiveTSP;
import java.util.Scanner;
import java.util.Arrays;


public class ConsoleUI {
    private String[] args;
    private Scanner scan;
    
    public ConsoleUI(String[] args) {
        this.args = args;
        this.scan = new Scanner(System.in);
    }

    public void run() {
        if (args.length == 0) {
            System.out.println("Käyttö (komentoriviargumentit):");
            System.out.println("create - uuden verkon luominen");
            System.out.println("open * - verkon avaaminen tiedostosta *");

            return;
        }

        if (args[0].equals("create")) {
            createGraph();
        }
    }

    private void createGraph() {
        System.out.print("Solmujen lukumäärä? ");
        while (!scan.hasNextInt()) scan.next();
        int nodesCount = scan.nextInt();
        double[][] newGraph = new double[nodesCount][nodesCount];
        for (int x = 0; x < nodesCount; x++) {
            for (int y = 0; y < nodesCount; y++) {
                if (x==y) {
                    newGraph[x][y] = 0;
                    continue;
                }

                if (newGraph[y][x] != 0) {
                    newGraph[x][y] = newGraph[y][x];
                    continue;
                }

                System.out.println("Kaaren "+(x+1)+"-"+(y+1)+" paino?");
                while (!scan.hasNextDouble()) scan.next();

                newGraph[x][y] = scan.nextDouble();
            }
        }
        System.out.println("Luotu verkko: ");
        for (double[] line : newGraph) {
            System.out.println(Arrays.toString(line));
        }
        
    }
}
