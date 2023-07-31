<template>
  <div class="profile">
    <div class="user">
       <img :src="yuser.avatarUrl" alt="yuser avatar" width="250px">
      <h1>{{ yuser.name }}</h1>
      <h1>{{ yuser.email }}</h1>
      </div>
  <table>
    <thead>
    <tr>
      <th>Date</th>
      <th>policyid</th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="(item, index) in history" :key="index">
      <td>{{ item.viewDate }}</td>
      <td>{{ item.policyId }}</td>
    </tr>
    </tbody>
  </table>
  </div>
</template>
<script>
import user from '@/store/modules/user'

export default {
  data() {
    return {
      yuser: {
        name: '',
        avatarUrl: '', // 初始状态为空
        id: null,
        email: ''
      },
      history: []
    }
  },
  created() {
    this.yuser.avatarUrl = user.state.avatar
    this.yuser.name = user.state.name
    this.yuser.email = user.state.email
    // 获取用户浏览记录
    this.$store.dispatch('history/fetchBrowseRecords')
    this.history = this.$store.state.history.browseRecords
  }
}
</script>
