<%--
  Created by IntelliJ IDEA.
  User: 장훈희
  Date: 2025-02-12
  Time: 오후 5:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>main</title>
    <link rel="icon" href="#">
  </head>
  <body>
    <div>
      <button type="button" id="goLogin">로그인</button>
      <button type="button" id="goRgstOtp">OTP등록</button>
    </div>
  </body>
</html>
<script type="text/javascript">
  document.getElementById("goLogin").addEventListener('click', function() {
    location.assign("/member/login-main")
  })

  document.getElementById("goRgstOtp").addEventListener('click', function() {
    location.assign("/google-auth/rgst-page");
  })
</script>
