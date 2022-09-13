import request from '@/utils/request'

// 查询申报管理列表
export function listSbgl(query) {
  return request({
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

// 新增申报管理
export function addSbgl(data) {
  return request({
    url: '/workload/sbgl',
    method: 'post',
    data: data
  })
}

// 修改申报管理
export function updateSbgl(data) {
  return request({
    url: '/workload/sbgl',
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
