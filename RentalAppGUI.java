import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RentalAppGUI {
    private JFrame frame;
    private JTable tableAvailable;
    private JTable tableRented;
    private DefaultTableModel modelAvailable;
    private DefaultTableModel modelRented;
    private JTextField txtId;
    private JTextField txtJam;
    private JTextField txtKuantitas;
    private Sewa sewa;
    private List<Kamera> kameraTersedia;
    private List<Lighting> lightingTersedia;

    public RentalAppGUI(List<Kamera> kameraTersedia, List<Lighting> lightingTersedia, Pelanggan pelanggan, Sewa sewa2) {
        this.kameraTersedia = kameraTersedia;
        this.lightingTersedia = lightingTersedia;
        this.sewa = sewa2; 

        frame = new JFrame("Rental Application");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        modelAvailable = new DefaultTableModel(new String[]{"ID", "Nama", "Harga/Jam", "Kuantitas"}, 0);
        modelRented = new DefaultTableModel(new String[]{"ID", "Nama", "Jam Sewa", "Kuantitas"}, 0);

        tableAvailable = new JTable(modelAvailable);
        tableRented = new JTable(modelRented);

        JScrollPane scrollPaneAvailable = new JScrollPane(tableAvailable);
        scrollPaneAvailable.setBounds(30, 20, 350, 200);
        frame.add(scrollPaneAvailable);

        JScrollPane scrollPaneRented = new JScrollPane(tableRented);
        scrollPaneRented.setBounds(400, 20, 350, 200);
        frame.add(scrollPaneRented);

        JLabel lblId = new JLabel("ID Barang:");
        lblId.setBounds(30, 250, 80, 25);
        frame.add(lblId);

        txtId = new JTextField();
        txtId.setBounds(120, 250, 150, 25);
        frame.add(txtId);

        JLabel lblJam = new JLabel("Jam Sewa:");
        lblJam.setBounds(30, 290, 80, 25);
        frame.add(lblJam);

        txtJam = new JTextField();
        txtJam.setBounds(120, 290, 150, 25);
        frame.add(txtJam);

        JLabel lblKuantitas = new JLabel("Kuantitas:");
        lblKuantitas.setBounds(30, 330, 80, 25);
        frame.add(lblKuantitas);

        txtKuantitas = new JTextField();
        txtKuantitas.setBounds(120, 330, 150, 25);
        frame.add(txtKuantitas);

        JButton btnSewa = new JButton("Sewa");
        btnSewa.setBounds(30, 370, 100, 25);
        frame.add(btnSewa);

        JButton btnHapus = new JButton("Hapus");
        btnHapus.setBounds(150, 370, 100, 25);
        frame.add(btnHapus);

        JButton btnLihat = new JButton("Lihat");
        btnLihat.setBounds(270, 370, 100, 25);
        frame.add(btnLihat);

        JButton btnSelesai = new JButton("Selesai");
        btnSelesai.setBounds(390, 370, 100, 25);
        frame.add(btnSelesai);

        updateAvailableTable();
        updateRentedTable();

        // Event handler untuk tombol Sewa
        btnSewa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = txtId.getText();
                Barang barang = cariBarangById(id, kameraTersedia, lightingTersedia);
                if (barang != null) {
                    int jam = Integer.parseInt(txtJam.getText());
                    int kuantitas = Integer.parseInt(txtKuantitas.getText());
                    if (barang.getKuantitas() < kuantitas) {
                        JOptionPane.showMessageDialog(frame, "Kuantitas tidak mencukupi.");
                    } else {
                        try {
                            barang.kurangiKuantitas(kuantitas);
                            sewa.tambahBarang(barang, jam, kuantitas);
                            modelRented.addRow(new Object[]{barang.getId(), barang.getNama(), jam, kuantitas});
                            updateAvailableTable();
                            JOptionPane.showMessageDialog(frame, "Barang berhasil disewa.");
                        } catch (InsufficientQuantityException ex) {
                            JOptionPane.showMessageDialog(frame, ex.getMessage());
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Barang tidak ditemukan.");
                }
            }
        });

        // Event handler untuk tombol Hapus
        btnHapus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = txtId.getText();
                boolean ditemukan = false;
                for (int i = 0; i < sewa.getBarangs().size(); i++) {
                    Barang barang = sewa.getBarangs().get(i);
                    if (barang.getId().equals(id)) {
                        int jam = sewa.getJamSewa().get(i);
                        int kuantitas = sewa.getKuantitasSewa().get(i);
                        sewa.hapusBarang(barang);
                        ditemukan = true;
                        JOptionPane.showMessageDialog(frame, String.format("Dihapus: %s, Jam Sewa: %d, Kuantitas: %d\n", barang.getNama(), jam, kuantitas));
                        updateRentedTable();
                        updateAvailableTable();
                        break;
                    }
                }
                if (!ditemukan) {
                    JOptionPane.showMessageDialog(frame, "Barang tidak ditemukan dalam sewa.");
                }
            }
        });

        // Event handler untuk tombol Lihat
        btnLihat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, sewa.toString());
            }
        });

        // Event handler untuk tombol Selesai
        btnSelesai.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double totalHarga = sewa.getTotalHarga();
                if (sewa.isEligibleForDiscount()) {
                    JOptionPane.showMessageDialog(frame, String.format("Selamat Anda mendapatkan diskon sebesar %.2f karena telah menyewa barang lebih dari 24 jam.", sewa.getDiscountAmount()));
                }
                JOptionPane.showMessageDialog(frame, String.format("Total Harga: %.2f", totalHarga));
                String uangStr = JOptionPane.showInputDialog(frame, "Masukkan jumlah uang yang Anda miliki:");
                double uang = Double.parseDouble(uangStr);

                double totalSetelahDiskon = totalHarga - sewa.getDiscountAmount();
                if (uang >= totalSetelahDiskon) {
                    JOptionPane.showMessageDialog(frame, String.format("Pembayaran berhasil. Kembalian: %.2f", uang - totalSetelahDiskon));
                } else {
                    JOptionPane.showMessageDialog(frame, String.format("Uang tidak cukup. Kurang: %.2f", totalSetelahDiskon - uang));
                }
            }
        });

        frame.setVisible(true);
    }

    private Barang cariBarangById(String id, List<Kamera> kameraTersedia, List<Lighting> lightingTersedia) {
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

    private void updateAvailableTable() {
        modelAvailable.setRowCount(0);
        for (Kamera kamera : kameraTersedia) {
            modelAvailable.addRow(new Object[]{kamera.getId(), kamera.getNama(), kamera.getHargaPerJam(), kamera.getKuantitas()});
        }
        for (Lighting lighting : lightingTersedia) {
            modelAvailable.addRow(new Object[]{lighting.getId(), lighting.getNama(), lighting.getHargaPerJam(), lighting.getKuantitas()});
        }
    }

    private void updateRentedTable() {
        modelRented.setRowCount(0);
        List<Barang> barangs = sewa.getBarangs();
        List<Integer> jamSewa = sewa.getJamSewa();
        List<Integer> kuantitasSewa = sewa.getKuantitasSewa();

        for (int i = 0; i < barangs.size(); i++) {
            modelRented.addRow(new Object[]{barangs.get(i).getId(), barangs.get(i).getNama(), jamSewa.get(i), kuantitasSewa.get(i)});
        }
    }
}
