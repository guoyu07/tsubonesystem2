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
<h4>※緊急時は以下のメンバーに連絡を取ること！！報告・連絡・相談を忘れない！！</h4>
<div class="table-responsive">
<h3>局長</h3>
<table class="table">
<tr>
	<th class="col-md-2 col-ms-2">本名</th><th class="col-md-2 col-ms-2">ハンドルネーム</th><th class="col-md-5 col-ms-5">連絡先（mail）</th><th class="col-md-3 col-ms-3">連絡先（電話番号）</th>
		<c:forEach var="e" items="${tLeadersChief}">
		<tr>
			<td>
				${f:h(e.tMember.name) }
			</td>
			<td>
				${f:h(e.tMember.hname) }
			</td>
			<td>
				<a href="mailto:${e.tMember.mail}">${f:h(e.tMember.mail)}</a>
			</td>
			<td>
				${f:h(e.tMember.tel1) }-${f:h(e.tMember.tel2) }-${f:h(e.tMember.tel3) }
			</td>
		</tr>
	</c:forEach>
</table>
<h3>副局長</h3>
<table class="table">
<tr>
	<th class="col-md-2 col-ms-2">本名</th><th class="col-md-2 col-ms-2">ハンドルネーム</th><th class="col-md-5 col-ms-5">連絡先（mail）</th><th class="col-md-3 col-ms-3">連絡先（電話番号）</th>
	<c:forEach var="e" items="${tLeadersSubChief}">
		<tr>
			<td>
				${f:h(e.tMember.name) }
			</td>
			<td>
				${f:h(e.tMember.hname) }
			</td>
			<td>
				<a href="mailto:${e.tMember.mail}">${f:h(e.tMember.mail)}</a>
			</td>
			<td>
				${f:h(e.tMember.tel1) }-${f:h(e.tMember.tel2) }-${f:h(e.tMember.tel3) }
			</td>
		</tr>
	</c:forEach>
</table>
<h3>会計</h3>
<table class="table">
<tr>
	<th class="col-md-2 col-ms-2">本名</th><th class="col-md-2 col-ms-2">ハンドルネーム</th><th class="col-md-5 col-ms-5">連絡先（mail）</th><th class="col-md-3 col-ms-3">連絡先（電話番号）</th>
	<c:forEach var="e" items="${tLeadersAccount}">
		<tr>
			<td>
				${f:h(e.tMember.name) }
			</td>
			<td>
				${f:h(e.tMember.hname) }
			</td>
			<td>
				<a href="mailto:${e.tMember.mail}">${f:h(e.tMember.mail)}</a>
			</td>
			<td>
				${f:h(e.tMember.tel1) }-${f:h(e.tMember.tel2) }-${f:h(e.tMember.tel3) }
			</td>
		</tr>
	</c:forEach>
</table>
<h3>各部長</h3>
<table class="table">
<tr>
	<th class="col-md-1 col-ms-1">部名</th><th class="col-md-2 col-ms-2">部の代表者</th><th class="col-md-5 col-ms-5">代表者の連絡先（mail）</th><th class="col-md-4 col-ms-4">代表者の連絡先（電話番号）</th>
	<c:forEach var="e" items="${officerListItem}">
		<tr>
			<td>
				${f:h(e.ClubName) }
			</td>
			<td>
				${f:h(e.tLeaders.tMember.name) }
			</td>
			<td>
				<a href="mailto:${e.tMember.mail}">${f:h(e.tMember.mail)}</a>
			</td>
			<td>
				${f:h(e.tLeaders.tMember.tel1) }-${f:h(e.tLeaders.tMember.tel2) }-${f:h(e.tLeaders.tMember.tel3) }
			</td>
		</tr>
	</c:forEach>
</table>
</div>
</div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="${f:url('/js/bootstrap.min.js')}"></script>
</body>
</html>