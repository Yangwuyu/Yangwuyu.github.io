<template>
  <div>
    <el-carousel indicator-position="outside">
      <el-carousel-item v-for="pid in item" :key="pid">
        <h3>{{ pid }}</h3>
      </el-carousel-item>
    </el-carousel>
    <div class="block">
      <span class="demonstration" />
      <el-date-picker
        v-model="value2"
        align="right"
        type="date"
        placeholder="选择日期"
        :picker-options="pickerOptions"
        @change="handleDatechange"
      />
    </div>
    <div>
      <el-select v-model="selectedProvince" placeholder="请选择省份" @change="handleProvinceChange">
        <el-option v-for="province in provinces" :key="province.province_id" :label="province.province_name" :value="province" />
      </el-select>
    </div>
  </div>
</template>

<script>
import { getProvinces } from '@/api/location'
export default {
  data() {
    return {
      item: '',
      selectedProvince: null, // 当前选中的省份对象
      provinces: [], // 所有省份列表
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now()
        },
        shortcuts: [{
          text: '今天',
          onClick(picker) {
            picker.$emit('pick', new Date())
          }
        }, {
          text: '昨天',
          onClick(picker) {
            const date = new Date()
            date.setTime(date.getTime() - 3600 * 1000 * 24)
            picker.$emit('pick', date)
          }
        }, {
          text: '一周前',
          onClick(picker) {
            const date = new Date()
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', date)
          }
        }]
      },
      value1: '',
      value2: ''
    }
  },
  mounted() {
    // 向后端请求省份数据
    this.fetchProvinces()
  },
  methods: {
    // 请求所有省份数据并存储在provinces属性中
    fetchProvinces() {
      // 假设后端API返回的省份数据格式为{ id: number, name: string }数组
      getProvinces().then(response => {
        this.provinces = response.data
      }).catch(error => {
        console.error('Failed to fetch provinces:', error)
      })
    },
    handleProvinceChange() {
      this.$store.commit('user/SET_province', this.selectedProvince)
    },
    handleDatechange() {
      this.$store.commit('user/SET_date', this.value2.toISOString())
    }
  }
}

</script>
<style>
.el-carousel__item h3 {
  color: #475669;
  font-size: 18px;
  opacity: 0.75;
  line-height: 300px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}
</style>
