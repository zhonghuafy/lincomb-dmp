package com.lincomb.dmp.util;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class FileUtilsTest {

    private static String basePath = "F:\\";

    public void setUp() {
        basePath = System.getProperty("user.dir") + "\\file\\";
    }

    public void tearDown() throws Exception {
    }

    /**
     * 拷贝文件
     * @throws IOException
     */
    public void testCopy() throws IOException {
        File srcFile = new File(basePath + "a.txt");
        File destFile = new File(basePath + "b.txt");
        FileUtils.copyFile(srcFile, destFile);
    }

    /**
     * 删除文件
     * @throws IOException
     */
    public void testDelete() throws IOException{
        File delFile = new File(basePath + "b.txt");
        FileUtils.forceDelete(delFile);
        //FileUtils.forceMkdir(delFile);
    }

    /**
     * 比较文件内容
     * @throws IOException
     */
    public void testCompareFile() throws IOException{
        File srcFile = new File(basePath + "a.txt");
        File destFile = new File(basePath + "b.txt");
        boolean result = FileUtils.contentEquals(srcFile, destFile);
        System.out.println(result);
    }

    /**
     * 移动文件
     * @throws IOException
     */
    public void testMoveFile() throws IOException{
        File srcFile = new File(basePath + "b.txt");
        File destDir = new File(basePath + "move");
        FileUtils.moveToDirectory(srcFile, destDir, true);
    }

    /**
     * 读取文件内容
     * @throws IOException
     */
    public static void  testRead() throws IOException{
        long date1 = new Date().getTime();
        System.out.println("==========="+new Date().getTime()+"===========");
        File srcFile = new File(basePath + "a.txt");
        List<String> contents = FileUtils.readLines(srcFile,"UTF-8");
        System.out.println("********"+contents+"**********");
//        for (String string : contents) {
//            System.out.println(string);
//        }
        System.out.println("==========="+(new Date().getTime()-date1)+"ms===========");
    }

    /**
     * 写入文件内容
     * @throws IOException
     */
    public static void testWrite() throws IOException{
        File srcFile = new File(basePath + "a.txt");
        FileUtils.writeStringToFile(srcFile, "INSERT INTO `sys_login_log` VALUES ('367', '登录日志', '1', '2017-12-29 090149', '成功', null, '00000001');", true);
    }



    public static void main(String[] args)throws Exception {
        String path = "F:\\mc-869-big-CompressionInfo.db";
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, "utf-8");
        BufferedReader br = new BufferedReader(isr);
        StringBuffer line = new StringBuffer();
        String str = null;
        while ((str = br.readLine()) != null) {
            line.append(str);
            line.append("\n");
        }
        System.out.println(line);
        br.close();
    }
}