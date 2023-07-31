import request from '@/utils/request'

export default {
  savehistories(data) {
    return request({
      url: 'retrieval/history/saveh',
      method: 'post',
      data
    })
  },
  gethistories(userId) {
    return request({
      url: 'retrieval/history/geth',
      method: 'get',
      params: {
        userId: userId
      }
    })
  }
}
