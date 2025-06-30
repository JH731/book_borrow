const addBorrowModal = document.querySelector('#addBorrowModal')
const addBorrowBtn = document.querySelector('#addBorrowBtn')
const addReturnBtn = document.querySelector('#addReturnBtn')
const addBorrowForm=document.querySelector('#addBorrowForm')
const borrowcancelBtn=document.querySelector('#borrow-cancelBtn')
const borrowsubmitBtn=document.querySelector('#borrow-submitBtn')
const tbody1 = document.querySelector('#contain1 tbody')
const editTextarea1=document.querySelector('#fixArea1')
const fixBorrowModal=document.querySelector('#fixBorrowModal')
const fixBorrowcancelBtn=document.querySelector('#fix-borrow-cancelBtn')
const fixBorrowsubmitBtn=document.querySelector('#fix-borrow-submitBtn')

const borrowBookTypeSelect=document.querySelector('#borrowBookTypeSelect')
const borrowBookReturnSelect=document.querySelector('#borrowBookReturnSelect')
const borrowbookName=addBorrowModal.querySelector('.form-item #bookName')
const borrowbookCategory=addBorrowModal.querySelector('.form-item #bookCategory')
const borrowCard=addBorrowModal.querySelector('.form-item #borrowCard')
const borrower=addBorrowModal.querySelector('.form-item #borrower')

const fBorrownormal=fixBorrowModal.querySelector('.form-item #normal')

const searchBar1=document.querySelector('#searchBar1')
const searchBorrowName=searchBar1.querySelector('input')
const searchBorrowBtn=searchBar1.querySelector('.btn-search')
const pagination1=document.querySelector('#contain1 .pagination')
const prevBtn1=pagination1.querySelector('.prev-page')
const nextBtn1=pagination1.querySelector('.next-page')
const pageIndex1=pagination1.querySelector('.pageIndex')
const toPageIndex1=pagination1.querySelector('.toPageIndex')
const pageConfirmBtn1=pagination1.querySelector('.page-confirm')
const pageSelect1=pagination1.querySelector('select')
const totalNumArea1=pagination1.querySelector('.totalNum')
let targetBorrowName
let targetBorrowType
let targetBorrowState
let target
let arr1
let tpage1=1
let pageSize1=5
let totalNum1
let limitPage1
let isBorrow=true

let currentEditRow1 //存储当前正在编辑的行



borrowBookTypeSelect.addEventListener('change',function(){
  const selectedOption=this.options[this.selectedIndex]
  targetBorrowType=selectedOption.value
  
})

// borrowBookReturnSelect.addEventListener('change',function(){
//   const selectedOption=this.options[this.selectedIndex]
//   targetBorrowState=selectedOption.value
  
// })

addBorrowBtn.addEventListener('click',function(){
  isBorrow=true
  tpage1=1
  pageIndex1.value=tpage1
  render1()
})

addReturnBtn.addEventListener('click',function(){
  isBorrow=false
  tpage1=1
  pageIndex1.value=tpage1
  render1()
})

searchBorrowBtn.addEventListener('click',function(){
  targetBorrowName=searchBorrowName.value
  // console.log(targetUserName)
  tpage1=1
    pageIndex1.value=tpage1
    alert("搜索成功")
  render1()
})

prevBtn1.addEventListener('click',function(){
  if(tpage1>1){
    tpage1--
    pageIndex1.value=tpage1
    render1()
  }
})
nextBtn1.addEventListener('click',function(){
  if(tpage1<limitPage1){
    tpage1++
    pageIndex1.value=tpage1
    render1()
  }
})
pageConfirmBtn1.addEventListener('click',function(){
  const targetPage=parseInt(toPageIndex1.value)
  console.log(targetPage)
  if(targetPage>0&&targetPage<=limitPage1){
    tpage1=targetPage
    pageIndex1.value=tpage1
    pageSize1=pageSelect1.value
    render1()
  }else{
    alert('请输入正确的页码')
  }
  
})

pageSelect1.addEventListener('change',function(){
  pageSize1=pageSelect1.value
  tpage1=1
    pageIndex1.value=tpage1
    render1()
})

