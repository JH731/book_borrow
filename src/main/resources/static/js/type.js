const addTypeModal = document.querySelector('#addTypeModal')
const addTypeBtn = document.querySelector('#addTypeBtn')
const addTypeForm=document.querySelector('#addTypeForm')
const typecancelBtn=document.querySelector('#type-cancelBtn')
const typesubmitBtn=document.querySelector('#type-submitBtn')
const tbody4 = document.querySelector('#contain4 tbody')
const textArea4=addTypeModal.querySelector('textarea')
const editTextarea4=document.querySelector('#fixArea4')
const fixTypeModal=document.querySelector('#fixTypeModal')
const fixTypecancelBtn=document.querySelector('#fix-type-cancelBtn')
const fixTypesubmitBtn=document.querySelector('#fix-type-submitBtn')
const searchBar4=document.querySelector('#searchBar4')
const searchTypeName=searchBar4.querySelector('input')
const searchTypeBtn=searchBar4.querySelector('.btn-search')
const pagination4=document.querySelector('#contain4 .pagination')
const prevBtn4=pagination4.querySelector('.prev-page')
const nextBtn4=pagination4.querySelector('.next-page')
const pageIndex4=pagination4.querySelector('.pageIndex')
const toPageIndex4=pagination4.querySelector('.toPageIndex')
const pageConfirmBtn4=pagination4.querySelector('.page-confirm')
const pageSelect4=pagination4.querySelector('select')
const totalNumArea4=pagination4.querySelector('.totalNum')
let targetTypeName
let arr4 
let tpage4=1
let pageSize4=5
let totalNum4
let limitPage4

let currentEditRow4 //存储当前正在编辑的行

searchTypeBtn.addEventListener('click',function(){
  targetTypeName=searchTypeName.value
  // console.log(targetUserName)
  tpage4=1
    pageIndex4.value=tpage4
  render4()
})

prevBtn4.addEventListener('click',function(){
  if(tpage4>1){
    tpage4--
    pageIndex4.value=tpage4
    render4()
  }
})
nextBtn4.addEventListener('click',function(){
  if(tpage4<limitPage4){
    tpage4++
    pageIndex4.value=tpage4
    render4()
  }
})
pageConfirmBtn4.addEventListener('click',function(){
  const targetPage=parseInt(toPageIndex4.value)
  console.log(targetPage)
  if(targetPage>0&&targetPage<=limitPage4){
    tpage4=targetPage
    pageIndex4.value=tpage4
    pageSize4=pageSelect4.value
    render4()
  }else{
    alert('请输入正确的页码')
  }
  
})

pageSelect4.addEventListener('change',function(){
  pageSize4=pageSelect4.value
  tpage4=1
    pageIndex4.value=tpage4
    render4()
})

function render4() {
  axios({
    url:`http://localhost:8088/employee/category/page`,
    method:'get',
    headers: {
      'employeeToken': `${localStorage.getItem('id-token')}`
    },
    params:{
      
      
      page:tpage4,
        pageSize:pageSize4,
        name:targetTypeName
    }
    
}).then(result=>{
    console.log(result)
    if(result.data.code=='1'){
      totalNumArea4.innerHTML=result.data.data.total
      totalNum4=parseInt(result.data.data.total)
      pageSize4=parseInt(pageSelect4.value)
      limitPage4=Math.ceil(totalNum4/pageSize4)
      arr4=[]
      
      for(let i=0;i<result.data.data.records.length;i++){
        arr4.push({
          id:result.data.data.records[i].id,
          content:result.data.data.records[i].name,
        })
      }
      //console.log(arr6)
      const trArr = arr4.map(function (ele, index) {
        return `<tr data-id="${index}"data-typeid=${ele.id}>
          <td id='tableContent'>${ele.content}</td>
          <td>
            <button class="btn-abnormal-return">修改</button><button class="btn-delete-record">删除</button>
          </td>
        </tr>
        `
    })
    //console.log(trArr)
    tbody4.innerHTML = trArr.join('')
    //console.log(trArr)
   
    }
    else{
        alert(result.data.msg)
    }
    
}).catch(error=>{
    console.log(error)
    //alert(error.response.data.message)
    alert('网络连接错误')
})
}
if(localStorage.getItem('token')=='employee')
render4()

