package com.example.administrator.myapp.client.file;

import com.example.administrator.myapp.client.configuration.FileConfiguration;
import com.example.administrator.myapp.client.configuration.SocketConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 适用于SOCKET传输的文件基础类，支持任意格式文件，支持顺序/乱序/断点传输、存储/读取文件
 * 继承/直接使用该类
 * 2020.12.24
 */
public class BaseFile {
    /**
     * 该文件完整数据(仅供较小/需使用的文件)
     */
    private byte[] fileFullData;
    /**
     * 文件名(不包含后缀)
     */
    //private String fileName;
    /**
     * 文件扩展名(文件后缀)
     */
    //private String fileExtensionName;
    /**
     * 文件完整信息
     */
    private File fileInfo;
    /**
     * 文件大小(单位byte)
     */
    private long fileSize=-1;
    /**
     * 文件包裹数量(Socket通信时每个数据包有大小上限,该值表示该文件总共所需多少数据包)
     */
    private int filePackCount;
    /**
     * socket接收时已接收到的包裹数量(用于快速指示接收进度;当该值等于filePackCount时，表示已完成操作)
     */
    private int receivePackCount;
    /**
     * socket发送时已发送包裹数量(用于快速指示发送进度;当该值等于filePackCount时，表示已完成操作)
     */
    private int sendPackCount;
    /**
     * 文件所有包裹的状态(用于指示相应位置包裹是否完成发送或接收)
     * 大小=filePackCount
     */
    private LinkedList<FilePack> filePackList;
    /**
     * 锁定本地文件数据,防止多个包裹同时读写
     */
    private Lock fileLock;

    /////////////////////////////////
    ///////以下函数为基本函数/////////
    ////////////////////////////////

    /**
     * 用于获取本机文件
     * @param file 文件信息
     */
    BaseFile(File file){
        fileInfo=file;this.fileSize=fileInfo.length();filePackCount=(int)(fileSize / SocketConfiguration.PACK_SIZE) + 1;
        initFilePackList(); fileLock=new ReentrantLock();

    }

    /**
     * 用于获取socket传输的文件信息
     * @param fileName 文件名
     * @param fileExtensionName 文件扩展名
     * @param fileSize 文件大小
     * @param filePackCount 文件包裹数量
     */
    BaseFile(String fileName, String fileExtensionName, int fileSize, int filePackCount){
        fileInfo=new File(FileConfiguration.DOWN_PATH,fileName+fileExtensionName);this.fileSize=fileSize;this.filePackCount=filePackCount;
        initFilePackList();fileLock=new ReentrantLock();
    }

    /**
     * 初始化包裹列表状态
     */
    private void initFilePackList(){
        for(int i=0;i<filePackCount;i++){
            filePackList.add(new FilePack(i));
        }
    }
    /////////////////////////////////
    ///以下函数使用于于SOCKET接收时////
    ////////////////////////////////

    public boolean getReceiveFileStatus(){
        return receivePackCount==filePackCount;
    }

    /////////////////////////////////
    ///以下函数使用于于SOCKET发送时////
    ////////////////////////////////

    public boolean getSendFileStatus(){
        return sendPackCount==filePackCount;
    }

    /////////////////////////////////
    //以下函数作用于获取该文件各项值(通用)
    ////////////////////////////////

    /**
     * 获取文件空包裹位置,适用于断点续传时,向服务器说明尚未完成传输的包裹位置以继续上传或下载
     * @return 文件空包裹位置
     */
    public List<Integer> getNullPackIndex(){
        List<Integer> indexList=new LinkedList<>();
        for(int i=0;i<filePackCount;i++){
            if(!filePackList.get(i).getPackStatus()){
                indexList.add(i);
            }
        }
        return indexList;
    }

    /**
     * 获取文件各包裹状态
     * @return 文件包裹状态列表
     */
    public LinkedList<Boolean> getFilePackStatus() {
        LinkedList<Boolean> filePackStatus=new LinkedList<>();
        for(int i=0;i<filePackCount;i++){
            filePackStatus.add(filePackList.get(i).getPackStatus());
        }
        return filePackStatus;
    }

    /**
     * 获取该文件包裹数量
     * @return
     */
    public int getFilePackCount() {
        return filePackCount;
    }

    /**
     * 获取文件信息
     * @return
     */
    public File getFileInfo() {
        return fileInfo;
    }

    /**
     * 获取文件名(不包含扩展名)
     * @return
     */
    public String getFileName() {
        return fileInfo.getName().substring(0, fileInfo.getName().lastIndexOf('.') - 1);
    }

    /**
     * 获取扩展/后缀名
     * @return
     */
    public String getExtensionName(){
        return fileInfo.getName().substring(fileInfo.getName().lastIndexOf('.'));
    }

    /**
     * 文件包裹类，储存该包裹在文件中的位置、该包裹完成状态、该包裹数据
     */
    class FilePack{
        /**
         * 该包裹在文件中的地址
         */
        private int packIndex;
        /**
         * 该包裹是否已完成
         */
        private boolean packStatus;
        /**
         * 该包裹的数据(若不使用时应将数据直接存入本地文件中而不是放在此处)
         */
        private byte[] packData;

        FilePack(int packIndex){
            this.packIndex=packIndex;
            packStatus=false;
        }

        /**
         * 存入包裹数据到内存(若文件需要使用时)
         * @param bytes 包裹数据
         */
        public void addDataToPackData(byte[] bytes){
             packData=bytes;packStatus=true;
        }

        /**
         * 存入使用完的包裹数据到本地(按包裹顺序存入时使用)
         */
        public void packDataToLocalFile(){
            try {
                fileLock.lock();
                FileOutputStream fileOutputStream=new FileOutputStream(fileInfo,true);
                fileOutputStream.write(this.packData,(int)fileInfo.length(),this.packData.length);
                fileLock.unlock();
                packData=null;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 存入使用完的包裹数据到本地(包裹顺序乱序存入时使用，需先计算该包裹存入位置)
         */
        public void packDataToLocalFile(int fileIndex){
            try {
                fileLock.lock();
                FileOutputStream fileOutputStream=new FileOutputStream(fileInfo,true);
                fileOutputStream.write(this.packData,fileIndex,this.packData.length);
                fileLock.unlock();
                packData=null;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        /**
         * 存入包裹数据到本地(按包裹顺序存入时使用)
         * @param bytes 包裹数据
         */
        public void addDataToLocalFile(byte[] bytes){
            try {
                fileLock.lock();
                FileOutputStream fileOutputStream=new FileOutputStream(fileInfo,true);
                fileOutputStream.write(bytes,(int)fileInfo.length(),bytes.length);
                fileLock.unlock();
                packStatus=true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 存入包裹数据到本地（包裹顺序乱序存入时使用，需先计算该包裹存入位置)
         * @param bytes 包裹数据
         * @param fileIndex 存入本地起始位置
         */
        public void addDataToLocalFile(byte[] bytes,int fileIndex){
            try {
                fileLock.lock();
                FileOutputStream fileOutputStream=new FileOutputStream(fileInfo,true);
                fileOutputStream.write(bytes,fileIndex,bytes.length);
                fileLock.unlock();
                packStatus=true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public boolean getPackStatus(){
            return packStatus;
        }

        public byte[] getPackData() {
            return packData;
        }

        public int getPackIndex() {
            return packIndex;
        }

    }
}
