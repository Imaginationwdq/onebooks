<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="角色名称" prop="name">
      <el-input v-model="dataForm.name" placeholder="角色名称"></el-input>
    </el-form-item>
    <el-form-item label="角色状态(0:无效,1:有效)" prop="status">
      <el-input v-model="dataForm.status" placeholder="角色状态(0:无效,1:有效)"></el-input>
    </el-form-item>
    <el-form-item label="角色描述" prop="remake">
      <el-input v-model="dataForm.remake" placeholder="角色描述"></el-input>
    </el-form-item>
    <el-form-item label="创建日期" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="创建日期"></el-input>
    </el-form-item>
    <el-form-item label="更新日期" prop="updateTime">
      <el-input v-model="dataForm.updateTime" placeholder="更新日期"></el-input>
    </el-form-item>
    <el-form-item label="更新账号" prop="updateAccount">
      <el-input v-model="dataForm.updateAccount" placeholder="更新账号"></el-input>
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
          roleId: 0,
          name: '',
          status: '',
          remake: '',
          createTime: '',
          updateTime: '',
          updateAccount: ''
        },
        dataRule: {
          name: [
            { required: true, message: '角色名称不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '角色状态(0:无效,1:有效)不能为空', trigger: 'blur' }
          ],
          remake: [
            { required: true, message: '角色描述不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '创建日期不能为空', trigger: 'blur' }
          ],
          updateTime: [
            { required: true, message: '更新日期不能为空', trigger: 'blur' }
          ],
          updateAccount: [
            { required: true, message: '更新账号不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.roleId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.roleId) {
            this.$http({
              url: this.$http.adornUrl(`/onebook/role/info/${this.dataForm.roleId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.name = data.role.name
                this.dataForm.status = data.role.status
                this.dataForm.remake = data.role.remake
                this.dataForm.createTime = data.role.createTime
                this.dataForm.updateTime = data.role.updateTime
                this.dataForm.updateAccount = data.role.updateAccount
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
              url: this.$http.adornUrl(`/onebook/role/${!this.dataForm.roleId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'roleId': this.dataForm.roleId || undefined,
                'name': this.dataForm.name,
                'status': this.dataForm.status,
                'remake': this.dataForm.remake,
                'createTime': this.dataForm.createTime,
                'updateTime': this.dataForm.updateTime,
                'updateAccount': this.dataForm.updateAccount
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
