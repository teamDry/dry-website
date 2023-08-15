let isIdOk = false;
let isPasswordOk = false;
let isNicknameOk = false;
let isEmailOk = false;

// Test용 DATA 세팅
function dataSetting() {
    document.getElementById("adminId").value = "abc";
    document.getElementById("adminPassword").value = "abcde";
    document.getElementById("adminPassword-check").value = "abcde";
    document.getElementById("adminNickname").value = "abcNick";
    document.getElementById("adminEmail").value = "abc@naver.com";
}


// 회원가입 버튼 눌렸을때 ajax 처리
function performSignUp() {
    if(!isIdOk) {
        return;
    }
    if(!isPasswordOk) {
        return;
    }
    if(!isNicknameOk) {
        return;
    }

    if(!isEmailOk) {
        return;
    }


    const admin = {
        id: document.getElementById("adminId").value,
        password: document.getElementById("adminPassword").value,
        nickname: document.getElementById("adminNickname").value,
        email: document.getElementById("adminEmail").value
    }

    fetch("/api/admin/sign-up", {
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
            window.location.href="/admin/login";
        })
        .catch(error => {
            console.log(error);
    });
}

// 회원가입 취소 버튼 눌렸을 때
function clickCancelButton() {
    window.location.href="/admin/login";
}

// ID 중복 검사
const idElement = document.getElementById("adminId");
idElement.addEventListener("focusout", idCheckEvent);

function idCheckEvent() {
    // 만약 값이 빈칸이면 바로 return ( fetch 실행 안함 )
    if(dontRunWhenNothingValueInComponent(idElement)) {
        return;
    }

    fetch(`/api/admin/sign-up/check-id?id=${idElement.value}`, {
        method: "GET",
        cache: "no-cache"
    })
        .then(response => { // 응답이 곧 유효성
            // 서버에 물어봤을때 ok(되는아이디)면 true, 아니면 false
            isIdOk = response.ok;
        })
    changeBackgroundColor(idElement, isIdOk); // 되는 값인지 안되는 값인지 표시
}

// PASSWORD CHECK 확인
const password = document.getElementById("adminPassword");
const passwordCheck = document.getElementById("adminPassword-check");

password.addEventListener("focusout", passwordCheckEvent);
passwordCheck.addEventListener("focusout", passwordCheckEvent);

function passwordCheckEvent() {
    // 만약 값이 빈칸이면 바로 return ( fetch 실행 안함 )
    if(dontRunWhenNothingValueInComponent(password)) {
        return;
    } else if(password.value.trim().length <= 7) {
        isPasswordOk = false; // 비밀번호 길이가 7글자 이하면 안됨
    } else if(password.value.trim() == passwordCheck.value.trim()) {
        isPasswordOk = true;
    } else {
        isPasswordOk = false;
    }
    changeBackgroundColor(passwordCheck, isPasswordOk); // 되는 값인지 안되는 값인지 표시
}


// NICKNAME 중복 검사
const nicknameElement = document.getElementById("adminNickname");
nicknameElement.addEventListener("focusout", nicknameCheckEvent);

function nicknameCheckEvent() {
    // 만약 값이 빈칸이면 바로 return ( fetch 실행 안함 )
    if(dontRunWhenNothingValueInComponent(nicknameElement)) {
        return; // fetch 실행 X
    }

    fetch(`/api/admin/sign-up/check-nickname?nickname=${nicknameElement.value}`, {
        method: "GET",
        cache: "no-cache"
    })
        .then(response => { // 응답이 곧 유효성
            // 서버에 물어봤을때 ok(되는 닉네임)면 true, 아니면 false
            isNicknameOk = response.ok;
        })
    changeBackgroundColor(nicknameElement, isNicknameOk); // 되는 값인지 안되는 값인지 표시
}

// EMAIL 유효성 검사
const emailElement = document.getElementById("adminEmail");
emailElement.addEventListener("focusout", emailCheckEvent);

function emailCheckEvent() {
    if(dontRunWhenNothingValueInComponent(emailElement)) {
        return; // fetch 실행 X
    } if(!isValidEmail(emailElement.value)) { // 이메일 형식인지 검사
        // 이메일 형식이 아니라면?
        isEmailOk = false; // 안되는 이메일입니다.
        changeBackgroundColor(emailElement, isEmailOk); // 되는 값인지 안되는 값인지 표시
        return; // fetch 실행 X
    }

    fetch(`/api/admin/sign-up/check-email?email=${emailElement.value}`, {
        method: "GET",
        cache: "no-cache"
    })
        .then(response => { // 응답이 곧 유효성
            // 서버에 물어봤을때 ok(되는 닉네임)면 true, 아니면 false
            isEmailOk = response.ok;
        })
    changeBackgroundColor(emailElement, isEmailOk); // 되는 값인지 안되는 값인지 표시
}

// 이메일 주소의 유효성을 검사하는 정규 표현식 검사 함수
function isValidEmail(email) {
    const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    return emailPattern.test(email);
}


// 가능 or 불가능한 항목에 대해 배경색으로 표시
// TODO : 추후 수정 예정
function changeBackgroundColor(component, isOk) {
    if(isOk) {
        component.style.backgroundColor = "black";
        component.style.color = "white";
    } else {
        component.style.color = "black";
        component.style.backgroundColor = "red";
    }
}

// 빈칸인 input은 작동 X
function dontRunWhenNothingValueInComponent(component) {
    if(component.value.trim().length == 0) {
        return true;
    }
}

