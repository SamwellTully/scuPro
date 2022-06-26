<template>
  <div class="app-container">
    <el-row
      :gutter="20"
      style="display: flex; align-items: center"
      type="flex"
      justify="center"
    >
      <el-col :span="20"
        ><div class="grid-content">
          <h3>保存内容</h3>
          <el-table :data="replaceContent" border style="width: 100%" height="500">
            <template v-for="(item, index) in tableHeader" >
              <el-table-column
                :label="item.key"
                :property="item.key"
                :key="index"
              ></el-table-column>
            </template>
          </el-table></div
      ></el-col>
    </el-row>

    <el-row
      :gutter="20"
      style="display: flex; align-items: center"
      type="flex"
      justify="center"
    >
      <el-col :span="6"><div class="grid-content"></div></el-col>

      <el-col :span="6"
        ><div class="grid-content">
          <el-button @click="saveData()" type="primary" style="width: 100%"
            >保存</el-button
          >
        </div></el-col
      >
      <el-col :span="6"><div class="grid-content"></div></el-col>
    </el-row>
  </div>
</template>

<style>
.el-row {
  margin-bottom: 20px;
}
.el-col {
  border-radius: 4px;
}
.bg-purple-dark {
  background: #99a9bf;
}
.bg-purple {
  background: #d3dce6;
}
.bg-purple-light {
  background: #e5e9f2;
}
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}
.el-header {
  background-color: #b3c0d1;
  color: #333;
  line-height: 60px;
}
.el-aside {
  color: #333;
}
.el-table td,
.el-table th {
  text-align: center;
}
.el-table .warning-row {
  background: oldlace;
}

.el-table .success-row {
  background: #f0f9eb;
}
</style>

<script>
export default {
  data() {
    return {
      replaceContent: null,
      relationMap: null,
      tableName: null,
      tableHeader: [],
    };
  },

  created() {
    this.replaceContent = this.$route.params.data;
    this.relationMap = this.$route.params.map;
    this.tableName = this.$route.params.name;
    this.tableHeader = this.$route.params.header;

    console.info(this.replaceContent);
    console.info(this.relationMap);
    console.info(this.tableName);
  },

  methods: {
    saveData() {
    //   let data = new FormData();
    //   data.append("data", JSON.stringify(this.replaceContent));
    //   data.append("relationMap", this.relationMap);
    //   data.append("tableName", this.tableName);

      let data = {};
      data["data"]=this.replaceContent;
      data["relationMap"]= this.relationMap;
      data["tableName"]=this.tableName;
      console.info(JSON.stringify(data));

    //   this.$axios({
    //       methods:'post',
    //       url:'http://8.134.49.56:8000/save/saveData',
    //       data: data,
    //       header:{
    //           'Content-Type': 'application/json;charset=UTF-8'
    //       }
    //   }).then((res)=>{
    //       this.$alert("保存成功", "提示", {
    //       confirmButtonText: "确定",
    //       callback: (action) => {},
    //     });
    //   });

      this.$http.post("http://8.134.49.56:8000/save/saveData", JSON.stringify(data)).then((res) => {
        this.$alert("保存成功", "提示", {
          confirmButtonText: "确定",
          callback: (action) => {},
        });
      });
    },
  },
};
</script>
