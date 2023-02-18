import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {

        GameProgress gameProgress1 = new GameProgress(100, 20, 5, 123.9);
        GameProgress gameProgress2 = new GameProgress(80, 40, 10, 211.3);
        GameProgress gameProgress3 = new GameProgress(76, 10, 7, 175.8);

        saveGame("D://Game/savegames/save.data", gameProgress1);
        saveGame("D://Game/savegames/save2.data", gameProgress2);
        saveGame("D://Game/savegames/save3.data", gameProgress3);

        List<String> list = new ArrayList<>();
        list.add("D://Game/savegames/save.data");
        list.add("D://Game/savegames/save2.data");
        list.add("D://Game/savegames/save3.data");

        zipFiles("D://Game/savegames/zip_output.zip", list);

       deleteFile(list);
    }
    public static void saveGame(String fullPathFiles, GameProgress gameProgress) {

        String[] string = new String[3];

        try (FileOutputStream fos = new FileOutputStream(fullPathFiles);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);

        } catch (Exception e) {
            System.out.println("Не удалось записать файл!!");
        }
        for (int i = 0; i < string.length; i++) {
            string[i] = fullPathFiles;
        }
    }
    public static void zipFiles(String fullPathArchiveFiles, List<String> list) {

        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(fullPathArchiveFiles))) {

            for (String path : list) {
                FileInputStream fis = new FileInputStream(path);
                ZipEntry entry = new ZipEntry(getName(path));
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
                fis.close();
            }
        } catch (Exception ex) {
            System.out.println("Не удалось заархивировать файл");
        }
    }
    public static String getName(String path) {

        int index = path.lastIndexOf("/");
        return path.substring(index + 1);
    }
    public static void deleteFile(List<String> list) {

        for (String path : list) {
            File file = new File(path);
            if (file.delete()) {
                System.out.println("Удаление прошло успешно!");
            } else {
                System.out.println("Файл не удалился!");
            }
        }

//        File file = new File(list.toString());
//        if (file.isDirectory()){
//            for (File item : file.listFiles()){
//                if (item.delete()){
//                    System.out.println("Удаление прошло успешно!");
//                }else {
//                    System.out.println("Файл не удалился!");
//                }
//            }
//        }
    }
}
