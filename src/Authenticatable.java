public interface Authenticatable {
    boolean login(String cardNumber, String pin);
    boolean verifyOTP(String otp);
    boolean verifyPIN(String pin); 
}
