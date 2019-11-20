import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class TestChannel {

    public static void main(String[] args) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        ByteBuffer byteBuffer = null;
        try {
            fis = new FileInputStream("/Users/lizehao/IdeaProject/NIOdemo/src/a.txt");
            fos = new FileOutputStream("/Users/lizehao/IdeaProject/NIOdemo/src/b.txt");
            inChannel = fis.getChannel();
            outChannel = fos.getChannel();
            byteBuffer = ByteBuffer.allocate(1024);
            while (inChannel.read(byteBuffer) != -1) {
                byteBuffer.flip();
                outChannel.write(byteBuffer);
                byteBuffer.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outChannel != null) {
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inChannel != null) {
                try {
                    inChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
