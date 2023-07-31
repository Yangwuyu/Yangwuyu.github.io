import request from '@/utils/request'

export function getProvinces() {
  return request({
    url: '/retrieval/provinces/locations',
    method: 'get'
  })
}
