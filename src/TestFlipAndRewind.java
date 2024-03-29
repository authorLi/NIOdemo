import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;

public class TestFlipAndRewind {

    @Test
    public void testFlip() throws Exception{
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        WritableByteChannel outChannel = Channels.newChannel(System.out);
        String str1 = "今天是个好日子啊";
        byteBuffer.put(str1.getBytes());
        byteBuffer.position(0);
        String str2 = "每天都";
        byteBuffer.put(str2.getBytes());
        byteBuffer.flip();
        outChannel.write(byteBuffer);//每天都
        outChannel.close();
    }

    @Test
    public void testRewinds() throws Exception{
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        WritableByteChannel outChannel = Channels.newChannel(System.out);
        String str1 = "今天真是个好日子啊";
        byteBuffer.put(str1.getBytes());
        byteBuffer.position(0);
        String str2 = "每天都";
        byteBuffer.put(str2.getBytes());
        byteBuffer.rewind();
        outChannel.write(byteBuffer);//每天都是个好日子啊                                                                         
        outChannel.close();
    }
}
