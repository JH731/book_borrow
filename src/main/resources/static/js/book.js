const addBookModal = document.querySelector('#addBookModal')
const addBookBtn = document.querySelector('#addBookBtn')
const addBookForm=document.querySelector('#addBookForm')
const bookcancelBtn=document.querySelector('#book-cancelBtn')
const booksubmitBtn=document.querySelector('#book-submitBtn')
const fixBookModal = document.querySelector('#fixBookModal')
const fixBookForm=document.querySelector('#fixBookForm')
const fixBookcancelBtn=document.querySelector('#fix-book-cancelBtn')
const fixBooksubmitBtn=document.querySelector('#fix-book-submitBtn')
const tbody2 = document.querySelector('#contain2 tbody')
// 添加弹窗中相关内容

const bookName=addBookModal.querySelector('.form-item #bookName')
const bookCategory=addBookModal.querySelector('.form-item #bookCategory')
const author=addBookModal.querySelector('.form-item #author')
const publiser=addBookModal.querySelector('.form-item #publiser')
const edition=addBookModal.querySelector('.form-item #edition')

const num=addBookModal.querySelector('.form-item #num')
// 修改弹窗中相关内容

const fbookName=fixBookModal.querySelector('.form-item #bookName')
const fbookCategory=fixBookModal.querySelector('.form-item #bookCategory')
const fauthor=fixBookModal.querySelector('.form-item #author')
const fpubliser=fixBookModal.querySelector('.form-item #publiser')
const fedition=fixBookModal.querySelector('.form-item #edition')
const fnum=fixBookModal.querySelector('.form-item #num')
// 
// const editTextarea1=document.querySelector('#fixArea1')
// const fixTypeModal=document.querySelector('#fixTypeModal')
// const fixTypecancelBtn=document.querySelector('#fix-type-cancelBtn')
// const fixTypesubmitBtn=document.querySelector('#fix-type-submitBtn')

const searchBar2=document.querySelector('#searchBar2')
const searchBookName=searchBar2.querySelector('input')
const searchBookBtn=searchBar2.querySelector('.btn-search')
const pagination2=document.querySelector('#contain2 .pagination')
const prevBtn2=pagination2.querySelector('.prev-page')
const nextBtn2=pagination2.querySelector('.next-page')
const pageIndex2=pagination2.querySelector('.pageIndex')
const toPageIndex2=pagination2.querySelector('.toPageIndex')
const pageConfirmBtn2=pagination2.querySelector('.page-confirm')
const pageSelect2=pagination2.querySelector('select')
const totalNumArea2=pagination2.querySelector('.totalNum')

const bookTypeSelect=document.querySelector('#bookTypeSelect')
let targetBookName
let targetBookType
let arr2 
let tpage2=1
let pageSize2=5
let totalNum2
let limitPage2
let currentEditRow2 //存储当前正在编辑的行



bookTypeSelect.addEventListener('change',function(){
  const selectedOption=this.options[this.selectedIndex]
  targetBookType=selectedOption.dataset.id
})

searchBookBtn.addEventListener('click',function(){
  targetBookName=searchBookName.value
  console.log(targetBookName,targetBookType)
  tpage2=1
    pageIndex2.value=tpage2
  render2()
})

prevBtn2.addEventListener('click',function(){
  if(tpage2>1){
    tpage2--
    pageIndex2.value=tpage2
    render2()
  }
})
nextBtn2.addEventListener('click',function(){
  if(tpage2<limitPage2){
    tpage2++
    pageIndex2.value=tpage2
    render2()
  }
})
pageConfirmBtn2.addEventListener('click',function(){
  const targetPage=parseInt(toPageIndex2.value)
  console.log(targetPage)
  if(targetPage>0&&targetPage<=limitPage2){
    tpage2=targetPage
    pageIndex2.value=tpage2
    pageSize2=pageSelect2.value
    render2()
  }else{
    alert('请输入正确的页码')
  }
  
})

