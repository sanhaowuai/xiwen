import request from '@/utils/request'

// 查询默认部门
export function getMrbmxx() {
  return request({
    url: '/workload/tjgl/getMrbmxx',
    method: 'get'
  })
}
// 表格数据
export function getBmzfList(query) {
  return request({
    url: '/workload/tjgl/getBmzfList',
    method: 'get',
    params: query
  })
}
// 统计总表表头
export function getTableHeader(query) {
  return request({
    url: '/workload/tjgl/getTableHeader',
    method: 'get',
    params: query
  })
}
// 个人统计
export function getGrzfList(query) {
  return request({
    url: '/workload/tjgl/getGrzfList',
    method: 'get',
    params: query
  })
}
// 人员list
export function getRyxxList() {
  return request({
    url: '/workload/tjgl/getRyxxList',
    method: 'get'
  })
}


