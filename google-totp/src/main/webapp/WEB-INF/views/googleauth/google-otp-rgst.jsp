<%--
  Created by IntelliJ IDEA.
  User: 장훈희
  Date: 2025-02-13
  Time: 오후 1:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Google OTP Rgst Page</title>
    <link rel="icon" href="#">
</head>
<body>
    <h3>등록페이지</h3>
    <div>
        <div>
            <span>ID: </span> <input type="text" id="loginId" />
        </div>
        <div>
            <span>PW: </span> <input type="password" id="loginPw" />
        </div>
        <div>
            <button id="rgstInfoBtn">확인</button>
        </div>
    </div>
    <div class="rgst-area" style="display:none;">
        <div>
            <img id="QrImg" alt="img" />
        </div>
        <div>
            <input type="text" id="authCode" />
            <button id="rgstBtn">등록</button>
        </div>
    </div>

</body>
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script type="text/javascript">
    const googleInfo = (function() {
        let secretKey = "";

        const getSecretKey = function() {
            return secretKey;
        }

        const setSecretKey = function(param) {
            secretKey = param;
        }

        return {
              getSecretKey: getSecretKey
            , setSecretKey: setSecretKey
        }
    })();



    const getGoogleRgstInfo = function() {
        const loginId = $('#loginId').val();
        const loginPw = $('#loginPw').val();

        if(!(loginId && loginPw)) {
            alert("ID, PW 를 입력해주세요.");
            return false;
        }
        const data = {
              loginId: loginId
            , loginPw: loginPw
        }

        $.ajax({
              url: "/google-auth/google-auth-rgst-info"
            , method: "GET"
            , data: data
            , dataType: "JSON"
        })
        .done(function(data) {
            if(!data) {
                alert("통신오류 다시시도");
                return false;
            }
            googleInfo.setSecretKey(data.secretKey);
            const img = "data:image/png;base64," + data.QRImg;
            $('#QrImg').prop("src", img);
            $('.rgst-area').show();
        })
    }

    const googleOtpChk = function() {
        const secretKey = googleInfo.getSecretKey();
        $.ajax({
              url: "/google-auth/google-otp-chk"
            , method: "GET"
            , data: {
                  otpCode: $('#authCode').val()
                , secretKey: secretKey
            }
            , dataType: "JSON"
        })
        .done(function(data) {
            alert(data.chkFlag + " ::: " + data.serverOTP);
            if(data.chkFlag) {
                mbrJoin(secretKey);
            }
        })
    }

    const mbrJoin = function(secretKey) {
        if(!secretKey) {
            return false;
        }

        const data = {
              loginId: $('#loginId').val()
            , loginPw: $('#loginPw').val()
            , secretKey: secretKey
        }

        $.ajax({
              url: "/member/join"
            , method: "POST"
            , data: data
            , dataType: "JSON"
        })
        .done(function(data) {
            alert(data.resultCode);
        })
    }

    $(function() {
        $('#rgstInfoBtn').on('click', function() {
            getGoogleRgstInfo();
        })

        $('#rgstBtn').on('click', function() {
            googleOtpChk();
        })
    })
</script>
</html>
