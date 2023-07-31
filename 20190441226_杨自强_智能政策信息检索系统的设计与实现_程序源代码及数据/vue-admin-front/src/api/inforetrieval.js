import request from '@/utils/request'

export default {
  getInformationList(searchmodel) {
    return request({
      url: 'retrieval/policy/allinfo',
      method: 'get',
      params: {
        pageNo: searchmodel.pageNo,
        pageSize: searchmodel.pageSize,
        policy_id: searchmodel.policy_id
      }
    })
  },
  getPolicytitle(policyid) {
    return request({
      url: 'retrieval/policy/policytitle',
      method: 'get',
      params: {
        policyid: policyid
      }
    })
  },
  getPolicybody(policyid) {
    return request({
      url: 'retrieval/policy/policybody',
      method: 'get',
      params: {
        policyid: policyid
      }
    })
  },
  search(searchmodel, province, date) {
    return request(({
      url: 'retrieval/policy/search',
      method: 'get',
      params: {
        pageNo: searchmodel.pageNo,
        pageSize: searchmodel.pageSize,
        keyinfo: searchmodel.keyinfo,
        province,
        date
      }
    }))
  }
}
