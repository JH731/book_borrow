const addReaderBtn =document.querySelector('#addReaderBtn')
const addReaderModal = document.querySelector('#addReaderModal')
const readercancelBtn=document.querySelector('#reader-cancelBtn')
const readersubmitBtn=document.querySelector('#reader-submitBtn')
const fixReaderModal = document.querySelector('#fixReaderModal')
const readerPwd=fixReaderModal.querySelector('#femppsd')
const readerName=fixReaderModal.querySelector('#fempname')
const readerPhone=fixReaderModal.querySelector('#fempphone')
const readerMaxBorrow=fixReaderModal.querySelector('#fmaxBorrow')
let readerSexRadio=fixReaderModal.querySelector('input[name="gender"]:checked')
const fixReaderForm=document.querySelector('#fixReaderForm')
const fixReadercancelBtn=document.querySelector('#fix-reader-cancelBtn')
const fixReadersubmitBtn=document.querySelector('#fix-reader-submitBtn')
const fnormal=fixReaderModal.querySelector('.form-item #normal')
const tbody3 = document.querySelector('#contain3 tbody')
const searchBar3=document.querySelector('#searchBar3')
const searchUserName=searchBar3.querySelector('input')
const searchUserBtn=searchBar3.querySelector('.btn-search')
const pagination3=document.querySelector('#contain3 .pagination')
const prevBtn3=pagination3.querySelector('.prev-page')
const nextBtn3=pagination3.querySelector('.next-page')
const pageIndex3=pagination3.querySelector('.pageIndex')
const toPageIndex3=pagination3.querySelector('.toPageIndex')
const pageConfirmBtn3=pagination3.querySelector('.page-confirm')
const pageSelect3=pagination3.querySelector('select')
const totalNumArea3=pagination3.querySelector('.totalNum')
let targetUserName
let arr3 
let tpage3=1
let pageSize3=5
let totalNum3
let limitPage3
let currentEditRow3 //存储当前正在编辑的行

window.addEventListener('click', function (event) {
    if (event.target === fixReaderModal) {
        fixReaderModal.style.display = 'none';
        fixReaderForm.reset()
    }
    else if (event.target === addReaderModal) {
      addReaderModal.style.display = 'none';
  }
    
});

addReaderBtn.addEventListener('click', function () {
  //console.log('点击到了')
  window.location.href = './register.html';
})

readercancelBtn.addEventListener('click', function () {
  addReaderModal.style.display = 'none';
});

searchUserBtn.addEventListener('click',function(){
  targetUserName=searchUserName.value
  // console.log(targetUserName)
  tpage3=1
    pageIndex3.value=tpage3
  render3()
})

prevBtn3.addEventListener('click',function(){
  if(tpage3>1){
    tpage3--
    pageIndex3.value=tpage3
    render3()
  }
})
nextBtn3.addEventListener('click',function(){
  if(tpage3<limitPage3){
    tpage3++
    pageIndex3.value=tpage3
    render3()
  }
})
pageConfirmBtn3.addEventListener('click',function(){
  const targetPage=parseInt(toPageIndex3.value)
  console.log(targetPage)
  if(targetPage>0&&targetPage<=limitPage3){
    tpage3=targetPage
    pageIndex3.value=tpage3
    pageSize3=pageSelect3.value
    render3()
  }else{
    alert('请输入正确的页码')
  }
  
})

pageSelect3.addEventListener('change',function(){
  pageSize3=pageSelect3.value
  tpage3=1
    pageIndex3.value=tpage3
    render3()
})

//修改类型的取消按钮
fixReadercancelBtn.addEventListener('click',function(){
    fixReaderModal.style.display = 'none'
    fixReaderForm.reset()
  })

 
