const addNoticeModal = document.getElementById('addNoticeModal');
const fixNoticeModal = document.getElementById('fixNoticeModal');
const cancelBtn = document.getElementById('cancelBtn');
const submitBtn = document.getElementById('submitBtn');
const fixcancelBtn=document.getElementById('fix-cancelBtn');
const fixsubmitBtn=document.getElementById('fix-submitBtn');
const addNoticeForm = document.getElementById('addNoticeForm');
const addNoticeBtn=document.getElementById('addNoticeBtn')
const textArea=addNoticeModal.querySelector('.modal textarea')
const editTextarea=document.getElementById('fixArea')


let currentEditRow //存储当前正在编辑的行

addNoticeBtn.addEventListener('click',function(){
  addNoticeModal.style.display='block';
})


// 点击取消按钮隐藏弹窗
cancelBtn.addEventListener('click', function () {
  addNoticeModal.style.display = 'none';
});

fixcancelBtn.addEventListener('click',function(){
  fixNoticeModal.style.display = 'none'
})

// 点击发布公告按钮（这里只是简单示例，实际需与后端交互）
submitBtn.addEventListener('click', function (e) {
  e.preventDefault();
  const now = new Date();
  const year = now.getFullYear();
  const month = ('0' + (now.getMonth() + 1)).slice(-2); // 月份从0开始，补0并取后两位
  const day = ('0' + now.getDate()).slice(-2); // 日期补0并取后两位
  const hours = ('0' + now.getHours()).slice(-2); // 小时补0并取后两位
  const minutes = ('0' + now.getMinutes()).slice(-2); // 分钟补0并取后两位
  const seconds = ('0' + now.getSeconds()).slice(-2); // 秒数补0并取后两位
  const customTimeString = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
  if(!textArea.value){
    alert('输入的内容不能为空')
  }else{
    arr.push({
      content:textArea.value,
      time:customTimeString
    })
    render()
    addNoticeForm.reset()
    localStorage.setItem('ancData',JSON.stringify(arr))
    // 隐藏弹窗
    addNoticeModal.style.display = 'none';
  }
  
  // 这里可添加向服务器发送数据等操作
});

fixsubmitBtn.addEventListener('click',function(e){
  e.preventDefault()
  const newContent=editTextarea.value
  if(!newContent){
    alert('输入的内容不能为空')
  }else{
    if(currentEditRow){
      currentEditRow.querySelector('#tableContent').textContent=newContent
    }
    fixNoticeModal.style.display='none'
    const id=currentEditRow.dataset.id
    arr[id].content=newContent
    render()
    localStorage.setItem('ancData',JSON.stringify(arr))
  }
})

// 点击弹窗外区域隐藏弹窗
window.addEventListener('click', function (event) {
  if (event.target === addNoticeModal) {
    addNoticeModal.style.display = 'none';
  }else if(event.target == fixNoticeModal){
    fixNoticeModal.style.display = 'none'
  }
});
const tbody = document.querySelector('#contain5 tbody')
//console.log(tbody)
const arr = JSON.parse(localStorage.getItem('ancData')) || []
//console.log(arr);

function render() {
    const trArr = arr.map(function (ele, index) {
        return `<tr data-id="${index}">
          <td id='tableContent'>${ele.content}</td>
          <td>${ele.time}</td>
          <td>
            <button class="btn-abnormal-return">修改</button><button class="btn-delete-record">删除</button>
          </td>
        </tr>
        `
    })
   // console.log(trArr)
    tbody.innerHTML = trArr.join('')

}
render()

tbody.addEventListener('click', function(e) {
  // 获取所有删除按钮
  if(e.target.classList.contains('btn-delete-record')){
      const row = e.target.closest('tr');
      const id=row.dataset.id
      // 添加删除动画效果
      row.style.transition = 'all 0.3s ease';
      row.style.opacity = '0';
      row.style.transform = 'translateX(50px)';
      setTimeout(() => {
        arr.splice(id,1)
        render()
        localStorage.setItem('ancData',JSON.stringify(arr))
      }, 300);
  }
  if(e.target.classList.contains('btn-abnormal-return')){
      currentEditRow = e.target.closest('tr');
      const ancContent=currentEditRow.querySelector('#tableContent').textContent
      editTextarea.value=ancContent
      fixNoticeModal.style.display='block'
  }
});
