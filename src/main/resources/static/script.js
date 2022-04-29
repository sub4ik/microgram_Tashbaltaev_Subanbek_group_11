
// document.querySelector('.login-wrap').hidden = true
const formSignIn = document.querySelector('#sign_in')
const formSignUp = document.querySelector('#sign_up')
const form = document.querySelector('.login-wrap')
const page = document.querySelector('.page')

if (localStorage.getItem('username') != null){

    formHidden(true)
} else {
    formHidden(false)
}

formSignIn.addEventListener('submit', e => {
    e.preventDefault()

    const userFormData = new FormData(e.target)
    console.log(userFormData.get("accountName"))
    authUser(userFormData);
})

formSignUp.addEventListener('submit', e => {
    e.preventDefault()

    const userFormData = new FormData(e.target)
    console.log(userFormData.get("username"))
    createUser(userFormData);
})


page.addEventListener('click', () => {
    console.log("vyhoju")
    localStorage.clear()
    formHidden(false)
})


async function authUser(userFormData) {
    const a = JSON.stringify(Object.fromEntries(userFormData));
    console.log(a)
    const user = {
        username : userFormData.get("accountName"),
        password : userFormData.get("password")
    }
    const baseUrl = 'http://localhost:8081/users/login';
    const settings = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Basic ' + window.btoa(user.username + ':' + user.password)
        }
    };
    const response = await fetch(baseUrl + `?text=${userFormData.get("accountName")}`, settings)
    .then (response=>{
        
        console.log(response)
        if(response.ok){
            localStorage.setItem('username', JSON.stringify(user))
            formHidden(true)
        } else alert("email или пароль не верны");

    })
    
}



// ------------------------------------------------------------
// document.querySelector('.login-wrap').hidden = true

async function createUser(userFormData) {
    const a = JSON.stringify(Object.fromEntries(userFormData));
    console.log(a)
    const baseUrl = 'http://localhost:8081';
    const settings = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: a
    };
    // console.log('qweqwewq')
    const user = {
        username : userFormData.get("accountName"),
        password : userFormData.get("password")
    }
    const response = await fetch(baseUrl + '/users/registration', settings)
    .then(response=>{
        console.log(response)
        if(response.ok){
            localStorage.setItem('username', JSON.stringify(user))
            formHidden(true)
        } else alert("Это имя или почта уже занато, введи другие данные");
    })

}
function formHidden(bool){
    form.hidden = bool;
    page.hidden = !bool;
}


