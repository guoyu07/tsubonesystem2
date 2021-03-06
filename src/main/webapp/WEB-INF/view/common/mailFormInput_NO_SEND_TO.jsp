<div class="container">
	<h3>メールの内容を入力してください。</h3>
	<div class="col-sm-12">
		<table class="table">
			<s:form method="POST" >
				<form class="form-horizontal">
					<div class="form-group">
						<label class="control-label col-sm-4" for="Title">メールのタイトル</label>
						<div class="col-sm-8 memberF" >
							<input type="text" id="title" name="title" property="title" class="form-control" placeholder="Title">
							<html:errors property="title"/>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4" for="Content">メールの内容</label>
						<div class="col-sm-8 memberF">
							<textarea class="form-control" name="content" rows="10" property="content" placeholder="Content"></textarea>
							<html:errors property="content"/>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-8">
							<input type="submit" value="確認" id="confirmMail" name="confirmMail" property="confirmMail" class="col-md-6 col-md-offset-6 col-sm-10 col-sm-offset-4 col-xs-12  btn btn-primary">
						</div>
					</div>
				</form>
			</s:form>
		</table>
	</div>
</div>