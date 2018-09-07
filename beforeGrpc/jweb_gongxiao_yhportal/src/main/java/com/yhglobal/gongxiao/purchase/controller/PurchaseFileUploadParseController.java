package com.yhglobal.gongxiao.purchase.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yhglobal.gongxiao.base.config.PortalConfig;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.RpcResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.foundation.file.FileService;
import com.yhglobal.gongxiao.purchase.service.PurchaseFileService;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.GongxiaoResultUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.util.YhPortalTraceIdGenerator;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/purchaseUpload")
public class PurchaseFileUploadParseController {

    private static Logger logger = LoggerFactory.getLogger(PurchaseFileUploadParseController.class);

    @Reference
    FileService fileService;

    @Reference
    PurchaseFileService purchaseFileService;

    @Autowired
    PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;

    @RequestMapping(value = "/uploadAndParse", method = RequestMethod.POST)
    public ModelAndView uploadAndParse(HttpServletRequest request,RedirectAttributes attr) {

        String projectId = "146798161";
        String projectName = "飞利浦小家电线下批发";
        String moduleName = "purchase";
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getProjectList] params: ", traceId);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            String storageRootPath = portalConfig.getFilePath();
            String storagePath = storageRootPath + File.separator + projectId + File.separator + moduleName + File.separator + DateUtil.getYearOfDate(new Date()) + File.separator + DateUtil.getMonthOfDate(new Date());
            logger.info("storagePath={}",storagePath);
            //1) 获取要上传的文件信息
            boolean isMultipartContent = ServletFileUpload.isMultipartContent(request); //获取请求头
            if (!isMultipartContent) {
                throw new RuntimeException("your form is not multipart/form-data");
            }
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setHeaderEncoding("UTF-8");
            List<FileItem> fileItems = upload.parseRequest(request);
            for (FileItem fileItem : fileItems) {
                if (fileItem.isFormField()) {// 普通表单项 不关注

                } else {// 上传表单项
                    File uploadRealFile = new File(storagePath);
                    if (!uploadRealFile.exists()) {//如果上传目录不存在，则创建上传目录
                        uploadRealFile.mkdirs();
                    }
                    //2)文件名变更
                    String realFileName = fileItem.getName();//获取上传文件的文件名
                    String[] fileArray = realFileName.split("\\.");
                    String fileName = traceId+"."+fileArray[fileArray.length-1];
                    logger.info("fileName:{}",fileName);
                    //3)保存文件
                    File file = new File(uploadRealFile, File.separator + fileName);
                    logger.info("文件大小:{}",file.length());
                    fileItem.write(file); //使用FileItem自带的write方法保存上传文件
                    fileItem.delete();//解决上传文件的临时文件问题
                    //4)数据库记录
                    int i = fileService.addFileInfo(rpcHeader, realFileName, fileName, storagePath, rpcHeader.getUid(), rpcHeader.getUsername());
                    //5)解析文件
                    RpcResult rpcResult = purchaseFileService.parsePurchaseOrderList(rpcHeader, storagePath + File.separator + fileName, projectId, projectName);
                    if (rpcResult.getCode()==0){
                        return new ModelAndView("redirect:/main/error/success.html");
                    }else {
                        ModelAndView modelAndView = new ModelAndView("redirect:/main/error/ERROR.html");
                        modelAndView.addObject("errorParam",rpcResult.getMessage());
                        return modelAndView;
                    }
                }
            }
            return new ModelAndView("redirect:/main/purchase/purchase-management.html");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            ModelAndView modelAndView = new ModelAndView("redirect:/main/error/ERROR.html");
            modelAndView.addObject("errorParam","未知系统异常");
            return modelAndView;
        }
    }
}
