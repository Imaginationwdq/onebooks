<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="图标编号" prop="iconId">
      <el-input v-model="dataForm.iconId" placeholder="图标编号"></el-input>
    </el-form-item>
    <el-form-item label="菜单名称" prop="authName">
      <el-input v-model="dataForm.authName" placeholder="菜单名称"></el-input>
    </el-form-item>
    <el-form-item label="菜单层级(1:一级导航菜单,2:二级导航菜单,3:三级导航菜单)" prop="level">
      <el-input v-model="dataForm.level" placeholder="菜单层级(1:一级导航菜单,2:二级导航菜单,3:三级导航菜单)"></el-input>
    </el-form-item>
    <el-form-item label="菜单路径(顶级菜单的路径为空)" prop="path">
      <el-input v-model="dataForm.path" placeholder="菜单路径(顶级菜单的路径为空)"></el-input>
    </el-form-item>
    <el-form-item label="菜单上级编号(-1:最顶级)" prop="parent">
      <el-input v-model="dataForm.parent" placeholder="菜单上级编号(-1:最顶级)"></el-input>
    </el-form-item>
    <el-form-item label="菜单顶级编号(-1:最顶级)" prop="top">
      <el-input v-model="dataForm.top" placeholder="菜单顶级编号(-1:最顶级)"></el-input>
    </el-form-item>
    <el-form-item label="菜单状态(0无效,1:有效)" prop="status">
      <el-input v-model="dataForm.status" placeholder="菜单状态(0无效,1:有效)"></el-input>
    </el-form-item>
    <el-form-item label="排序" prop="sort">
      <el-input v-model="dataForm.sort" placeholder="排序"></el-input>
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
          menuId: 0,
          iconId: '',
          authName: '',
          level: '',
          path: '',
          parent: '',
          top: '',
          status: '',
          sort: ''
        },
        dataRule: {
          iconId: [
            { required: true, message: '图标编号不能为空', trigger: 'blur' }
          ],
          authName: [
            { required: true, message: '菜单名称不能为空', trigger: 'blur' }
          ],
          level: [
            { required: true, message: '菜单层级(1:一级导航菜单,2:二级导航菜单,3:三级导航菜单)不能为空', trigger: 'blur' }
          ],
          path: [
            { required: true, message: '菜单路径(顶级菜单的路径为空)不能为空', trigger: 'blur' }
          ],
          parent: [
            { required: true, message: '菜单上级编号(-1:最顶级)不能为空', trigger: 'blur' }
          ],
          top: [
            { required: true, message: '菜单顶级编号(-1:最顶级)不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '菜单状态(0无效,1:有效)不能为空', trigger: 'blur' }
          ],
          sort: [
            { required: true, message: '排序不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.menuId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.menuId) {
            this.$http({
              url: this.$http.adornUrl(`/onebook/menu/info/${this.dataForm.menuId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.iconId = data.menu.iconId
                this.dataForm.authName = data.menu.authName
                this.dataForm.level = data.menu.level
                this.dataForm.path = data.menu.path
                this.dataForm.parent = data.menu.parent
                this.dataForm.top = data.menu.top
                this.dataForm.status = data.menu.status
                this.dataForm.sort = data.menu.sort
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
              url: this.$http.adornUrl(`/onebook/menu/${!this.dataForm.menuId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'menuId': this.dataForm.menuId || undefined,
                'iconId': this.dataForm.iconId,
                'authName': this.dataForm.authName,
                'level': this.dataForm.level,
                'path': this.dataForm.path,
                'parent': this.dataForm.parent,
                'top': this.dataForm.top,
                'status': this.dataForm.status,
                'sort': this.dataForm.sort
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
