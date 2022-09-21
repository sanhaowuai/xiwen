import request from '@/utils/request'

// 查询申报管理列表
export function listSbshgl(query) {
  return request({
    // url: '/workload/sbgl/getSbshList',
    url: '/workload/sbgl/list',
    method: 'get',
    params: query
  })
}
// 查询参与人员list
export function queryCyryList(query) {
  return request({
    url: '/workload/sbgl/queryCyryList',
    method: 'get',
    params: query
  })
}

// 查询申报管理详细
export function getSbgl(id) {
  return request({
    url: '/workload/sbgl/' + id,
    method: 'get'
  })
}
// 查询系统参数表是否可申报
export function getWfConfig(key) {
  return request({
    url: '/workload/ndgl/getWfConfig/' + key,
    method: 'get'
  })
}
// 查询申报管理详细
export function querySbcyryList(query) {
  return request({
    url: '/workload/sbgl/querySbcyryList',
    method: 'get',
    params: query
  })
}

// 新增申报管理
export function addSbgl(data) {
  return request({
    url: '/workload/sbgl',
    method: 'post',
    data: data
  })
}

// 获取个人信息
export function getUserByDlr() {
  return request({
    url: '/workload/sbgl/getUserByDlr',
    method: 'get'
  })
}


// 添加 修改申报管理
export function updateSbsh(data) {
  return request({
    url: '/workload/sbgl/updateSbsh',
    method: 'put',
    data: data
  })
}

// 删除申报管理
export function delSbgl(id) {
  return request({
    url: '/workload/sbgl/' + id,
    method: 'delete'
  })
}
