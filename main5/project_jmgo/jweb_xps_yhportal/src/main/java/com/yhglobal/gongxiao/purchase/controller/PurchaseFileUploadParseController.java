package com.yhglobal.gongxiao.purchase.controller;

import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.base.protal.PortalConfig;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.purchase.microservice.PurchaseFileServiceGrpc;
import com.yhglobal.gongxiao.purchase.microservice.PurchaseFileStructure;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.YhPortalTraceIdGenerator;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
@Controller
@RequestMapping("/purchaseUpload")
public class PurchaseFileUploadParseController {

    private static Logger logger = LoggerFactory.getLogger(PurchaseFileUploadParseController.class);

    @Autowired
    PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;


    @RequestMapping(value = "/uploadAndParse", method = RequestMethod.POST)
    public ModelAndView uploadAndParse(HttpServletRequest request, RedirectAttributes attr) {

        long projectId = portalUserInfo.getProjectId();
        String projectName = "飞利浦小家电线下批发";
        String moduleName = "purchase";
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        logger.info("#traceId={}# [IN][getProjectList] params: ", traceId);
        try {
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
//                    //4)数据库记录
//                    int i = fileService.addFileInfo(rpcHeader, realFileName, fileName, storagePath, rpcHeader.getUid(), rpcHeader.getUsername());
                    //5)解析文件
                    PurchaseFileStructure.PurchaseFileReq purchaseFileReq = PurchaseFileStructure.PurchaseFileReq.newBuilder()
                            .setRpcHeader(rpcHeader)
                            .setFilePath(storagePath + File.separator + fileName)
                            .setProjectId(projectId+"")
                            .setProjectName(projectName)
                            .build();
                    PurchaseFileServiceGrpc.PurchaseFileServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(PurchaseFileServiceGrpc.PurchaseFileServiceBlockingStub.class);
                    PurchaseFileStructure.PurchaseFileResp resp = null;
                    try {
                        resp=rpcStub.parsePurchaseOrderList(purchaseFileReq);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    PurchaseFileStructure.RpcResultPurchaseFile rpcResultPurchaseFile = resp.getRpcResultPurchaseFile();
                    if (rpcResultPurchaseFile.getReturnCode()==0){
                        return new ModelAndView("redirect:/philips/shaver/main/error/success.html");
                    }else {
                        ModelAndView modelAndView = new ModelAndView("redirect:/philips/shaver/main/error/ERROR.html");
                        modelAndView.addObject("errorParam",rpcResultPurchaseFile.getMsg());
                        return modelAndView;
                    }
                }
            }
            return new ModelAndView("redirect:/philips/shaver/main/purchase/purchase-management.html");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            ModelAndView modelAndView = new ModelAndView("redirect:/philips/shaver/main/error/ERROR.html");
            modelAndView.addObject("errorParam","未知系统异常");
            return modelAndView;
        }
    }

}
