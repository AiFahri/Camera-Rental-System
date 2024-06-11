public interface SewaInterface {
    void tambahBarang(Barang barang, int jam, int kuantitas);
    void hapusBarang(Barang barang);
    double getTotalHarga();
    boolean isEligibleForDiscount();
    double getDiscountAmount();
}