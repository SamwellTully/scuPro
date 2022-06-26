<template>
  <div class="app-container">
    <el-row
      :gutter="20"
      style="display: flex; align-items: center"
      type="flex"
      justify="center"
    >
      <el-col :span="9"
        ><div class="grid-content"><h3>未关联字段</h3></div></el-col
      >

      <el-col :span="2"><div class="grid-content"></div></el-col>

      <el-col :span="7"
        ><div class="grid-content">
          <h3>
            目标字段组<template>
              <el-select
                v-model="value"
                @change="handleSelectChange"
                style="margin-left: 10px"
              >
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </template>
          </h3></div
      ></el-col>
      <el-col :span="2"
        ><div class="grid-content">
          <el-button type="primary" @click="add" style="width: 90px">关联</el-button>
        </div></el-col
      >
      <el-col :span="2"
        ><div class="grid-content">
          <el-button
            type="primary"
            @click="showHistoricalMapping = true"
            style="width: 90px"
            >历史映射</el-button
          >
        </div></el-col
      >

      <el-dialog title="历史映射信息" :visible.sync="showHistoricalMapping">
        <el-table max-height="500" :data="gridData" :row-click="clickHistoricalMapping">
          <el-table-column type="expand">
            <template slot-scope="props">
              <el-form label-position="left" class="demo-table-expand">
                <el-form-item v-for="(key, value) in props.row.map" :key="key" label="">
                  <el-tag>{{ value }}</el-tag>
                  ==========
                  <el-tag>{{ key }}</el-tag>
                </el-form-item>
              </el-form>
            </template>
          </el-table-column>

          <el-table-column label="目标字段组" prop="name"> </el-table-column>

          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button @click="setMapping(scope.row.map)" size="mini">添加</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-dialog>
    </el-row>

    <el-row
      :gutter="20"
      style="display: flex; align-items: center"
      type="flex"
      justify="center"
    >
      <el-col :span="9"
        ><div class="grid-content">
          <template>
            <el-table
              ref="t1"
              :data="table1"
              border
              highlight-current-row
              @current-change="handleCurrentChange1"
              @row-click="rowclick1"
              style="width: 100%"
              height="230"
            >
              <el-table-column fixed prop="key" label="源字段">
                <template slot-scope="scope">
                  <el-tag
                    :type="scope.row.flag === 1 ? 'info' : ''"
                    disable-transitions
                    >{{ scope.row.key }}</el-tag
                  >
                </template>
              </el-table-column>
              <el-table-column prop="value" label="实例数据"> </el-table-column>
            </el-table>
          </template></div
      ></el-col>

      <el-col :span="2"><div class="grid-content"></div></el-col>

      <el-col :span="9"
        ><div class="grid-content">
          <template>
            <el-table
              ref="t2"
              :data="table2"
              border
              highlight-current-row
              @current-change="handleCurrentChange2"
              @row-click="rowclick2"
              style="width: 100%"
              height="230"
            >
              <el-table-column fixed prop="key" label="目标字段">
                <template slot-scope="scope">
                  <el-tag
                    :type="scope.row.flag === 1 ? 'info' : ''"
                    disable-transitions
                    >{{ scope.row.key }}</el-tag
                  >
                </template></el-table-column
              >
              <el-table-column fixed prop="value" label="字段描述">
              </el-table-column> </el-table
          ></template></div
      ></el-col>
    </el-row>

    <el-row
      :gutter="20"
      style="display: flex; align-items: center"
      type="flex"
      justify="center"
    >
      <el-col :span="20"
        ><div class="grid-content">
          <template>
            <h3>已关联字段</h3>
            <el-table
              :data="table3"
              border
              style="width: 100%"
              :row-key="getRowKeys"
              :expand-row-keys="expands"
              @expand-change="expandSelect"
            >
              <el-table-column prop="key" label="源字段"> </el-table-column>
              <el-table-column prop="value" label="目的字段"> </el-table-column>
              <el-table-column label="操作">
                <template slot-scope="scope">
                  <el-button
                    @click="clickbtn(scope.row)"
                    size="mini"
                    style="margin-right: 50px"
                    >替换</el-button
                  ><el-button
                    @click.native.prevent="deleteRow(scope.$index, table3)"
                    size="mini"
                    type="danger"
                  >
                    移除
                  </el-button>
                </template>
              </el-table-column>
              <el-table-column type="expand">
                <template slot-scope="props">
                  <el-card shadow="always">
                    <el-table
                      :data="matchingValue"
                      border
                      style="width: 100%"
                      :cell-style="{ background: '#FFFFFF' }"
                    >
                      <el-table-column prop="originalValue" label="源值">
                      </el-table-column>
                      <el-table-column prop="targetValue" label="目标值">
                      </el-table-column>
                      <el-table-column prop="right" label="操作">
                        <el-button
                          @click.native.prevent="deleteRow2(props.row)"
                          size="mini"
                          type="danger"
                        >
                          移除
                        </el-button>
                      </el-table-column>
                    </el-table>
                  </el-card>
                </template>
              </el-table-column>
            </el-table>
          </template>
        </div></el-col
      >
    </el-row>

    <el-dialog title="内容替换" :visible.sync="dialogFormVisible">
      <div v-if="currentRow3 != null">
        <h3>{{ currentRow3["key"] }}</h3>
        <el-form :model="form" :rules="rules" ref="ruleForm" :inline="true" size="small">
          <el-form-item label="源值" prop="key">
            <el-select v-model="form.key" placeholder="请选择源值">
              <el-option v-for="item in originalSeletionData" :label="item" :value="item">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="目标值" prop="value">
            <el-input
              v-if="!isMulSelection"
              v-model="form.value"
              clearable
              placeholder="请输入目标值"
            ></el-input>
            <el-select
              v-if="isMulSelection"
              v-model="form.value"
              placeholder="请选择目标值"
            >
              <el-option
                v-for="item in mulSelectionData"
                :label="item.enumeration"
                :value="item.enumeration"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
      </div>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click.native.prevent="replacement()">确 定</el-button>
      </div>
    </el-dialog>

    <el-row
      :gutter="20"
      style="display: flex; align-items: center"
      type="flex"
      justify="center"
    >
      <el-col :span="20"
        ><div class="grid-content">
          <template>
            <h3>内容替换</h3>
            <el-table :data="replaceField" border style="width: 100%">
              <el-table-column fixed prop="originalField" label="源字段">
              </el-table-column>
              <el-table-column fixed prop="originalValue" label="源值"> </el-table-column>
              <el-table-column prop="targetValue" label="目标值"> </el-table-column>
              <el-table-column fixed="right" label="操作">
                <template slot-scope="scope">
                  <el-button
                    @click.native.prevent="deleteRow2(scope.row)"
                    size="mini"
                    style="margin-left: 10px"
                    type="danger"
                  >
                    移除
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
          </template>
        </div></el-col
      >
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
          <el-button @click="replacementAll()" type="primary" style="width: 100%"
            >确定</el-button
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
</style>

