<%--
  Created by IntelliJ IDEA.
  User: 장훈희
  Date: 2025-02-13
  Time: 오전 9:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <div>
        <div>
            <span>ID: </span>
            <input type="text" id="loginId" />
        </div>
        <div>
            <span>PW: </span>
            <input type="password" id="loginPw" />
        </div>
        <div>
            <span>OTP: </span>
            <input type="text" id="otpCode" />
        </div>
    </div>

    <div style="margin-top: 10px;">
        <button type="button" id="goLoginBtn">로그인</button>
    </div>
</body>
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script type="text/javascript">
    $('#goLoginBtn').on('click', function() {
        $.ajax({
            url: "/member/login"
            , method: "POST"
            , data: {
                  loginId: $('#loginId').val()
                , loginPw: $('#loginPw').val()
                , otpCode: $('#otpCode').val()
            }
            , dataType: "JSON"
        })
        .done(function(data) {
            alert(data.resultMsg);
            if(data.loginFlag) {
                location.assign("/");
            }
        })
    })

</script>
</html>
