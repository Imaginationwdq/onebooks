<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="书名" prop="title">
      <el-input v-model="dataForm.title" placeholder="书名"></el-input>
    </el-form-item>
    <el-form-item label="ISBN" prop="isbn">
      <el-input v-model="dataForm.isbn" placeholder="ISBN"></el-input>
    </el-form-item>
    <el-form-item label="作者" prop="author">
      <el-input v-model="dataForm.author" placeholder="作者"></el-input>
    </el-form-item>
    <el-form-item label="出版社" prop="publisher">
      <el-input v-model="dataForm.publisher" placeholder="出版社"></el-input>
    </el-form-item>
    <el-form-item label="图书标价" prop="price">
      <el-input v-model="dataForm.price" placeholder="图书标价"></el-input>
    </el-form-item>
    <el-form-item label="用户定价" prop="userPrice">
      <el-input v-model="dataForm.userPrice" placeholder="用户定价"></el-input>
    </el-form-item>
    <el-form-item label="描述" prop="remake">
      <el-input v-model="dataForm.remake" placeholder="描述"></el-input>
    </el-form-item>
    <el-form-item label="状态(0:无效,1:上架, 2:下架,3:已卖出）" prop="status">
      <el-input v-model="dataForm.status" placeholder="状态(0:无效,1:上架, 2:下架,3:已卖出）"></el-input>
    </el-form-item>
    <el-form-item label="正面照片" prop="frontimg">
      <el-input v-model="dataForm.frontimg" placeholder="正面照片"></el-input>
    </el-form-item>
    <el-form-item label="反面照片" prop="backimg">
      <el-input v-model="dataForm.backimg" placeholder="反面照片"></el-input>
    </el-form-item>
    <el-form-item label="侧面照片" prop="sideimg">
      <el-input v-model="dataForm.sideimg" placeholder="侧面照片"></el-input>
    </el-form-item>
    <el-form-item label="创建日期" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="创建日期"></el-input>
    </el-form-item>
    <el-form-item label="更新日期" prop="updateTime">
      <el-input v-model="dataForm.updateTime" placeholder="更新日期"></el-input>
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
          bookId: 0,
          title: '',
          isbn: '',
          author: '',
          publisher: '',
          price: '',
          userPrice: '',
          remake: '',
          status: '',
          frontimg: '',
          backimg: '',
          sideimg: '',
          createTime: '',
          updateTime: ''
        },
        dataRule: {
          title: [
            { required: true, message: '书名不能为空', trigger: 'blur' }
          ],
          isbn: [
            { required: true, message: 'ISBN不能为空', trigger: 'blur' }
          ],
          author: [
            { required: true, message: '作者不能为空', trigger: 'blur' }
          ],
          publisher: [
            { required: true, message: '出版社不能为空', trigger: 'blur' }
          ],
          price: [
            { required: true, message: '图书标价不能为空', trigger: 'blur' }
          ],
          userPrice: [
            { required: true, message: '用户定价不能为空', trigger: 'blur' }
          ],
          remake: [
            { required: true, message: '描述不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '状态(0:无效,1:上架, 2:下架,3:已卖出）不能为空', trigger: 'blur' }
          ],
          frontimg: [
            { required: true, message: '正面照片不能为空', trigger: 'blur' }
          ],
          backimg: [
            { required: true, message: '反面照片不能为空', trigger: 'blur' }
          ],
          sideimg: [
            { required: true, message: '侧面照片不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '创建日期不能为空', trigger: 'blur' }
          ],
          updateTime: [
            { required: true, message: '更新日期不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.bookId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.bookId) {
            this.$http({
              url: this.$http.adornUrl(`/onebook/bookdetail/info/${this.dataForm.bookId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.title = data.bookDetail.title
                this.dataForm.isbn = data.bookDetail.isbn
                this.dataForm.author = data.bookDetail.author
                this.dataForm.publisher = data.bookDetail.publisher
                this.dataForm.price = data.bookDetail.price
                this.dataForm.userPrice = data.bookDetail.userPrice
                this.dataForm.remake = data.bookDetail.remake
                this.dataForm.status = data.bookDetail.status
                this.dataForm.frontimg = data.bookDetail.frontimg
                this.dataForm.backimg = data.bookDetail.backimg
                this.dataForm.sideimg = data.bookDetail.sideimg
                this.dataForm.createTime = data.bookDetail.createTime
                this.dataForm.updateTime = data.bookDetail.updateTime
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
              url: this.$http.adornUrl(`/onebook/bookdetail/${!this.dataForm.bookId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'bookId': this.dataForm.bookId || undefined,
                'title': this.dataForm.title,
                'isbn': this.dataForm.isbn,
                'author': this.dataForm.author,
                'publisher': this.dataForm.publisher,
                'price': this.dataForm.price,
                'userPrice': this.dataForm.userPrice,
                'remake': this.dataForm.remake,
                'status': this.dataForm.status,
                'frontimg': this.dataForm.frontimg,
                'backimg': this.dataForm.backimg,
                'sideimg': this.dataForm.sideimg,
                'createTime': this.dataForm.createTime,
                'updateTime': this.dataForm.updateTime
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
