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
<%@ include file="/WEB-INF/view/common/indeividualsHeader.jsp"%>
<%@ include file="/WEB-INF/view/common/jumbotronMenu.jsp"%>
<div class="container">
<div class="col-sm-12">
<s:form method="POST" >
<div class="table-responsive">
<h4>変更したいメンバーは以下でよろしいですか？</h4>
<table class="table">
<tr>
<th>ハンドルネーム</th>
	<c:forEach var="e" items="${tMemberNew}">
		<tr>
			<td>
				<a href="<c:url value="/admin/memberDetail/detail"/>/${e.id}">${f:h(e.hname) }</a>
			</td>
		</tr>
	</c:forEach>
</table>
</div>
<input type="submit" value="登録" id="complete" name="complete" property="complete" class="btn btn-primary">
</s:form>
</div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="${f:url('/js/bootstrap.min.js')}"></script>
</body>
</html>