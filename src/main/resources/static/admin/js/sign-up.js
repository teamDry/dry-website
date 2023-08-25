let isIdOk = false;
let isPasswordOk = false;
let isPasswordCheckOk = false;
let isNicknameOk = false;
let isEmailOk = false;

// 회원가입 버튼 눌렸을때 ajax 처리
function performSignUp() {
    if(!isIdOk && !isPasswordOk && !isPasswordCheckOk && !isNicknameOk && !isEmailOk) {
        return;
    }

    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);

    const code = urlParams.get('code'); // 'John'

    const admin = {
        id: document.getElementById("id").value,
        password: document.getElementById("password").value,
        nickname: document.getElementById("nickname").value,
        email: document.getElementById("email").value
    };

    fetch("/api/admins/sign-up", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(admin)
    })
        .then(response => {
            if(response.ok) {
                return response.json();
            } else {
                throw Error("SignUp Error");
            }
        })
        .then(data => {
            // 회원가입한 id를 login id칸에 설정
            localStorage.setItem("id", data.id);
            window.location.href="/admins/login/code?code=" + code;
        })
        .catch(error => {
            console.log(error);
    });
}

// 회원가입 취소 버튼 눌렸을 때
function clickCancelButton() {
    window.location.href="/admins/login";
}

// ID 중복 검사
const idElement = document.getElementById("id");
idElement.addEventListener("focusout", idCheckEvent);

function idCheckEvent() {
    // 만약 값이 빈칸이면 바로 return ( 실행 안함 )
    if(dontRunWhenNothingValueInComponent(idElement)) {
        return;
    } else if(isValidId(idElement.value)) {
        isIdOk = false;
        changeBackgroundColor(document.getElementById("id-check"), isIdOk); // 되는 값인지 안되는 값인지 표시
        return;
    }

    fetch(`/api/admins/id/check?id=${idElement.value}`, {
        method: "GET",
        cache: "no-cache"
    })
        .then(response => { // 응답이 곧 유효성
            // 서버에 물어봤을때 ok(되는아이디)면 true, 아니면 false
            isIdOk = response.ok
            changeBackgroundColor(document.getElementById("id-check"), isIdOk); // 되는 값인지 안되는 값인지 표시
        })
}

// PASSWORD 확인
const password = document.getElementById("password");
const passwordCheck = document.getElementById("passwordCheck");

password.addEventListener("focusout", passwordValidCheckEvent);
passwordCheck.addEventListener("focusout", passwordCheckEvent);

function passwordValidCheckEvent() {
    if(dontRunWhenNothingValueInComponent(password)) {
        return;
    } else if(isValidPassword(password.value.trim())) {
        isPasswordOk = true;
    } else {
        isPasswordOk = false;
    }
    changeBackgroundColor(document.getElementById("password-check"), isPasswordOk); // 되는 값인지 안되는 값인지 표시
}

function passwordCheckEvent() {
    // 만약 값이 빈칸이면 바로 return ( 실행 안함 )
    if(dontRunWhenNothingValueInComponent(passwordCheck)) {
        return;
    }
    isPasswordCheckOk = password.value.trim() == passwordCheck.value.trim() && isPasswordOk;
    changeBackgroundColor(document.getElementById("passwordCheck-check"), isPasswordCheckOk); // 되는 값인지 안되는 값인지 표시
}


// NICKNAME 중복 검사
const nicknameElement = document.getElementById("nickname");
nicknameElement.addEventListener("focusout", nicknameCheckEvent);

function nicknameCheckEvent() {
    // 만약 값이 빈칸이면 바로 return ( fetch 실행 안함 )
    if(dontRunWhenNothingValueInComponent(nicknameElement)) {
        return; // fetch 실행 X
    } else if(isValidNickname(nicknameElement.value)) {
        isNicknameOk = false;
        changeBackgroundColor(document.getElementById("nickname-check"), isIdOk); // 되는 값인지 안되는 값인지 표시
        return;
    }

    fetch(`/api/admins/nickname/check?nickname=${nicknameElement.value}`, {
        method: "GET",
        cache: "no-cache"
    })
        .then(response => { // 응답이 곧 유효성
            // 서버에 물어봤을때 ok(되는 닉네임)면 true, 아니면 false
            isNicknameOk = response.ok;
            changeBackgroundColor(document.getElementById("nickname-check"), isNicknameOk); // 되는 값인지 안되는 값인지 표시
        })
}

// EMAIL 유효성 검사
const emailElement = document.getElementById("email");
emailElement.addEventListener("focusout", emailCheckEvent);

function emailCheckEvent() {
    if(dontRunWhenNothingValueInComponent(emailElement)) {
        return; // fetch 실행 X
    } if(!isValidEmail(emailElement.value)) { // 이메일 형식인지 검사
        // 이메일 형식이 아니라면?
        isEmailOk = false; // 안되는 이메일입니다.
        changeBackgroundColor(document.getElementById("email-check"), isEmailOk); // 되는 값인지 안되는 값인지 표시
        return; // fetch 실행 X
    }

    fetch(`/api/admins/email/check?email=${emailElement.value}`, {
        method: "GET",
        cache: "no-cache"
    })
        .then(response => { // 응답이 곧 유효성
            // 서버에 물어봤을때 ok(되는 닉네임)면 true, 아니면 false
            isEmailOk = response.ok
            changeBackgroundColor(document.getElementById("email-check"), isEmailOk); // 되는 값인지 안되는 값인지 표시
        })
}

// 비밀번호가 8글자 이상에 특수문자 반드시 포함할 것
function isValidPassword(password) {
    const passwordRegex = /^(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,}$/;
    return passwordRegex.test(password);
}

// 이메일 주소의 유효성을 검사하는 정규 표현식 검사 함수
function isValidEmail(email) {
    const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    return emailPattern.test(email);
}

function isValidId(id) {
    const regex = /^[가-힣]{0}$|^(?![!@#$%^&* ]+$)[a-zA-Z0-9 ]{4,}$/;
    return regex.text(id);
}

function isValidNickname(nickname) {
    const regex = /^(?![!@#$%^&* ]+$)[a-zA-Z0-9]+$/;
    return regex.text(nickname);
}


// 가능 or 불가능한 항목에 대해 배경색으로 표시
// TODO : 추후 수정 예정
function changeBackgroundColor(component, isOk) {
    if(isOk) {
        component.style.color = "#279EFF";
    } else {
        component.style.color = "#F94C10";
    }
}

// 빈칸인 input은 작동 X
function dontRunWhenNothingValueInComponent(component) {
    if(component.value.trim().length == 0) {
        return true;
    }
}
