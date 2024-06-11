import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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

        Scanner scanner = new Scanner(System.in);
        Pelanggan pelanggan = new Pelanggan("001", "Ai");
        pelanggan.buatSewa("S1");
        Sewa sewa = pelanggan.getSewa("S1");

        tampilkanBarangTersedia(kameraTersedia, lightingTersedia);

        while (true) {
            System.out.println("Masukkan ID barang untuk disewa, 'hapus' untuk menghapus barang, 'lihat' untuk melihat barang sewaan, atau 'selesai' untuk menyelesaikan:");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("selesai")) {
                break;
            } else if (input.equalsIgnoreCase("lihat")) {
                System.out.println("Barang yang disewa:");
                System.out.println(sewa.toString());
            } else if (input.equalsIgnoreCase("hapus")) {
                System.out.println("Masukkan ID barang yang ingin dihapus:");
                String idHapus = scanner.nextLine();
                Barang barangHapus = cariBarangById(idHapus, kameraTersedia, lightingTersedia);
                if (barangHapus != null) {
                    sewa.hapusBarang(barangHapus);
                    System.out.println("Barang berhasil dihapus.");
                } else {
                    System.out.println("Barang tidak ditemukan.");
                }
            } else {
                Barang barang = cariBarangById(input, kameraTersedia, lightingTersedia);
                if (barang != null) {
                    System.out.println("Masukkan jumlah jam sewa:");
                    int jam = scanner.nextInt();
                    System.out.println("Masukkan kuantitas:");
                    int kuantitas = scanner.nextInt();
                    scanner.nextLine(); // consume newline

                    if (jam < 6 || kuantitas < 1) {
                        System.out.println("Jam sewa minimal adalah 6 jam dan kuantitas harus lebih dari 0.");
                    } else if (kuantitas > barang.getKuantitas()) {
                        System.out.println("Kuantitas yang diminta melebihi kuantitas yang tersedia.");
                    } else {
                        sewa.tambahBarang(barang, jam, kuantitas);
                        barang.setKuantitas(barang.getKuantitas() - kuantitas);
                        System.out.printf("Ditambahkan: Id Barang: '%s', Nama Barang: '%s', Harga Per Jam: %.2f, Kuantitas Tersisa: %d untuk %d jam, kuantitas: %d\n",
                                barang.getId(), barang.getNama(), barang.getHargaPerJam(), barang.getKuantitas(), jam, kuantitas);
                    }
                } else {
                    System.out.println("Barang tidak ditemukan.");
                }
            }
        }

        System.out.println("Detail Sewa:");
        System.out.println(sewa.toString());
        System.out.printf("Total Harga: %.2f\n", sewa.getTotalHarga());
        if (sewa.isEligibleForDiscount()) {
            System.out.printf("Diskon: %.2f\n", sewa.getDiscountAmount());
            System.out.printf("Total Setelah Diskon: %.2f\n", sewa.getTotalHarga() - sewa.getDiscountAmount());
        }

        System.out.println("Masukkan jumlah uang yang Anda miliki:");
        double uang = scanner.nextDouble();
        double totalHarga = sewa.getTotalHarga() - sewa.getDiscountAmount();
        if (uang >= totalHarga) {
            System.out.printf("Pembayaran berhasil. Kembalian: %.2f\n", uang - totalHarga);
        } else {
            System.out.printf("Uang tidak cukup. Kurang: %.2f\n", totalHarga - uang);
        }

        scanner.close();
    }

    private static void tampilkanBarangTersedia(List<Kamera> kameraTersedia, List<Lighting> lightingTersedia) {
        System.out.println("Barang yang tersedia:");
        System.out.println("|-----|---------------------|---------------|-----------|");
        System.out.println("| ID  | Nama                | Harga per Jam | Kuantitas |");
        System.out.println("|-----|---------------------|---------------|-----------|");
        for (Kamera kamera : kameraTersedia) {
            System.out.printf("| %-3s | %-19s | %-13.2f | %-9d |\n", kamera.getId(), kamera.getNama(), kamera.getHargaPerJam(), kamera.getKuantitas());
        }
        for (Lighting lighting : lightingTersedia) {
            System.out.printf("| %-3s | %-19s | %-13.2f | %-9d |\n", lighting.getId(), lighting.getNama(), lighting.getHargaPerJam(), lighting.getKuantitas());
        }
        System.out.println("|-----|---------------------|---------------|-----------|");
    }

    private static Barang cariBarangById(String id, List<Kamera> kameraTersedia, List<Lighting> lightingTersedia) {
        for (Kamera kamera : kameraTersedia) {
            if (kamera.getId().equals(id)) {
                return kamera;
            }
        }
        for (Lighting lighting : lightingTersedia) {
            if (lighting.getId().equals(id)) {
                return lighting;
            }
        }
        return null;
    }
}