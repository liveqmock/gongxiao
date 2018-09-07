package com.yhglobal.gongxiao.transport.dao;

import com.yhglobal.gongxiao.transport.dao.mapping.SignedPictureMsgMapper;
import com.yhglobal.gongxiao.transport.model.SignedPictrueMsg;
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