//修改类型的保存按钮
fixReadersubmitBtn.addEventListener('click',function(e){
    e.preventDefault()
    readerSexRadio=document.querySelector('input[name="gender"]:checked')
    if(!readerName.value||!readerPhone.value||!readerPwd.value){
      alert('输入的内容不能为空')
    }else{
      const isNameValid=verifyName(readerName.value)
      const isPwdValid=verifyPwd(readerPwd.value)
      const isPhoneValid=verifyPhone(readerPhone.value)
      if(isNameValid&&isPwdValid&&isPhoneValid){
        if(currentEditRow3){
          const readerid=currentEditRow3.dataset.userid
          const requestData={
            'id':readerid,
            'password':readerPwd.value,
            'name':readerName.value,
            'phone':readerPhone.value,
            'sex':readerSexRadio.value,
            'maxBorrow':readerMaxBorrow.value
          }
          axios({
            url: 'http://localhost:8088/admin/user',
            method: 'put',
            headers: {
              "Content-Type":"application/json",
              'adminToken': `${localStorage.getItem('id-token')}`
            },
            data: JSON.stringify(requestData)
          }).then(result => {
            //console.log(result)
            if (result.data.code == '1') {
              // arr6.push({
              //   content:EmpName.value,
              // })
              render3()
              fixReaderForm.reset()
              // localStorage.setItem('adminData',JSON.stringify(arr6))
              // 隐藏弹窗
              fixReaderModal.style.display = 'none';
            }
            else {
              alert(result.data.msg)
            }
    
          }).catch(error => {
            //console.log(error)
    
            //alert(error.response.data.message)
            console.log(error)
          })
        }
      }
     
      // fixReaderModal.style.display='none'
      // const id=currentEditRow3.dataset.id
      // arr3[id].normal=newContent
      // render3()
      
    }
  })  

function render3() {
  axios({
    url:`http://localhost:8088/admin/user/page`,
    method:'get',
    headers: {
      'adminToken': `${localStorage.getItem('id-token')}`
    },
    params:{
      
      
      page:tpage3,
        pageSize:pageSize3,
        name:targetUserName
        
    }
    
}).then(result=>{
    console.log(result)
    if(result.data.code=='1'){
      totalNumArea3.innerHTML=result.data.data.total
      totalNum3=parseInt(result.data.data.total)
      pageSize3=parseInt(pageSelect3.value)
      limitPage3=Math.ceil(totalNum3/pageSize3)
      arr3=[]
      
      for(let i=0;i<result.data.data.records.length;i++){
        arr3.push({
          id:result.data.data.records[i].id,
          username:result.data.data.records[i].name,
          sex:result.data.data.records[i].sex,
          phone:result.data.data.records[i].phone,
          maxBorrow:result.data.data.records[i].maxBorrow
        })
      }
      //console.log(arr6)
      const trArr = arr3.map(function (ele, index) {
        return `<tr data-id="${index}"data-userid=${ele.id}>
          <td>${ele.username}</td>
           <td>${ele.sex}</td>
          <td>${ele.phone}</td>
         
          <td>
            <button class="btn-abnormal-return">修改</button><button class="btn-delete-record">删除</button>
          </td>
        </tr>
        `
    })
    //console.log(trArr)
    tbody3.innerHTML = trArr.join('')
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
if(localStorage.getItem('token')=='admin')
  render3()


tbody3.addEventListener('click', function(e) {
    // 获取所有删除按钮
    if(e.target.classList.contains('btn-delete-record')){
        const row = e.target.closest('tr');
        const id=row.dataset.id
        // 添加删除动画效果
        
        axios({
          url:`http://localhost:8088/admin/user`,
          method:'delete',
          headers: {
            'adminToken': `${localStorage.getItem('id-token')}`
          },
          params:{
            id:row.dataset.userid
          }
          
      }).then(result=>{
        if(result.data.code == '1'){
          row.style.transition = 'all 0.3s ease';
        row.style.opacity = '0';
        row.style.transform = 'translateX(50px)';
          console.log(result)
          setTimeout(() => {
            arr3.splice(id,1)
            render3()
           
          }, 300);
        }else{
          alert(result.data.msg)
        }
        
          
      }).catch(error=>{
          console.log(error)
          //alert(error.response.data.message)
          alert('网络连接错误')
      })
        
    }
    if(e.target.classList.contains('btn-abnormal-return')){
        currentEditRow3 = e.target.closest('tr');
        //console.log(currentEditRow3)
        // const typeContent=currentEditRow3.querySelector('#tableContent').textContent
        // fnormal.value=currentEditRow3.querySelector('#normal').textContent
        fixReaderModal.style.display='block'
    }
  });