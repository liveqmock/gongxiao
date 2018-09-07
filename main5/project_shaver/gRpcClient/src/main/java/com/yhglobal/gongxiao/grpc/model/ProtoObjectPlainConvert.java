package com.yhglobal.gongxiao.grpc.model;

import com.google.protobuf.ByteString;
import com.yhglobal.gongxiao.grpc.model.java.JavaInnerModel;
import com.yhglobal.gongxiao.grpc.model.java.JavaModel;
import com.yhglobal.gongxiao.grpc.model.proto.ProtoModelStructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProtoObjectPlainConvert {

    //java对象 组装为 proto对象
    public static ProtoModelStructure.ProtoModel javaToProto(JavaModel javaModel) {
        ProtoModelStructure.ProtoModel.Builder protoModelBuilder = ProtoModelStructure.ProtoModel.newBuilder();
        protoModelBuilder.setTraceId(javaModel.getTraceId());
        protoModelBuilder.setProjectId(javaModel.getProjectId());
        protoModelBuilder.setIsDeleted(javaModel.getIsDeleted());
        protoModelBuilder.setBody(ByteString.copyFrom(javaModel.getBody()));

        //组装List:
        List<JavaInnerModel> javaInnerModelList = javaModel.getInnerModelList();
        if (javaInnerModelList != null) {
            for (JavaInnerModel javaInnerModel : javaInnerModelList) {
                ProtoModelStructure.ProtoInnerModel protoInnerModel = ProtoModelStructure.ProtoInnerModel.newBuilder()
                        .setInnerId(javaInnerModel.getInnerId())
                        .build();
                protoModelBuilder.addInnerModel(protoInnerModel);
            }
        }

        //组装Map:
        Map<String, JavaInnerModel> javaMapping = javaModel.getMapping();
        if (javaMapping != null) {
            for (Map.Entry<String, JavaInnerModel> entry : javaMapping.entrySet()) {
                ProtoModelStructure.ProtoInnerModel protoInnerModel = ProtoModelStructure.ProtoInnerModel.newBuilder()
                        .setInnerId(entry.getValue().getInnerId())
                        .build();
                protoModelBuilder.putMapping(entry.getKey(), protoInnerModel);
            }
        }

        return protoModelBuilder.build();
    }

    //proto对象 组装为 Java对象
    public static JavaModel protoToJava(ProtoModelStructure.ProtoModel protoModel) {
        JavaModel javaModel = new JavaModel();
        javaModel.setTraceId(protoModel.getTraceId());
        javaModel.setProjectId(protoModel.getProjectId());
        javaModel.setIsDeleted(protoModel.getIsDeleted());
        javaModel.setBody(protoModel.getBody().toByteArray());

        //组装List
        List<JavaInnerModel> javaInnerModelList = new ArrayList<JavaInnerModel>();
        for (ProtoModelStructure.ProtoInnerModel protoInnerModel : protoModel.getInnerModelList()) {
            JavaInnerModel javaInnerModel = new JavaInnerModel();
            javaInnerModel.setInnerId(protoInnerModel.getInnerId());
            javaInnerModelList.add(javaInnerModel);
        }
        javaModel.setInnerModelList(javaInnerModelList);

        //组装Map
        Map<String, JavaInnerModel> javaMapping = new HashMap<String, JavaInnerModel>();
        for (Map.Entry<String, ProtoModelStructure.ProtoInnerModel> entry : protoModel.getMappingMap().entrySet()) {
            JavaInnerModel javaInnerModel = new JavaInnerModel();
            javaInnerModel.setInnerId(entry.getValue().getInnerId());
            javaMapping.put(entry.getKey(), javaInnerModel);
        }
        javaModel.setMapping(javaMapping);

        return javaModel;
    }

}