function render1() {
  if(localStorage.getItem('token')=='employee'){
    axios({
      url:`http://localhost:8088/employee/category/list`,
      method:'get',
      headers: {
        'employeeToken': `${localStorage.getItem('id-token')}`
      }
     
  }).then(result=>{
      //console.log(result)
      while(borrowBookTypeSelect.options.length>1){
        borrowBookTypeSelect.remove(1)
      }
      result.data.data.forEach((element,index) => {
        //console.log(element)
        const option1 = document.createElement('option'); 
        
        
        option1.value = element;
        option1.textContent = element;
       
  
        option1.dataset.id=index+1
       
    
        borrowBookTypeSelect.appendChild(option1); 
        
      });
  }).catch(error=>{
      console.log(error)
      //alert(error.response.data.message)
      alert('网络连接错误')
  })
  }
  if(isBorrow)
  {
    axios({
      url:`http://localhost:8088/employee/borrow/borrowList`,
      method:'get',
      headers: {
        'employeeToken': `${localStorage.getItem('id-token')}`
      },
      params:{
        userName:targetBorrowName,
        categoryName:targetBorrowType,
        page:tpage1,
        pageSize:pageSize1,
        //status:targetBorrowState
        
        
      }
      
  }).then(result=>{
      console.log(result)
      if(result.data.code=='1'){
        totalNumArea1.innerHTML=result.data.data.total
        totalNum1=parseInt(result.data.data.total)
        pageSize1=parseInt(pageSelect1.value)
        limitPage1=Math.ceil(totalNum1/pageSize1)
        arr1=[]
        
        for(let i=0;i<result.data.data.records.length;i++){
          arr1.push({
            id:result.data.data.records[i].id,
            author:result.data.data.records[i].author,
            categoryName:result.data.data.records[i].categoryName,
            edition:result.data.data.records[i].edition,
            bookName:result.data.data.records[i].bookName,
            publish:result.data.data.records[i].publish,
            userName:result.data.data.records[i].userName,
            startTime:result.data.data.records[i].startTime,
            returnTime:result.data.data.records[i].returnTime
          })
        }
        //console.log(arr6)
        const trArr = arr1.map(function (ele, index) {
          return `<tr data-id="${index}"data-borrowid=${ele.id}>
            <td>${ele.userName}</td>
              <td id='bookName'>${ele.bookName}</td>
               <td id='student'>${ele.publish}</td>
            
            <td id='cardId'>${ele.edition}</td>
             <td>${ele.author}</td>
           <td id='bookType'>${ele.categoryName}</td>
            <td id='borrowTime'>${ele.startTime}</td>
            <td>${ele.returnTime}</td>
            <td>
              <button class="btn-abnormal-return">通过</button><button class="btn-delete-record">拒绝</button>
            </td>
          </tr>
          `
      })
      //console.log(trArr)
      tbody1.innerHTML = trArr.join('')
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
  else{
    axios({
      url:`http://localhost:8088/employee/back/adminList`,
      method:'get',
      headers: {
        'employeeToken': `${localStorage.getItem('id-token')}`
      },
      params:{
        userName:targetBorrowName,
        categoryId:targetBorrowType,
        page:tpage1,
        pageSize:pageSize1,
        status:null
        
        
      }
      
  }).then(result=>{
      console.log(result)
      if(result.data.code=='1'){
        totalNumArea1.innerHTML=result.data.data.total
        totalNum1=parseInt(result.data.data.total)
        pageSize1=parseInt(pageSelect1.value)
        limitPage1=Math.ceil(totalNum1/pageSize1)
        arr1=[]
        
        for(let i=0;i<result.data.data.records.length;i++){
          arr1.push({
            id:result.data.data.records[i].id,
            author:result.data.data.records[i].author,
            categoryName:result.data.data.records[i].categoryName,
            edition:result.data.data.records[i].edition,
            bookName:result.data.data.records[i].bookName,
            publish:result.data.data.records[i].publish,
            userName:result.data.data.records[i].userName,
            startTime:result.data.data.records[i].startTime,
            returnTime:result.data.data.records[i].returnTime
          })
        }
        //console.log(arr6)
        const trArr = arr1.map(function (ele, index) {
          return `<tr data-id="${index}"data-borrowid=${ele.id}>
            <td>${ele.userName}</td>
              <td id='bookName'>${ele.bookName}</td>
               <td id='student'>${ele.publish}</td>
            
            <td id='cardId'>${ele.edition}</td>
             <td>${ele.author}</td>
           <td id='bookType'>${ele.categoryName}</td>
            <td id='borrowTime'>${ele.startTime}</td>
            <td>${ele.returnTime}</td>
           
            <td>
              <button class="btn-abnormal-return">通过</button>
            </td>
          </tr>
          `
      })
      //console.log(trArr)
      tbody1.innerHTML = trArr.join('')
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
    
   

}
if(localStorage.getItem('token')=='employee')
render1()

// addBorrowBtn.addEventListener('click', function () {
//   //console.log('点击到了')
//   addBorrowModal.style.display = 'block'
// })
//按到空白处
window.addEventListener('click', function (event) {
  if (event.target === addBorrowModal) {
      addBorrowModal.style.display = 'none';
  }else if(event.target == fixBorrowModal){
    fixBorrowModal.style.display='none';
  }
});
//取消按钮
borrowcancelBtn.addEventListener('click', function () {
  addBorrowModal.style.display = 'none';
});

//修改类型的取消按钮
fixBorrowcancelBtn.addEventListener('click',function(){
  fixBorrowModal.style.display = 'none'
})

//修改类型的保存按钮
fixBorrowsubmitBtn.addEventListener('click',function(e){
  e.preventDefault()
  const now = new Date();
  const year = now.getFullYear();
  const month = ('0' + (now.getMonth() + 1)).slice(-2); // 月份从0开始，补0并取后两位
  const day = ('0' + now.getDate()).slice(-2); // 日期补0并取后两位
  const hours = ('0' + now.getHours()).slice(-2); // 小时补0并取后两位
  const minutes = ('0' + now.getMinutes()).slice(-2); // 分钟补0并取后两位
  const seconds = ('0' + now.getSeconds()).slice(-2); // 秒数补0并取后两位
  const customTimeString = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
  const newContent=fBorrownormal.value
  let retClass
  if(!newContent){
    alert('输入的内容不能为空')
  }else{
    if(currentEditRow1){
      
      // currentEditRow7.querySelector('#returnTime').textContent=customTimeString
      // currentEditRow7.querySelector('#returnType span').textContent=newContent
      // currentEditRow7.querySelector('#returnType span').classList.remove('status-borrowing')
      // currentEditRow7.querySelector('.btn-abnormal-return').remove()
      // if(newContent=='正常还书'){
      //   currentEditRow7.querySelector('#returnType span').classList.add('status-normal-return')
      //   retClass='status-normal-return'
      // }else if(newContent=='延迟还书'){
      //   currentEditRow7.querySelector('#returnType span').classList.add('status-delay-return')
      //   retClass='status-delay-return'
      // }else if(newContent=='破损还书'){
      //   currentEditRow7.querySelector('#returnType span').classList.add('status-damaged-return')
      //   retClass='status-damaged-return'
      // }else if(newContent=='丢失赔书'){
      //   currentEditRow7.querySelector('#returnType span').classList.add('status-lost-book')
      //   retClass='status-lost-book'
      // }
    }
    fixBorrowModal.style.display='none'
    const id=currentEditRow1.dataset.id
    arr1[id].returnTime=customTimeString
    arr1[id].returnType=newContent
    arr1[id].returnClass=retClass
    arr1[id].btn='<button class="btn-delete-record">删除</button>'
    render1()
    localStorage.setItem('borrowData',JSON.stringify(arr1))
  }
})

// borrowsubmitBtn.addEventListener('click', function (e) {
//   e.preventDefault();
//   const now = new Date();
//   const year = now.getFullYear();
//   const month = ('0' + (now.getMonth() + 1)).slice(-2); // 月份从0开始，补0并取后两位
//   const day = ('0' + now.getDate()).slice(-2); // 日期补0并取后两位
//   const hours = ('0' + now.getHours()).slice(-2); // 小时补0并取后两位
//   const minutes = ('0' + now.getMinutes()).slice(-2); // 分钟补0并取后两位
//   const seconds = ('0' + now.getSeconds()).slice(-2); // 秒数补0并取后两位
//   const customTimeString = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
//   if(!borrowRBookName.value||!borrowRBookCategory.value||!borrowCard.value||!rBorrower.value){
//     alert('输入的内容不能为空')
//   }else{
//     arr7.push({
//       borrowbookName:borrowRBookName.value,
//       bookType:borrowRBookCategory.value,
//       cardId:borrowCard.value,
//       student:rBorrower.value,
//       borrowTime:customTimeString,
//       returnTime:'',
//       returnType:'在借中',
//       returnClass:'status-borrowing',
//       btn:'<button class="btn-abnormal-return">还书</button><button class="btn-delete-record">删除</button>'
//     })
//     render1()
//     rAddBorrowForm.reset()
//     localStorage.setItem('borrowData',JSON.stringify(arr7))
//     // 隐藏弹窗
//     rAddBorrowModal.style.display = 'none';
//   }
  
//   // 这里可添加向服务器发送数据等操作
// });

tbody1.addEventListener('click', function(e) {
  // 获取所有通过按钮
  if(e.target.classList.contains('btn-delete-record')){
    if(isBorrow){
      alert("已经拒绝了申请")
      currentEditRow1 = e.target.closest('tr');
      const id = currentEditRow1.dataset.borrowid
      axios({
        url: `http://localhost:8088/employee/borrow/notAllow/${id}`,
        method: 'post',
        headers: {
          "Content-Type": "application/json",
          'employeeToken': `${localStorage.getItem('id-token')}`
        }
        // params:{
        //   id:currentEditRow1.dataset.borrowid
        // }


      }).then(result => {
        render1()


      }).catch(error => {
        console.log(error)
        //alert(error.response.data.message)
        alert('网络连接错误')
      })
    }else{
      alert("已经拒绝了还书")
      currentEditRow1 = e.target.closest('tr');
      const id = currentEditRow1.dataset.borrowid
      axios({
        url: `http://localhost:8088/employee/back/notAllow/${id}`,
        method: 'post',
        headers: {
          "Content-Type": "application/json",
          'employeeToken': `${localStorage.getItem('id-token')}`
        }
        // params:{
        //   id:currentEditRow1.dataset.borrowid
        // }


      }).then(result => {
        render1()


      }).catch(error => {
        console.log(error)
        //alert(error.response.data.message)
        alert('网络连接错误')
      })
    }
      
  }
  if(e.target.classList.contains('btn-abnormal-return')){
    if (isBorrow) {
      currentEditRow1 = e.target.closest('tr');
      const id = currentEditRow1.dataset.borrowid
      axios({
        url: `http://localhost:8088/employee/borrow/allow/${id}`,
        method: 'post',
        headers: {
          "Content-Type": "application/json",
          'employeeToken': `${localStorage.getItem('id-token')}`
        }
        // params:{
        //   id:currentEditRow1.dataset.borrowid
        // }


      }).then(result => {
        render1()


      }).catch(error => {
        console.log(error)
        //alert(error.response.data.message)
        alert('网络连接错误')
      })
    }else{
      console.log("已经通过了还书")
      currentEditRow1 = e.target.closest('tr');
      const id = currentEditRow1.dataset.borrowid
      axios({
        url: `http://localhost:8088/employee/back/allow/${id}`,
        method: 'post',
        headers: {
          "Content-Type": "application/json",
          'employeeToken': `${localStorage.getItem('id-token')}`
        }
        // params:{
        //   id:currentEditRow1.dataset.borrowid
        // }


      }).then(result => {
        render1()
        render2()

      }).catch(error => {
        console.log(error)
        //alert(error.response.data.message)
        alert('网络连接错误')
      })
    }
    
  }
});