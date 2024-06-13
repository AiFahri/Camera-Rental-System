public abstract class Barang {
    private String id;
    private String nama;
    private double hargaPerJam;
    private int kuantitas;

    public Barang(String id, String nama, double hargaPerJam, int kuantitas) {
        this.id = id;
        this.nama = nama;
        this.hargaPerJam = hargaPerJam;
        this.kuantitas = kuantitas;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public double getHargaPerJam() {
        return hargaPerJam;
    }

    public int getKuantitas() {
        return kuantitas;
    }

    public void setKuantitas(int kuantitas) {
        this.kuantitas = kuantitas;
    }
    public void kurangiKuantitas(int kuantitas) throws InsufficientQuantityException {
        if (this.kuantitas < kuantitas) {
            throw new InsufficientQuantityException("Kuantitas tidak mencukupi.");
        }
        this.kuantitas -= kuantitas;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - Harga: %.2f, Kuantitas: %d", nama, id, hargaPerJam, kuantitas);
    }
}