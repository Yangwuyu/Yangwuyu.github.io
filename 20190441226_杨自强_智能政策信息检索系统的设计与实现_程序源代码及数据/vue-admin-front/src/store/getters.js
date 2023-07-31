const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  name: state => state.user.name,
  policyId: state => state.history.policyId,
  browseRecords: state => state.history.browseRecords,
  userId: state => state.user.userId,
  email: state => state.user.email,
  adminname: state => state.admin.adminname,
  adminavatar: state => state.admin.adminavatar,
  adminId: state => state.admin.adminId,
  admintoken: state => state.admin.admintoken
}
export default getters
