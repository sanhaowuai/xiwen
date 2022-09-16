import request from '@/utils/request'

// 查询人员考核分数列表
export function listKhfsb(query) {
  return request({
    url: '/workload/khfsb/list',
    method: 'get',
    params: query
  })
}

// 查询人员考核分数详细
export function getKhfsb(id) {
  return request({
    url: '/workload/khfsb/' + id,
    method: 'get'
  })
}

// 新增人员考核分数
export function addKhfsb(data) {
  return request({
    url: '/workload/khfsb',
    method: 'post',
    data: data
  })
}

// 修改人员考核分数
export function updateKhfsb(data) {
  return request({
    url: '/workload/khfsb',
    method: 'put',
    data: data
  })
}

// 删除人员考核分数
export function delKhfsb(id) {
  return request({
    url: '/workload/khfsb/' + id,
    method: 'delete'
  })
}
