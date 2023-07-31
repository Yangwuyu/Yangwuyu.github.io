import Cookies from 'js-cookie'
const TokenKey = 'vue admin template token' // 必须保留的 token，不管是管理员还是用户登录都必须存储在此 key 中
const UserTokenKey = 'token'
const AdminTokenKey = 'admintoken'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken() {
  return Cookies.set(TokenKey)
}

export function removeToken() {
  Cookies.remove(TokenKey)
}

export function getUserToken() {
  return Cookies.get(UserTokenKey)
}

export function getAdminToken() {
  return Cookies.get(AdminTokenKey)
}

export function setUserToken(token) {
  return Cookies.set(UserTokenKey, token)
}

export function setAdminToken(token) {
  return Cookies.set(AdminTokenKey, token)
}

export function removeUserToken() {
  return Cookies.remove(UserTokenKey)
}

export function removeAdminToken() {
  return Cookies.remove(AdminTokenKey)
}

export function hasToken() {
  return getToken() || getUserToken() || getAdminToken()
}
