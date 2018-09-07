package dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import model.Tsupplier;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

/**
 * @Author:Nize
 * @Description:
 * @Date:Created in 19:09 2018/1/18
 **/
public interface TsupplierMapper extends BaseMapper{
    @Delete({
            "delete from t_supplier",
            "where supplierId = #{supplierid,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long supplierid);

    @Insert({
            "insert into t_supplier (supplierId, supplierName, ",
            "supplierCode, status, ",
            "address, email, ",
            "contactName, countryCode, ",
            "phoneNumber, createdById, ",
            "createdByName, createTime, ",
            "lastUpdateTime, tracelog)",
            "values (#{supplierid,jdbcType=BIGINT}, #{suppliername,jdbcType=VARCHAR}, ",
            "#{suppliercode,jdbcType=VARCHAR}, #{status,jdbcType=TINYINT}, ",
            "#{address,jdbcType=VARCHAR}, #{email,jdbcType=VARCHAR}, ",
            "#{contactname,jdbcType=VARCHAR}, #{countrycode,jdbcType=VARCHAR}, ",
            "#{phonenumber,jdbcType=VARCHAR}, #{createdbyid,jdbcType=BIGINT}, ",
            "#{createdbyname,jdbcType=VARCHAR}, #{createtime,jdbcType=TIMESTAMP}, ",
            "#{lastupdatetime,jdbcType=TIMESTAMP}, #{tracelog,jdbcType=LONGVARCHAR})"
    })
    int insert(Tsupplier record);


    @Select({
            "select",
            "supplierId, supplierName, supplierCode, status, address, email, contactName, ",
            "countryCode, phoneNumber, createdById, createdByName, createTime, lastUpdateTime, ",
            "tracelog",
            "from t_supplier",
            "where supplierId = #{supplierid,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column="supplierId", property="supplierid", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="supplierName", property="suppliername", jdbcType=JdbcType.VARCHAR),
            @Result(column="supplierCode", property="suppliercode", jdbcType=JdbcType.VARCHAR),
            @Result(column="status", property="status", jdbcType=JdbcType.TINYINT),
            @Result(column="address", property="address", jdbcType=JdbcType.VARCHAR),
            @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
            @Result(column="contactName", property="contactname", jdbcType=JdbcType.VARCHAR),
            @Result(column="countryCode", property="countrycode", jdbcType=JdbcType.VARCHAR),
            @Result(column="phoneNumber", property="phonenumber", jdbcType=JdbcType.VARCHAR),
            @Result(column="createdById", property="createdbyid", jdbcType=JdbcType.BIGINT),
            @Result(column="createdByName", property="createdbyname", jdbcType=JdbcType.VARCHAR),
            @Result(column="createTime", property="createtime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="lastUpdateTime", property="lastupdatetime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="tracelog", property="tracelog", jdbcType=JdbcType.LONGVARCHAR)
    })
    Tsupplier selectByPrimaryKey(Long supplierid);


    @Update({
            "update t_supplier",
            "set supplierName = #{suppliername,jdbcType=VARCHAR},",
            "supplierCode = #{suppliercode,jdbcType=VARCHAR},",
            "status = #{status,jdbcType=TINYINT},",
            "address = #{address,jdbcType=VARCHAR},",
            "email = #{email,jdbcType=VARCHAR},",
            "contactName = #{contactname,jdbcType=VARCHAR},",
            "countryCode = #{countrycode,jdbcType=VARCHAR},",
            "phoneNumber = #{phonenumber,jdbcType=VARCHAR},",
            "createdById = #{createdbyid,jdbcType=BIGINT},",
            "createdByName = #{createdbyname,jdbcType=VARCHAR},",
            "createTime = #{createtime,jdbcType=TIMESTAMP},",
            "lastUpdateTime = #{lastupdatetime,jdbcType=TIMESTAMP},",
            "tracelog = #{tracelog,jdbcType=LONGVARCHAR}",
            "where supplierId = #{supplierid,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Tsupplier record);
}
