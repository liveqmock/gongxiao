package com.yhglobal.gongxiao.tms.dao;

import com.yhglobal.gongxiao.tms.dao.mapping.SignedPictureMsgMapper;
import com.yhglobal.gongxiao.tms.model.SignedPictrueMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author: 葛灿
 */
@Repository
public class SignedPictrueMsgDao {

    @Autowired
    SignedPictureMsgMapper signedPictureMsgMapper;

    public int insert(SignedPictrueMsg record) {
        return signedPictureMsgMapper.insert(record);
    }
}
