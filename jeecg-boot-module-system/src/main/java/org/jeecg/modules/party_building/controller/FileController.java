package org.jeecg.modules.party_building.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.party_building.service.PhotographsService;
import org.jeecg.modules.party_building.util.FTPUtil;
import org.jeecg.modules.oss.entity.OSSFile;
import org.jeecg.modules.oss.service.IOSSFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.concurrent.Callable;

@Controller
@RequestMapping("/file")
@Slf4j
@Api(tags = "文件上传FTP")
public class FileController {
    @Autowired
    IOSSFileService iossFileService;
    @Autowired
    private PhotographsService photographsService;
    @Value(value = "${jeecg.path.upload}")
    private String uploadpath;
    @Value(value = "${jeecg.path.upload1}")
    private String uploadpath1;
    @ApiOperation("文件上传")
    @ResponseBody
    @RequestMapping("/uploadToFtp")
    public Result<?> photoUploadByFtp(MultipartFile file, HttpServletRequest request) throws Exception {
        if (file != null) {// 判断上传的文件是否为空
            if (file.getSize() > 500 * 1024 * 1024) {
                return Result.error("附件最大支持500M");
            }
            String fileName = file.getOriginalFilename();// 文件原名称
            InputStream inputStream = file.getInputStream();
            FTPUtil ftpUtil = new FTPUtil();
            Date dt = new Date();
            String year = String.format("%tY", dt);
            String mon = String.format("%tm", dt);
            String day = String.format("%td", dt);
            String filePath = ftpUtil.uploadFile("hisoft/"+ year + mon + day, fileName, inputStream);
            if(Objects.isNull(filePath)){
                return Result.error("附件上传FTP失败");
            }
//            String filePath = ftpUtil.uploadFile("/opt/hisoft/", fileName, inputStream);
            /*map.put("filePath", "/opt/hisoft" + filePath);
            map.put("fileName", fileName);*/
            OSSFile ossFile = new OSSFile();
            ossFile.setFileName(fileName);
            ossFile.setUrl(filePath);
            iossFileService.save(ossFile);
            Result<OSSFile> result=new Result<>();
            result.setResult(ossFile);
            return result;
        }
        return Result.error("没有文件");
    }

/*
    @ApiOperation("文件上传")
    @RequestMapping("/fileUploadByFtp")
    public Result<?> upload(HttpServletRequest request, MultipartFile file) throws Exception {
        Result<?> result = new Result<>();
        if (file != null) {// 判断上传的文件是否为空
            String fileName = file.getOriginalFilename();// 文件原名称
            InputStream inputStream = file.getInputStream();
            FTPUtil ftpUtil = new FTPUtil();
            Date dt = new Date();
            String year = String.format("%tY", dt);
            String mon = String.format("%tm", dt);
            String day = String.format("%td", dt);
            String savePath = ftpUtil.uploadFile("/opt/hisoft/" + year + "/" + year + mon + "/" + year + mon + day, fileName, inputStream);
            if (oConvertUtils.isNotEmpty(savePath)) {
                result.setMessage("/"+year+ "/" + year + mon + "/" + year + mon + day+"/" + savePath);
                *//*result.setMessage(fileName);*//*
                result.setSuccess(true);
            } else {
                result.setMessage("上传失败！");
                result.setSuccess(false);
            }
        }
        return result;
    }*/

