import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

public class TestPipe {

    @Test
    public void test() throws Exception{
        Pipe pipe = Pipe.open();
        ByteBuffer byteBuffer = ByteBuffer.allocate(50);

        //写入数据
        Pipe.SinkChannel sinkChannel = pipe.sink();
        String request = "通过单向管道发送数据";
        byteBuffer.put(request.getBytes());
        byteBuffer.flip();
        sinkChannel.write(byteBuffer);

        //读取数据
        Pipe.SourceChannel sourceChannel = pipe.source();
        byteBuffer.flip();
        int len = sourceChannel.read(byteBuffer);
        System.out.println(new String(byteBuffer.array(), 0, len));

        //关闭channel
        sourceChannel.close();
        sinkChannel.close();
    }
}
