public class Tabungan extends AkunBank {
    public Tabungan(String noRekening, double saldo) {
        super(noRekening, saldo);
    }

    @Override
    public String getJenisAkun() {
        return "Tabungan";
    }

    // withdraw khusus tabungan Jaga" Klo misalkan ada logika khusus kita bisa tambahkan di sini pak
  
  
     @Override
public boolean withdraw(double jumlah) {
    // Batas maksimal per transaksi
    if (jumlah > 5_000_000) {
        System.out.println("Maksimal tarik tunai per transaksi Rp 5.000.000.");
        return false;
    }

    return super.withdraw(jumlah);
}


}
