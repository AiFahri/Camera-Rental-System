import java.util.ArrayList;
import java.util.List;

public class Sewa implements SewaInterface {
    private String id;
    private List<Barang> barangg = new ArrayList<>();
    private List<Integer> jamSewa = new ArrayList<>();
    private List<Integer> kuantitasSewa = new ArrayList<>();

    public Sewa(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

   
    public List<Barang> getBarangs() {
        return barangg;
    }

   
    public List<Integer> getJamSewa() {
        return jamSewa;
    }

    
    public List<Integer> getKuantitasSewa() {
        return kuantitasSewa;
    }
    @Override
    public void tambahBarang(Barang barang, int jam, int kuantitas) {
        barangg.add(barang);
        jamSewa.add(jam);
        kuantitasSewa.add(kuantitas);
    }

    @Override
    public void hapusBarang(Barang barang) {
        int index = barangg.indexOf(barang);
        if (index != -1) {
            barangg.remove(index);
            jamSewa.remove(index);
            kuantitasSewa.remove(index);
        }
    }

    @Override
    public double getTotalHarga() {
        double total = 0;
        for (int i = 0; i < barangg.size(); i++) {
            total += barangg.get(i).getHargaPerJam() * jamSewa.get(i) * kuantitasSewa.get(i);
        }
        return total;
    }

    @Override
    public boolean isEligibleForDiscount() {
        int totalJam = 0;
        for (int jam : jamSewa) {
            totalJam += jam;
        }
        return totalJam > 24;
    }

    @Override
    public double getDiscountAmount() {
        return isEligibleForDiscount() ? getTotalHarga() * 0.1 : 0;
    }

    public String getDetailSewa() {
        StringBuilder sb = new StringBuilder();
        sb.append("| ID  | Nama                | Jam Sewa | Kuantitas | Total Harga |\n");
        sb.append("|-----|---------------------|----------|-----------|-------------|\n");
        for (int i = 0; i < barangg.size(); i++) {
            double totalHargaPerBarang = barangg.get(i).getHargaPerJam() * jamSewa.get(i) * kuantitasSewa.get(i);
            sb.append(String.format("| %-3s | %-19s | %-8d | %-9d | %-11.2f |\n",
                    barangg.get(i).getId(), barangg.get(i).getNama(), jamSewa.get(i), kuantitasSewa.get(i), totalHargaPerBarang));
        }
        sb.append("|-----|---------------------|----------|-----------|-------------|\n");
        return sb.toString();
    }

    @Override
    public String toString() {
        return getDetailSewa();
    }
}
