import java.util.*;

public class App {
    private static List<Nasabah> nasabahList;
    private static Scanner scanner = new Scanner(System.in);
    private static Nasabah currentNasabah;

    public static void main(String[] args) {
        nasabahList = Database.loadNasabah();
        System.out.println("========================================");
        System.out.println("      SELAMAT DATANG DI ATM SEDERHANA   ");
        System.out.println("========================================");
        while (true) {
            System.out.println("\n+-----------------------------+");
            System.out.println("| 1. Login                    |");
            System.out.println("| 2. Daftar Nasabah Baru      |");
            System.out.println("| 3. Keluar                   |");
            System.out.println("+-----------------------------+");
            System.out.print("Pilih menu: ");
            String menu = scanner.nextLine();
            switch (menu) {
                case "1":
                    if (login()) {
                        menuUtama();
                    } else {
                        System.out.println("Login gagal. Coba lagi.");
                    }
                    break;
                case "2":
                    daftarNasabahBaru();
                    break;
                case "3":
                    System.out.println("Terima kasih!");
                    System.exit(0);
                default:
                    System.out.println("Menu tidak valid.");
            }
        }
    }

private static boolean login() {
    System.out.println("\n======= LOGIN NASABAH =======");
    System.out.print("No Kartu ATM : ");
    String cardNumber = scanner.nextLine();
    System.out.print("PIN          : ");
    String pin = scanner.nextLine();

    for (Nasabah n : nasabahList) {
        if (n instanceof Authenticatable autentikasi) {
            if (autentikasi.login(cardNumber, pin)) {
                System.out.print("Masukkan OTP: ");
                String otp = scanner.nextLine();
                if (autentikasi.verifyOTP(otp)) {
                    currentNasabah = n;
                    return true;
                } else {
                    System.out.println("OTP salah. Autentikasi gagal.");
                    return false;
                }
            }
        }
    }
    return false;
}


    private static void menuUtama() {
        while (true) {
            System.out.println("\n========================================");
            System.out.println("  Halo, " + currentNasabah.getNama() + " (" + currentNasabah.getAkun().getJenisAkun() + ")");
            System.out.println("========================================");
            System.out.println("| 1. Cek Saldo              |");
            System.out.println("| 2. Tarik Tunai            |");
            System.out.println("| 3. Setor Tunai            |");
            System.out.println("| 4. Transfer               |");
            System.out.println("| 5. Histori Transaksi      |");
            System.out.println("| 6. Info Akun              |");
            System.out.println("| 7. Ganti PIN              |");
            System.out.println("| 8. Logout                 |");
            System.out.println("+---------------------------+");
            System.out.print("Pilih menu: ");
            String pilih = scanner.nextLine();
            switch (pilih) {
                case "1":
                    System.out.println("\n------------------------------");
                    System.out.printf("Saldo Anda: Rp %, .0f\n", currentNasabah.getAkun().getSaldo());
                    System.out.println("------------------------------");
                    break;
                case "2":
                    tarikTunai();
                    break;
                case "3":
                    setorTunai();
                    break;
                case "4":
                    transfer();
                    break;
                case "5":
                    historiTransaksi();
                    break;
                case "6":
                    infoAkun();
                    break;
                case "7":
                    gantiPIN();
                    break;
                case "8":
                    currentNasabah = null;
                    Database.saveNasabah(nasabahList);
                    return;
                default:
                    System.out.println("Menu tidak valid.");
            }
        }
    }

    private static void tarikTunai() {
        System.out.println("\n======= TARIK TUNAI =======");
        System.out.print("Jumlah tarik tunai: Rp ");
        double jumlah = Double.parseDouble(scanner.nextLine());
        System.out.print("Masukkan PIN Anda untuk konfirmasi tarik tunai: ");
        String pin = scanner.nextLine();
        if (!currentNasabah.verifyPIN(pin)) {
            System.out.println("\nPIN salah. Tarik tunai dibatalkan.");
            return;
        }
        if (jumlah > 0 && currentNasabah.getAkun().withdraw(jumlah)) {
            System.out.println("\n====================================");
            System.out.println("  Berhasil tarik tunai");
            System.out.println("  Jumlah : Rp " + String.format("%,.0f", jumlah));
            System.out.println("  Saldo  : Rp " + String.format("%,.0f", currentNasabah.getAkun().getSaldo()));
            System.out.println("====================================");
        } else {
            System.out.println("\nSaldo tidak cukup atau jumlah tidak valid.");
        }
    }

    private static void setorTunai() {
        System.out.println("\n======= SETOR TUNAI =======");
        System.out.print("Jumlah setor tunai: Rp ");
        double jumlah = Double.parseDouble(scanner.nextLine());
        if (jumlah > 0) {
            currentNasabah.getAkun().deposit(jumlah);
            System.out.println("\n====================================");
            System.out.println("  Setor tunai berhasil");
            System.out.println("  Jumlah : Rp " + String.format("%,.0f", jumlah));
            System.out.println("  Saldo  : Rp " + String.format("%,.0f", currentNasabah.getAkun().getSaldo()));
            System.out.println("====================================");
        } else {
            System.out.println("Jumlah tidak valid.");
        }
    }

