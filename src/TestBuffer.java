import java.nio.ByteBuffer;

public class TestBuffer {

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        System.out.println("初始化--->byteBuffer limit:" + byteBuffer.limit());
        System.out.println("初始化--->byteBuffer capacity:" + byteBuffer.capacity());
        System.out.println("初始化--->byteBuffer position:" + byteBuffer.position());
        System.out.println("初始化--->byteBuffer mark:" + byteBuffer.mark());
        System.out.println();
        String str = "Mycclee";
        byteBuffer.put(str.getBytes());
        System.out.println("添加后--->byteBuffer limit:" + byteBuffer.limit());
        System.out.println("添加后--->byteBuffer capacity:" + byteBuffer.capacity());
        System.out.println("添加后--->byteBuffer position:" + byteBuffer.position());
        System.out.println("添加后--->byteBuffer mark:" + byteBuffer.mark());
        System.out.println();
        byteBuffer.flip();
        System.out.println("调用flip之后--->byteBuffer limit:" + byteBuffer.limit());
        System.out.println("调用flip之后--->byteBuffer capacity:" + byteBuffer.capacity());
        System.out.println("调用flip之后--->byteBuffer position:" + byteBuffer.position());
        System.out.println("调用flip之后--->byteBuffer mark:" + byteBuffer.mark());
        System.out.println();
        System.out.println("从缓冲区读取数据：");
        byte[] bytes = new byte[byteBuffer.limit()];
        byteBuffer.get(bytes);
        System.out.println("数据为:" + new String(bytes, 0, bytes.length));
        System.out.println();
        System.out.println("取出数据后--->byteBuffer limit:" + byteBuffer.limit());
        System.out.println("取出数据后--->byteBuffer capacity:" + byteBuffer.capacity());
        System.out.println("取出数据后--->byteBuffer position:" + byteBuffer.position());
        System.out.println("取出数据后--->byteBuffer mark:" + byteBuffer.mark());
        System.out.println();
        byteBuffer.clear();
        System.out.println("clear之后--->byteBuffer limit:" + byteBuffer.limit());
        System.out.println("clear之后--->byteBuffer capacity:" + byteBuffer.capacity());
        System.out.println("clear之后--->byteBuffer position:" + byteBuffer.position());
        System.out.println("clear之后--->byteBuffer mark:" + byteBuffer.mark());
    }
}
