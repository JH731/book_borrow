const rAddBorrowModal = document.querySelector('#rAddBorrowModal')
const rAddBorrowBtn = document.querySelector('#rAddBorrowBtn')
const rAddReturnBtn = document.querySelector('#rAddReturnBtn')
const rAddBorrowForm = document.querySelector('#rAddBorrowForm')
const rBorrowcancelBtn = document.querySelector('#rBorrow-cancelBtn')
const rBorrowsubmitBtn = document.querySelector('#rBorrow-submitBtn')
const tbody7 = document.querySelector('#contain7 tbody')
const editTextarea7 = document.querySelector('#fixArea7')
const fixRBorrowModal = document.querySelector('#fixRBorrowModal')
const fixRBorrowcancelBtn = document.querySelector('#fix-RBorrow-cancelBtn')
const fixRBorrowsubmitBtn = document.querySelector('#fix-RBorrow-submitBtn')

const borrowRBookTypeSelect = document.querySelector('#borrowRBookTypeSelect')
const borrowRBookReturnSelect = document.querySelector('#borrowRBookReturnSelect')
const borrowRBookName = rAddBorrowModal.querySelector('.form-item #rBookName')
const borrowRBookCategory = rAddBorrowModal.querySelector('.form-item #rBookCategory')
const rBorrower = rAddBorrowModal.querySelector('.form-item #rBorrower')

const fRBorrownormal = fixRBorrowModal.querySelector('.form-item #normal')

const searchBar7 = document.querySelector('#searchBar7')
const searchRBorrowName = searchBar7.querySelector('#readerBookName')
const searchRBorrowAuthorName = searchBar7.querySelector('#readerAuthorName')
const searchRBorrowPublishName = searchBar7.querySelector('#readerPublishName')
const searchRBorrowBtn = searchBar7.querySelector('.btn-search')
const pagination7 = document.querySelector('#contain7 .pagination')
const prevBtn7 = pagination7.querySelector('.prev-page')
const nextBtn7 = pagination7.querySelector('.next-page')
const pageIndex7 = pagination7.querySelector('.pageIndex')
const toPageIndex7 = pagination7.querySelector('.toPageIndex')
const pageConfirmBtn7 = pagination7.querySelector('.page-confirm')
const pageSelect7 = pagination7.querySelector('select')
const totalNumArea7 = pagination7.querySelector('.totalNum')
const operationTh=document.querySelector('#contain7 .book-table thead th:last-child')
let targetRBorrowName
let targetRBorrowAuthorName
let targetRBorrowPublishName
let targetRBorrowType

let arr7
let tpage7 = 1
let pageSize7 = 5
let totalNum7
let limitPage7
let isRBorrow = true

let currentEditRow7 //存储当前正在编辑的行

function returnState(status){
    if(status==0){
        return "未批准"
    }else if(status==1){
        return "已通过"
    }else if(status==2){
        return "未通过"
    }else if(status==3){
        return "已归还"
    }
}

if (localStorage.getItem('token') == 'user') {
    operationTh.textContent='状态'
    axios({
        url: `http://localhost:8088/user/category/list`,
        method: 'get',
        headers: {
            'userToken': `${localStorage.getItem('id-token')}`
        }

    }).then(result => {
        //console.log(result)
        result.data.data.forEach((element, index) => {
            //console.log(element)
            const option1 = document.createElement('option');


            option1.value = element.name;
            option1.textContent = element.name;


            option1.dataset.id = index + 1


            borrowRBookTypeSelect.appendChild(option1);

        });
    }).catch(error => {
        console.log(error)
        //alert(error.response.data.message)
        alert('网络连接错误')
    })
}

borrowRBookTypeSelect.addEventListener('change', function () {
    const selectedOption = this.options[this.selectedIndex]
    console.log(selectedOption.value)
    targetRBorrowType = selectedOption.value

})

rAddBorrowBtn.addEventListener('click', function () {
    isRBorrow = true
    tpage7 = 1
    pageIndex7.value = tpage7
    operationTh.textContent='状态'
    render7()
})