//新增类型按钮
addTypeBtn.addEventListener('click', function () {
    //console.log('点击到了')
    addTypeModal.style.display = 'block'
})
//按到空白处
window.addEventListener('click', function (event) {
    if (event.target === addTypeModal) {
        addTypeModal.style.display = 'none';
    }
});
//取消按钮
typecancelBtn.addEventListener('click', function () {
    addTypeModal.style.display = 'none';
});

//修改类型的取消按钮
fixTypecancelBtn.addEventListener('click',function(){
  fixTypeModal.style.display = 'none'
})

//修改类型的保存按钮
fixTypesubmitBtn.addEventListener('click',function(e){
  e.preventDefault()
  const newContent=editTextarea4.value
  
  if(!newContent){
    alert('输入的内容不能为空')
  }else{
    if(currentEditRow4){
      currentEditRow4.querySelector('#tableContent').textContent=newContent
      const tid=currentEditRow4.dataset.typeid
      const typeData={
        "id":tid,
        "type":0,
        "name":editTextarea4.value,
        "sort":0
      }
      axios({
        url:'http://localhost:8088/employee/category',
        method:'put',
        headers:{
            "Content-Type":"application/json",
            'employeeToken': `${localStorage.getItem('id-token')}`
        },
        data:JSON.stringify(typeData)
    }).then(result=>{
        console.log(result)
        if(result.data.code=='1'){
          // arr4.push({
          //   content:textArea4.value,
          // })
          render1()
          render2()
          render4()
          addTypeForm.reset()
          // 隐藏弹窗
          addTypeModal.style.display = 'none';
        }
        else{
            alert(result.data.msg)
        }
        
    }).catch(error=>{
        console.log(error)
        //alert(error.response.data.message)
        alert('网络连接错误')
    })
    }
    fixTypeModal.style.display='none'
    const id=currentEditRow4.dataset.id
    arr4[id].content=newContent
    render4()
    
  }
})

// 点击保存类型按钮（这里只是简单示例，实际需与后端交互）
typesubmitBtn.addEventListener('click', function (e) {
    e.preventDefault();
    const typeData={
      "id":0,
      "type":0,
      "name":textArea4.value,
      "sort":0
    }
    if(!textArea4.value){
      alert('输入的内容不能为空')
    }else{
      axios({
        url:'http://localhost:8088/employee/category',
        method:'post',
        headers:{
            "Content-Type":"application/json",
            'employeeToken': `${localStorage.getItem('id-token')}`
        },
        data:JSON.stringify(typeData)
    }).then(result=>{
        console.log(result)
        if(result.data.code=='1'){
          // arr4.push({
          //   content:textArea4.value,
          // })
          render1()
          render2()
          render4()
          addTypeForm.reset()
          // 隐藏弹窗
          addTypeModal.style.display = 'none';
        }
        else{
            alert(result.data.msg)
        }
        
    }).catch(error=>{
        console.log(error)
        //alert(error.response.data.message)
        alert('网络连接错误')
    })
     
    }
    
    // 这里可添加向服务器发送数据等操作
  });
  tbody4.addEventListener('click', function(e) {
    // 获取所有删除按钮
    if(e.target.classList.contains('btn-delete-record')){
        const row = e.target.closest('tr');
        const id=row.dataset.id
        const tid=row.dataset.typeid
        console.log(tid)
        // 添加删除动画效果
        row.style.transition = 'all 0.3s ease';
        row.style.opacity = '0';
        row.style.transform = 'translateX(50px)';
        axios({
          url:`http://localhost:8088/employee/category`,
          method:'delete',
          headers: {
            'employeeToken': `${localStorage.getItem('id-token')}`
          },
          params:{
            id:row.dataset.typeid
          }
          
      }).then(result=>{
        console.log(result)
        setTimeout(() => {
          arr4.splice(id,1)
          render1()
          render2()
          render4()
         
        }, 300);
          
      }).catch(error=>{
          console.log(error)
          //alert(error.response.data.message)
          alert('网络连接错误')
      })
        
    }
    if(e.target.classList.contains('btn-abnormal-return')){
        currentEditRow4 = e.target.closest('tr');
        const typeContent=currentEditRow4.querySelector('#tableContent').textContent
        editTextarea4.value=typeContent
        fixTypeModal.style.display='block'
    }
  });