public interface Transferable {
    boolean transfer(AkunBank tujuan, double jumlah);
    String getJenisAkun();
}
