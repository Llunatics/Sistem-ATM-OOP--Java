import java.io.Serializable;
import java.util.*;

public abstract class AkunBank implements Serializable, Transferable {
    protected String noRekening;
    protected double saldo;
    protected List<Transaksi> transaksiList = new ArrayList<>();

    public AkunBank(String noRekening, double saldo) {
        this.noRekening = noRekening;
        this.saldo = saldo;
    }

    public String getNoRekening() { return noRekening; }
    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }
    public List<Transaksi> getTransaksiList() { return transaksiList; }

    public void deposit(double jumlah) {
        if (jumlah > 0) {
            saldo += jumlah;
            createTransaksi("DEPOSIT", jumlah, "Setoran tunai");
        }
    }

    public boolean withdraw(double jumlah) {
        if (jumlah > 0 && saldo >= jumlah) {
            saldo -= jumlah;
            createTransaksi("WITHDRAW", jumlah, "Tarik tunai");
            return true;
        }
        return false;
    }

    public void createTransaksi(String jenis, double jumlah, String keterangan) {
        transaksiList.add(new Transaksi(jenis, jumlah, keterangan, saldo));
    }

    @Override
    public boolean transfer(AkunBank tujuan, double jumlah) {
        if (this.saldo >= jumlah && jumlah > 0) {
            this.saldo -= jumlah;
            tujuan.saldo += jumlah;
            this.createTransaksi("TRANSFER OUT", jumlah, "Ke: " + tujuan.noRekening);
            tujuan.createTransaksi("TRANSFER IN", jumlah, "Dari: " + this.noRekening);
            return true;
        }
        return false;
    }

    @Override
    public abstract String getJenisAkun();
}
