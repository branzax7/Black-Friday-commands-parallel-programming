import java.io.*;

public class MyRunnable2 implements Runnable{
    int id;
    String order_product;
    String id_comanda;
    Produse produs;

    public MyRunnable2(int id, String order_product, String id_comanda, Produse produs){
        this.id = id;
        this.order_product = order_product;
        this.id_comanda = id_comanda;
        this.produs = produs;
    }
    @Override
    public void run() {

        String result[] = order_product.split(",");
        String id_comanda2 = result[0];
        String id_produs = result[1];

        try {
            Tema2.semaphore.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        if (id_comanda2.equals(id_comanda)){
            produs.nr_produse--;
            try {
                //dc e pus din greseala in cos tot trebuie pus la iesire da fara shipped
                FileWriter fw = new FileWriter("order_products_out.txt", true);
                fw.write(id_comanda +","+id_produs+",shipped\n");
                fw.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        Tema2.semaphore.release();


    }
}