pageSelect2.addEventListener('change',function(){
  pageSize2=pageSelect2.value
  tpage2=1
    pageIndex2.value=tpage2
    render2()
})
function render2() {
  if(localStorage.getItem('token')=='employee'){
    axios({
      url:`http://localhost:8088/employee/category/list`,
      method:'get',
      headers: {
        'employeeToken': `${localStorage.getItem('id-token')}`
      }
     
  }).then(result=>{
      //console.log(result)
      while(bookTypeSelect.options.length>1){
        bookTypeSelect.remove(1)
      }
      while(bookCategory.options.length>1){
        bookCategory.remove(1)
      }
      while(fbookCategory.options.length>1){
        fbookCategory.remove(1)
      }
      result.data.data.forEach((element,index) => {
        //console.log(element)
        const option1 = document.createElement('option'); 
        const option2 = document.createElement('option');
        const option3 = document.createElement('option');
        
        option1.value = element;
        option1.textContent = element;
        option2.value = element;
        option2.textContent = element;
        option3.value = element;
        option3.textContent = element;
  
        option1.dataset.id=index+1
        option2.dataset.id=index+1
        option3.dataset.id=index+1
    
        bookTypeSelect.appendChild(option1); 
        bookCategory.appendChild(option2); 
        fbookCategory.appendChild(option3); 
      });
  }).catch(error=>{
      console.log(error)
      //alert(error.response.data.message)
      alert('网络连接错误')
  })
  }
  axios({
    url:`http://localhost:8088/employee/book/page`,
    method:'get',
    headers: {
      'employeeToken': `${localStorage.getItem('id-token')}`
    },
    params:{
      page:tpage2,
      pageSize:pageSize2,
      name:targetBookName,
      categoryId:targetBookType,
      
    }
    
}).then(result=>{
    //console.log(result)
    if(result.data.code=='1'){
      totalNumArea2.innerHTML=result.data.data.total
      totalNum2=parseInt(result.data.data.total)
      pageSize2=parseInt(pageSelect2.value)
      limitPage2=Math.ceil(totalNum2/pageSize2)
      arr2=[]
      
      for(let i=0;i<result.data.data.records.length;i++){
        arr2.push({
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
      const trArr = arr2.map(function (ele, index) {
        return `<tr data-id="${index}"data-bookid=${ele.id}>
            
            <td id='n1'>${ele.name}</td>
            <td id='n2'>${ele.publish}</td>
            <td id='n3'>${ele.edition}</td>
            <td id='n4'>${ele.author}</td>
            <td id='n5'>${ele.categoryName}</td>
            <td id='n6'>${ele.stock}</td>
          <td>
            <button class="btn-abnormal-return">修改</button><button class="btn-delete-record">删除</button>
          </td>
        </tr>
        `
    })
    //console.log(trArr)
    tbody2.innerHTML = trArr.join('')
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
render2()

//新增书籍按钮
addBookBtn.addEventListener('click', function () {
    console.log('点击到了')
    addBookModal.style.display = 'block'
})

//按到空白处
window.addEventListener('click', function (event) {
  if (event.target === addBookModal) {
      addBookModal.style.display = 'none';
  }else if(event.target==fixBookModal){
    fixBookModal.style.display='none'
  }
});
//取消按钮
bookcancelBtn.addEventListener('click', function () {
  addBookModal.style.display = 'none';
});

//修改书本的取消按钮
fixBookcancelBtn.addEventListener('click',function(){
  fixBookModal.style.display = 'none'
})

//修改书籍的保存按钮
fixBooksubmitBtn.addEventListener('click',function(e){
  e.preventDefault()
 
  const nfbookName=fbookName.value
  const nfbookCategory=fbookCategory.value
  const nfauthor=fauthor.value
  const nfpubliser=fpubliser.value
  
  const nfnum=fnum.value
  if(!nfbookName||!nfbookCategory||!nfauthor||!nfpubliser||!nfnum){
    alert('输入的内容不能为空')
  }else{
    if(currentEditRow2){
      // currentEditRow2.querySelector('#n1').textContent=nfisbn
      // currentEditRow2.querySelector('#n2').textContent=nfbookName
      // currentEditRow2.querySelector('#n3').textContent=nfbookCategory
      // currentEditRow2.querySelector('#n4').textContent=nfauthor
      // currentEditRow2.querySelector('#n5').textContent=nfpubliser
      // currentEditRow2.querySelector('#n6').textContent=nfprice
      // currentEditRow2.querySelector('#n7').textContent=nflag
      // currentEditRow2.querySelector('#n8').textContent=nfnum
    }
    fixBookModal.style.display='none'
    const id=currentEditRow2.dataset.id
    const bookid=currentEditRow2.dataset.bookid
    const bookData={
      id:bookid,
      name:fbookName.value,
      categoryId:0,
      categoryName:fbookCategory.value,
      description:null,
      stock:fnum.value,
      author:fauthor.value,
      publish:fpubliser.value,
      edition:fedition.value,
  }
    axios({
      url:'http://localhost:8088/employee/book',
      method:'put',
      headers:{
          "Content-Type":"application/json",
          'employeeToken': `${localStorage.getItem('id-token')}`
      },
      data:JSON.stringify(bookData)
  }).then(result=>{
      // console.log(result)
      if(result.data.code=='1'){
        render2()
        addBookForm.reset()
        
        // 隐藏弹窗
        addBookModal.style.display = 'none';
      }
      else{
          alert(result.data.msg)
      }
      
  }).catch(error=>{
      console.log(error)
      //alert(error.response.data.message)
      alert('网络连接错误')
  })
    arr2[id].isbn=nfisbn
    arr2[id].bookName=nfbookName
    arr2[id].bookCategory=nfbookCategory
    arr2[id].author=nfauthor
    arr2[id].publiser=nfpubliser
    arr2[id].price=nfprice
    arr2[id].lag=nflag
    arr2[id].num=nfnum
    render2()
    localStorage.setItem('bookData',JSON.stringify(arr2))
  }
})

// 点击保存书籍按钮（这里只是简单示例，实际需与后端交互）
booksubmitBtn.addEventListener('click', function (e) {
  e.preventDefault();
  if(!bookName.value||!bookCategory.value||!author.value||!publiser.value||!num.value
  ){
    alert('输入的内容不能为空')
  }else{
    const bookData={
      id:0,
      name:bookName.value,
      categoryId:0,
      categoryName:bookCategory.value,
      description:null,
      stock:num.value,
      author:author.value,
      publish:publiser.value,
      edition:edition.value,
  }
    axios({
      url:'http://localhost:8088/employee/book',
      method:'post',
      headers:{
          "Content-Type":"application/json",
          'employeeToken': `${localStorage.getItem('id-token')}`
      },
      data:JSON.stringify(bookData)
  }).then(result=>{
      // console.log(result)
      if(result.data.code=='1'){
        render2()
        addBookForm.reset()
        
        // 隐藏弹窗
        addBookModal.style.display = 'none';
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

tbody2.addEventListener('click', function(e) {
  // 获取所有删除按钮
  if(e.target.classList.contains('btn-delete-record')){
      const row = e.target.closest('tr');
      const id=row.dataset.id
      // 添加删除动画效果
      row.style.transition = 'all 0.3s ease';
      row.style.opacity = '0';
      row.style.transform = 'translateX(50px)';
      const ids=[row.dataset.bookid]
      axios({
        url:`http://localhost:8088/employee/book`,
        method:'delete',
        headers: {
          'employeeToken': `${localStorage.getItem('id-token')}`
        },
        params:{
          ids:row.dataset.bookid
        }
        
    }).then(result=>{
      //console.log(result)
      setTimeout(() => {
        arr2.splice(id,1)
        render2()
       
      }, 300);
        
    }).catch(error=>{
        console.log(error)
        //alert(error.response.data.message)
        alert('网络连接错误')
    })
      
  }
  if(e.target.classList.contains('btn-abnormal-return')){
      currentEditRow2 = e.target.closest('tr');
      //const typeContent=currentEditRow2.querySelector('#tableContent').textContent
      // fisbn.value=currentEditRow2.querySelector('#n1').textContent
      // fbookName.value=currentEditRow2.querySelector('#n2').textContent
      // fbookCategory.value=currentEditRow2.querySelector('#n3').textContent
      // fauthor.value=currentEditRow2.querySelector('#n4').textContent
      // fpubliser.value=currentEditRow2.querySelector('#n5').textContent
      // fprice.value=currentEditRow2.querySelector('#n6').textContent
      // flag.value=currentEditRow2.querySelector('#n7').textContent
      // fnum.value=currentEditRow2.querySelector('#n8').textContent
      fixBookModal.style.display='block'
  }
});