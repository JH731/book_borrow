const addAdminModal = document.querySelector('#addAdminModal')
const addAdminBtn = document.querySelector('#addAdminBtn')
const addAdminForm=document.querySelector('#addAdminForm')
const admincancelBtn=document.querySelector('#admin-cancelBtn')
const adminsubmitBtn=document.querySelector('#admin-submitBtn')
const admintextArea=addAdminModal.querySelector('textarea')
const fixAdminModal =document.querySelector('#fixAdminModal')
const fixAdmincancelBtn=document.querySelector('#fix-admin-cancelBtn')
const fixAdminsubmitBtn=document.querySelector('#fix-admin-submitBtn')
const Emppwd=addAdminModal.querySelector('#emppsd')
const EmpName=addAdminModal.querySelector('#empname')
const Empphone=addAdminModal.querySelector('#empphone')
let EmpSexRadio=addAdminModal.querySelector('input[name="gender"]:checked')
const fEmppwd=fixAdminModal.querySelector('#femppsd')
const fEmpName=fixAdminModal.querySelector('#fempname')
const fEmpphone=fixAdminModal.querySelector('#fempphone')
let fEmpSexRadio=fixAdminModal.querySelector('input[name="gender"]:checked')
const tbody6 = document.querySelector('#contain6 tbody')
const searchBar6=document.querySelector('#searchBar6')
const searchEmpName=searchBar6.querySelector('input')
const searchEmpBtn=searchBar6.querySelector('button')
const pagination6=document.querySelector('#contain6 .pagination')
const prevBtn6=pagination6.querySelector('.prev-page')
const nextBtn6=pagination6.querySelector('.next-page')
const pageIndex6=pagination6.querySelector('.pageIndex')
const toPageIndex6=pagination6.querySelector('.toPageIndex')
const pageConfirmBtn6=pagination6.querySelector('.page-confirm')
const pageSelect6=pagination6.querySelector('select')
const totalNumArea6=pagination6.querySelector('.totalNum')
let targetEmpName
let arr6 
let tpage6=1
let pageSize6=5
let totalNum6
let limitPage6
// = JSON.parse(localStorage.getItem('adminData')) || []

let currentEditRow6 //存储当前正在编辑的行

addAdminBtn.addEventListener('click', function () {
    //console.log('点击到了')
    addAdminModal.style.display = 'block'
})
//按到空白处
window.addEventListener('click', function (event) {
    if (event.target === addAdminModal) {
        addAdminModal.style.display = 'none';
        addAdminForm.reset()
    }else if(event.target === fixAdminModal){
      fixAdminModal.style.display = 'none';
      fixAdminForm.reset()
    }
});
//取消按钮
admincancelBtn.addEventListener('click', function () {
    addAdminModal.style.display = 'none';
    addAdminForm.reset()
});

searchEmpBtn.addEventListener('click',function(){
  targetEmpName=searchEmpName.value
  tpage6=1
    pageIndex6.value=tpage6
  render6()
})

prevBtn6.addEventListener('click',function(){
  if(tpage6>1){
    tpage6--
    pageIndex6.value=tpage6
    render6()
  }
})
nextBtn6.addEventListener('click',function(){
  if(tpage6<limitPage6){
    tpage6++
    pageIndex6.value=tpage6
    render6()
  }
})
pageConfirmBtn6.addEventListener('click',function(){
  const targetPage=parseInt(toPageIndex6.value)
  console.log(targetPage)
  if(targetPage>0&&targetPage<=limitPage6){
    tpage6=targetPage
    pageIndex6.value=tpage6
    pageSize6=pageSelect6.value
    render6()
  }else{
    alert('请输入正确的页码')
  }
  
})

pageSelect6.addEventListener('change',function(){
  pageSize6=pageSelect6.value
  tpage6=1
    pageIndex6.value=tpage6
    render6()
})

fixAdmincancelBtn.addEventListener('click',function(){
  fixAdminModal.style.display = 'none'
  fixAdminForm.reset()
})

fixAdminsubmitBtn.addEventListener('click',function(e){
  e.preventDefault()
  const nfEmppwd=fEmppwd.value
  const nfEmpName=fEmpName.value
  const nfEmpphone=fEmpphone.value
  fEmpSexRadio=fixAdminModal.querySelector('input[name="gender"]:checked')
  const nfEmpSexRadio=fEmpSexRadio.value
  if(!nfEmpName||!nfEmpSexRadio||!nfEmppwd||!nfEmpphone){
    alert('输入的内容不能为空')
  }else{
    const isNameValid=verifyName(nfEmpName)
      const isPwdValid=verifyPwd(nfEmppwd)
      const isPhoneValid=verifyPhone(nfEmpphone)
      if(isNameValid&&isPwdValid&&isPhoneValid){
        if(currentEditRow6){
          const empid=currentEditRow6.dataset.empid
          console.log(empid)
          const requestData = {
            'id': empid,
            'password': nfEmppwd,
            'name': nfEmpName,
            'phone': nfEmpphone,
            'sex': nfEmpSexRadio
          }
          console.log(requestData)
          axios({
            url: 'http://localhost:8088/admin/employee',
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
              render6()
              fixAdminForm.reset()
              // localStorage.setItem('adminData',JSON.stringify(arr6))
              // 隐藏弹窗
              fixAdminModal.style.display = 'none';
            }
            else {
              alert(result.data.msg)
            }
    
          }).catch(error => {
            //console.log(error)
    
            //alert(error.response.data.message)
            alert('网络连接错误')
          })
        }
      }
    
    
  }
})

