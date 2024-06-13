import javax.swing.SwingUtilities;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Kamera> kameraTersedia = Arrays.asList(
            new Kamera("1", "Canon EOS 60D", 15000.0, 3),
            new Kamera("2", "Canon EOS 650D", 20000.0, 5),
            new Kamera("3", "Sony a6000", 18000.0, 4),
            new Kamera("4", "Sony a6400", 22000.0, 2),
            new Kamera("5", "Sony a7II", 25000.0, 1)
        );

        List<Lighting> lightingTersedia = Arrays.asList(
            new Lighting("6", "Lighting Godox", 10000.0, 4),
            new Lighting("7", "Lighting Panggung", 30000.0, 3)
        );

        Pelanggan pelanggan = new Pelanggan("001", "Ai");
        pelanggan.buatSewa("S1");
        Sewa sewa = new Sewa("S1"); 

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RentalAppGUI(kameraTersedia, lightingTersedia, pelanggan, sewa);
            }
        });
    }
}
