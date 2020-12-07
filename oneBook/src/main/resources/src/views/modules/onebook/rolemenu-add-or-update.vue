<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="角色编号" prop="roleId">
      <el-input v-model="dataForm.roleId" placeholder="角色编号"></el-input>
    </el-form-item>
    <el-form-item label="权限编号" prop="menuId">
      <el-input v-model="dataForm.menuId" placeholder="权限编号"></el-input>
    </el-form-item>
    <el-form-item label="操作人账号" prop="updateAccount">
      <el-input v-model="dataForm.updateAccount" placeholder="操作人账号"></el-input>
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
          roleMenuId: 0,
          roleId: '',
          menuId: '',
          updateAccount: ''
        },
        dataRule: {
          roleId: [
            { required: true, message: '角色编号不能为空', trigger: 'blur' }
          ],
          menuId: [
            { required: true, message: '权限编号不能为空', trigger: 'blur' }
          ],
          updateAccount: [
            { required: true, message: '操作人账号不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.roleMenuId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.roleMenuId) {
            this.$http({
              url: this.$http.adornUrl(`/onebook/rolemenu/info/${this.dataForm.roleMenuId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.roleId = data.roleMenu.roleId
                this.dataForm.menuId = data.roleMenu.menuId
                this.dataForm.updateAccount = data.roleMenu.updateAccount
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
              url: this.$http.adornUrl(`/onebook/rolemenu/${!this.dataForm.roleMenuId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'roleMenuId': this.dataForm.roleMenuId || undefined,
                'roleId': this.dataForm.roleId,
                'menuId': this.dataForm.menuId,
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
