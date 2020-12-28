package com.example.administrator.myapp.client.tool;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.example.administrator.myapp.client.configuration.SocketConfiguration;

import java.io.UnsupportedEncodingException;

public class ConvertTypeTool {
    /**
     * byte[4]数组转int类型的对象
     * @param bytes 需转换的byte[4]数组
     * @return int 转换后的int
     */
    static public int ByteToInt(byte[] bytes) {
        int int1=bytes[0]&0xff;
        int int2=(bytes[1]&0xff)<<8;
        int int3=(bytes[2]&0xff)<<16;
        int int4=(bytes[3]&0xff)<<24;

        return int1|int2|int3|int4;
    }

    /**
     * int类型转byte[4]数组
     * @param num 需转换的int数组
     * @return byte[4] 转换后的字节数组
     */
    static public byte[] IntToBytes(int num){
        byte[] arry = new byte[4];

        if (arry == null) return null;
        if (arry.length < 4) return null;

        arry[0] = (byte)(num & 0xFF);
        arry[1] = (byte)((num & 0xFF00) >> 8);
        arry[2] = (byte)((num & 0xFF0000) >> 16);
        arry[3] = (byte)((num >> 24) & 0xFF);

        return arry;
    }

    /**
     * 工具函数，用于合并数组
     * @param a 第一个合并数组
     * @param b 第二个合并数组
     * @return 合并完成的数组
     */
    static public byte[] CopyByte(byte[] a, byte[] b)
    {
        byte[] c;
        //使用Buffer.BlockCopy合成数组优化性能
        c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    /**
     * 工具函数，用于合并数组(注意:本函数专用于封包，将默认开头结尾添加标识符)
     * @param a 第一个合并数组
     * @param b 第二个合并数组
     * @return 合并完成的数组
     */
    static public byte[] CopyByteOfSocket(byte[] a, byte[] b)
    {
        byte[] c = new byte[a.length + b.length + 2];


        System.arraycopy(a, 0, c, 1, a.length);
        System.arraycopy(b, 0, c, a.length + 1, b.length);
        c[0] = SocketConfiguration.DATA_START_TAG;
        c[c.length - 1] = SocketConfiguration.DATA_END_TAG;

        return c;
    }

    /**
     * 用于截取byte数组
     * @param by 需截取的数组
     * @param start 截取数组起始位置
     * @param count 需截取的长度
     * @return 截取完毕的数组
     */
    static public byte[] CutByte(byte[] by, int start, int count) {
        byte[] cut = new byte[count];
        System.arraycopy(by, start, cut, 0, count);
        return cut;
    }

    /**
     * bytes数组通过UTF8标准转String
     * @param bytes
     * @return
     */
    static public String BytesToStringByUTF8(byte[] bytes){
        try {
            return new String(bytes,0,bytes.length,"utf-8");
        } catch (UnsupportedEncodingException e) {
            Log.e("bytes转String失败",e.getLocalizedMessage());
            return null;
        }
    }

    /**
     * byte[]转bitmap
     * @param bytes
     * @return
     */
    static public Bitmap BytesToBitmap(byte[] bytes){
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * base64转byte[]
     * @param base
     * @return
     */
    static public byte[] Base64ToByte(String base){
        return Base64.decode(base,Base64.DEFAULT);
    }

    /**
     * byte[]转base64
     * @param bytes
     * @return
     */
    static public String ByteToBase64(byte[] bytes){
        return Base64.encodeToString(bytes,Base64.DEFAULT);
    }
}