    private static void transfer() {
        System.out.println("\n======= TRANSFER =======");
        System.out.print("No Rekening tujuan: ");
        String tujuan = scanner.nextLine();
        AkunBank akunTujuan = null;
        for (Nasabah n : nasabahList) {
            if (n.getAkun().getNoRekening().equals(tujuan)) {
                akunTujuan = n.getAkun();
                break;
            }
        }
        if (akunTujuan == null) {
            System.out.println("Rekening tujuan tidak ditemukan.");
            return;
        }
        System.out.print("Jumlah transfer: Rp ");
        double jumlah = Double.parseDouble(scanner.nextLine());
        System.out.print("Masukkan PIN Anda untuk konfirmasi transfer: ");
        String pin = scanner.nextLine();
        if (!currentNasabah.verifyPIN(pin)) {
            System.out.println("\nPIN salah. Transfer dibatalkan.");
            return;
        }
        if (currentNasabah.getAkun().transfer(akunTujuan, jumlah)) {
            System.out.println("\n====================================");
            System.out.println("  Transfer berhasil");
            System.out.println("  Ke      : " + tujuan);
            System.out.println("  Jumlah  : Rp " + String.format("%,.0f", jumlah));
            System.out.println("  Saldo   : Rp " + String.format("%,.0f", currentNasabah.getAkun().getSaldo()));
            System.out.println("====================================");
        } else {
            System.out.println("\nTransfer gagal. Saldo tidak cukup atau jumlah tidak valid.");
        }
    }

    private static void historiTransaksi() {
        List<Transaksi> list = currentNasabah.getAkun().getTransaksiList();
        if (list.isEmpty()) {
            System.out.println("Belum ada transaksi.");
        } else {
            System.out.println("\n================ HISTORI TRANSAKSI ================");
            System.out.printf("%-12s | %-10s | %-12s | %-18s | %-15s\n", "Tanggal", "Jenis", "Jumlah", "Keterangan", "Saldo Akhir");
            System.out.println("------------------------------------------------------------------------------------------");
            for (Transaksi t : list) {
                System.out.printf("%-12s | %-10s | Rp %,10.0f | %-18s | Rp %,10.0f\n",
                    t.getTanggalString(), t.getJenis(), t.getJumlah(), t.getKeterangan(), t.getPostBalance());
            }
            System.out.println("------------------------------------------------------------------------------------------");
        }
    }

    private static void infoAkun() {
        System.out.println("\n======= INFO AKUN =======");
        System.out.println("Nama         : " + currentNasabah.getNama());
        System.out.println("Alamat       : " + currentNasabah.getAddress());
        System.out.println("Tanggal Lahir: " + currentNasabah.getDob());
        System.out.println("No Identitas : " + currentNasabah.getNoIdentitas());
        System.out.println("No Rekening  : " + currentNasabah.getAkun().getNoRekening());
        System.out.println("Jenis Akun   : " + currentNasabah.getAkun().getJenisAkun());
        System.out.println("Saldo        : Rp " + String.format("%,.0f", currentNasabah.getAkun().getSaldo()));
        System.out.println("No Kartu ATM : " + currentNasabah.getCardNumber());
    }

    private static void gantiPIN() {
        System.out.println("\n======= GANTI PIN =======");
        System.out.print("PIN lama: ");
        String pinLama = scanner.nextLine();
        if (!currentNasabah.verifyPIN(pinLama)) {
            System.out.println("PIN lama salah!");
            return;
        }
        System.out.print("PIN baru (4 digit): ");
        String pinBaru = scanner.nextLine();
        if (pinBaru.length() != 4 || !pinBaru.matches("\\d{4}")) {
            System.out.println("PIN harus 4 digit angka!");
            return;
        }
        // update PIN
        try {
            java.lang.reflect.Field f = currentNasabah.getClass().getDeclaredField("pin");
            f.setAccessible(true);
            f.set(currentNasabah, pinBaru);
        } catch (Exception e) {
            System.out.println("Gagal mengganti PIN.");
            return;
        }
        System.out.println("PIN berhasil diganti!");
        Database.saveNasabah(nasabahList);
    }

    private static void daftarNasabahBaru() {
        System.out.println("\n======= DAFTAR NASABAH BARU =======");
        System.out.print("Nama             : ");
        String nama = scanner.nextLine();
        System.out.print("Alamat           : ");
        String address = scanner.nextLine();
        System.out.print("Tanggal Lahir    : ");
        String dob = scanner.nextLine();
        System.out.print("No Kartu ATM     : ");
        String cardNumber = scanner.nextLine();
        System.out.print("PIN (4 digit)    : ");
        String pin = scanner.nextLine();
        System.out.print("No Identitas     : ");
        String noId = scanner.nextLine();
        System.out.print("Pilih jenis akun (1. Tabungan, 2. Giro): ");
        String jenis = scanner.nextLine();
        System.out.print("Buat No Rekening : ");
        String noRek = scanner.nextLine();
        // Pastikan no rekening unik
        for (Nasabah n : nasabahList) {
            if (n.getAkun().getNoRekening().equals(noRek)) {
                System.out.println("No rekening sudah terdaftar!");
                return;
            }
        }
        System.out.print("Setor awal       : Rp ");
        double saldo = Double.parseDouble(scanner.nextLine());
        AkunBank akun;
        if ("1".equals(jenis)) {
            akun = new Tabungan(noRek, saldo);
        } else if ("2".equals(jenis)) {
            akun = new Giro(noRek, saldo);
        } else {
            System.out.println("Jenis akun tidak valid.");
            return;
        }
        nasabahList.add(new Nasabah(nama, address, dob, cardNumber, pin, noId, akun));
        Database.saveNasabah(nasabahList);
        System.out.println("\nPendaftaran berhasil! Silakan login.");
    }
}


