package com.yhglobal.gongxiao.warehouse.service.impl;

import com.yhglobal.gongxiao.warehouse.dao.ReportPsiOverviewDao;
import com.yhglobal.gongxiao.warehouse.model.dto.ShaverReportPsiOverview;
import com.yhglobal.gongxiao.warehouse.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
@Service
public class ReportServiceImpl implements ReportService {

    private Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

    @Autowired
    ReportPsiOverviewDao reportPsiOverviewDao;

    @Override
    public List<ShaverReportPsiOverview> selectEffectiveReportList() {
        try {
            return reportPsiOverviewDao.selectAllData();
        }catch (Exception e){
            logger.error("# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }
}
