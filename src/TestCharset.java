import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Map;
import java.util.Set;

public class TestCharset {

    @Test
    public void testCharSet() throws Exception {
        //查看可用字符集
        Map<String, Charset> sortMap = Charset.availableCharsets();
        Set<String> keys = sortMap.keySet();
        for (String str : keys) {
            System.out.println(sortMap.get(str));
        }
    }

    /**
     * 用什么方式编码就用什么方式解码
     * @throws Exception
     */
    @Test
    public void testUseCharset() throws Exception {
        Charset gbk = Charset.forName("GBK");
        CharsetEncoder encoder = gbk.newEncoder();
        CharsetDecoder decoder = gbk.newDecoder();
        String str = "今天是个好日子";
        System.out.println("源字符串：" + str);
        System.out.println("---------开始编码----------");
        CharBuffer charBuffer = CharBuffer.allocate(15);
        charBuffer.put(str);
        //别忘了切换为读模式
        charBuffer.flip();
        //这不就是不把CharBuffer编码成ByteBuffer么。。。
        ByteBuffer byteBuffer = encoder.encode(charBuffer);
        for (int i = 0; i < byteBuffer.limit(); i++) {
            System.out.println(byteBuffer.get(i));//一堆数字
        }
        System.out.println("----------编码完成---------");
        System.out.println("--------------------------");
        System.out.println("----------进行解码---------");
        byteBuffer.rewind();
        CharBuffer decodeCode = decoder.decode(byteBuffer);
        System.out.println("----------解码完成---------");
        System.out.println("解析后的结果：" + decodeCode.toString());
    }
}
