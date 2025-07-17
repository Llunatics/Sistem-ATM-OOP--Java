import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Transaksi implements Serializable {
    private String id;
    private String jenis;
    private double jumlah;
    private String keterangan;
    private LocalDateTime waktu;
    private double postBalance;

    public Transaksi(String jenis, double jumlah, String keterangan, double postBalance) {
        this.id = UUID.randomUUID().toString();
        this.jenis = jenis;
        this.jumlah = jumlah;
        this.keterangan = keterangan;
        this.waktu = LocalDateTime.now();
        this.postBalance = postBalance;
    }

    public String getId() { return id; }
    public String getJenis() { return jenis; }
    public double getJumlah() { return jumlah; }
    public String getKeterangan() { return keterangan; }
    public LocalDateTime getWaktu() { return waktu; }
    public double getPostBalance() { return postBalance; }

    public String getTanggalString() {
        return waktu.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public String toString() {
        return getTanggalString() + " - " + jenis + ": " + jumlah + " (" + keterangan + ")" + (postBalance > 0 ? ", Saldo: " + postBalance : "");
    }
}

