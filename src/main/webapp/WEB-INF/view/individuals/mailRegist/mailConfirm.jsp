"src/main/webapp/WEB-INF/view/individuals/mailRegist/mailConfirm.jsp"<html>
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
	<h3 class="col-md-4">以下の内容でよろしいですか？</h3>
	<div class="col-sm-12">
		<%@ include file="/WEB-INF/view/common/mailFormConfirm.jsp"%>
	</div>
<div class="col-sm-12">
	<s:form method="POST" >
		<%@ include file="/WEB-INF/view/common/confirmButton.jsp"%>
	</s:form>
</div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="${f:url('/js/bootstrap.min.js')}"></script>
</body>
</html>