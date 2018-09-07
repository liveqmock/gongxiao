//package com.yhglobal.gongxiao.phjd.sales;
//
//import com.yhglobal.gongxiao.common.GongxiaoResult;
//import com.yhglobal.gongxiao.phjd.base.model.PortalUserInfo;
//import com.yhglobal.gongxiao.phjd.base.protal.PortalConfig;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * @author 王帅
// * 京东质保金管理界面入口
// */
//@Controller
//@RequestMapping("/yhglobal/qgd")
//public class QualityGuaranteeDepositController {
//
//    private static Logger logger = LoggerFactory.getLogger(QualityGuaranteeDepositController.class);
//
//    @Autowired
//    PortalConfig portalConfig; //property注入类
//
//    @Autowired
//    PortalUserInfo portalUserInfo; //用户信息注入类
//
//    /**
//     * 质保金导出
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "/export")
//    @ResponseBody
//    public GongxiaoResult qualityGuaranteeDepositExport(HttpServletRequest request, String ids){
//        // 根据选中的ID查询对应的质保金
//        // 然后把前端界面字段数据导出成Excel文件
//        return null;
//    }
//
//    /**
//     * 质保金多条件查询
//     * @param request
//     * @param settlementNo
//     * @param documentCode
//     * @param depositStatus
//     * @param jdProjectId
//     * @return
//     */
//    @RequestMapping(value = "/query")
//    @ResponseBody
//    public GongxiaoResult queryQualityGuaranteeDeposit(HttpServletRequest request,String settlementNo,
//                                                Integer documentCode,
//                                                Integer depositStatus,
//                                                Long jdProjectId){
//
//        return null;
//    }
//
//    /**
//     * 质保金相关收据 上传保存
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "/upload")
//    @ResponseBody
//    public GongxiaoResult uploadQualityGuaranteeDepositDocument(HttpServletRequest request, Long depositId){
//        // 每次上传只对一个质保金对象
//        // 查询质保金主键ID对应文件夹 不存在则新建
//        // 文件夹存在则建立输出流把文件保存至对应文件夹下(文件名为原先文件名拼接当时时间)
//        // 更新质保金文件存储路径字段
//        return null;
//    }
//
//    /**
//     * 质保金相关收据 下载功能
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "/download")
//    @ResponseBody
//    public GongxiaoResult downloadQualityGuaranteeDepositDocument(HttpServletRequest request, Long depositId){
//        // 根据ID从数据库查询对象 获取下载路径
//        // 根据路径创建输出流
//        return null;
//    }
//
//    /**
//     * 手动新增质保金
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "/download")
//    @ResponseBody
//    public GongxiaoResult insertQualityGuaranteeDeposit(HttpServletRequest request){
//        // 新增质保金
//        return null;
//    }
//}
