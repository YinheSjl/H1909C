<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/vue.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/axios-0.18.0.js"></script>
</head>
<body>
<div id="did">
<span v-text="info"></span><br>
	学生姓名：<input type="text" v-model="user.username"><br>
	登录密码：<input type="text" v-model="user.password"><br>
	<input type="button" @click="login" value="登录">
</div>
<script type="text/javascript">
	var div = new Vue({
		el:"#did",
		data:{
			user:{
				username:'',
				password:''
			},
			info:'',
			ninfo:'',
			pinfo:''
		},
		methods:{
			login(){
				if(this.user.username.length>0){
					if(this.user.password.length>0){
						axios.post("${pageContext.request.contextPath}/user/login.action",this.user).then(function(res){
							if(res.data>0){
								location.href="${pageContext.request.contextPath}/student/toShow.action";
							}else{
								div.info="用户名或密码错误！";
							}
						})
					}else{
						this.pinfo="密码不能为空！";
					}
				}else{
					this.ninfo="账户不能为空！";
				}
				
			}
		}
	});
</script>
</body>
</html>