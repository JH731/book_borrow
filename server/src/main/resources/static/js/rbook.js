// const addBookModal = document.querySelector('#addBookModal')
// const addBookBtn = document.querySelector('#addBookBtn')
// const addBookForm=document.querySelector('#addBookForm')
// const bookcancelBtn=document.querySelector('#book-cancelBtn')
// const booksubmitBtn=document.querySelector('#book-submitBtn')
// const fixBookModal = document.querySelector('#fixBookModal')
// const fixBookForm=document.querySelector('#fixBookForm')
// const fixBookcancelBtn=document.querySelector('#fix-book-cancelBtn')
// const fixBooksubmitBtn=document.querySelector('#fix-book-submitBtn')
const tbody8 = document.querySelector('#contain8 tbody')
// 添加弹窗中相关内容

// const bookName=addBookModal.querySelector('.form-item #bookName')
// const bookCategory=addBookModal.querySelector('.form-item #bookCategory')
// const author=addBookModal.querySelector('.form-item #author')
// const publiser=addBookModal.querySelector('.form-item #publiser')
// const edition=addBookModal.querySelector('.form-item #edition')

// const num=addBookModal.querySelector('.form-item #num')
// 修改弹窗中相关内容

// const fbookName=fixBookModal.querySelector('.form-item #bookName')
// const fbookCategory=fixBookModal.querySelector('.form-item #bookCategory')
// const fauthor=fixBookModal.querySelector('.form-item #author')
// const fpubliser=fixBookModal.querySelector('.form-item #publiser')
// const fedition=fixBookModal.querySelector('.form-item #edition')
// const fnum=fixBookModal.querySelector('.form-item #num')
// 
// const editTextarea1=document.querySelector('#fixArea1')
// const fixTypeModal=document.querySelector('#fixTypeModal')
// const fixTypecancelBtn=document.querySelector('#fix-type-cancelBtn')
// const fixTypesubmitBtn=document.querySelector('#fix-type-submitBtn')

const searchBar8=document.querySelector('#searchBar8')
const searchRBookName=searchBar8.querySelector('#readerBookName')
const searchRAuthoName=searchBar8.querySelector('#readerAuthorName')
const searchRPublishName=searchBar8.querySelector('#readerPublishName')
const searchRBookBtn=searchBar8.querySelector('.btn-search')
const pagination8=document.querySelector('#contain8 .pagination')
const prevBtn8=pagination8.querySelector('.prev-page')
const nextBtn8=pagination8.querySelector('.next-page')
const pageIndex8=pagination8.querySelector('.pageIndex')
const toPageIndex8=pagination8.querySelector('.toPageIndex')
const pageConfirmBtn8=pagination8.querySelector('.page-confirm')
const pageSelect8=pagination8.querySelector('select')
const totalNumArea8=pagination8.querySelector('.totalNum')

const rBookTypeSelect=document.querySelector('#rBookTypeSelect')
let targetRBookName
let targetRAuthorName
let targetRPublishName
let targetRBookType
let arr8 
let tpage8=1
let pageSize8=5
let totalNum8
let limitPage8
let currentEditRow8 //存储当前正在编辑的行

if(localStorage.getItem('token')=='user'){
  axios({
    url:`http://localhost:8088/user/category/list`,
    method:'get',
    headers: {
      'userToken': `${localStorage.getItem('id-token')}`
    }
   
}).then(result=>{
    //console.log("结果是"+result)
    result.data.data.forEach((element,index) => {
      //console.log(element.name)
      const option1 = document.createElement('option'); 
      
      option1.value = element.name;
      option1.textContent = element.name;

      option1.dataset.id=index+1
  
      rBookTypeSelect.appendChild(option1); 
      
    });
}).catch(error=>{
    console.log(error)
    //alert(error.response.data.message)
    alert('网络连接错误')
})
}

rBookTypeSelect.addEventListener('change',function(){
  const selectedOption=this.options[this.selectedIndex]
  targetRBookType=selectedOption.dataset.id
})

searchRBookBtn.addEventListener('click',function(){
  console.log(searchRBookName)
  targetRBookName=searchRBookName.value
  targetRAuthorName=searchRAuthoName.value
  targetRPublishName=searchRPublishName.value
  console.log(targetRAuthorName,targetRPublishName)
  tpage8=1
    pageIndex8.value=tpage8
  render8()
})

