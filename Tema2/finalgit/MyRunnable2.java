import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MyRunnable2 implements Runnable{
    
    String order_product;
    String id_comanda;
    Produse produs;

    public MyRunnable2(String order_product, String id_comanda, Produse produs){
        this.order_product = order_product;
        this.id_comanda = id_comanda;
        this.produs = produs;
    }
    @Override
    public void run() {

        String result[] = order_product.split(",");
        String id_comanda2 = result[0];

        if (id_comanda2.equals(id_comanda)){
            synchronized(this){
                produs.nr_produse--;
                try {
                    FileWriter fw = new FileWriter("order_products_out.txt", true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(order_product + ",shipped\n");
                    bw.close();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
