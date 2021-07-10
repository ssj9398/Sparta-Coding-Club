window.addEventListener('load', () => {
    getMessages();
});

//메모 내용 체크
function isValidContents(contents) {
    if (contents == '') {
        alert('내용을 입력해주세요');
        return false;
    }
    if (contents.trim().length > 140) {
        alert('공백 포함 140자 이하로 입력해주세요');
        return false;
    }
    return true;
}

// 익명의 username 생성
function genRandomName(length) {
    let result = '';
    let characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    let charactersLength = characters.length;
    for (let i = 0; i < length; i++) {
        let number = Math.random() * charactersLength;
        let index = Math.floor(number);
        result += characters.charAt(index);
    }
    return result;
}

// 수정 버튼을 눌렀을 때, 기존 작성 내용을 textarea 에 전달합니다.
// 숨길 버튼을 숨기고, 나타낼 버튼을 나타냅니다.
function editPost(id) {
    showEdits(id);
    let contents = $(`#${id}-contents`).text().trim();
    $(`#${id}-textarea`).val(contents);
}

function showEdits(id) {
    $(`#${id}-editarea`).show();
    $(`#${id}-submit`).show();
    $(`#${id}-delete`).show();

    $(`#${id}-contents`).hide();
    $(`#${id}-edit`).hide();
}

function hideEdits(id) {
    $(`#${id}-editarea`).hide();
    $(`#${id}-submit`).hide();
    $(`#${id}-delete`).hide();

    $(`#${id}-contents`).show();
    $(`#${id}-edit`).show();
}

// 메모 불러오기
function getMessages() {
    $('#cards-box').empty();
    $.ajax({
        type: "GET",
        url: "/api/memos",
        success: function (response) {
            for (let i = 0; i < response.length; i++) {
                let message = response[i];
                let id = message['id'];
                let username = message['username'];
                let contents = message['contents'];
                let modifiedAt = message['modifiedAt'];
                addHTML(id, username, contents, modifiedAt);
            }
        }
    });
}

//메모를 append로 html에 붙이기
function addHTML(id, username, contents, modifiedAt) {
    // 1. HTML 태그를 만듭니다.
    let tempHtml = `<div class="card">
        <!-- date/username 영역 -->
            <div class="metadata">
            <div class="date">
            ${modifiedAt}
            </div>
            <div id="${id}-username" class="username">
            ${username}
            </div>
            </div>
                <!-- contents 조회/수정 영역-->
            <div class="contents">
            <div id="${id}-contents" class="text">
            ${contents}
            </div>
            <div id="${id}-editarea" class="edit">
            <textarea id="${id}-textarea" class="te-edit" name="" id="" cols="30" rows="5"></textarea>
            </div>
            </div>
                <!-- 버튼 영역-->
            <div class="footer">
            <img id="${id}-edit" class="icon-start-edit" src="images/edit.png" alt="" onclick="editPost('${id}')">
            <img id="${id}-delete" class="icon-delete" src="images/delete.png" alt="" onclick="deleteOne('${id}')">
            <img id="${id}-submit" class="icon-end-edit" src="images/done.png" alt="" onclick="submitEdit('${id}')">
            </div>
            </div>`;
    // #cards-box 에 HTML을 붙인다.
    $('#cards-box').append(tempHtml);
}

// 메모 생성
function writePost() {
    let contents = $('#contents').val();
    if (isValidContents(contents) == false) {
        return;
    }
    let username = genRandomName(10);
    let data = {'username': username, 'contents': contents};

    $.ajax({
        type: "POST",
        url: "/api/memos",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (res) {

            window.location.reload();
        }
    });
}

// 메모 수정
function submitEdit(id) {
    let username = $(`#${id}-username`).text().trim();
    let contents = $(`#${id}-textarea`).val().trim();

    if (isValidContents(contents) == false) {
        return;
    }

    let data = {'username': username, 'contents': contents}

    $.ajax({
        type: "PUT",
        url: `/api/memos/${id}`,
        data: JSON.stringify(data),
        contentType: 'application/JSON',
        success: function (res) {
            window.location.reload();
        }
    })
}

// 메모 삭제
function deleteOne(id) {
    $.ajax({
        type:"DELETE",
        url:`/api/memos/${id}`,
        success:function (res){
            window.location.reload();
        }
    })
}