<template>
  <div class="app-container">
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column align="center" label="ID" width="95">
        <template slot-scope="scope">
          {{ scope.$index }}
        </template>
      </el-table-column>
      <el-table-column label="文件名称">
        <template slot-scope="scope">
          {{ scope.row.gtalename }}
        </template>
      </el-table-column>

      <el-table-column label="文件描述">
        <template slot-scope="scope">
          {{ scope.row.gtabledescription }}
        </template>
      </el-table-column>


      <el-table-column label="操作1" width="110" align="center">
        <template slot-scope="scope">
          <el-button type="primary" @click="open(scope.row.gtalename)">查看</el-button>
        </template>
        <el-dialog title="详情" :visible.sync="dialogTableVisible" :append-to-body="true">
          <el-button @click="exportToFile">选择导出</el-button>
          <el-checkbox-group v-model="exportList">
            <el-checkbox v-for="(fieldkey, index) in fieldKeys" :key="index" :label="fieldkey"></el-checkbox>
          </el-checkbox-group>
          <el-table v-loading="loadingTable" :data="gridData" height="600">
            <el-table-column v-for="(fieldkey, index) in fieldKeys" :key="index" :property="fieldkey" :prop="fieldkey" :label="fieldkey"></el-table-column>
          </el-table>
        </el-dialog>
      </el-table-column>

      <el-table-column class-name="status-col" label="操作2" width="110" align="center">
        <el-button type="danger">删除</el-button>
      </el-table-column>

      <el-table-column class-name="status-col" label="操作3" width="110" align="center">
        <el-button @click="jumpForm()" >上传</el-button>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { getList } from '@/api/table'


export default {
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'gray',
        deleted: 'danger'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      list: null,
      listLoading: true,
      saveway: '',
      inventoryCode: '',
      loadingTable: false,
      gridData: [{
        
      }],
      dialogTableVisible: false,
      dialogFormVisible: false,
      formLabelWidth: '120px',
      exportList: [],
      currentTableName: "", // 当前表名
      fieldKeys: [], // 所有字段
    }
  },
  async created() {
    await this.fetchData()
    console.log("===========")
    console.log(this.list)
  },
  methods: {
    exportToFile(){
      let fields = JSON.stringify(this.exportList);
      let resultFields = "%5B"
      resultFields += fields.slice(1, fields.length - 1)
      resultFields += "%5D"
      console.log(resultFields)
      
      window.location.href = 'http://8.134.49.56:8000/EXPORT/download?tableNames=' + this.currentTableName + '&filedNames=' + resultFields
      
    },
    async fetchData() {
      this.listLoading = true
      this.list = (await getList()).data
      this.listLoading = false
    },
    jumpForm(info) {
      this.$router.push('/example/publish', 1)
    },
    open: function(tName) {
      this.currentTableName = tName
      this.dialogTableVisible = true
      this.loadingTable = true

      // 首先获取列名
      let getDataParams = new URLSearchParams();
      getDataParams.append("tableName", tName);
      let allKeys = []
      this.$axios({
        method: 'post',
        url: "http://8.134.49.56:8000/G/getdata",
        data: getDataParams
      }).then((response) => {
        allKeys = Object.keys(response.data[0]) // response.data返回的是个数组，里面只有一个元素，为该表的示范数据（键值对），通过keys()获得所有键
        this.fieldKeys = allKeys // 必须在里面赋值
      })
      console.log(this.fieldKeys)

      // 获取表格数据
      let params = new URLSearchParams();
      params.append("tableName", tName);
      this.$axios({
         method: 'post',
         url: "http://8.134.49.56:8000/G/GetAllData",
         data: params
       }).then((response) => {
         console.log(response.data.data)
         this.gridData = response.data.data
         console.log(this.gridData)
       })
      this.loadingTable = false // 加载成功
     }
  }
}
</script>
