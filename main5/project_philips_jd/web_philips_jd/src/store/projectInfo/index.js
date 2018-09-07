import getData from '../../assets/js/fetchPublicData'

var state = {
  selectedProjectInfo:{
    projectId:0
  },
  warehouseList:[{}],
  supplierList:[{}]
};

const actions = {
  changeProjectInfo(context,selectedProjectInfo){
    context.commit('CHANGEPROJECTINFO',selectedProjectInfo);
  },
  fetchWarehouseList({commit}){
    getData.getWarehouseList()
      .then(res =>{
        commit('FETCHWAREHOUSELIST',res)
      })
  },
  fetchSupplierList({commit}){
    getData.getSupplierList()
      .then(res => {
        commit('FETCHSUPPLIERLIST',res)
      })
  }
};

const mutations = {
  CHANGEPROJECTINFO(state,projectId){
    state.selectedProjectInfo.projectId = projectId;
  },
  FETCHWAREHOUSELIST(state,res){
    let {data:data,message,returnCode} = res;
    if(returnCode === 0){
      state.warehouseList = data;
    }else{
      console.log(message)
    }
  },
  FETCHSUPPLIERLIST(state,res){
    let {data:data,message,returnCode} = res;
    if(returnCode === 0){
      state.supplierList = data;
    }else{
      console.log(message)
    }
  }
};

const getters = {
  // getProjectInfo(state){
  //   return state.selectedProjectInfo.projectId
  // },
  getProjectInfo: state => state.selectedProjectInfo.projectId,
  warehouseList: state => state.warehouseList,
  supplierList: state => state.supplierList
};

export default({
  state,
  actions,
  mutations,
  getters
})
