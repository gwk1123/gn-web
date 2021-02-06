package com.gn.web.common.utils;


import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.zip.*;

public class GzipUtil {

    public static byte[] compressByte(String str)  {
        return  Objects.requireNonNull(compressStream(str)).toByteArray();
    }

    public static String compressString(String str) {
        return  Objects.requireNonNull(compressStream(str)).toString();
    }

    public static  ByteArrayOutputStream compressStream(String str) {
        ByteArrayOutputStream out =null;
        GZIPOutputStream gzip=null;
        try{
            if (str == null || str.length() == 0) {
                return null;
            }
            out = new ByteArrayOutputStream();
            gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes("utf-8"));
            gzip.finish();
            return out;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(gzip!=null){
                    gzip.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public static String unCompress(byte []by) {
        ByteArrayOutputStream out=null;
        GZIPInputStream gunzip=null;
        try{
            if(by==null || by.length==0){
                return "";
            }
            out=new ByteArrayOutputStream();
            gunzip= new GZIPInputStream(new ByteArrayInputStream(by));
            byte[] buffer = new byte[1024];
            int n;
            while ((n=gunzip.read(buffer))!=-1) {
                out.write(buffer, 0, n);
            }
            out.flush();
            return new String(out.toByteArray(),"utf-8");
        }catch(Exception e){
            e.printStackTrace();
            return "";
        }finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(gunzip!=null){
                    gunzip.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public static String compressGzipFile(String targetFile) {
        FileInputStream in = null;
        GZIPOutputStream out = null;
        String outFileName = targetFile + ".gz";

        try {
            out = new GZIPOutputStream(new FileOutputStream(outFileName));
            in = new FileInputStream(targetFile);
            byte[] buf = new byte[1024];

            int len;
            while((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } catch (Exception var14) {
            var14.printStackTrace();
            System.exit(1);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }

                if (out != null) {
                    out.finish();
                }

                if (out != null) {
                    out.close();
                }
            } catch (IOException var13) {
                var13.printStackTrace();
            }
        }

        return outFileName;
    }

    public static String compressFileChecksum(String targetFile) {
        FileOutputStream dest = null;
        CheckedOutputStream checkStream = null;
        ZipOutputStream out = null;
        FileInputStream fi = null;
        BufferedInputStream origin = null;
        String zipFileName = null;

        try {
            File f = new File(targetFile);
            String fileName = f.getName();
            String folderPath = f.getParent();
            zipFileName = folderPath + "\\" + fileName.substring(0, fileName.lastIndexOf(".")) + ".zip";
            dest = new FileOutputStream(zipFileName);
            checkStream = new CheckedOutputStream(dest, new CRC32());
            out = new ZipOutputStream(new BufferedOutputStream(checkStream));
            byte[] data = new byte[2048];
            fi = new FileInputStream(folderPath + "\\" + fileName);
            origin = new BufferedInputStream(fi, 2048);
            out.putNextEntry(new ZipEntry(fileName));

            int count;
            while((count = origin.read(data, 0, 2048)) != -1) {
                out.write(data, 0, count);
            }

            return zipFileName;
        } catch (Exception var20) {
            var20.printStackTrace();
        } finally {
            try {
                origin.close();
                fi.close();
                out.flush();
                out.close();
                checkStream.close();
                dest.flush();
                dest.close();
            } catch (IOException var19) {
                var19.printStackTrace();
                return null;
            }
        }

        return null;
    }

    public static String compress(String str)  {
        if (null == str || str.length() <= 0) {
            return str;
        }
        // 创建一个新的输出流
        org.apache.commons.io.output.ByteArrayOutputStream out = new org.apache.commons.io.output.ByteArrayOutputStream();
        // 使用默认缓冲区大小创建新的输出流
        GZIPOutputStream gzip = null;
        try {
            gzip = new GZIPOutputStream(out);
            // 将字节写入此输出流
            gzip.write(str.getBytes("utf-8"));
            gzip.close();
            // 使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串
            return out.toString("ISO-8859-1");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }


    public static String unCompress(String str)  {
        if (null == str || str.length() <= 0) {
            return str;
        }
        // 创建一个新的输出流
        org.apache.commons.io.output.ByteArrayOutputStream out = new org.apache.commons.io.output.ByteArrayOutputStream();
        // 创建一个 ByteArrayInputStream，使用 buf 作为其缓冲区数组
        ByteArrayInputStream in = null;
        try {
            in = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));
            // 使用默认缓冲区大小创建新的输入流
            GZIPInputStream gzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n = 0;

            // 将未压缩数据读入字节数组
            while ((n = gzip.read(buffer)) >= 0){
                out.write(buffer, 0, n);
            }
            // 使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串
            //String string = out.toString("utf-8");
            //String unescapeJava = StringEscapeUtils.unescapeJava(string);
            return out.toString("utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) throws IOException {



/*        cid: fuyao20201123
        secret: a745-5af10370f8ad*/
        String res = "{\"tse\":\"ddd\"}";
        BASE64Encoder encoder = new BASE64Encoder();
        String data = encoder.encodeBuffer(GzipUtil.compressStream(res).toByteArray());
        System.out.println("BASE64加密：" + data);
        data  = URLEncoder.encode( data, "UTF-8" );
        System.out.println("url编码后："+data);

    }

}
