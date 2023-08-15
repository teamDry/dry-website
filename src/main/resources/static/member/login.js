document.addEventListener("DOMContentLoaded", function() {
    document.getElementById("btnLogin").addEventListener("click", login);
});

function login() {

    // 전송할 데이터 key-value 값으로 가공
    let data = {
        id : document.getElementById("memberId").value,
        password : document.getElementById("memberPassword").value
    };

    if(data.id == "" || data.password == "") {
        alert("아이디와 비밀번호를 입력해 주세요");
    }else {
        let xhr = new XMLHttpRequest();
        xhr.open("POST", "/member/login", true);            // ajax로 통신할 주소 및 방식(비동기 : true, 동기 : false)
        xhr.setRequestHeader("Content-Type", "application/json");   // 데이터를 보낼 형식 설정(Json)
        xhr.send(JSON.stringify(data));     // 데이터 json 형식으로 변경하여 전송
        // xhr.send();
        xhr.onload = () => {
            if(xhr.readyState == xhr.DONE) {     // 통신이 성공적으로 이루어졌을 때

                // ajax 통신상태 확인
                if(xhr.status == 200) {
                    const resultData = xhr.response;
                    const jsonData = JSON.parse(resultData);
                    console.log(jsonData);
                    if(Object.keys(jsonData).length !== 0) {        // empty object(빈객체)는 null과 속성이 달라서 해당 조건과 같이 해야함
                        alert("Login Success!");
                        window.location.href = "/member/loginSuccess";
                    }else {
                        alert("Login Fail..");
                    }
                }else {
                    alert("Error: " + xhr.statusText);
                }
            }
        }
    }
}