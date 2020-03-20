import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class BigFile {

    public static void main(String[] args) throws IOException {
        //BigFile.create(new File("F:\\aircat.txt"), 12L*1024*1024*1024);
        BigFile.mappedBuffer();
    }

    public static void create(File file, long length) throws IOException {
        long start = System.currentTimeMillis();
        RandomAccessFile r = null;
        try {
            r = new RandomAccessFile(file, "rw");
            r.setLength(length);
            while(r.getFilePointer() < r.length()) {
                r.writeBytes("this is a test");
            }
        } finally{
            if (r != null) {
                try {
                    r.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println(end-start);

    }

    public static void mappedBuffer() throws IOException{
        long start = System.currentTimeMillis();

        FileChannel read = new FileInputStream("F://aircat.txt").getChannel();
        //FileChannel writer = new RandomAccessFile("F://aircat.tmp","rw").getChannel();
        long i = 0;
        long size = read.size()/1000;
        ByteBuffer bb,cc = null;
        while(i<read.size()&&(read.size()-i)>size){
            bb = read.map(FileChannel.MapMode.READ_ONLY, i, size);
            //cc = writer.map(FileChannel.MapMode.READ_WRITE, i, size);
            //cc.put(bb);
            i+=size;
            System.out.println(byteBufferToString(bb));
            bb.clear();
            //cc.clear();
        }
        bb = read.map(FileChannel.MapMode.READ_ONLY, i, read.size()-i);
        //cc.put(bb);
        bb.clear();
        //cc.clear();
        read.close();
        //writer.close();

        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }

    public static String byteBufferToString(ByteBuffer buffer) {
        CharBuffer charBuffer = null;
        try {
            Charset charset = Charset.forName("UTF-8");
            CharsetDecoder decoder = charset.newDecoder();
            charBuffer = decoder.decode(buffer);
            buffer.flip();
            return charBuffer.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
