interface Playable {
    void play();
}

interface Compressible {
    void compress();
}

abstract class MediaFile {
    private String fileName;
    private double sizeInMB;

    public MediaFile(String fileName, double sizeInMB) {
        this.fileName = fileName;
        this.sizeInMB = sizeInMB;
    }

    public abstract void open();

    public String getFileName() { return fileName; }
    public double getSizeInMB() { return sizeInMB; }
}

class ImageFile extends MediaFile implements Compressible {
    public ImageFile(String fileName, double sizeInMB) {
        super(fileName, sizeInMB);
    }

    @Override
    public void open() {
        System.out.println("開啟圖片檔案：" + getFileName());
    }

    @Override
    public void compress() {
        System.out.println("進行 JPEG 圖片壓縮：" + getFileName());
    }
}

class AudioFile extends MediaFile implements Playable, Compressible {
    public AudioFile(String fileName, double sizeInMB) {
        super(fileName, sizeInMB);
    }

    @Override
    public void open() {
        System.out.println("載入音訊檔案：" + getFileName());
    }

    @Override
    public void play() {
        System.out.println("播放音軌中..." + getFileName());
    }

    @Override
    public void compress() {
        System.out.println("進行 MP3 音訊壓縮：" + getFileName());
    }
}

class VideoFile extends MediaFile implements Playable, Compressible {
    public VideoFile(String fileName, double sizeInMB) {
        super(fileName, sizeInMB);
    }

    @Override
    public void open() {
        System.out.println("解碼影片檔案：" + getFileName());
    }

    @Override
    public void play() {
        System.out.println("串流播放高畫質影片..." + getFileName());
    }

    @Override
    public void compress() {
        System.out.println("進行 H.264 影片轉碼壓縮：" + getFileName());
    }
}

public class MediaProcessingSystem {
    public static void main(String[] args) {
        MediaFile[] mediaFiles = new MediaFile[]{
            new ImageFile("banner.png", 4.5),
            new AudioFile("song.mp3", 8.2),
            new VideoFile("movie.mp4", 1250.0)
        };

        for (MediaFile file : mediaFiles) {
            System.out.println("------------------------------------");
            file.open();

            if (file instanceof Playable) {
                ((Playable) file).play();
            }

            if (file instanceof Compressible) {
                ((Compressible) file).compress();
            }
        }
        System.out.println("------------------------------------");
    }
}