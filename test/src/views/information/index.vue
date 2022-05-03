<template>

<div class="all">
    <div class="tip">
        <h3>上传文件提示</h3>
        姓名,性别,医院代码,医院名称,医院住址,医院等级...........................
        </div>
        <el-upload
  class="upload-demo"
  drag
  action="https://jsonplaceholder.typicode.com/posts/"
  multiple
  style="margin-top:100px"
  >
  <i class="el-icon-upload"></i>
  <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
  <div class="el-upload__tip" slot="tip">只能上传excle文件</div>
</el-upload>
    </div>

</template>

<script>
  export default {
    data () {
      return {
        size: ''
      };
    }
  }
</script>

<style scoped>

.all{
    height: 100%;
    width: 100%;
    display: flex;
    flex-direction:column;
    align-items: center;
}
.tip{
width: 400px;
background-color: rgb(242, 241, 241);
padding: 20px;
border-radius: 6px;
}

</style>