rAddReturnBtn.addEventListener('click', function () {
    isRBorrow = false
    tpage7 = 1
    pageIndex7.value = tpage7
    operationTh.textContent='操作'
    render7()
})

searchRBorrowBtn.addEventListener('click', function () {
    targetRBorrowName = searchRBorrowName.value
    targetRBorrowAuthorName=searchRBorrowAuthorName.value
    targetRBorrowPublishName=searchRBorrowPublishName.value
    // console.log(targetUserName)
    tpage7 = 1
    pageIndex7.value = tpage7
    alert("成功搜查")
    render7()
})

prevBtn7.addEventListener('click', function () {
    if (tpage7 > 1) {
        tpage7--
        pageIndex7.value = tpage7
        render7()
    }
})
nextBtn7.addEventListener('click', function () {
    if (tpage7 < limitPage7) {
        tpage7++
        pageIndex7.value = tpage7
        render7()
    }
})
pageConfirmBtn7.addEventListener('click', function () {
    const targetPage = parseInt(toPageIndex7.value)
    console.log(targetPage)
    if (targetPage > 0 && targetPage <= limitPage7) {
        tpage7 = targetPage
        pageIndex7.value = tpage7
        pageSize7 = pageSelect7.value
        render7()
    } else {
        alert('请输入正确的页码')
    }

})

pageSelect7.addEventListener('change', function () {
    pageSize7 = pageSelect7.value
    tpage7 = 1
    pageIndex7.value = tpage7
    render7()
})

