package com.yhglobal.gongxiao.foundation.file.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yhglobal.gongxiao.base.config.PortalConfig;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.foundation.file.FileService;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.GongxiaoResultUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.util.YhPortalTraceIdGenerator;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * 文件上传功能
 *
 * @author: 陈浩
 **/
@Controller
@RequestMapping("/upload")
public class UploadFileController {

    private static Logger logger = LoggerFactory.getLogger(UploadFileController.class);

    @Reference
    FileService fileService;

    @Autowired
    PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;

    /**
     * 文件上传
     * @param request
     * @param projectName   项目名称
     * @param moduleName     模块名称
     * @return
     */
    @RequestMapping("/add")
    public GongxiaoResult addFile(HttpServletRequest request,String projectName,String moduleName){
        String traceId = null;
        try {
            traceId= YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(),  request.getServletPath());
            logger.info("#traceId={}# [IN][getPurchaseReturnList] params: projectName={} ",projectName);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            String storageRootPath = portalConfig.getFilePath();
            String storagePath = storageRootPath+File.separator+projectName+moduleName+File.separator+ DateUtil.getYearOfDate(new Date())+File.separator+DateUtil.getMonthOfDate(new Date());
            //1) 获取要上传的文件信息
            boolean isMultipartContent = ServletFileUpload.isMultipartContent(request); //获取请求头
            if (!isMultipartContent) {
                throw new RuntimeException("your form is not multipart/form-data");
            }
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setHeaderEncoding("UTF-8");
                List<FileItem> fileItems = upload.parseRequest(request);
                for (FileItem fileItem:fileItems){
                    if (fileItem.isFormField()) {// 普通表单项 不关注

                    } else {// 上传表单项
                        File uploadRealFile = new File(storagePath);
                        if (!uploadRealFile.exists()) {//如果上传目录不存在，则创建上传目录
                            uploadRealFile.mkdirs();
                        }
                        //2)文件名变更
                        String realFileName = fileItem.getName();//获取上传文件的文件名
                        String fileName = traceId+"_"+realFileName;
                        //3)保存文件
                        File file = new File(uploadRealFile, File.separator + fileName);
                        fileItem.write(file); //使用FileItem自带的write方法保存上传文件
                        fileItem.delete();//解决上传文件的临时文件问题
                        //4)数据库记录
                        int i = fileService.addFileInfo(rpcHeader, realFileName, fileName, storagePath, "1234", "testname");
                    }
                }
                return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), 1);
        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(),  e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }
}
