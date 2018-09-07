import Vue from 'vue'

const globalEvent = {
  showMessage({type = 'error', message}) {
    var _vue = new Vue();
    _vue.$message({
      showClose: true,
      message: message,
      type: type,
      center: true
    });
  },
  transformTime({time, seconds = ""}) {
    //转换成两位数
    function transformDouble(num, length) {
      return ('' + num).length < length ? ((new Array(length + 1)).join('0') + num).slice(-length) : '' + num;
    }
    var tempTime = new Date(time);
    var confirmTime = tempTime.getFullYear() + "-" + transformDouble((tempTime.getMonth() + 1), 2) + "-" + transformDouble(tempTime.getDate(), 2);
    if (time && !seconds) {
      return confirmTime;
    } else if (time && seconds) {
      confirmTime += " " + transformDouble(tempTime.getHours(), 2) + ":" + transformDouble(tempTime.getMinutes(), 2) + ":" + transformDouble(tempTime.getSeconds(), 2);
      return confirmTime;
    } else {
      return ""
    }
  }
};

export default globalEvent
