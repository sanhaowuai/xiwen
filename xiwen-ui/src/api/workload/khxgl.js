import request from '@/utils/request'

// 查询考核项管理列表
export function listKhxglZs(query) {
  return request({
    url: '/workload/khxgl/getKhxlist',
    method: 'get',
    params: query
  })
}

// 查询考核项管理列表
export function listKhxgl(query) {
  return request({
    url: '/workload/khxgl/list',
    method: 'get',
    params: query
  })
}

// 查询考核项管理详细
export function getKhxgl(id) {
  return request({
    url: '/workload/khxgl/' + id,
    method: 'get'
  })
}

// 新增考核项管理
export function addKhxgl(data) {
  return request({
    url: '/workload/khxgl',
    method: 'post',
    data: data
  })
}

// 修改考核项管理
export function updateKhxgl(data) {
  return request({
    url: '/workload/khxgl',
    method: 'put',
    data: data
  })
}

// 删除考核项管理
export function delKhxgl(id) {
  return request({
    url: '/workload/khxgl/' + id,
    method: 'delete'
  })
}
