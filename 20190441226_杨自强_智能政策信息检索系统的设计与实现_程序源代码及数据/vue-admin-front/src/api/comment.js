import request from '@/utils/request'

export default {
  getComments(policyId) {
    return request({
      url: '/retrieval/comment/comments',
      method: 'get',
      params: { policyId }
    })
  },
  saveComment(comment) {
    return request({
      url: '/retrieval/comment/savec',
      method: 'post',
      data: comment
    })
  }
}

