import Vue from 'vue'
import VueX from 'vuex'

Vue.use(VueX);

import projectInfo from './projectInfo'

export default new VueX.Store({
  modules:{
    // projectInfo: projectInfo
    projectInfo
  }
})
