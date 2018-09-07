package com.yhglobal.gongxiao.warehouse.service;

import com.yhglobal.gongxiao.warehouse.model.dto.ShaverReportPsiOverview;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
public interface ReportService {

    List<ShaverReportPsiOverview> selectEffectiveReportList();

}
