import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;


public class MyRunnable implements Runnable{

    String order;

    public MyRunnable(String order){
        this.order = order;
    }
    
    @Override
    public void run() {
        BufferedReader reader2;
        int P = Tema2.P;
    
        int nr = 0;

        Thread[] threads = new Thread[P];

        String[] result = order.split(",");

        String id_comanda = result[0];
        int nr_produse = Integer.parseInt(result[1]);

        Produse produs = new Produse(nr_produse);

        if (nr_produse != 0) {
            try {
                reader2 = new BufferedReader(new FileReader(Tema2.products_in));

                String order_product = reader2.readLine();
                while (order_product != null && produs.nr_produse != 0) {
                    nr = 0;
                    while (nr < P && order_product != null) {
                        threads[nr] = new Thread(new MyRunnable2(order_product, id_comanda, produs));
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

            synchronized(this){
                if (produs.nr_produse == 0){
                    try {
                        FileWriter fw = new FileWriter("orders_out.txt", true);
                        BufferedWriter bw = new BufferedWriter(fw);
                        bw.write(order + ",shipped\n");
                        bw.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
