function sendMail() { // 메일 가는지 확인용 함수 / 추후 변경 예정
    fetch(`/api/admins/member/invite`, {
        method: "GET",
        cache: "no-cache"
    })
        .then(response => {
            response.json();
        })
        .then(data => {
            alert("메일이 잘 갔습니다.");
        })
        .catch(error => {

        })
}
