import java.io.Serializable;

public class Nasabah implements Serializable, Authenticatable{
    private String nama;
    private String address;
    private String dob;
    private String cardNumber;
    private String pin;
    private String noIdentitas;
    private AkunBank akun;
    private transient String currentOTP; // OTP saat ini, tidak disimpan di file

    

    // Constructor lengkap
    public Nasabah(String nama, String address, String dob, String cardNumber, String pin,
        String noIdentitas, AkunBank akun) {
        this.nama = nama;
        this.address = address;
        this.dob = dob;
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.noIdentitas = noIdentitas;
        this.akun = akun;
    }

    // Getter umum (tidak ada getPin!)
    public String getNama() {
        return nama;
    }

    public String getAddress() {
        return address;
    }

    public String getDob() {
        return dob;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getNoIdentitas() {
        return noIdentitas;
    }

    public AkunBank getAkun() {
        return akun;
    }

    // public String getPIN() {
    //     return pin; // Hanya untuk tujuan demonstrasi membaca di BacadataNasabah.java (Uji Coba aja), sebaiknya tidak diakses langsung Jadi private jadi kita tidak buat getter getPin() karena justru akan bisa membocorkan datanya.
    // }

    // Verifikasi PIN
    public boolean verifyPIN(String inputPin) {
        return pin != null && pin.equals(inputPin);
    }

    // Login dengan nomor kartu dan PIN, jika cocok kirim OTP (simulasi)
    @Override
    public boolean login(String inputCardNumber, String inputPin) {
        if (cardNumber != null && pin != null &&
            cardNumber.equals(inputCardNumber) &&
            pin.equals(inputPin)) {

            currentOTP = OTPUtil.generateOTP();
            System.out.println("OTP terkirim (simulasi): " + currentOTP);
            return true;
        }
        return false;
    }

    // Verifikasi OTP dan hapus setelah dipakai
    @Override
    public boolean verifyOTP(String inputOtp) {
        boolean valid = currentOTP != null && currentOTP.equals(inputOtp);
        if (valid) {
            currentOTP = null; // Hapus OTP agar tidak bisa digunakan ulang
        }
        return valid;
    }
}
