<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="按钮名称" prop="name">
      <el-input v-model="dataForm.name" placeholder="按钮名称"></el-input>
    </el-form-item>
    <el-form-item label="点击次数" prop="num">
      <el-input v-model="dataForm.num" placeholder="点击次数"></el-input>
    </el-form-item>
    <el-form-item label="日期" prop="time">
      <el-input v-model="dataForm.time" placeholder="日期"></el-input>
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
          mouseId: 0,
          name: '',
          num: '',
          time: ''
        },
        dataRule: {
          name: [
            { required: true, message: '按钮名称不能为空', trigger: 'blur' }
          ],
          num: [
            { required: true, message: '点击次数不能为空', trigger: 'blur' }
          ],
          time: [
            { required: true, message: '日期不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.mouseId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.mouseId) {
            this.$http({
              url: this.$http.adornUrl(`/onebook/mouse/info/${this.dataForm.mouseId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.name = data.mouse.name
                this.dataForm.num = data.mouse.num
                this.dataForm.time = data.mouse.time
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
              url: this.$http.adornUrl(`/onebook/mouse/${!this.dataForm.mouseId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'mouseId': this.dataForm.mouseId || undefined,
                'name': this.dataForm.name,
                'num': this.dataForm.num,
                'time': this.dataForm.time
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