<style>
.el-header {
  background-color: #b3c0d1;
  color: #333;
  line-height: 60px;
}

.el-aside {
  color: #333;
}
</style>

<style>
.el-table td,
.el-table th {
  text-align: center;
}
,
.el-table_header {
  table-layout: auto;
}
</style>

<style>
.el-table .warning-row {
  background: oldlace;
}

.el-table .success-row {
  background: #f0f9eb;
}
</style>

<script>
import qs from "qs";

export default {
  data() {
    return {
      gridData: [],
      showHistoricalMapping: false,
      showHistoricalMappingDetails: false,
      selectedHistoricalMappingDate: {},
      options: [],
      value: " ",

      table1: [],

      table2: [],

      table3: [],

      originalField: null,
      file: null,
      database: [],

      currentRow1: null,
      currentRow2: null,
      currentRow3: null,
      v1: 0,
      v2: 0,

      resource: "",

      dialogFormVisible: false,
      form: {
        key: "",
        value: "",
      },

      replaceField: [],

      replaceContent: null,
      relationMap: null,

      isMulSelection: false,
      mulSelectionData: [],

      rules: {
        key: [],
        value: [{ required: true, message: "请输入目标值", trigger: "blur" }],
      },

      fileOriginalData: null,
      fileSeletionData: {},
      originalSeletionData: [],

      expands: [],
      getRowKeys(row) {
        return row.key;
      },
      matchingValue: [],
    };
  },

  created() {
    //获取上传的数据
    this.originalField = this.$route.params.val;
    this.table1 = this.originalField;
    this.file = this.$route.params.file;
    this.fileOriginalData = this.$route.params.data;

    //初始化表格数据
    this.value = "脑出血";

    setTimeout(() => {
      //获取数据库中的表名
      this.getDatabase();
    }, 0);

    var x = null;
    setTimeout(() => {
      x = this.getData(this.value);
    }, 300);
    setTimeout(() => {
      this.table2 = x;
    }, 600);

    //刷新
    setTimeout(() => {
      this.refdata();
      // this.reftable1();
      // this.reftable2();
      this.resetTabele(this.table1);
      this.resetTabele(this.table2);
      this.addFlag();
    }, 900);

    setTimeout(() => {
      this.getFileSeletionData();
    }, 1000);

    setTimeout(() => {
      this.getHistoricalMapping();
      console.info(this.gridData);
    }, 1200);
  },

  methods: {
    clickHistoricalMapping(row, column, event) {
      this.selectedHistoricalMappingDate = row.map;
      this.showHistoricalMappingDetails = true;
    },
    getHistoricalMapping() {
      let data = new FormData();
      data.append("UserId", "1");
      data.append("Tablename", "test");

      this.$http.post(
        "http://8.134.49.56:8000/mappings/check?UserId=" + 1 + "&Tablename=" + "test"
      );
      this.$http.post("http://8.134.49.56:8000/mappings/check", data).then((res) => {
        console.info(res.data.data);
        let historicalMapping = res.data.data;
        let name = "test";

        for (const key in historicalMapping) {
          let obj = {};
          obj["name"] = name;
          obj["map"] = historicalMapping[key];

          this.gridData.push(obj);
        }
      });
    },

    setMapping(map) {
      this.table3 = [];
      this.replaceField = [];

      this.resetTabele(this.table1);
      this.resetTabele(this.table2);

      for (const key in map) {

        console.info(key);
        console.info(map[key]);

        for (let i = 0; i < this.table1.length; i++) {
          if (this.table1[i]["key"] === key) {
            this.table1[i]["flag"] = 1;
          }
        }

        for (let i = 0; i < this.table2.length; i++) {
          if (this.table2[i]["key"] === map[key]) {
            this.table2[i]["flag"] = 1;
          }
        }

        let obj = {};
        obj.key = key;
        obj.value = map[key];
        this.table3.push(obj);
      }

      this.refdata();
    },

    expandSelect(row, expandedRows) {
      this.matchingValue = [];
      if (expandedRows.length) {
        this.expands = [];
        if (row) {
          this.expands.push(row.key); // 每次push进去的是每行的ID

          if (this.replaceField.length) {
            for (let i = 0; i < this.replaceField.length; i++) {
              if (row.key === this.replaceField[i]["originalField"]) {
                this.matchingValue.push(this.replaceField[i]);
              }
            }
          }
        }
      } else {
        this.expands = []; // 默认不展开
      }
    },

    //增加标识位，保证已关联字段无法选中
    addFlag() {
      if (this.table1.length != 0 && this.table2.length != 0) {
        for (let i = 0; i < this.table1.length; i++) {
          for (let j = 0; j < this.table2.length; j++) {
            if (this.table1[i]["key"] === this.table2[j]["key"]) {
              let obj = {};
              obj.key = this.table1[i]["key"];
              obj.value = this.table2[j]["key"];
              this.table3.push(obj);
              this.table1[i]["flag"] = 1;
              this.table2[j]["flag"] = 1;
              break;
            } else {
              this.table1[i]["flag"] = 0;
              if (this.table2[j]["flag"] != 1) {
                this.table2[j]["flag"] = 0;
              }
            }
          }
        }
      }
      //刷新
      this.refdata();
    },

    handleCurrentChange1(val) {
      this.v1 = 0;
      if (val != null) {
        if (val.flag != null && val.flag == 0) {
          this.currentRow1 = val;
        } else {
          this.$refs.t1.setCurrentRow();
          this.currentRow1 = null;
        }
      }
    },
    handleCurrentChange2(val) {
      this.v2 = 0;
      if (val != null) {
        if (null != val.flag && val.flag == 0) {
          this.currentRow2 = val;
        } else {
          this.$refs.t2.setCurrentRow();
          this.currentRow2 = null;
          // console.info("wo2");
        }
      }
    },

    rowclick1(row, column, event) {
      this.v1 = this.v1 + 1;
      if (row === this.currentRow1 && this.v1 === 2) {
        this.currentRow1 = null;
        this.$refs.t1.setCurrentRow();
        this.v1 = 0;
      }
    },
    rowclick2(row, column, event) {
      this.v2 = this.v2 + 1;
      if (row === this.currentRow2 && this.v2 === 2) {
        this.currentRow2 = null;
        this.$refs.t2.setCurrentRow();
        this.v2 = 0;
      }
    },

    // 选择器切换表
    handleSelectChange(val) {
      this.value = val;
      this.table3 = [];
      this.replaceField = [];

      var x = null;
      setTimeout(() => {
        x = this.getData(this.value);
      }, 0);
      setTimeout(() => {
        this.table2 = x;
      }, 100);

      //刷新
      setTimeout(() => {
        this.refdata();
        // this.reftable1();
        // this.reftable2();
        this.resetTabele(this.table1);
        this.resetTabele(this.table2);
        this.addFlag();
      }, 200);

      if (this.currentRow1 != null && this.currentRow1.flag === 1) {
        this.$refs.t1.setCurrentRow();
        this.currentRow1 = null;
        this.v1 = 0;
      }
      this.$refs.t2.setCurrentRow();
      this.currentRow2 = null;
      this.v2 = 0;
    },

    //刷新数据，实时更新
    refdata() {
      var newTableData1 = [];
      var newTableData2 = [];
      for (var i = 0; i < this.table1.length; i++) {
        newTableData1.push(this.table1[i]);
      }
      for (var i = 0; i < this.table2.length; i++) {
        newTableData2.push(this.table2[i]);
      }
      this.table1 = newTableData1;
      this.table2 = newTableData2;
    },

    // //重置标识位，恢复全未选中状态
    // reftable1() {
    //   for (let i = 0; i < this.table1.length; i++) {
    //     this.table1[i]["flag"] = 0;
    //   }
    // },

    // //重置标识位，恢复全未选中状态
    // reftable2() {
    //   for (let i = 0; i < this.table2.length; i++) {
    //     this.table2[i]["flag"] = 0;
    //   }
    // },

    //重置标识位，恢复全未选中状态
    resetTabele(table) {
      for (let i = 0; i < table.length; i++) {
        table[i]["flag"] = 0;
      }
    },

    add() {
      if (this.currentRow1 !== null && this.currentRow2 !== null) {
        if (this.currentRow1["flag"] === 0 && this.currentRow2["flag"] === 0) {
          let obj = {};
          obj.key = this.currentRow1["key"];
          obj.value = this.currentRow2["key"];
          this.table3.push(obj);
          let index1 = this.table1.findIndex((item) => item["key"] === obj.key);
          this.table1[index1]["flag"] = 1;
          let index2 = this.table2.findIndex((item) => item["key"] === obj.value);
          this.table2[index2]["flag"] = 1;
        }
      } else {
        this.$alert("不能为空", "提示", {
          confirmButtonText: "确定",
          callback: (action) => {},
        });
      }

      //刷新
      this.$refs.t1.setCurrentRow();
      this.$refs.t2.setCurrentRow();
      this.currentRow1 = null;
      this.currentRow2 = null;
      this.v1 = 0;
      this.v2 = 0;
    },

    deleteRow(index, rows) {
      let index1 = this.table1.findIndex((item) => item["key"] === rows[index]["key"]);
      this.table1[index1]["flag"] = 0;
      let index2 = this.table2.findIndex((item) => item["key"] === rows[index]["value"]);
      this.table2[index2]["flag"] = 0;
      rows.splice(index, 1);

      //刷新
      var newTableData1 = [];
      var newTableData2 = [];
      for (var i = 0; i < this.table1.length; i++) {
        newTableData1.push(this.table1[i]);
      }
      for (var i = 0; i < this.table2.length; i++) {
        newTableData2.push(this.table2[i]);
      }
      this.table1 = newTableData1;
      this.table2 = newTableData2;
    },

    clickbtn(row) {
      this.currentRow3 = row;
      this.form["key"] = "";
      this.form["value"] = "";
      this.dialogFormVisible = true;
      if (this.$refs["ruleForm"] !== undefined) {
        this.$refs["ruleForm"].resetFields();
      }
      this.rules.value.splice(1, 1);
      this.originalSeletionData = this.fileSeletionData[row.key];

      //是否为空
      // this.rules.key.push({ required: true, message: "请输入源值", trigger: "blur" });
      // this.rules.value =

      let data = new FormData();
      data.append("tableName", this.value);
      data.append("columnName", row.value);

      this.$http.post("http://8.134.49.56:8000/G/IsNotEnume", data).then((res) => {
        this.isMulSelection = res.data;

        if (res.data) {
          this.$http
            .post("http://8.134.49.56:8000/G/IsNotEnumeData", data)
            .then((res) => {
              this.mulSelectionData = res.data.data;
            });
        } else {
          this.$http.post("http://8.134.49.56:8000/G/NotEnumeData", data).then((res) => {
            console.info(res.data.data.length !== 0);
            console.info(this.rules.value);
            if (res.data.data.length !== 0) {
              let obj = {};
              obj["min"] = res.data.data[0].lengthMin;
              obj["max"] = res.data.data[0].lengthMax;
              obj["message"] = "长度在 " + obj["min"] + " 到 " + obj["max"] + " 个字符";
              obj["trigger"] = "blur";
              this.rules.value.push(obj);
              console.info(this.rules.value);
            }
          });
        }
      });
    },

    getFileSeletionData() {
      let obj = {};

      for (let i = 0; i < this.originalField.length; i++) {
        obj[this.originalField[i]["key"]] = [];
        for (let j = 0; j < this.fileOriginalData.length; j++) {
          obj[this.originalField[i]["key"]].push(
            this.fileOriginalData[j][this.originalField[i]["key"]]
          );
        }
        obj[this.originalField[i]["key"]] = [
          ...new Set(obj[this.originalField[i]["key"]]),
        ];
      }
      this.fileSeletionData = obj;
    },

    replacement() {
      this.$refs["ruleForm"].validate((valid) => {
        if (valid) {
          this.dialogFormVisible = false;

          let obj = {};

          obj["originalField"] = this.currentRow3["key"];
          obj["targetField"] = this.currentRow3["value"];
          obj["originalValue"] = this.form["key"];
          obj["targetValue"] = this.form["value"];

          this.replaceField.push(obj);
          //新增
          if (this.expands.length > 0 && this.expands[0] === obj["originalField"]) {
            this.matchingValue.push(obj);
          }
        }
      });
    },

    deleteRow2(row) {
      console.info(row);
      let originalField = row["originalField"];
      let originalValue = row["originalValue"];
      let targetValue = row["targetValue"];
      console.info(originalField);
      console.info(originalValue);
      console.info(targetValue);

      let i = this.replaceField.findIndex(
        (item) =>
          item["originalField"] === originalField &&
          item["originalValue"] === originalValue &&
          item["targetValue"] === targetValue
      );
      this.replaceField.splice(i, 1);

      let j = this.matchingValue.findIndex(
        (item) =>
          item["originalField"] === originalField &&
          item["originalValue"] === originalValue &&
          item["targetValue"] === targetValue
      );
      this.matchingValue.splice(j, 1);
    },

    replacementAll() {
      let obj1 = {};
      let obj2 = {};

      for (let i = 0; i < this.table3.length; i++) {
        obj1[this.table3[i]["key"]] = this.table3[i]["value"];
      }

      if (this.replaceField.length > 0) {
        for (let i = 0; i < this.replaceField.length; i++) {
          obj2[this.replaceField[i]["originalField"]] = [];
        }

        for (let i = 0; i < this.replaceField.length; i++) {
          let obj = {};
          obj[this.replaceField[i]["originalValue"]] = this.replaceField[i][
            "targetValue"
          ];
          obj2[this.replaceField[i]["originalField"]].push(obj);
        }

        for (const key in obj2) {
          let val = {};
          val = Object.values(obj2[key]);
          console.log(val);

          let obj = {};
          for (const key2 in val) {
            console.log(Object.keys(val[key2])[0]);
            console.log(Object.values(val[key2])[0]);
            let k = Object.keys(val[key2])[0];
            let v = Object.values(val[key2])[0];
            obj[k] = v;
          }

          obj2[key] = obj;
        }
      }

      let data = new FormData();
      data.append("file", this.file);
      data.append("relationString", JSON.stringify(obj1));
      data.append("hashString", JSON.stringify(obj2));

      this.relationMap = JSON.stringify(obj1);

      this.$http.post("http://8.134.49.56:8000/G/relacement", data).then((res) => {
        this.replaceContent = res.data.data;

        let temp = this.replaceContent[0];
        let tableHeader = [];
        Object.keys(temp).some((key) => {
          tableHeader.push({ key: key });
        });

        this.$alert("替换成功", "提示", {
          confirmButtonText: "确定",
          callback: (action) => {
            if (action === "confirm") {
              this.$router.push({
                name: "Save",
                params: {
                  data: this.replaceContent,
                  map: obj1,
                  name: this.value,
                  header: tableHeader,
                },
              });
            }
          },
        });
      });
    },

    getDatabase() {
      this.$http.get("http://8.134.49.56:8000/G/getdatabase").then((res) => {
        for (var i = 0; i < res.data.length; i++) {
          let obj = {};
          obj.value = res.data[i]["Generaltable_name"];
          obj.label = res.data[i]["Generaltable_name"];
          this.database.push(obj);
        }
        this.options = this.database;
      });
    },

    getData(name) {
      var url = "http://8.134.49.56:8000/G/getdata";
      var params = "tableName=" + name;
      var xhr = new XMLHttpRequest();
      let list = [];
      xhr.open("POST", url, true);
      xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
      xhr.onload = function (e) {
        if (xhr.readyState === 4) {
          if (xhr.status === 200) {
            var data = xhr.responseText;
            var jsonResponse = JSON.parse(data);
            Object.keys(jsonResponse[0]).some((key) => {
              list.push({ key: key, value: jsonResponse[0][key] });
            });
          } else {
            console.error(xhr.statusText);
          }
        }
      };
      xhr.onerror = function (e) {
        console.error(xhr.statusText);
      };
      xhr.send(params);

      return list;
    },
  },
};
</script>
