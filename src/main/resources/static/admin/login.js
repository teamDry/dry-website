// 아이디 저장 checkbox
const saveId = document.getElementById("saveId");

if(localStorage.getItem("id") != null) {
    // 저장된 아이디가 있다면 아이디 저장 체크 O
    saveId.checked = true;
} else {
    // 저장된 아이디가 없다면 아이디 저장 체크 X
    saveId.checked = false;
}

// localStorage에 자동 저장 id가 있다면 id 입력칸에 기본값 설정
let adminId = localStorage.getItem("id");

if(adminId != null) {
    document.getElementById("id").value = adminId;
}

function performLogin() {
    const idAndPassword = { // RequestBody에 들어갈 Json의 이름, RestController 에서 받아쓸 수 있다.
        id: document.getElementById("id").value,
        password: document.getElementById("password").value
    };

    fetch("/api/admins/login", { // client > Server로 요청 보내기
        method: "POST", // POST 방식으로
        headers: {
            "Content-Type": "application/json" // Json 타입으로 컨텐트를 보낼겁니다
        },
        body: JSON.stringify(idAndPassword) // data객체인 idAndPassword를 Json 형식으로 body에 첨부
    })
        .then(response => { // 요청을 보냈을때 응답이
            if (response.ok) { // response.ok : 통신 성공 여부 boolean 값
                return response.json(); // 성공했을 시 응답에서 온 json 객체 리턴
            } else { // 응답에 실패했을 시
                throw Error("Login Error"); // 에러 발생
            }
        })
        .then(data => { // 위에서 요청이 잘 왔을 경우에는
            if(saveId.checked) { // 아이디 자동 저장 체크 하고 로그인?
                localStorage.setItem("id", data.id); // 아이디 자동 저장
            } else { // 체크 안하고 로그인?
                localStorage.removeItem("id"); // 저장된 아이디 삭제
            }
            window.location.href="/admins/loginSuccess"; // view단으로 GET 요청을 보내 서버 랜더링
        })
        .catch(error => {
            window.location.href="/admins/loginFail";
    });
}

function clickSignUp() { // 회원가입 버튼 눌렸을 때
    window.location.href="/admins/sign-up";
}