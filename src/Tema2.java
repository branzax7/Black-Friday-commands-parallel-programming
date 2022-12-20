import java.io.*;
//import javafx.util.Pair;
import java.security.KeyPair;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Tema2 {

    public static int P;
    public static Semaphore semaphore = new Semaphore(1);
    public static Semaphore semaphore2 = new Semaphore(1);
    public static String orders_in;
    public static String products_in;



    public static void main(String[] args) {
        System.out.println("Hello world!");
        

        System.out.println("First arg: " + args[0] + "  Second arg: " + args[1]);

        

        BufferedReader reader , reader2;
        //Pair<String, Integer> pair = new Pair<>();
        orders_in = args[0];
        products_in = args[1];

        P = Integer.parseInt(args[2]);
        int nr = 0;

        //String line;
        Thread[] threads = new Thread[P];

        try {
            FileWriter f_out1 = new FileWriter("order_products_out.txt");
            f_out1.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            FileWriter f_out2 = new FileWriter("orders_out.txt");
            f_out2.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            reader = new BufferedReader(new FileReader(orders_in));

            String order = reader.readLine(); //de prelucrat line-ul inainte de trimis in Myrunnable
            while (order != null) {
                nr = 0;
                while (nr < P && order != null) {

                    threads[nr] = new Thread(new MyRunnable(order));
                    threads[nr].start();
                    order = reader.readLine();
                    nr++;
                }

                for (int i = 0; i < nr; i++) {
                    try {
                        threads[i].join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }



            reader.close();


        } catch (IOException e) {
            e.printStackTrace();
        }




    }
}