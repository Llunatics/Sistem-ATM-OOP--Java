
// Untuk membaca data nasabah dari file nasabah.dat (menunjukan data Tersimpam di File Hanya untuk mencoba doang yakkkk ):
// import java.io.*;
// import java.util.*;

// public class BacaDataNasabah {
//     public static void main(String[] args) {
//         List<Nasabah> daftarNasabah;

//         try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("nasabah.dat"))) {
//             daftarNasabah = (List<Nasabah>) ois.readObject();

//             System.out.println("=========== DAFTAR NASABAH ===========\n");

//             int nomor = 1;
//             for (Nasabah n : daftarNasabah) {
//                 System.out.println("Nasabah #" + nomor++);
//                 System.out.println("Nama           : " + n.getNama());
//                 System.out.println("Alamat         : " + n.getAddress());
//                 System.out.println("Tanggal Lahir  : " + n.getDob());
//                 System.out.println("No Identitas   : " + n.getNoIdentitas());
//                 System.out.println("No Rekening    : " + n.getAkun().getNoRekening());
//                 System.out.println("Jenis Akun     : " + n.getAkun().getJenisAkun());
//                 System.out.printf ("Saldo          : Rp %,12.2f\n", n.getAkun().getSaldo());
//                 System.out.println("No Kartu ATM   : " + n.getCardNumber());
//                 System.out.println("PIN            : " + n.getPIN()); (ini simulasi saja Bapak )untuk membuktikan data pin tersimpan di file .dat ( harusnya gausah Mencegah Penyalahgunaan ) : Kalau ada fitur "lihat PIN", bisa disalahgunakan oleh siapa pun yang mengakses aplikasi.

//                 System.out.println("--------------------------------------");
//             }

//         } catch (Exception e) {
//             System.out.println("Gagal membaca file nasabah.dat");
//             e.printStackTrace();
//         }
//     }
// }
