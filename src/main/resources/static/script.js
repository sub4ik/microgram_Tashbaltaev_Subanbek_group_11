
// document.querySelector('.login-wrap').hidden = true
const formSignIn = document.querySelector('#sign_in')
const formSignUp = document.querySelector('#sign_up')

if (localStorage.getItem('username') != null){

    document.querySelector('.login-wrap').hidden = true
    document.querySelector('.page').hidden = false
} else {
    document.querySelector('.login-wrap').hidden = false
    document.querySelector('.page').hidden = true
}

async function authUser(userFormData) {
    const a = JSON.stringify(Object.fromEntries(userFormData));
    console.log(a)
    const user = {
        username : userFormData.get("accountName"),
        password : userFormData.get("password")
    }
    console.log(user)
    const baseUrl = 'http://localhost:8081/users/login';
    const settings = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Basic ' + window.btoa(user.username + ':' + user.password)
        }
    };
    // console.log('qweqwewq')
    const response = await fetch(baseUrl + `?text=${userFormData.get("accountName")}`, settings)
    // .then(return response.json())
    console.log(response)
    
}

formSignIn.addEventListener('submit', e => {
    e.preventDefault()

    const userFormData = new FormData(e.target)
    console.log(userFormData.get("accountName"))
    authUser(userFormData);
})

// ------------------------------------------------------------
// document.querySelector('.login-wrap').hidden = true

// async function createUser(userFormData) {
//     const a = JSON.stringify(Object.fromEntries(userFormData));
//     console.log(a)
//     const baseUrl = 'http://localhost:8081';
//     const settings = {
//         method: 'POST',
//         headers: {
//             'Content-Type': 'application/json'
//         },
//         body: a
//     };
//     // console.log('qweqwewq')
//     const response = await fetch(baseUrl + '/users/registration', settings);
// }

// formSignUp.addEventListener('submit', e => {
//     e.preventDefault()

//     const userFormData = new FormData(e.target)
//     console.log(userFormData.get("username"))
//     createUser(userFormData);
// })
