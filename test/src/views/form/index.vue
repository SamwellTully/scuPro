<template>
  <div class="app-container">
    <el-form ref="form" :model="form" label-width="120px">
      <el-form-item label="机构名称">
        <el-input  />
      </el-form-item>
            <el-form-item label="法人机构代码">
        <el-input />
      </el-form-item>
                  <el-form-item label="法人机构类型">
        <el-input />
      </el-form-item>
                  <el-form-item label="保存机构简介">
        <el-input />
      </el-form-item>
                  <el-form-item label="通讯地址">
        <el-input />
      </el-form-item>
                  <el-form-item label="邮政编码">
        <el-input />
      </el-form-item>
                  <el-form-item label="联系人姓名">
        <el-input />
      </el-form-item>
                  <el-form-item label="联系人电话">
        <el-input />
      </el-form-item>
                  <el-form-item label="联系人邮箱">
        <el-input />
      </el-form-item>
      <el-form-item label="权限申请">
        <el-select v-model="form.region" placeholder="请选择您要申请的权限">
          <el-option label="一级用户" value="shanghai" />
          <el-option label="二级用户" value="beijing" />
          <el-option label="三级用户" value="beijing" />
        </el-select>
      </el-form-item>
     <el-button type="primary" class="submit">提交修改</el-button>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      form: {

      }
    }
  },
  methods: {
    onSubmit() {
      this.$message('submit!')
    },
    onCancel() {
      this.$message({
        message: 'cancel!',
        type: 'warning'
      })
    }
  }
}
</script>

<style scoped>
.line{
  text-align: center;
}
.submit{
  position: relative;
  left: 50%;
  transform: translateX(-50%);
}
</style>

