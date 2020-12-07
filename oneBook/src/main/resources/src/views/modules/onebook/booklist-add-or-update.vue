<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="图书编号" prop="bookId">
      <el-input v-model="dataForm.bookId" placeholder="图书编号"></el-input>
    </el-form-item>
    <el-form-item label="用户编号" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="用户编号"></el-input>
    </el-form-item>
    <el-form-item label="数量" prop="num">
      <el-input v-model="dataForm.num" placeholder="数量"></el-input>
    </el-form-item>
    <el-form-item label="状态(0:用户图书列表,1:购物车图书列表)" prop="status">
      <el-input v-model="dataForm.status" placeholder="状态(0:用户图书列表,1:购物车图书列表)"></el-input>
    </el-form-item>
    <el-form-item label="创建日期" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="创建日期"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          bookListId: 0,
          bookId: '',
          userId: '',
          num: '',
          status: '',
          createTime: ''
        },
        dataRule: {
          bookId: [
            { required: true, message: '图书编号不能为空', trigger: 'blur' }
          ],
          userId: [
            { required: true, message: '用户编号不能为空', trigger: 'blur' }
          ],
          num: [
            { required: true, message: '数量不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '状态(0:用户图书列表,1:购物车图书列表)不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '创建日期不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.bookListId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.bookListId) {
            this.$http({
              url: this.$http.adornUrl(`/onebook/booklist/info/${this.dataForm.bookListId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.bookId = data.bookList.bookId
                this.dataForm.userId = data.bookList.userId
                this.dataForm.num = data.bookList.num
                this.dataForm.status = data.bookList.status
                this.dataForm.createTime = data.bookList.createTime
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/onebook/booklist/${!this.dataForm.bookListId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'bookListId': this.dataForm.bookListId || undefined,
                'bookId': this.dataForm.bookId,
                'userId': this.dataForm.userId,
                'num': this.dataForm.num,
                'status': this.dataForm.status,
                'createTime': this.dataForm.createTime
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
