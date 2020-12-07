<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="角色编号" prop="roleId">
      <el-input v-model="dataForm.roleId" placeholder="角色编号"></el-input>
    </el-form-item>
    <el-form-item label="角色名称" prop="roleName">
      <el-input v-model="dataForm.roleName" placeholder="角色名称"></el-input>
    </el-form-item>
    <el-form-item label="账号" prop="username">
      <el-input v-model="dataForm.username" placeholder="账号"></el-input>
    </el-form-item>
    <el-form-item label="密码" prop="password">
      <el-input v-model="dataForm.password" placeholder="密码"></el-input>
    </el-form-item>
    <el-form-item label="用户名" prop="name">
      <el-input v-model="dataForm.name" placeholder="用户名"></el-input>
    </el-form-item>
    <el-form-item label="年龄" prop="age">
      <el-input v-model="dataForm.age" placeholder="年龄"></el-input>
    </el-form-item>
    <el-form-item label="性别" prop="sex">
      <el-input v-model="dataForm.sex" placeholder="性别"></el-input>
    </el-form-item>
    <el-form-item label="出生日期" prop="birthday">
      <el-input v-model="dataForm.birthday" placeholder="出生日期"></el-input>
    </el-form-item>
    <el-form-item label="状态(0:失效,1:有效)" prop="status">
      <el-input v-model="dataForm.status" placeholder="状态(0:失效,1:有效)"></el-input>
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
          userId: 0,
          roleId: '',
          roleName: '',
          username: '',
          password: '',
          name: '',
          age: '',
          sex: '',
          birthday: '',
          status: '',
          createTime: '',
          updateTime: '',
          updateAccount: ''
        },
        dataRule: {
          roleId: [
            { required: true, message: '角色编号不能为空', trigger: 'blur' }
          ],
          roleName: [
            { required: true, message: '角色名称不能为空', trigger: 'blur' }
          ],
          username: [
            { required: true, message: '账号不能为空', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '密码不能为空', trigger: 'blur' }
          ],
          name: [
            { required: true, message: '用户名不能为空', trigger: 'blur' }
          ],
          age: [
            { required: true, message: '年龄不能为空', trigger: 'blur' }
          ],
          sex: [
            { required: true, message: '性别不能为空', trigger: 'blur' }
          ],
          birthday: [
            { required: true, message: '出生日期不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '状态(0:失效,1:有效)不能为空', trigger: 'blur' }
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
        this.dataForm.userId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.userId) {
            this.$http({
              url: this.$http.adornUrl(`/onebook/users/info/${this.dataForm.userId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.roleId = data.users.roleId
                this.dataForm.roleName = data.users.roleName
                this.dataForm.username = data.users.username
                this.dataForm.password = data.users.password
                this.dataForm.name = data.users.name
                this.dataForm.age = data.users.age
                this.dataForm.sex = data.users.sex
                this.dataForm.birthday = data.users.birthday
                this.dataForm.status = data.users.status
                this.dataForm.createTime = data.users.createTime
                this.dataForm.updateTime = data.users.updateTime
                this.dataForm.updateAccount = data.users.updateAccount
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
              url: this.$http.adornUrl(`/onebook/users/${!this.dataForm.userId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'userId': this.dataForm.userId || undefined,
                'roleId': this.dataForm.roleId,
                'roleName': this.dataForm.roleName,
                'username': this.dataForm.username,
                'password': this.dataForm.password,
                'name': this.dataForm.name,
                'age': this.dataForm.age,
                'sex': this.dataForm.sex,
                'birthday': this.dataForm.birthday,
                'status': this.dataForm.status,
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
