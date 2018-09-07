import axios from 'axios'

export function fetchBaseData(url,params) {
  return new Promise((resolve,reject) => {
    axios.get(url,params)
      .then((response) => {
        resolve(response['data'])
      })
      .catch((error) => {
        reject(error)
      })
  })
}

export default {
  getWarehouseList(){
    return fetchBaseData('http://localhost:9000/getWarehouseList')
  },
  getSupplierList(){
    return fetchBaseData('http://localhost:9000/getSupplierList')
  }
}
