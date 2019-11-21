import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class TestScatterAndGather {
    @Test
    public void testScatter() throws Exception {
        FileInputStream fis = new FileInputStream("/Users/lizehao/IdeaProject/NIOdemo/src/a.txt");
        FileChannel inChannel = fis.getChannel();
        ByteBuffer buffer1 = ByteBuffer.allocate(100);
        ByteBuffer buffer2 = ByteBuffer.allocate(100);
        ByteBuffer[] bytes = new ByteBuffer[]{buffer1, buffer2};
        inChannel.read(buffer1);
        for (ByteBuffer byteBuffer : bytes) {
            byteBuffer.flip();
        }
        System.out.println(new String(bytes[0].array(), StandardCharsets.UTF_8));
        System.out.println(new String(bytes[1].array(), StandardCharsets.UTF_8));
        inChannel.close();
        fis.close();
    }

    @Test
    public void testGather() throws Exception{
        FileOutputStream fos = new FileOutputStream("/Users/lizehao/IdeaProject/NIOdemo/src/f.txt");
        FileChannel outChannel = fos.getChannel();
        ByteBuffer buffer1 = ByteBuffer.allocate(100);
        ByteBuffer buffer2 = ByteBuffer.allocate(100);
        buffer1.put("abc".getBytes());
        buffer2.put("def".getBytes());
        ByteBuffer[] bytes = new ByteBuffer[]{buffer1,buffer2};
        //注意要开读模式，否则不会将你写的数据存到文件中，而是把缓冲区中剩下的空数据存入文件，因为存操作会把缓冲区的position到limit部分存入文件
        for (ByteBuffer byteBuffer:bytes) {
            byteBuffer.flip();
        }
        outChannel.write(bytes);
        outChannel.close();
        fos.close();
    }

    /**
     * 复制文件
     * @throws Exception
     */
    @Test
    public void testScatterAndGather() throws Exception{
        FileInputStream fis = new FileInputStream("/Users/lizehao/IdeaProject/NIOdemo/src/a.txt");
        FileOutputStream fos = new FileOutputStream("/Users/lizehao/IdeaProject/NIOdemo/src/g.txt");
        FileChannel inChannel = fis.getChannel();
        FileChannel outChannel = fos.getChannel();
        ByteBuffer byteBuffer1 = ByteBuffer.allocate(100);
        ByteBuffer byteBuffer2 = ByteBuffer.allocate(100);
        ByteBuffer[] bytes = new ByteBuffer[]{byteBuffer1,byteBuffer2};
        inChannel.read(bytes);
        for (ByteBuffer byteBuffer:bytes) {
            byteBuffer.flip();
        }
        System.out.println(new String(bytes[0].array(), StandardCharsets.UTF_8));
        System.out.println(new String(bytes[1].array(), StandardCharsets.UTF_8));

        outChannel.write(bytes);

        inChannel.close();
        outChannel.close();
        fis.close();
        fos.close();
    }

    @Test
    public void test() {
        String str1 = "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000";
        String str2 = "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000";
        String str3 = "asdfgjkglsjhgalrjn\n" +
                "fefae\n" +
                "fefasf";
        System.out.println(str2.length());
    }
}
