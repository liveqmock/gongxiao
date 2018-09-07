<template>
  <div id="app">
    <fen-xiao-sidebar></fen-xiao-sidebar>
    <div class="fx-container">
      <fen-xiao-header></fen-xiao-header>
      <transition
        name="fade"
        mode="out-in"
      >
        <keep-alive>
          <router-view></router-view>
          <purchase-management></purchase-management>
        </keep-alive>

      </transition>
    </div>

  </div>
</template>

<script>
  import FenXiaoSidebar from './components/FenXiaoSidebar'
  import FenXiaoHeader from './components/FenXiaoHeader'
  import PurchaseManagement from './components/purchase/PurchaseManagement'
  import {mapActions} from 'vuex'

  export default {
    name: 'App',
    components: {
      FenXiaoSidebar,
      FenXiaoHeader,
      PurchaseManagement
    },
    methods:{
      ...mapActions([
        'fetchWarehouseList',
        'fetchSupplierList'
      ])
    },
    created() {
      this.fetchWarehouseList();
      this.fetchSupplierList();
    }
  }
</script>

<style lang="less">
  @import "./assets/less/layout.less";
</style>
<style lang="css">
  @import "./assets/less/reset.css";
</style>
