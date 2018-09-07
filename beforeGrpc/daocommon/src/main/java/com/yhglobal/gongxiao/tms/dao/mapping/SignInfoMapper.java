package com.yhglobal.gongxiao.tms.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.tms.model.SignInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author: 葛灿
 */
public interface SignInfoMapper extends BaseMapper {

    @Insert({
            "insert into tms_sign_info ( custOrdNo, tmsOrdNo, remark ,",
            "signedBy, postedBy, signedPhone, signedTime, createTime, transporter)",
            "values",
            "( #{custOrdNo}, #{tmsOrdNo}, #{remark}, #{signedBy}, #{postedBy}, #{signedPhone}, #{signedTime}, NOW(), #{transporter})"}
    )
    int insert(SignInfo record);

    @Select({
            "select ",
            "custOrdNo, remark ,signedBy, postedBy, signedPhone, signedTime",
            "from tms_sign_info",
            "where custOrdNo = #{orderNo}"
    })
    SignInfo getByOrderNo(@Param("orderNo") String orderNo);

    @Update({
            "update tms_sign_info set",
            "remark = #{remark},",
            "signedBy = #{signedBy},",
            "postedBy = #{postedBy},",
            "signedPhone = #{signedPhone},",
            "signedTime = #{signedTime}",
            "where custOrdNo = #{custOrdNo}"
    })
    int update(SignInfo record);
}
