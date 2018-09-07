package com.yhglobal.gongxiao.transport.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.transport.model.SignedPictrueMsg;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: 葛灿
 */
public interface SignedPictureMsgMapper extends BaseMapper {


    @Insert({
            "insert into tms_signed_picture_message ",
            "( custOrdNo,remark, pictureData, pictureType, pictureTime, createTime )",
            "values ( #{custOrdNo}, #{remark}, #{pictureData}, #{pictureType}, #{pictureTime}, NOW() )"}
    )
    int insert(SignedPictrueMsg record);

}
