import request from '@/utils/request'

// 查询人员考核分数详情列表
export function listKhfsxqb(query) {
  return request({
    url: '/workload/Khfsxqb/list',
    method: 'get',
    params: query
  })
}

// 查询人员考核分数详情详细
export function getKhfsxqb(id) {
  return request({
    url: '/workload/Khfsxqb/' + id,
    method: 'get'
  })
}

// 新增人员考核分数详情
export function addKhfsxqb(data) {
  return request({
    url: '/workload/Khfsxqb',
    method: 'post',
    data: data
  })
}

// 修改人员考核分数详情
export function updateKhfsxqb(data) {
  return request({
    url: '/workload/Khfsxqb',
    method: 'put',
    data: data
  })
}

// 删除人员考核分数详情
export function delKhfsxqb(id) {
  return request({
    url: '/workload/Khfsxqb/' + id,
    method: 'delete'
  })
}
