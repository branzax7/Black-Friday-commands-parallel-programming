import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class MyRunnable implements Runnable{

    String order;
    //public static int nr_produse;
    //public static FileWriter file;

    public MyRunnable(String order){
        this.order = order;
    }
    @Override
    public void run() {
        System.out.println(order);
        BufferedReader reader , reader2;
        int P = Main.P;
        int nr = 0;
        //String line;
        Thread[] threads = new Thread[P];

        String[] result = order.split(",");
        System.out.println("SPLIT: " +result[0] + " " + result[1]);
        String id_comanda = result[0];
        int nr_produse = Integer.parseInt(result[1]);
        System.out.println("Intule este: " + nr_produse );
        Produse produs = new Produse(nr_produse);
        System.out.println("Pr este: " + produs.nr_produse);

//        try {
//            file = new FileWriter("order_products_out.txt");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        try {
            reader2 = new BufferedReader(new FileReader("order_products.txt"));

            String order_product = reader2.readLine();
            while (order_product != null) {
                nr = 0;
                while (nr < P && order_product != null) {
                    //System.out.println("@@@@@@@@@@@@@ " + line2);
                    threads[nr] = new Thread(new MyRunnable2(nr, order_product, id_comanda, produs));
                    threads[nr].start();
                    order_product = reader2.readLine();
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



            reader2.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Main.semaphore2.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Pr este: " + produs.nr_produse);
        if (produs.nr_produse == 0){
            System.out.println("OOOOOO");
            try {
                FileWriter fw = new FileWriter("orders_out.txt", true);
                fw.write(id_comanda +","+nr_produse+",shipped\n");
                fw.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        Main.semaphore2.release();


    }
}
