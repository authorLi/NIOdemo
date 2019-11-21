import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TestChannel {

    @Test
    public void test1() throws Exception {
        FileInputStream fis = new FileInputStream("/Users/lizehao/IdeaProject/NIOdemo/src/a.txt");
        FileOutputStream fos = new FileOutputStream("/Users/lizehao/IdeaProject/NIOdemo/src/b.txt");
        FileChannel inChannel = fis.getChannel();
        FileChannel outChannel = fos.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while (inChannel.read(byteBuffer) != -1) {
            byteBuffer.flip();
            outChannel.write(byteBuffer);
            byteBuffer.clear();
        }
        if (fos != null) {
            fos.close();
        }
        if (fis != null) {
            fis.close();
        }
        if (outChannel != null) {
            outChannel.close();
        }
        if (inChannel != null) {
            inChannel.close();
        }
    }

    @Test
    public void test2() throws Exception {
        FileChannel inChannel = FileChannel.open(Paths.get("/Users/lizehao/IdeaProject/NIOdemo/src/a.txt"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("/Users/lizehao/IdeaProject/NIOdemo/src/c.txt"), StandardOpenOption.READ,StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        MappedByteBuffer inMappedBuf = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer outMappedBuf = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

        byte[] dst = new byte[inMappedBuf.limit()];
        inMappedBuf.get(dst);
        outMappedBuf.put(dst);
    }

    @Test
    public void test3() throws Exception {
        FileChannel inChannel = FileChannel.open(Paths.get("/Users/lizehao/IdeaProject/NIOdemo/src/a.txt"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("/Users/lizehao/IdeaProject/NIOdemo/src/d.txt"), StandardOpenOption.WRITE, StandardOpenOption.READ);
        inChannel.transferTo(0, inChannel.size(), outChannel);
        inChannel.close();
        outChannel.close();
    }
}
