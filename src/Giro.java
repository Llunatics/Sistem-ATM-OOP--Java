public class Giro extends AkunBank {
    public Giro(String noRekening, double saldo) {
        super(noRekening, saldo);
    }

    @Override
    public String getJenisAkun() {
        return "Giro";
    }
    

       @Override
    public boolean withdraw(double jumlah) {
        // Batas maksimal per transaksi untuk Giro Misalkan dsisini naro bisa lebih besar dibanding sitabungan 
        if (jumlah > 10_000_000) {
            System.out.println("Maksimal tarik tunai per transaksi untuk Giro adalah Rp 10.000.000.");
            return false;
        }

        return super.withdraw(jumlah);
    }
}
