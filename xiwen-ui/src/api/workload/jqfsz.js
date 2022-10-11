import request from '@/utils/request'

// 查询加权分设置列表
export function listJqfsz(query) {
  return request({
    url: '/workload/jqfsz/list',
    method: 'get',
    params: query
  })
}

// 查询加权分设置详细
export function getJqfsz(id) {
  return request({
    url: '/workload/jqfsz/' + id,
    method: 'get'
  })
}

// 新增加权分设置
export function addJqfsz(data) {
  return request({
    url: '/workload/jqfsz',
    method: 'post',
    data: data
  })
}

// 修改加权分设置
export function updateJqfsz(data) {
  return request({
    url: '/workload/jqfsz',
    method: 'put',
    data: data
  })
}

// 删除加权分设置
export function delJqfsz(id) {
  return request({
    url: '/workload/jqfsz/' + id,
    method: 'delete'
  })
}
