import java.io.*;
import java.util.*;

@SuppressWarnings("unchecked")

public class Database {
    private static final String FILE = "nasabah.dat";

    public static List<Nasabah> loadNasabah() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE))) {
            return (List<Nasabah>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static void saveNasabah(List<Nasabah> nasabahList) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE))) {
            oos.writeObject(nasabahList);
        } catch (Exception e) {
            System.out.println("Gagal menyimpan data nasabah.");
        }
    }
}
