import java.io.*;
import java.util.*;

public class Tema2 {

    public static int P;
    public static String orders_in;
    public static String products_in;

    public static void main(String[] args) {

        BufferedReader reader;
        String folder;
        folder = args[0];
        orders_in = folder + "/orders.txt";
        products_in = folder + "/order_products.txt";

        P = Integer.parseInt(args[1]);
        int nr = 0;

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
                    //System.out.println(order);
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