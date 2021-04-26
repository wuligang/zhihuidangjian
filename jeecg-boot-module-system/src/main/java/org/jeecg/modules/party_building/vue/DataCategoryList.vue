<template>
  <a-card :bordered="false">
    
    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('分类表（资讯，微课堂，活动）')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :expandedRowKeys="expandedRowKeys"
        @change="handleTableChange"
        @expand="handleExpand"
        v-bind="tableProps">
        
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="uploadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleAddChild(record)">添加下级</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDeleteNode(record)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <dataCategory-modal ref="modalForm" @ok="modalFormOk"></dataCategory-modal>
  </a-card>
</template>

<script>

  import { getAction, deleteAction } from '@/api/manage'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import DataCategoryModal from './modules/DataCategoryModal'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  
  export default {
    name: "DataCategoryList",
    mixins:[JeecgListMixin],
    components: {
      DataCategoryModal
    },
    data () {
      return {
        description: '分类表（资讯，微课堂，活动）管理页面',
        // 表头
        columns: [
          {
            title:'类目名称',
            align:"left",
            dataIndex: 'name'
          },
          {
            title:'排序',
            align:"left",
            dataIndex: 'sort'
          },
          {
            title:'是否支持投稿',
            align:"left",
            dataIndex: 'isManuscript_dictText'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: "/party_building/dataCategory/rootList",
          childList: "/party_building/dataCategory/childList",
          delete: "/party_building/dataCategory/delete",
          deleteBatch: "/party_building/dataCategory/deleteBatch",
          exportXlsUrl: "/party_building/dataCategory/exportXls",
          importExcelUrl: "party_building/dataCategory/importExcel",
        },
        expandedRowKeys:[],
        hasChildrenField:"hasChild",
        pidField:"pid",
        dictOptions: {},
        loadParent: false
      }
    },
    computed: {
      importExcelUrl(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
      tableProps() {
        let _this = this
        return {
          // 列表项是否可选择
          rowSelection: {
            selectedRowKeys: _this.selectedRowKeys,
            onChange: (selectedRowKeys) => _this.selectedRowKeys = selectedRowKeys
          }
        }
      }
    },
    methods: {
      loadData(arg){
        if(arg==1){
          this.ipagination.current=1
        }
        this.loading = true
        this.expandedRowKeys = []
        let params = this.getQueryParams()
        return new Promise((resolve) => {
          getAction(this.url.list,params).then(res=>{
            if(res.success){
              let result = res.result
              if(Number(result.total)>0){
                this.ipagination.total = Number(result.total)
                this.dataSource = this.getDataByResult(res.result.records)
                resolve()
              }else{
                this.ipagination.total=0
                this.dataSource=[]
              }
            }else{
              this.$message.warning(res.message)
            }
            this.loading = false
          })
        })
      },
      getDataByResult(result){
        return result.map(item=>{
          //判断是否标记了带有子节点
          if(item[this.hasChildrenField]=='1'){
            let loadChild = { id: item.id+'_loadChild', name: 'loading...', isLoading: true }
            item.children = [loadChild]
          }
          return item
        })
      },
      handleExpand(expanded, record){
        // 判断是否是展开状态
        if (expanded) {
          this.expandedRowKeys.push(record.id)
          if (record.children.length>0 && record.children[0].isLoading === true) {
            let params = this.getQueryParams();//查询条件
            params[this.pidField] = record.id
            getAction(this.url.childList,params).then((res)=>{
              if(res.success){
                if(res.result && res.result.length>0){
                  record.children = this.getDataByResult(res.result)
                  this.dataSource = [...this.dataSource]
                }else{
                  record.children=''
                  record.hasChildrenField='0'
                }
              }else{
                this.$message.warning(res.message)
              }
            })
          }
        }else{
          let keyIndex = this.expandedRowKeys.indexOf(record.id)
          if(keyIndex>=0){
            this.expandedRowKeys.splice(keyIndex, 1);
          }
        }
      },
      initDictConfig(){
      },
      modalFormOk(formData,arr){
        if(!formData.id){
          this.addOk(formData,arr)
        }else{
          if(!arr){
            this.loadData()
          }else{
            this.editOk(formData,this.dataSource)
            this.dataSource=[...this.dataSource]
          }
        }
      },
      editOk(formData,arr){
        if(arr && arr.length>0){
          for(let i=0;i<arr.length;i++){
            if(arr[i].id==formData.id){
              arr[i]=formData
              break
            }else{
              this.editOk(formData,arr[i].children)
            }
          }
        }
      },
      async addOk(formData,arr){
        if(!formData[this.pidField]){
          this.loadData()
        }else{
          if(this.loadParent===true){
            this.expandTreeNode(formData[this.pidField])
            this.loadParent = false
          }else{
            this.expandedRowKeys=[]
            for(let i of arr){
              await this.expandTreeNode(i)
            }
          }
        }
      },
      expandTreeNode(nodeId){
        return new Promise((resolve,reject)=>{
          this.getFormDataById(nodeId,this.dataSource)
          let row = this.parentFormData
          this.expandedRowKeys.push(nodeId)
          let params = this.getQueryParams();//查询条件
          params[this.pidField] = nodeId
          getAction(this.url.childList,params).then((res)=>{
            if(res.success){
              if(res.result && res.result.length>0){
                row.children = this.getDataByResult(res.result)
              }else{
                row.children=''
              }
              this.dataSource = [...this.dataSource]
              resolve()
            }else{
              reject()
            }
          })
        })
      },
      getFormDataById(id,arr){
        if(arr && arr.length>0){
          for(let i=0;i<arr.length;i++){
            if(arr[i].id==id){
              this.parentFormData = arr[i]
            }else{
              this.getFormDataById(id,arr[i].children)
            }
          }
        }
      },
      handleAddChild(record){
        this.loadParent = true
        let obj = {}
        obj[this.pidField] = record['id']
        this.$refs.modalForm.add(obj);
      },
      handleDeleteNode(record) {
        if(!this.url.delete){
          this.$message.error("请设置url.delete属性!")
          return
        }
        var that = this;
        deleteAction(that.url.delete, {id: record.id}).then((res) => {
          if (res.success) {
            if(!record[this.pidField] || record[this.pidField] =='0' || record[this.pidField].length==0){
              that.loadData(1)
            }else{
              that.$message.success(res.message);
              that.expandTreeNode(record[this.pidField]);
            }
          } else {
            that.$message.warning(res.message);
          }
        });
      }

    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>