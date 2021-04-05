import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.nio.file.*;



public class FileWatcher implements Runnable {
    public Thread dirMonitorThread;
    private WatchService watchService;
    private WatchKey watchKey;
    private Controller controller;



    void register(String directoryPath) throws IOException, ClassNotFoundException {
        System.out.println("Путь : " + directoryPath);
        Path faxFolder = Paths.get(directoryPath);
        try {
            watchService = FileSystems.getDefault().newWatchService();
        } catch (IOException e1) {
            System.out.println("directoryPath NOT found");
            e1.printStackTrace();
        }
        try {
            faxFolder.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            watchKey = watchService.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startWatcher(directoryPath);
    }

    public void startWatcher(String directoryPath) throws IOException, ClassNotFoundException {
        boolean valid = true;
        do {
            for (WatchEvent event : watchKey.pollEvents()) {
                event.kind();
                String eventType = "";
                String tempPath = directoryPath;
                String fileName = "";
                if (StandardWatchEventKinds.ENTRY_CREATE.equals(event.kind())) {
                    fileName = event.context().toString();
                    System.out.println("File Created:" + fileName
                            + ", EventKind : " + event.kind());
                    eventType = event.kind().toString();
                } else if (StandardWatchEventKinds.ENTRY_DELETE.equals(event
                        .kind())) {
                    fileName = event.context().toString();
                    System.out.println("File Deleted:" + fileName
                            + ", EventKind : " + event.kind());
                    eventType = event.kind().toString();
                }
            }
            valid = watchKey.reset();
        } while (valid);
    }

    public void stop() {
        dirMonitorThread.interrupt();
        try {
            this.watchService.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
            String name = LoginController.getFolderName();
            FileWatcher fileWatcher = null;
            fileWatcher = new FileWatcher();
            String directoryPath = "Server/ServerStorage/" + name;
            try {
                fileWatcher.register(directoryPath);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
}