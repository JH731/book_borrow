//通用验证函数
function verifyName(name){
    const reg = /^[a-zA-Z0-9-_]{5,10}$/
    if(!reg.test(name)){
        alert("用户名格式错误，请按要求重新输入")
        return false
    }else{
        return true
    }
}

function verifyPwd(pwd){
    const reg = /^[a-zA-Z0-9-_]{6,20}$/
    if(!reg.test(pwd)){
        alert("密码格式错误，请按要求重新输入")
        return false
    }else{
        return true
    }
}

function verifyPhone(phone){
    const reg = /^1(3\d|4[5-9]|5[0-35-9]|6[567]|7[0-8]|8\d|9[0-35-9])\d{8}$/
    if(!reg.test(phone)){
        alert("电话号码格式错误，请按要求重新输入")
        return false
    }else{
        return true
    }
}