    /**
     * 下载文件缓存到本地返回路径
     */
    @ApiOperation("文件下载")
    @GetMapping(value = "/downloadThisFile",produces = MediaType.ALL_VALUE, consumes = MediaType.ALL_VALUE)
    public Result<?> downloadThisFile(String id, HttpServletRequest request, HttpServletResponse response) {
        OutputStream outputStream = null;
        String url = "";
        String url2 = "";

        OSSFile o = iossFileService.getById(id);
        try {

            if (o != null) {
                String ftpFileName = o.getUrl();
                String path = ftpFileName.substring(0, ftpFileName.lastIndexOf("/"));
                String filename = ftpFileName.substring(ftpFileName.lastIndexOf("/") + 1, ftpFileName.length());
                url = uploadpath1 + File.separator + filename;
                url2 = uploadpath + File.separator + filename;
                url2.replace("\\", "/");
                File file = new File(url);
                if (!file.exists()) {
                    File filepath = new File(uploadpath1);
                    //如果文件夹不存在则创建
                    if (!filepath.exists() && !filepath.isDirectory()) {
                        filepath.mkdirs();
                    }
                    FTPUtil.downloadFile(path, filename, uploadpath1);
                }
                sendVideo(request, response, file, filename);
            }
        } catch (Exception e) {
            log.error("下载失败: " + e.getMessage());
            return Result.error(e.getMessage());
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {

                }
            }
        }
        return Result.ok();
    }

    @ApiOperation("文件下载")
    @GetMapping(value = "/downloadFile")
    public Result<?> downloadFile(String id, HttpServletRequest request, HttpServletResponse response) {
        OutputStream outputStream = null;
        String url="";
        String url2="";

        OSSFile o = iossFileService.getById(id);
        try {

            if (o!=null) {
                String ftpFileName=o.getUrl();
                String path=ftpFileName.substring(0,ftpFileName.lastIndexOf("/"));
                String filename=ftpFileName.substring(ftpFileName.lastIndexOf("/")+1,ftpFileName.length());
                url=uploadpath1+File.separator+filename;
                url2=uploadpath+File.separator+filename;
                url2.replace("\\","/");
                File file=new File(url);
                if(!file.exists()){
                    File filepath =new File(uploadpath1);
                    //如果文件夹不存在则创建
                    if  (!filepath .exists()  && !filepath .isDirectory()){
                        filepath.mkdirs();
                    }
                    FTPUtil.downloadFile(path,filename,uploadpath1);
                }
//                sendVideo(request,response,file,filename);
                downloadFile(response,file.getAbsolutePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Result.ok("下载失败");
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {

                }
            }
        }

        return Result.ok(url2);
    }

    private void downloadFile(HttpServletResponse response, String filepath) {
//        log.debug("download "+filepath);
//        OutputStream outputStream = null;
//        String fileName = FilenameUtils.getName(filepath);
//        FileInputStream fileInputStream = null;
//        try {
//            File file = new File(filepath);
//            fileInputStream = new FileInputStream(file);
////            byte[] data = IOUtils.toByteArray(fileInputStream);
//            response.setContentType("application/force-download");// 设置强制下载不打开
//            response.addHeader("content-disposition", "attachment;fileName=" + new String(file.getName().replaceAll(",","").getBytes("UTF-8"),"iso-8859-1"));
//            outputStream = new BufferedOutputStream(response.getOutputStream());
////            outputStream.write(data);
////            outputStream.flush();
//            byte[] buffer = new byte[1024 * 1024 * 4];
//            int i = -1;
//            while ((i = fileInputStream.read(buffer)) != -1) {
//                outputStream.write(buffer, 0, i);
//            }
//            outputStream.flush();
//        }catch(Exception e){
//            log.error("下载文件异常",e);
//        }finally{
//            IOUtils.closeQuietly(outputStream);
//            IOUtils.closeQuietly(fileInputStream);
//        }
        log.debug("download " + filepath);
        File file = new File(filepath);
        String fileName = FilenameUtils.getName(filepath);
        try (FileInputStream fileInputStream = new FileInputStream(file);
             OutputStream outputStream = new BufferedOutputStream(response.getOutputStream())
        ) {
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("content-disposition", "attachment;fileName=" + new String(file.getName().replaceAll(",", "").getBytes("UTF-8"), "iso-8859-1"));
            byte[] buffer = new byte[1024 * 1024 * 4];
            int i = -1;
            while ((i = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, i);
            }
            outputStream.flush();
        } catch (Exception e) {
            log.error("下载文件异常", e);
        }

    }

   /**
     * 下载视频流，兼容IOS
     * @param request
     * @param response
     * @param file
     * @param fileName
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void sendVideo(HttpServletRequest request, HttpServletResponse response, File file, String fileName) throws FileNotFoundException, IOException {
        RandomAccessFile randomFile = new RandomAccessFile(file, "r");//只读模式
        long contentLength = randomFile.length();
        String range = request.getHeader("Range");
        int start = 0, end = 0;
        if(range != null && range.startsWith("bytes=")){
            String[] values = range.split("=")[1].split("-");
            start = Integer.parseInt(values[0]);
            if(values.length > 1){
                end = Integer.parseInt(values[1]);
            }
        }
        int requestSize = 0;
        if(end != 0 && end > start){
            requestSize = end - start + 1;
        } else {
            requestSize = Integer.MAX_VALUE;
        }

//        response.setContentType("application/octet-stream;charset=GBK");
        String extension = FilenameUtils.getExtension(fileName);
        if("avi".equalsIgnoreCase(extension)){
            response.setContentType("video/x-msvideo");
        }else if("3gp".equalsIgnoreCase(extension)){
            response.setContentType("video/3gpp");
        }else if("flv".equalsIgnoreCase(extension)){
            response.setContentType("flv-application/octet-stream");
        }else{
            // 默认MP4
            response.setContentType("video/mp4");
        }
        response.setHeader("Accept-Ranges", "bytes");
        response.setHeader("ETag", fileName);
        response.setHeader("Last-Modified", new Date().toString());
        //第一次请求只返回content length来让客户端请求多次实际数据
        if(range == null){
            response.setHeader("Content-length", contentLength + "");
        }else{
            //以后的多次以断点续传的方式来返回视频数据
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);//206
            long requestStart = 0, requestEnd = 0;
            String[] ranges = range.split("=");
            if(ranges.length > 1){
                String[] rangeDatas = ranges[1].split("-");
                requestStart = Integer.parseInt(rangeDatas[0]);
                if(rangeDatas.length > 1){
                    requestEnd = Integer.parseInt(rangeDatas[1]);
                }
            }
            long length = 0;
            if(requestEnd > 0){
                length = requestEnd - requestStart + 1;
                response.setHeader("Content-length", "" + length);
                response.setHeader("Content-Range", "bytes " + requestStart + "-" + requestEnd + "/" + contentLength);
            }else{
                length = contentLength - requestStart;
                response.setHeader("Content-length", "" + length);
                response.setHeader("Content-Range", "bytes "+ requestStart + "-" + (contentLength - 1) + "/" + contentLength);
            }
        }
        ServletOutputStream out = response.getOutputStream();
        int needSize = requestSize;
        randomFile.seek(start);
        while(needSize > 0){
            byte[] buffer = new byte[4096];
            int len = randomFile.read(buffer);
            if(needSize < buffer.length){
                out.write(buffer, 0, needSize);
            } else {
                out.write(buffer, 0, len);
                if(len < buffer.length){
                    break;
                }
            }
            needSize -= buffer.length;
        }
        randomFile.close();
        out.close();

    }


    /**
     * 下载图片返回二进制
     * 请求地址：http://localhost:8080/common/static/{user/20190119/e1fe9925bc315c60addea1b98eb1cb1349547719_1547866868179.jpg}
     *
     * @param id
     * @param response
     */
    /*@ApiOperation("文件下载")
    @GetMapping(value = "/downloadFile")
    public void downloadFile(String id,  HttpServletResponse response) {
        OutputStream outputStream = null;
        try {
            OSSFile o = iossFileService.getById(id);
            if (o!=null) {
                response.setContentType("application/force-download");// 设置强制下载不打开
//                response.setContentType("video/mpeg4");// 设置强制下载不打开
                String[] filenames=o.getFileName().split("/");
                String filename=filenames[filenames.length-1];
                if(filenames.length>0){
            }
                response.addHeader("content-disposition", "attachment;fileName=" + new String(o.getFileName().replaceAll(",","").getBytes("UTF-8"),"iso-8859-1"));
                FTPUtil.download(o.getUrl(), response.getOutputStream());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {

                }
            }
        }

    }*/
    @ApiOperation("ids查询文件")
    @GetMapping(value = "/selectFiles")
    public Result<?> selectFiles(String ids) {
        try {
            QueryWrapper<OSSFile> queryWrapper=new QueryWrapper<>();
            queryWrapper.in("id",Arrays.asList(ids.split(",")));
            List<OSSFile> os = iossFileService.list(queryWrapper);
            Result<List> listResult=new Result<>();
            listResult.setResult(os);
            return listResult;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("查询错误");
        }
    }

    @ApiOperation("删除文件")
    @DeleteMapping (value = "/deleteFile")
    public Result deleteFile(String id) {
        try {
            OSSFile o = iossFileService.getById(id);
            if (o!=null) {
                boolean isdelete=iossFileService.removeById(id);
                String url=o.getUrl();
                String path=url.substring(0,url.lastIndexOf("/"));
                String filename=url.substring(url.lastIndexOf("/")+1,url.length());
                FTPUtil.deleteFile(path,filename);
            }else return Result.error("删除失败,未找到id");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("删除失败");
        }
        return Result.ok("删除成功");
    }


}
