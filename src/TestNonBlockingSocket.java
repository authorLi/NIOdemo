import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

public class TestNonBlockingSocket {

    @Test
    public void client() throws Exception{
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",6666));
        //将其设置为非阻塞IO
        socketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_READ);
        ByteBuffer byteBuffer = ByteBuffer.allocate(50);
        String data = "我是数据啦";
        byteBuffer.put(data.getBytes());
        byteBuffer.flip();
        socketChannel.write(byteBuffer);

        while (selector.select() > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isReadable()) {
                    SocketChannel responseChannel = (SocketChannel) selectionKey.channel();
                    ByteBuffer responseBuffer = ByteBuffer.allocate(50);
                    int readBytes = responseChannel.read(responseBuffer);
                    if (readBytes > 0) {
                        responseBuffer.flip();
                        System.out.println(new String(responseBuffer.array(), 0, readBytes));
                    }
                }
                iterator.remove();
            }
        }

        socketChannel.close();
    }

    @Test
    public void server() throws Exception{

        SocketChannel socketChannel = SocketChannel.open();
        FileChannel outChannel = null;
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(6666));
        //获取选择器
        Selector selector = Selector.open();
        //将通道注册到选择器上，并接受"监听通道"事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //轮询选择器上已经就绪的事件，只要select()方法的返回值大于零则表明以就绪
        while (selector.select() > 0) {
            //获取当前选择器所有注册的"选择键"(已就绪监听事件)
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            //获取所有已就绪的事
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                //如果事件已就绪
                if (selectionKey.isAcceptable()) {
                    //获取客户端连接
                    socketChannel = serverSocketChannel.accept();
                    //设置为非阻塞
                    socketChannel.configureBlocking(false);
                    //注册到选择器上
                    socketChannel.register(selector, SelectionKey.OP_READ);

                } else if (selectionKey.isReadable()) {//读事件就绪
                    //获取当前选择器就绪状态的通道
                    socketChannel = (SocketChannel) selectionKey.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(50);
                    outChannel = FileChannel.open(Paths.get("/Users/lizehao/IdeaProject/NIOdemo/src/h.txt"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
                    while (socketChannel.read(byteBuffer) != -1) {
                        byteBuffer.flip();
                        outChannel.write(byteBuffer);
                        byteBuffer.clear();
                    }
                    String response = "我收到了，老哥";
                    ByteBuffer responseBuffer = ByteBuffer.allocate(50);
                    byteBuffer.put(response.getBytes());
                    byteBuffer.flip();
                    socketChannel.write(responseBuffer);
                }
                iterator.remove();
            }
        }

        selector.close();
        if (outChannel != null) {
            outChannel.close();
        }
        socketChannel.close();
        serverSocketChannel.close();
    }

    @Test
    public void client2() throws IOException {
    // 获取通道
    SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8888));

    // 分配1024字节大小的缓冲区
    ByteBuffer buffer = ByteBuffer.allocate(1024);

    Scanner scan = new Scanner(System.in);

    // 输入
        while (scan.hasNext()) {

        String str = scan.next();

        buffer.put((new Date().toString() + "--" + str).getBytes());

        // 切换回读模式，实质是令limit=position；position=0。
        buffer.flip();
        // 写入通道
        socketChannel.write(buffer);
        // 清空缓冲区
        buffer.clear();
    }
}

    @Test
    public void server2() throws IOException {
        // 获取服务器端通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        // 切换该通道为非阻塞模式
        serverSocketChannel.configureBlocking(false);

        // 绑定端口号
        serverSocketChannel.bind(new InetSocketAddress(8888));

        // 获取选择器
        Selector selector = Selector.open();

        // 将通道注册到选择器上，并指定监听的事件类型为“接收”
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 轮询式地获取选择器上已经准备就绪的事件
        //selector.select()方法是阻塞的
        while (selector.select() > 0) {

            // 获取当前选择器中所有已经注册的“选择键”，即已就绪的监听事件
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();

            while (it.hasNext()) {
                // 获取已经准备就绪的事件
                SelectionKey selectionKey = it.next();

                // 判断事件的类型
                // 接收类型
                if (selectionKey.isAcceptable()) {
                    // 接收客户端通道
                    SocketChannel socketChannel = serverSocketChannel.accept();

                    socketChannel.configureBlocking(false);

                    socketChannel.register(selector, selectionKey.OP_READ);
                } else if (selectionKey.isReadable()) {
                    // 获取当前选择器上“读就绪”状态的通道
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

                    ByteBuffer buffer = ByteBuffer.allocate(1024);

                    int len;

                    while ((len = socketChannel.read(buffer)) > 0) {
                        buffer.flip();
                        System.out.println(new String(buffer.array(), 0, len));
                        buffer.clear();
                    }
                }
                // 取消选择键 SelectionKey
                it.remove();
            }

        }
    }
}
