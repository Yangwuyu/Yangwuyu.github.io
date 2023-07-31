<template>
  <div class="app-container">
    <!--搜索栏-->
    <el-card id="search">
      <el-row>
        <el-col :span="12">
          <el-input v-model="searchmodel.policy_id" placeholder="政策id" clearable="true" />
          <el-button type="primary" round="true" icon="el-icon-search" size="medium" @click="getInformationList">查询</el-button>
        </el-col>
        <el-col :span="12">
          <el-input v-model="searchmodel.keyinfo" placeholder="关键信息" clearable="true" />
          <el-button icon="el-icon-search" circle @click="search">检索</el-button>
        </el-col>
      </el-row>
    </el-card>
    <!--所有信息列表-->
    <el-card>
      <el-table
        :data="informationList"
        stripe
        style="width: 100%"
      >
        <el-table-column
          type="index"
          label="#"
          width="80"
          align="center"
        >
          <template slot-scope="scope">
            <!--pageNo*pageSize+index+1-->
            {{ (searchmodel.pageNo-1)*searchmodel.pageSize+scope.$index+1 }}
          </template>
        </el-table-column>
        <el-table-column
          prop="policyid"
          label="政策id"
          width="200"
          align="center"
        >
          <template slot-scope="scope1">
            <router-link :to="{ name: 'policy-body', params: { policyid: scope1.row.policyid } }" >{{ scope1.row.policyid }}</router-link>
          </template>
        </el-table-column>
        <el-table-column
          prop="policytitle"
          label="政策标题"
          align="center">
        </el-table-column>
        <el-table-column
          prop="province"
          label="省份"
          width="180"
          align="center"
        />
        <el-table-column
          prop="updatedate"
          label="更新日期"
          width="200"
          align="center"
        />
      </el-table>
    </el-card>
    <!--分页组件-->
    <el-pagination
      :current-page="searchmodel.pageNo"
      :page-sizes="[5, 10, 20, 50]"
      :page-size="searchmodel.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>
</template>
<script>
import inforetrieval from '@/api/inforetrieval'

export default {
  data() {
    return {
      total: 0,
      searchmodel: {
        pageNo: 1,
        pageSize: 10
      },
      informationList: []
    }
  },
  created() {
    this.getInformationList()
  },
  methods: {
    search() {
      inforetrieval.search(this.searchmodel, this.$store.state.user.province, this.$store.state.user.date).then(response => {
        this.informationList = response.data.rows
        this.total = response.data.total
      })
    },
    handleSizeChange(pageSize) {
      this.searchmodel.pageSize = pageSize
      this.getInformationList()
    },
    handleCurrentChange(pageNo) {
      this.searchmodel.pageNo = pageNo
      this.getInformationList()
    },
    getInformationList() {
      inforetrieval.getInformationList(this.searchmodel).then(response => {
        this.informationList = response.data.rows
        this.total = response.data.total
      })
    }
  }
}
</script>
<style>
#search .el-input {
  width: 300px;
  margin-right: 10px;
}
</style>
