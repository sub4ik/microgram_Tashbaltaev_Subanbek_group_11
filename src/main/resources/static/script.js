// if (localStorage.getItem('username') != null){
//     document.querySelector('.login-wrap').hidden = true
// }
// const formSignIn = document.querySelector('#sign_in')
const formSignUp = document.querySelector('#sign_up')

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
    console.log('qweqwewq')
    const response = await fetch(baseUrl + '/users/registration', settings);
    // const responseData = await response.json();
    // console.log(responseData);
    console.log('alena')

}

formSignUp.addEventListener('submit', e => {
    e.preventDefault()

    const userFormData = new FormData(e.target)
    console.log(userFormData.get("username"))
    createUser(userFormData);
})