prevBtn8.addEventListener('click',function(){
  if(tpage8>1){
    tpage8--
    pageIndex8.value=tpage8
    render8()
  }
})
nextBtn8.addEventListener('click',function(){
  if(tpage8<limitPage8){
    tpage8++
    pageIndex8.value=tpage8
    render8()
  }
})
pageConfirmBtn8.addEventListener('click',function(){
  const targetPage=parseInt(toPageIndex8.value)
  console.log(targetPage)
  if(targetPage>0&&targetPage<=limitPage8){
    tpage8=targetPage
    pageIndex8.value=tpage8
    pageSize8=pageSelect8.value
    render8()
  }else{
    alert('请输入正确的页码')
  }
  
})

pageSelect8.addEventListener('change',function(){
  pageSize8=pageSelect8.value
  tpage8=1
    pageIndex8.value=tpage8
    render8()
})
function render8() {
  axios({
    url:`http://localhost:8088/user/book/page`,
    method:'get',
    headers: {
      'userToken': `${localStorage.getItem('id-token')}`
    },
    params:{
      page:tpage8,
      pageSize:pageSize8,
      name:targetRBookName,
      categoryId:targetRBookType,
      author:targetRAuthorName,
      publish:targetRPublishName
      
    }
    
}).then(result=>{
    //console.log(result)
    if(result.data.code=='1'){
      totalNumArea8.innerHTML=result.data.data.total
      totalNum8=parseInt(result.data.data.total)
      pageSize8=parseInt(pageSelect8.value)
      limitPage8=Math.ceil(totalNum8/pageSize8)
      arr8=[]
      
      for(let i=0;i<result.data.data.records.length;i++){
        arr8.push({
          id:result.data.data.records[i].id,
          author:result.data.data.records[i].author,
          categoryName:result.data.data.records[i].categoryName,
          edition:result.data.data.records[i].edition,
          name:result.data.data.records[i].name,
          publish:result.data.data.records[i].publish,
          stock:result.data.data.records[i].stock
        })
      }
      //console.log(arr6)
      const trArr = arr8.map(function (ele, index) {
        return `<tr data-id="${index}"data-bookid=${ele.id}>
            <td id='n1'>${ele.name}</td>
            <td id='n2'>${ele.publish}</td>
            <td id='n3'>${ele.edition}</td>
            <td id='n4'>${ele.author}</td>
            <td id='n5'>${ele.categoryName}</td>
            <td id='n6'>${ele.stock}</td>
          <td>
            <button class="btn-abnormal-return">借阅</button>
          </td>
        </tr>
        `
    })
    //console.log(trArr)
    tbody8.innerHTML = trArr.join('')
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
if(localStorage.getItem('token')=='user')
render8()

// //新增书籍按钮
// addBookBtn.addEventListener('click', function () {
//     console.log('点击到了')
//     addBookModal.style.display = 'block'
// })

// //按到空白处
// window.addEventListener('click', function (event) {
//   if (event.target === addBookModal) {
//       addBookModal.style.display = 'none';
//   }else if(event.target==fixBookModal){
//     fixBookModal.style.display='none'
//   }
// });
// //取消按钮
// bookcancelBtn.addEventListener('click', function () {
//   addBookModal.style.display = 'none';
// });

// //修改书本的取消按钮
// fixBookcancelBtn.addEventListener('click',function(){
//   fixBookModal.style.display = 'none'
// })

// //修改书籍的保存按钮
// fixBooksubmitBtn.addEventListener('click',function(e){
//   e.preventDefault()
 
//   const nfbookName=fbookName.value
//   const nfbookCategory=fbookCategory.value
//   const nfauthor=fauthor.value
//   const nfpubliser=fpubliser.value
  
//   const nfnum=fnum.value
//   if(!nfbookName||!nfbookCategory||!nfauthor||!nfpubliser||!nfnum){
//     alert('输入的内容不能为空')
//   }else{
//     if(currentEditRow2){
//       // currentEditRow2.querySelector('#n1').textContent=nfisbn
//       // currentEditRow2.querySelector('#n2').textContent=nfbookName
//       // currentEditRow2.querySelector('#n3').textContent=nfbookCategory
//       // currentEditRow2.querySelector('#n4').textContent=nfauthor
//       // currentEditRow2.querySelector('#n5').textContent=nfpubliser
//       // currentEditRow2.querySelector('#n6').textContent=nfprice
//       // currentEditRow2.querySelector('#n7').textContent=nflag
//       // currentEditRow2.querySelector('#n8').textContent=nfnum
//     }
//     fixBookModal.style.display='none'
//     const id=currentEditRow2.dataset.id
//     const bookid=currentEditRow2.dataset.bookid
//     const bookData={
//       id:bookid,
//       name:fbookName.value,
//       categoryId:0,
//       categoryName:fbookCategory.value,
//       description:null,
//       stock:fnum.value,
//       author:fauthor.value,
//       publish:fpubliser.value,
//       edition:fedition.value,
//   }
//     axios({
//       url:'http://localhost:8088/employee/book',
//       method:'put',
//       headers:{
//           "Content-Type":"application/json",
//           'employeeToken': `${localStorage.getItem('id-token')}`
//       },
//       data:JSON.stringify(bookData)
//   }).then(result=>{
//       // console.log(result)
//       if(result.data.code=='1'){
//         render2()
//         addBookForm.reset()
        
//         // 隐藏弹窗
//         addBookModal.style.display = 'none';
//       }
//       else{
//           alert(result.data.msg)
//       }
      
//   }).catch(error=>{
//       console.log(error)
//       //alert(error.response.data.message)
//       alert('网络连接错误')
//   })
//     arr2[id].isbn=nfisbn
//     arr2[id].bookName=nfbookName
//     arr2[id].bookCategory=nfbookCategory
//     arr2[id].author=nfauthor
//     arr2[id].publiser=nfpubliser
//     arr2[id].price=nfprice
//     arr2[id].lag=nflag
//     arr2[id].num=nfnum
//     render2()
//     localStorage.setItem('bookData',JSON.stringify(arr2))
//   }
// })

// // 点击保存书籍按钮（这里只是简单示例，实际需与后端交互）
// booksubmitBtn.addEventListener('click', function (e) {
//   e.preventDefault();
//   if(!bookName.value||!bookCategory.value||!author.value||!publiser.value||!num.value
//   ){
//     alert('输入的内容不能为空')
//   }else{
//     const bookData={
//       id:0,
//       name:bookName.value,
//       categoryId:0,
//       categoryName:bookCategory.value,
//       description:null,
//       stock:num.value,
//       author:author.value,
//       publish:publiser.value,
//       edition:edition.value,
//   }
//     axios({
//       url:'http://localhost:8088/employee/book',
//       method:'post',
//       headers:{
//           "Content-Type":"application/json",
//           'employeeToken': `${localStorage.getItem('id-token')}`
//       },
//       data:JSON.stringify(bookData)
//   }).then(result=>{
//       // console.log(result)
//       if(result.data.code=='1'){
//         render2()
//         addBookForm.reset()
        
//         // 隐藏弹窗
//         addBookModal.style.display = 'none';
//       }
//       else{
//           alert(result.data.msg)
//       }
      
//   }).catch(error=>{
//       console.log(error)
//       //alert(error.response.data.message)
//       alert('网络连接错误')
//   })
    
    
//   }
  
//   // 这里可添加向服务器发送数据等操作
// });

tbody8.addEventListener('click', function(e) {
  // 获取所有借书按钮

  if(e.target.classList.contains('btn-abnormal-return')){
    console.log('已经点击了借书按钮')
      const row = e.target.closest('tr');
      const bookId=row.dataset.bookid
      //console.log(id)
      axios({
        url:`http://localhost:8088/user/borrow/borrow/${bookId}`,
        method:'post',
        headers: {
            "Content-Type":"application/json",
          'userToken': `${localStorage.getItem('id-token')}`
        },
        
        
    }).then(result=>{
        if(result.data.code=='1'){
          render7()
          render8()
          alert("已发出借阅申请，请等待通过")
        }else{
          alert(result.data.msg)
        }
        
        
    }).catch(error=>{
        console.log(error)
        //alert(error.response.data.message)
        alert('网络连接错误')
    })
      
  }
});