import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        List<File> fileList = Arrays.asList(new File("/home/dmitry/Games/savegames/save0.dat"),
                new File("/home/dmitry/Games/savegames/save1.dat"),
                new File("/home/dmitry/Games/savegames/save2.dat"));
        GameProgress[] newList =
                {new GameProgress(100, 3, 32, 54),
                        new GameProgress(95, 15, 12, 365.1),
                        new GameProgress(2, 8, 2, 44)};

        saveGame(newList);
        zipFiles(newList);
        for (File x : fileList) {
            deleteFile(x);
        }
    }

    public static void saveGame(GameProgress[] newList) {
        for (int i = 0; i < newList.length; i++) {
            try (FileOutputStream fos = new FileOutputStream("/home/dmitry/Games/savegames/save" + i + ".dat");
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(newList[i]);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void zipFiles(GameProgress[] newList) {
        try (ZipOutputStream zout = new ZipOutputStream(new
                FileOutputStream("/home/dmitry/Games/savegames/zip_output.zip"))) {
            for (int i = 0; i < newList.length; i++) {
                FileInputStream fis = new FileInputStream("/home/dmitry/Games/savegames/save" + i + ".dat");
                ZipEntry entry = new ZipEntry("save" + i + ".dat");
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }


    public static void deleteFile(File file) {
        if (file.delete()) {
            System.out.println("Файл " + file.getName() + " удален");
        } else {
            System.out.println("Файл " + file.getName() + " не может быть удален");
        }

    }
}