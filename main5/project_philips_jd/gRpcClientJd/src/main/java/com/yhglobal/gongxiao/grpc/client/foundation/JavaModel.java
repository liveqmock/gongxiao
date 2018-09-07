//package com.yhglobal.gongxiao.grpc.client.foundation;
//
//import com.github.taojoe.Transformer;
//import com.yhglobal.gongxiao.foundation.project.microservice.ProjectServiceStructure;
//
///**
// * 从proto对象转换成为java对象
// *
// * 注1: JavaModel必须是public的
// * 注2: 基础的数据类型可这么转 有List/Map/内嵌类型的不推荐这么直接转 通过代码手工显示转换
// */
//public class JavaModel {
//
//    public int projectId;
//
//    public int getProjectId() {
//        return projectId;
//    }
//
//    public void setProjectId(int projectId) {
//        this.projectId = projectId;
//    }
//
//    public static void main(String[] args) {
//        //1. 构建GetProjectByIdReq
//        ProjectServiceStructure.GetProjectByIdReq req = ProjectServiceStructure.GetProjectByIdReq.newBuilder()
//                .setProjectId(1234)
//                .build();
//
//        Transformer trans = new Transformer();
//        JavaModel javaModel = trans.messageToJava(req, JavaModel.class);
//        System.out.println(javaModel.projectId);
//    }
//
//}
