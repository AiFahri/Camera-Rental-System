import java.util.HashMap;
import java.util.Map;

public class Pelanggan implements PelangganInterface {
    @SuppressWarnings("unused")
    private String id;
    @SuppressWarnings("unused")
    private String nama;
    private Map<String, Sewa> sewaMap = new HashMap<>();

    public Pelanggan(String id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    @Override
    public void buatSewa(String id) {
        sewaMap.put(id, new Sewa(id));
    }

    @Override
    public Sewa getSewa(String id) {
        return sewaMap.get(id);
    }

    @Override
    public void hapusSewa(String id) {
        sewaMap.remove(id);
    }
}