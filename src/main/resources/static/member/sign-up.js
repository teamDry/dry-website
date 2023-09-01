document.addEventListener("DOMContentLoaded", function() {
    document.getElementById("btnSignUp").addEventListener("click", signUp);
});

function signUp() {
    let data = {
        id: document.querySelector('input[name="id"]').value,
        password: document.querySelector('input[name="password"]').value,
        nickname: document.querySelector('input[name="nickname"]').value,
        email: document.querySelector('input[name="email"]').value
    };

    if(data.id.length == 0 || data.password.length == 0 || data.nickname.length == 0 || data.email.length == 0) {
        alert("회원 정보를 모두 입력해 주세요.");
    }else {
        let xhr = new XMLHttpRequest();
        xhr.open("POST", "/member/sign-up", true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.send(JSON.stringify(data));

        xhr.onload = () => {
            if(xhr.readyState == xhr.DONE) {
                if(xhr.Status == 200) {
                    const resultData = xhr.response;
                    const jsonData = JSON.parse(resultData);
                    if(Object.keys(jsonData).length !== 0) {
                        alert("Sign Up Success");
                    }else{
                        alert("등록안됨");
                    }
                }
            }
        }
    }
}