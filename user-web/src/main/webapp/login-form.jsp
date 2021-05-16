<head>
<jsp:directive.include file="/WEB-INF/jsp/prelude/include-head-meta.jspf" />
	<title>My Home Page</title>
    <style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }
    </style>
</head>
<body>
	<div class="container">
		<form class="form-signin" action="${pageContext.request.contextPath}/sign-in" method="post">
			<h1 class="h3 mb-3 font-weight-normal">登录</h1>
			<label for="inputName" class="sr-only">用户名</label> <input
				type="text" id="inputName" class="form-control" name="name"
				placeholder="请输入用户名" required autofocus> <label
				for="inputPassword" class="sr-only">密码</label> <input
				type="password" id="inputPassword" class="form-control" name="password"
				placeholder="请输入密码" required>
			<div class="checkbox mb-3">
				<label> <input type="checkbox" value="remember-me">
					Remember me
				</label>
			</div>
			<button class="btn btn-lg btn-primary btn-block" type="submit">Sign
				in</button>
			<p class="mt-5 mb-3 text-muted">&copy; 2017-2021</p>
		</form>
	</div>
	<div class="container">
		<span>其他方式登录</span>
		<a href="/login/gitee" img="/static/img/gitee.jpg" title="使用gitee账号登录"/>gitee账号登录
		<a href="/login/github" img="/static/img/github.jpg" title="使用github账号登录"/>github账号登录
	</div>
</body>