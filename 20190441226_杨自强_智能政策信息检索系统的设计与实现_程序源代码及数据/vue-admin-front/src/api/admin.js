import request from '@/utils/request'

export function adminlogin(data) {
  return request({
    url: '/retrieval/admin/adminlogin',
    method: 'post',
    data
  })
}

export function getadminInfo(admintoken) {
  return request({
    url: '/retrieval/admin/admininfo',
    method: 'get',
    params: { admintoken }
  })
}

export function adminlogout() {
  return request({
    url: '/retrieval/admin/adminlogout',
    method: 'post'
  })
}
