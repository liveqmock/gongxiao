<template>
  <el-aside class="sidebar" style="width:180px;">
    <el-menu :default-active="$route.path" class="el-menu-vertical-demo" background-color="#545c64" text-color="#fff" active-text-color="#ffd04b" router>
      <el-submenu index="purchase">
        <template slot="title">
          <i class="el-icon-menu"></i>采购管理
        </template>
        <el-menu-item-group>
          <el-menu-item :index="item.menuPath" v-for="(item,index) in sideBarPath.purchase" :key="index">{{item.menuName}}</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
      <el-submenu index="sales">
        <template slot="title">
          <i class="el-icon-menu"></i>销售管理
        </template>
        <el-menu-item-group>
          <el-menu-item :index="item.menuPath" v-for="(item,index) in sideBarPath.sales" :key="index">{{item.menuName}}</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
      <el-submenu index="stock">
        <template slot="title">
          <i class="el-icon-setting"></i>库存管理
        </template>
        <el-menu-item-group>
          <el-menu-item :index="item.menuPath" v-for="(item,index) in sideBarPath.stock" :key="index">{{item.menuName}}</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
      <el-submenu index="storage">
        <template slot="title">
          <i class="el-icon-setting"></i>仓储管理
        </template>
        <el-menu-item-group>
          <el-menu-item :index="item.menuPath" v-for="(item,index) in sideBarPath.storage" :key="index">{{item.menuName}}</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
      <el-submenu index="EDI">
        <template slot="title">
          <i class="el-icon-setting"></i>EDI中心
        </template>
        <el-menu-item-group>
          <el-menu-item :index="item.menuPath" v-for="(item,index) in sideBarPath.EDI" :key="index">{{item.menuName}}</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
      <el-submenu index="account">
        <template slot="title">
          <i class="el-icon-setting"></i>账户管理</template>
        <el-menu-item-group>
          <el-menu-item :index="item.menuPath" v-for="(item,index) in sideBarPath.account" :key="index">{{item.menuName}}</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
      <el-submenu index="basicData">
        <template slot="title">
          <i class="el-icon-setting"></i>基础资料</template>
        <el-menu-item-group>
          <el-menu-item :index="item.menuPath" v-for="(item,index) in sideBarPath.basicData" :key="index">{{item.menuName}}</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
    </el-menu>
  </el-aside>
</template>

<script>
import SIDEBARMENU from '../assets/js/sidebarPath'
export default {
    name: "FenXiaoSidebar",
    data() {
        return {
            sideBarPath: {
              purchase:[],
              sales:[],
              account:[],
              storage:[],
              stock:[],
              pay:[],
              EDI:[],
              basicData:[]
            },
        };
    },
    methods: {
      getSidebarMenuData(){
        let {SIDEBARMENU:{purchase,sales,account,storage,stock,pay,EDI,basicData}} = SIDEBARMENU;
        [this.sideBarPath.purchase,this.sideBarPath.sales,this.sideBarPath.account,this.sideBarPath.storage,this.sideBarPath.stock,this.sideBarPath.pay,this.sideBarPath.EDI,this.sideBarPath.basicData] = [purchase,sales,account,storage,stock,pay,EDI,basicData];
      }
    },
    created: function() {
        this.getSidebarMenuData();
    }
};
</script>

<style scoped lang="less">
.el-aside {
    width: 180px;
    position: fixed;
    top: 0;
    left: 0;
    height: 1000px;
    z-index: 9999;
    overflow: hidden;
}
.el-menu {
    height: 100%;
}
</style>