function render7() {
    if (isRBorrow) {
        axios({
            url: `http://localhost:8088/user/borrow/pageQuery`,
            method: 'get',
            headers: {
                'userToken': `${localStorage.getItem('id-token')}`
            },
            params: {
                bookName: targetRBorrowName,
                categoryName: targetRBorrowType,
                page: tpage7,
                pageSize: pageSize7,
                author:targetRBorrowAuthorName,
                publish:targetRBorrowPublishName,
                status: null


            }

        }).then(result => {
            console.log(result)
            if (result.data.code == '1') {
                totalNumArea7.innerHTML = result.data.data.total
                totalNum7 = parseInt(result.data.data.total)
                pageSize7 = parseInt(pageSelect7.value)
                limitPage7 = Math.ceil(totalNum7 / pageSize7)
                arr7 = []

                for (let i = 0; i < result.data.data.records.length; i++) {
                    arr7.push({
                        id: result.data.data.records[i].id,
                        author: result.data.data.records[i].author,
                        categoryName: result.data.data.records[i].categoryName,
                        edition: result.data.data.records[i].edition,
                        bookName: result.data.data.records[i].bookName,
                        publish: result.data.data.records[i].publish,
                        userName: result.data.data.records[i].userName,
                        borrowTime:result.data.data.records[i].startTime,
                        endTime:result.data.data.records[i].returnTime,
                        returnTime:result.data.data.records[i].endTime,
                        status:result.data.data.records[i].status
                    })
                }
                //console.log(arr6)
                const trArr = arr7.map(function (ele, index) {
                    return `<tr data-id="${index}"data-borrowid=${ele.id}>
              <td id='bookName'>${ele.bookName}</td>
               <td id='student'>${ele.publish}</td>
            
            <td id='cardId'>${ele.edition}</td>
             <td>${ele.author}</td>
           <td id='bookType'>${ele.categoryName}</td>
            <td id='borrowTime'>${ele.borrowTime==null?'未开始':ele.borrowTime}</td>
            <td>${ele.endTime==null?'未开始':ele.endTime}</td>
            
            <td>
              ${returnState(ele.status)}
            </td>
          </tr>
          `
                })
                //console.log(trArr)
                tbody7.innerHTML = trArr.join('')
                //console.log(trArr)

            }
            else {
                alert(result.data.msg)
            }

        }).catch(error => {
            console.log(error)
            //alert(error.response.data.message)
            alert('网络连接错误')
        })
    } else {

        {
            axios({
                url: `http://localhost:8088/user/back/page`,
                method: 'get',
                headers: {
                    'userToken': `${localStorage.getItem('id-token')}`
                },
                params: {
                    userName: targetRBorrowName,
                    categoryName: targetRBorrowType,
                    page: tpage7,
                    pageSize: pageSize7,
                    status: null


                }

            }).then(result => {
                console.log(result)
                if (result.data.code == '1') {
                    totalNumArea7.innerHTML = result.data.data.total
                    totalNum7 = parseInt(result.data.data.total)
                    pageSize7 = parseInt(pageSelect7.value)
                    limitPage7 = Math.ceil(totalNum7 / pageSize7)
                    arr7 = []

                    for (let i = 0; i < result.data.data.records.length; i++) {
                        arr7.push({
                            id: result.data.data.records[i].id,
                            author: result.data.data.records[i].author,
                            categoryName: result.data.data.records[i].categoryName,
                            edition: result.data.data.records[i].edition,
                            bookName: result.data.data.records[i].bookName,
                            publish: result.data.data.records[i].publish,
                            userName: result.data.data.records[i].userName,
                            borrowTime:result.data.data.records[i].startTime,
                            endTime:result.data.data.records[i].returnTime
                        })
                    }
                    //console.log(arr6)
                    const trArr = arr7.map(function (ele, index) {
                        return `<tr data-id="${index}"data-borrowid=${ele.id}>
                    <td id='bookName'>${ele.bookName}</td>
                     <td id='student'>${ele.publish}</td>
                  
                  <td id='cardId'>${ele.edition}</td>
                   <td>${ele.author}</td>
                 <td id='bookType'>${ele.categoryName}</td>
                  <td id='borrowTime'>${ele.borrowTime}</td>
                  <td>${ele.endTime}</td>
                  
                  <td>
                    <button class="btn-abnormal-return">还书</button>
                  </td>
                </tr>
                `
                    })
                    //console.log(trArr)
                    tbody7.innerHTML = trArr.join('')
                    //console.log(trArr)

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




}
if (localStorage.getItem('token') == 'user')
    render7()

// addBorrowBtn.addEventListener('click', function () {
//   //console.log('点击到了')
//   addBorrowModal.style.display = 'block'
// })
//按到空白处
// window.addEventListener('click', function (event) {
//     if (event.target === rAddBorrowModal) {
//         rAddBorrowModal.style.display = 'none';
//     } else if (event.target == fixRBorrowModal) {
//         fixRBorrowModal.style.display = 'none';
//     }
// });
// //取消按钮
// rBorrowcancelBtn.addEventListener('click', function () {
//     rAddBorrowModal.style.display = 'none';
// });

// //修改类型的取消按钮
// fixRBorrowcancelBtn.addEventListener('click', function () {
//     fixRBorrowModal.style.display = 'none'
// })

// //修改类型的保存按钮
// fixRBorrowsubmitBtn.addEventListener('click', function (e) {
//     e.preventDefault()
//     const now = new Date();
//     const year = now.getFullYear();
//     const month = ('0' + (now.getMonth() + 1)).slice(-2); // 月份从0开始，补0并取后两位
//     const day = ('0' + now.getDate()).slice(-2); // 日期补0并取后两位
//     const hours = ('0' + now.getHours()).slice(-2); // 小时补0并取后两位
//     const minutes = ('0' + now.getMinutes()).slice(-2); // 分钟补0并取后两位
//     const seconds = ('0' + now.getSeconds()).slice(-2); // 秒数补0并取后两位
//     const customTimeString = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
//     const newContent = fRBorrownormal.value
//     let retClass
//     if (!newContent) {
//         alert('输入的内容不能为空')
//     } else {
//         if (currentEditRow7) {

//             currentEditRow7.querySelector('#returnTime').textContent = customTimeString
//             currentEditRow7.querySelector('#returnType span').textContent = newContent
//             currentEditRow7.querySelector('#returnType span').classList.remove('status-borrowing')
//             currentEditRow7.querySelector('.btn-abnormal-return').remove()
//             if (newContent == '正常还书') {
//                 currentEditRow7.querySelector('#returnType span').classList.add('status-normal-return')
//                 retClass = 'status-normal-return'
//             } else if (newContent == '延迟还书') {
//                 currentEditRow7.querySelector('#returnType span').classList.add('status-delay-return')
//                 retClass = 'status-delay-return'
//             } else if (newContent == '破损还书') {
//                 currentEditRow7.querySelector('#returnType span').classList.add('status-damaged-return')
//                 retClass = 'status-damaged-return'
//             } else if (newContent == '丢失赔书') {
//                 currentEditRow7.querySelector('#returnType span').classList.add('status-lost-book')
//                 retClass = 'status-lost-book'
//             }
//         }
//         fixRBorrowModal.style.display = 'none'
//         const id = currentEditRow7.dataset.id
//         arr7[id].returnTime = customTimeString
//         arr7[id].returnType = newContent
//         arr7[id].returnClass = retClass
//         arr7[id].btn = '<button class="btn-delete-record">删除</button>'
//         render7()
//         localStorage.setItem('borrowData', JSON.stringify(arr7))
//     }
// })

// rBorrowsubmitBtn.addEventListener('click', function (e) {
//     e.preventDefault();
//     const now = new Date();
//     const year = now.getFullYear();
//     const month = ('0' + (now.getMonth() + 1)).slice(-2); // 月份从0开始，补0并取后两位
//     const day = ('0' + now.getDate()).slice(-2); // 日期补0并取后两位
//     const hours = ('0' + now.getHours()).slice(-2); // 小时补0并取后两位
//     const minutes = ('0' + now.getMinutes()).slice(-2); // 分钟补0并取后两位
//     const seconds = ('0' + now.getSeconds()).slice(-2); // 秒数补0并取后两位
//     const customTimeString = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
//     if (!borrowRBookName.value || !borrowRBookCategory.value || !borrowCard.value || !rBorrower.value) {
//         alert('输入的内容不能为空')
//     } else {
//         arr7.push({
//             borrowbookName: borrowRBookName.value,
//             bookType: borrowRBookCategory.value,
//             cardId: borrowCard.value,
//             student: rBorrower.value,
//             borrowTime: customTimeString,
//             returnTime: '',
//             returnType: '在借中',
//             returnClass: 'status-borrowing',
//             btn: '<button class="btn-abnormal-return">还书</button><button class="btn-delete-record">删除</button>'
//         })
//         render7()
//         rAddBorrowForm.reset()
//         localStorage.setItem('borrowData', JSON.stringify(arr7))
//         // 隐藏弹窗
//         rAddBorrowModal.style.display = 'none';
//     }

//     // 这里可添加向服务器发送数据等操作
// });

tbody7.addEventListener('click', function (e) {
    // 获取所有删除按钮
    // if (e.target.classList.contains('btn-delete-record')) {
    //     const row = e.target.closest('tr');
    //     const id = row.dataset.id
    //     // 添加删除动画效果
    //     row.style.transition = 'all 0.3s ease';
    //     row.style.opacity = '0';
    //     row.style.transform = 'translateX(50px)';
    //     setTimeout(() => {
    //         arr7.splice(id, 1)
    //         render7()
    //         localStorage.setItem('borrowData', JSON.stringify(arr7))
    //     }, 300);
    // }
    if (e.target.classList.contains('btn-abnormal-return')) {
        if(!isRBorrow){
            console.log("已经点击了归还")
            currentEditRow7 = e.target.closest('tr');
            const borrowId=currentEditRow7.dataset.borrowid
            axios({
                url:`http://localhost:8088/user/back/add/${borrowId}`,
                method:'post',
                headers: {
                    "Content-Type":"application/json",
                  'userToken': `${localStorage.getItem('id-token')}`
                },
                
                
            }).then(result=>{
                render7()
                
                
            }).catch(error=>{
                console.log(error)
                //alert(error.response.data.message)
                alert('网络连接错误')
            })
        }
        
        // console.log(currentEditRow7)
        // // const typeContent=currentEditRow3.querySelector('#tableContent').textContent
        // fixRBorrowModal.style.display = 'block'
    }
});