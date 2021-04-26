package org.jeecg.modules.party_building.util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.*;
import java.net.MalformedURLException;
import java.util.UUID;
@Component
@Slf4j
public class FTPUtil {

       //ftp服务器地址
      /*  private static String hostname ="152.136.135.39" ;
        //ftp服务器端口号默认为21
        private static Integer port = 21 ;
        //ftp登录账号
        private static String username = "vaftpd";
        //ftp登录密码
        private static String password = "vsftpd2019";*/
    //ftp服务器地址
     private static String hostname;
    @Value("${ftp.hostname}")
    public  void setHostname(String hostname) {
        this.hostname= hostname;
    }
    //ftp服务器端口号默认为21
    private static Integer port ;
    @Value("${ftp.port}")
    public  void setPort(Integer port) {
        this.port= port;
    }
    //ftp登录账号
    private static String username;
    @Value("${ftp.username}")
    public  void setUsername(String username) {
        this.username= username;
    }
    //ftp登录密码
    private static String password;
    @Value("${ftp.password}")
    public  void setPassword(String password) {
        this.password= password;
    }
    /**
     * 初始化ftp服务器
     */
    public static FTPClient initFtpClient(){
        FTPClient ftpClient = null;
        ftpClient = new FTPClient();
        ftpClient.setControlEncoding("utf-8");
        try {
            log.info("connecting...ftp服务器:"+hostname+":"+port);
            ftpClient.connect(hostname, port); //连接ftp服务器

            ftpClient.login(username,password ); //登录ftp服务器
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            int replyCode = ftpClient.getReplyCode(); //是否成功登录服务器
            if(!FTPReply.isPositiveCompletion(replyCode)){
                log.info("connect failed...ftp服务器:"+hostname+":"+port);
                return null;
            }
            log.info("connect successfu...ftp服务器:"+hostname+":"+port);
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return ftpClient;
    }



    /**
     * 上传文件
     * @param pathname ftp服务保存地址
     * @param fileName 上传到ftp的文件名
     *  @param originfilename 待上传文件的名称（绝对地址） *
     * @return
     */
    public static boolean uploadFile( String pathname, String fileName,String originfilename){
        FTPClient ftpClient = initFtpClient();
        InputStream inputStream = null;
        try{
            log.info("开始上传文件");
            //把文件转化为流
            inputStream = new FileInputStream(new File(originfilename));
            //初始化ftp
            //设置编码
            ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
            //文件需要保存的路径
            CreateDirecroty(ftpClient,pathname);
            //
            ftpClient.makeDirectory(pathname);
            //
            ftpClient.changeWorkingDirectory(pathname);
            //
            ftpClient.storeFile(fileName, inputStream);
            inputStream.close();
            ftpClient.logout();
            log.info("上传文件成功");
        }catch (Exception e) {
            log.info("上传文件失败");
            e.printStackTrace();
        }finally{
            if(ftpClient.isConnected()){
                try{
                    ftpClient.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
            if(null != inputStream){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
    /**
     * 上传文件
     * @param pathname ftp服务保存地址
     * @param fileName 上传到ftp的文件名
     * @param inputStream 输入文件流
     * @return
     */
    public static String uploadFile( String pathname, String fileName,InputStream inputStream){
        String newName = "";
        FTPClient ftpClient = initFtpClient();

        try{
            log.info("开始上传文件");
            ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
            CreateDirecroty(ftpClient,pathname);
            //ftpClient.makeDirectory(pathname);
            ftpClient.changeWorkingDirectory(pathname);
//             FTPFile[] ftpFiles = ftpClient.listFiles();
            //给文件重命名
            newName = UUID.randomUUID().toString();
            newName = newName + fileName.substring(fileName.lastIndexOf("."));
            boolean t =ftpClient.storeFile(newName, inputStream);
            log.info("上传文件结果:"+t);

            inputStream.close();
            ftpClient.logout();

        }catch (Exception e) {
            log.error("FTP上传异常: "+e.getMessage());
            return null;
        }finally{
            if(ftpClient.isConnected()){
                try{
                    ftpClient.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
            if(null != inputStream){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return pathname+"/"+newName;
    }


    public static void download(String ftpFileName, OutputStream  outputStream) throws IOException
    {
        try {
            FTPClient ftpClient = initFtpClient();
            // Get file info.

            String path=ftpFileName.substring(0,ftpFileName.lastIndexOf("/"));
            String filename=ftpFileName.substring(ftpFileName.lastIndexOf("/")+1,ftpFileName.length());
            boolean flag1 =ftpClient.changeWorkingDirectory(path);
            FTPFile ftpFile=ftpClient.mdtmFile(filename);
            if (ftpFile == null) {
                throw new    FileNotFoundException("File '" + filename + "' was not found on FTP server.");
            }
            long size = ftpFile.getSize();
            if (size > Integer.MAX_VALUE) {
                throw new IOException("File '" + ftpFileName + "' is too large.");
            }
            // Download file.
            if (!ftpClient.retrieveFile(filename, outputStream)) {
                throw new IOException("Error loading file '" + ftpFileName + "' from FTP server. Check FTP permissions and path.");
            }
            outputStream.flush();


        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException ex) {
                }
            }
        }
    }

    //创建多层目录文件，如果有ftp服务器已存在该文件，则不创建，如果无，则创建
    public static boolean CreateDirecroty(FTPClient ftpClient,String remote) throws IOException {
        boolean success = true;
        String directory = remote + "/";
        // 如果远程目录不存在，则递归创建远程服务器目录
        if (!directory.equalsIgnoreCase("/") && !ftpClient.changeWorkingDirectory(new String(directory))) {
            int start = 0;
            int end = 0;
            if (directory.startsWith("/")) {
                start = 1;
            } else {
                start = 0;
            }
            end = directory.indexOf("/", start);
            String path = "";
            String paths = "";
            while (true) {
                String subDirectory = new String(remote.substring(start, end).getBytes("GBK"), "iso-8859-1");
                path = path + "/" + subDirectory;
                if (!existFile(ftpClient,path)) {
                    if (makeDirectory(ftpClient,subDirectory)) {
                        ftpClient.changeWorkingDirectory(subDirectory);
                    } else {
                        log.info("创建目录[" + subDirectory + "]失败");
                        ftpClient.changeWorkingDirectory(subDirectory);
                    }
                } else {
                    ftpClient.changeWorkingDirectory(subDirectory);
                }

                paths = paths + "/" + subDirectory;
                start = end + 1;
                end = directory.indexOf("/", start);
                // 检查所有目录是否创建完毕
                if (end <= start) {
                    break;
                }
            }
        }
        return success;
    }

    //判断ftp服务器文件是否存在
    public static boolean existFile(FTPClient ftpClient,String path) throws IOException {
        boolean flag = false;
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        if (ftpFileArr.length > 0) {
            flag = true;
        }
        return flag;
    }
    //创建目录
    public static boolean makeDirectory(FTPClient ftpClient,String dir) {
        boolean flag = true;
        try {
            flag = ftpClient.makeDirectory(dir);
            if (flag) {
                log.info("创建文件夹" + dir + " 成功！");

            } else {
                log.info("创建文件夹" + dir + " 失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /** * 下载文件 *
     * @param pathname FTP服务器文件目录 *
     * @param filename 文件名称 *
     * @param localpath 下载后的文件路径 *
     * @return */
    public static boolean downloadFile(String pathname, String filename, String localpath){
        boolean flag = false;
        boolean jg=false;
        OutputStream os=null;
        FTPClient ftpClient = initFtpClient();
        try {
            log.info("开始下载文件");
            //切换FTP目录
            ftpClient.changeWorkingDirectory(pathname);
            File localFile = new File(localpath, filename);
            os = new FileOutputStream(localFile);
            jg=ftpClient.retrieveFile(new String(filename.getBytes("utf-8"),"ISO-8859-1"), os);
            log.info("缓存到本地结果 => " + jg);

            ftpClient.logout();
            flag = true;
        } catch (Exception e) {
            log.info("下载文件失败");
            e.printStackTrace();
        } finally{
            if(ftpClient.isConnected()){
                try{
                    ftpClient.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
            if(null != os){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        int i=0;
        while(!jg){
            ftpClient = initFtpClient();
            File localFile = null;
            try {
                log.info("开始下载文件");
                //切换FTP目录
                ftpClient.changeWorkingDirectory(pathname);
                localFile = new File(localpath + "/" +filename);
                os = new FileOutputStream(localFile);
                jg=ftpClient.retrieveFile(new String(filename.getBytes("utf-8"),"ISO-8859-1"), os);
                log.info("缓存到本地结果 => " + jg);
                ftpClient.logout();
                flag = true;
            } catch (Exception e) {
                log.info("下载文件失败");
                e.printStackTrace();
            } finally{
                if(ftpClient.isConnected()){
                    try{
                        ftpClient.disconnect();
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
                if(null != os){
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }  log.info("缓存到本地结果 => " + jg);
            i++;
            if(i>3){
                if (jg == false) {
                    log.error("缓存本地失败，删除临时文件: " + localFile.getAbsolutePath());
                    FileUtils.deleteQuietly(localFile);
                }
                break;
            }
        }
        log.info("下载文件成功");
        return flag;
    }

    /** * 删除文件 *
     * @param pathname FTP服务器保存目录 *
     * @param filename 要删除的文件名称 *
     * @return */
    public static boolean deleteFile(String pathname, String filename){
        boolean flag = false;
        FTPClient ftpClient = initFtpClient();
        try {
            log.info("开始删除文件");
            //切换FTP目录
            ftpClient.changeWorkingDirectory(pathname);
            ftpClient.dele(filename);
            ftpClient.logout();
            flag = true;
            log.info("删除文件成功");
        } catch (Exception e) {
            log.info("删除文件失败");
            e.printStackTrace();
        } finally {
            if(ftpClient.isConnected()){
                try{
                    ftpClient.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    public static void main(String[] args) {
        /*    FTPUtil ftp =new FTPUtil();
           ftp.uploadFile("ftpFile/data", "123.docx", "E://123.docx");
           ftp.downloadFile("ftpFile/data", "123.docx", "F://");
            ftp.deleteFile("ftpFile/data", "123.docx");
            log.info("ok");
        FTPUtil f = new FTPUtil();
        f.initFtpClient();*/


    }
}
