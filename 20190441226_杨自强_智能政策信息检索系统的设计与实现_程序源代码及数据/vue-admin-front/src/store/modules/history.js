import browse_record from '@/api/browse-records'
import user from '@/store/modules/user'

const state = {
  policyId: null,
  policyTitle: '',
  browseRecords: []
}
const mutations = {
  setPolicyId(state, policyId) {
    state.policyId = policyId
  },
  setPolicyTitle(state, policyTitle) {
    state.policyId = policyTitle
  },
  setBrowseRecords(state, records) {
    state.browseRecords = records
  }
}
const actions = {
  saveBrowseRecord({ state }, { userId, policyId }) {
    const timestamp = Date.now()
    const isoString = new Date(timestamp).toISOString()
    const browseRecord = {
      userId,
      policyId,
      viewDate: isoString
    }
    return browse_record.savehistories(browseRecord)
  },
  fetchBrowseRecords({ commit }) {
    const userId = user.state.userId
    // return axios.get(`/api/browse-records?userId=${userId}`)
    return browse_record.gethistories(userId)
      .then(response => {
        commit('setBrowseRecords', response.data)
      })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