adminsubmitBtn.addEventListener('click', function (e) {
    e.preventDefault();
    if(!Emppwd.value||!EmpName.value||!Empphone.value){
      alert('输入的内容不能为空')
    }else{
      const isNameValid=verifyName(EmpName.value)
      const isPwdValid=verifyPwd( Emppwd.value)
      const isPhoneValid=verifyPhone( Empphone.value)
      if(isNameValid&&isPwdValid&&isPhoneValid){
        EmpSexRadio=addAdminModal.querySelector('input[name="gender"]:checked')
      const requestData = {
        'id': 0,
        'password': Emppwd.value,
        'name': EmpName.value,
        'phone': Empphone.value,
        'sex': EmpSexRadio.value
      }
      axios({
        url: 'http://localhost:8088/admin/employee',
        method: 'post',
        headers: {
          "Content-Type":"application/json",
          'adminToken': `${localStorage.getItem('id-token')}`
        },
        data: JSON.stringify(requestData)
      }).then(result => {
        console.log(result)
        if (result.data.code == '1') {
          // arr6.push({
          //   content:EmpName.value,
          // })
          render6()
          addAdminForm.reset()
          // localStorage.setItem('adminData',JSON.stringify(arr6))
          // 隐藏弹窗
          addAdminModal.style.display = 'none';
        }
        else {
          alert(result.data.msg)
        }

      }).catch(error => {
        console.log(error)

        //alert(error.response.data.message)
        alert('网络连接错误')
      })
      }
      
      
    }
    
    // 这里可添加向服务器发送数据等操作
  });

function render6() {
  
  axios({
    url:`http://localhost:8088/admin/employee/page`,
    method:'get',
    headers: {
      'adminToken': `${localStorage.getItem('id-token')}`
    },
    params:{
      name:targetEmpName,
      page:tpage6,
        pageSize:pageSize6
    }
    
}).then(result=>{
     console.log(result)
    if(result.data.code=='1'){
      totalNumArea6.innerHTML=result.data.data.total
      totalNum6=parseInt(result.data.data.total)
      pageSize6=parseInt(pageSelect6.value)
      limitPage6=Math.ceil(totalNum6/pageSize6)
      arr6=[]
      
      for(let i=0;i<result.data.data.records.length;i++){
        arr6.push({
          id:result.data.data.records[i].id,
          content:result.data.data.records[i].name
        })
      }
      //console.log(arr6)
      const trArr = arr6.map(function (ele, index) {
        return `<tr data-id="${index}"data-empid=${ele.id}>
          <td id='tableContent'>${ele.content}</td>
          <td>
            <button class="btn-abnormal-return">修改</button><button class="btn-delete-record">删除</button>
          </td>
        </tr>
        `
    })
    //console.log(trArr)
    tbody6.innerHTML = trArr.join('')
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
render6()

tbody6.addEventListener('click', function(e) {
    // 获取所有删除按钮
    if(e.target.classList.contains('btn-delete-record')){
        const row = e.target.closest('tr');
        const id=row.dataset.id
        // 添加删除动画效果
        row.style.transition = 'all 0.3s ease';
        row.style.opacity = '0';
        row.style.transform = 'translateX(50px)';
        axios({
          url:`http://localhost:8088/admin/employee`,
          method:'delete',
          headers: {
            'adminToken': `${localStorage.getItem('id-token')}`
          },
          params:{
            id:row.dataset.empid
          }
          
      }).then(result=>{
        console.log(result)
        setTimeout(() => {
          arr3.splice(id,1)
          render3()
         
        }, 300);
          
      }).catch(error=>{
          console.log(error)
          //alert(error.response.data.message)
          alert('网络连接错误')
      })
        setTimeout(() => {
          arr6.splice(id,1)
          render6()
          localStorage.setItem('adminData',JSON.stringify(arr6))
        }, 300);
    }
    if(e.target.classList.contains('btn-abnormal-return')){
      currentEditRow6=e.target.closest('tr')
      
        fixAdminModal.style.display='block'
    }
  });