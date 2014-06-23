<html>
<html lang="jp">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>TsuboneSystem</title>
    <link href="${f:url('/css/bootstrap.min.css')}" type="text/css" rel="stylesheet">
    <link href="${f:url('/css/layout.css')}" type="text/css" rel="stylesheet">
    <link href="${f:url('/css/signin.css')}" type="text/css" rel="stylesheet">
  </head>
<body>
<%@ include file="/WEB-INF/view/common/header.jsp"%>
<%@ include file="/WEB-INF/view/common/jumbotronMenu.jsp"%>
<div class="container">
<div class="row CENTER">
	<h3 class="col-md-6">${f:h(hname)}ちゃんの詳細情報</h3>
	<a class="col-md-2 col-sm-5 col-sm-offset-1 col-xs-12  btn btn-danger btnYOKO btnMRC" href="<c:url value="/admin/memberDelete/${id}"/>">削除</a>
	<a class="col-md-2 col-sm-5 col-xs-12 btn btn-primary" href="<c:url value="/admin/memberUpdate/${id}"/>">更新</a>
</div>
<div class="col-sm-12">
<table class="table">
	<tr>
		<th><h4>名前</h4></th>
		<td><h5>${f:h(name)}</h5></td>
	</tr>
	<tr>
		<th><h4>性別</h4></th>
		<td><h5>${f:h(sexMap[sex])}</h5></td>
	</tr>
	<tr>
		<th><h4>ハンドネーム</h4></th>
		<td><h5>${f:h(hname)}</h5></td>
	</tr>
	<tr>
		<th><h4>メールアドレス</h4></th>
		<td><h5>${f:h(mail)}</h5></td>
	</tr>
	<tr>
		<th><h4>所属部</h4></th>
		<td>
			<h5>
				<c:forEach var="e" items="${tMemberClubList}">
					${f:h(clubMap[e.ClubId])}
				</c:forEach>
			</h5>
		</td>
	</tr>
	<tr>
		<th><h4>学科</h4></th>
		<td><h5>${f:h(curriculum)}</h5></td>
	</tr>
	<tr>
		<th><h4>入学年度</h4></th>
		<td><h5>${f:h(entrance)}</h5></td>
	</tr>
	<tr>
		<th><h4>電話番号</h4></th>
		<td><h5>${f:h(tel1)}-${f:h(tel2)}-${f:h(tel3)}</h5></td>
	</tr>
	<tr>
		<th><h4>OB宣言</h4></th>
	　	<td>
			<c:if test="${obFlag}">
				<h5>OB宣言済み</h5>
			</c:if>
			<c:if test="${!obFlag}">
				<h5>(現役部員)</h5>
			</c:if>
		</td>
	</tr>
	<tr>
		<th><h4>UserName</h4></th>
		<td><h5>${f:h(userName)}</h5></td>
	</tr>
</table>
</div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="${f:url('/js/bootstrap.min.js')}"></script>
</body>
</html>