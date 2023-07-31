<template>
  <div class="policy-body">
    <!-- Policy Title -->
    <h1 class="policy-title">{{ policyTitle }}</h1>

    <!-- Policy Body -->
    <div class="policy-content" v-html="policyContent" />

    <!-- Comment -->
    <div>
      <h3>评论</h3>
      <el-form ref="commentForm" :model="commentForm" :rules="commentRules" label-width="80px">
        <el-form-item label="评论内容" prop="content">
          <el-input v-model="commentForm.content" type="textarea" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitComment">提交</el-button>
        </el-form-item>
      </el-form>
      <hr>
      <div v-for="(comment, index) in comments" :key="index">
        <p>{{ comment.userId }}</p>
        <p>{{ comment.content }}</p>
      </div>
    </div>
  </div>
</template>

<script>
import inforetrieval from '@/api/inforetrieval'
import comment from '@/api/comment'

export default {
  name: 'PolicyBody',

  data() {
    return {
      policyTitle: '',
      policyContent: '<p>This is the policy content.</p>',
      commentForm: {
        content: ''
      },
      commentRules: {
        content: [
          { required: true, message: '请填写评论内容', trigger: 'blur' }
        ]
      },
      comments: []
    }
  },
  created() {
    // 通过policyid来获取对应的policytitle和policybody
    inforetrieval.getPolicytitle(this.$route.params.policyid).then(res => {
      this.policyTitle = res.data
    })
    inforetrieval.getPolicybody(this.$route.params.policyid).then(res => {
      this.policyContent = res.data
    })
    // 将政策id存入浏览记录中
    const userId = this.$store.state.user.userId
    const policyId = this.$route.params.policyid
    this.$store.commit('history/setPolicyId', inforetrieval.getPolicytitle(policyId))
    this.$store.commit('history/setPolicyTitle', this.$route.params.policyid)
    this.$store.dispatch('history/saveBrowseRecord', { userId, policyId })
  },
  mounted() {
    this.loadComments()
  },

  methods: {
    submitComment() {
      this.$refs.commentForm.validate(valid => {
        if (valid) {
          const timestamp = Date.now()
          const isoString = new Date(timestamp).toISOString()
          const userId = this.$store.state.user.userId
          const params = {
            userId: userId,
            policyId: this.$route.params.policyid,
            content: this.commentForm.content,
            pubDate: isoString
          }
          this.saveComment(params)
        }
      })
    },
    saveComment(params) {
      return new Promise((resolve, reject) => {
        // 调用后端函数
        comment.saveComment(params, function(err) {
          if (err) {
            reject(err) // 报错的情况，返回Promise中的reject函数
          } else {
            this.commentForm.content = ''
            this.loadComments()
            resolve() // 无返回值，返回Promise中的resolve函数
          }
        })
      })
    },
    loadComments() {
      const policyId = this.$route.params.policyid
      comment.getComments(policyId).then(response => {
        this.comments = response.data
      })
    }
  }
}
</script>

<style>
.policy-body {
  margin: 20px;
}

.policy-title {
  font-size: 24px;
  margin-bottom: 10px;
}

.policy-content {
  margin-bottom: 20px;
}

.comments-section {
  border: 1px solid #ccc;
  padding: 10px;
}

.comments-section form textarea {
  width: 100%;
  margin-bottom: 10px;
}

.comments-section form button {
  display: block;
}

</style>
