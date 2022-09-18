import request from '@/utils/request'

// 查询年度管理列表
export function listNdgl(query) {
  return request({
    url: '/workload/ndgl/list',
    method: 'get',
    params: query
  })
}

// 查询正式列表
export function getNdList(query) {
  return request({
    url: '/workload/ndgl/getNdList',
    method: 'get',
    params: query
  })
}

// 查询年度管理详细
export function getNdgl(id) {
  return request({
    url: '/workload/ndgl/' + id,
    method: 'get'
  })
}

// 新增年度管理
export function addNdgl(data) {
  return request({
    url: '/workload/ndgl',
    method: 'post',
    data: data
  })
}

// 修改年度管理
export function updateNdgl(data) {
  return request({
    url: '/workload/ndgl',
    method: 'put',
    data: data
  })
}

// 删除年度管理
export function delNdgl(id) {
  return request({
    url: '/workload/ndgl/' + id,
    method: 'delete'
  })
}
