import { resetRouter } from '@/router'
import { adminlogin, adminlogout, getadminInfo } from '@/api/admin'
import { getAdminToken, setAdminToken, removeAdminToken } from '@/utils/auth'

const getDefaultState = () => {
  return {
    admintoken: getAdminToken(),
    adminname: '',
    adminavatar: '',
    adminId: ''
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, admintoken) => {
    state.admintoken = admintoken
  },
  SET_ADMINNAME: (state, name) => {
    state.adminname = name
  },
  SET_ADMINAVATAR: (state, avatar) => {
    state.adminavatar = avatar
  },
  SET_ADMINId: (state, id) => {
    state.adminId = id
  }
}

const actions = {
  // user login
  adminlogin({ commit }, adminInfo) {
    const { username, password } = adminInfo
    return new Promise((resolve, reject) => {
      adminlogin({ adminname: username.trim(), password: password }).then(response => {
        const { data } = response
        commit('SET_TOKEN', data.admintoken)
        setAdminToken(data.admintoken)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // get admin info
  getadminInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      getadminInfo(state.admintoken).then(response => {
        const { data } = response

        if (!data) {
          reject('Verification failed, please Login again.')
        }

        const { adminid, adminname, adminavatar } = data
        commit('SET_ADMINNAME', adminname)
        commit('SET_ADMINAVATAR', adminavatar)
        commit('SET_ADMINId', adminid)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // admin logout
  adminlogout({ commit, state }) {
    return new Promise((resolve, reject) => {
      adminlogout(state.admintoken).then(() => {
        removeAdminToken() // must remove  token  first
        resetRouter()
        commit('RESET_STATE')
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      removeAdminToken() // must remove  token  first
      commit('RESET_STATE')
      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

