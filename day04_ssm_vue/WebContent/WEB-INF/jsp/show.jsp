<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/vue.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/axios-0.18.0.js"></script>
<style>
	.show{
	display:black
	}
	.hidden{
	display:none
	}

</style>
</head>
<body>
<a href="${pageContext.request.contextPath }/student/toAdd.action">添加</a>
<div id="did">

姓名：<input type="text" v-model="student.sname"><br>
班级：<select v-model="student.cid">
		<option value="0">==请选择==</option>
		<option v-for="clazz in clist" :value="clazz.cid" v-text = "clazz.cname"></option>
	</select><br>
生日：<input type="date" v-model = "start" >-<input type="date" v-model = "end" ><br/>
<input type="button" value="查询" @click="jump(1)">
<table id="tid" border="1" :class="flag2">
	<tr>
		<td>选择</td>
		<td>编号</td>
		<td>姓名</td>
		<td>性别</td>
		<td>图片</td>
		<td>生日</td>
		<td>班级</td>
		<td>操作</td>
	</tr>
	
	<tr v-for="(stu,index) in slist">
	<td><input type="checkbox" v-model="ids" :value="stu.sid"></td>
		<td v-text="stu.sid"></td>
		<td v-text="stu.sname"></td>
		<td v-text="stu.sex"></td>
		<td>图片</td>
		<td v-text="format(stu.birthday)"></td>
		<td v-text="stu.clazz.cname"></td>
		<td><input type="button" @click="toUpdate(index)" value="修改"></td>
	</tr>
	
</table>
当前页：{{page.pageNum}}/总页数：{{page.pages}}&nbsp;总条数：{{page.total}}<br>
<input type="button" value="首页" @click="jump(page.firstPage)">
<input type="button" value="上一页" @click="jump(page.prePage)">
<input type="button" value="下一页" @click="jump(page.nextPage)">
<input type="button" value="尾页" @click="jump(page.lastPage)">
<input type="button" @click="del" value="删除">
<!--   -->
<form id="fid" action="" :class="flag">
<input type="hidden" v-model="student.sid" ><br/>
	姓名:<input type="text" v-model="student.sname"><br/>
	性别:<input type="radio" value="男" v-model="student.sex">  男
		<input type="radio" value="女" v-model="student.sex">  女<br/>
	图片:<input type="file" ><br/>
	生日:<input type="date" v-model = "student.birthday" ><br/>
	班级: <select v-model="student.cid">
		<option v-for="clazz in clist" :value="clazz.cid" v-text = "clazz.cname"></option>
	</select><br/> 
	<input type="button" @click="update" value="修改">
</form>
</div>
<script type="text/javascript">
	var table = new Vue({
		el:"#did",
		data:{
			slist:[],
			student:{
				cid:0
			},
			clist:[],
			flag:'hidden',
			flag2:'show',
			ids:[],
			page:{},
			start:'',
			end:''
		},
		created(){
			axios.post("${pageContext.request.contextPath}/student/findAll.action",{pageNum:1}).then(function(res){
				table.slist = res.data.list;
				table.page = res.data;
			}),
			axios.post("${pageContext.request.contextPath}/student/findClass.action").then(function(res){
				table.clist = res.data;
			})
		},
		methods:{
			format(datetime){
				var year = new Date(this.datetime).getFullYear();
				var month1 = new Date(this.datetime).getMonth()+1;
				var month = month1<10?"0"+month1:month1;
				var date = new Date(this.datetime).getDate()<10?"0"+new Date(this.datetime).getDate():new Date(this.datetime).getDate();
				return year+"-"+month+"-"+date;
			},
			toUpdate(i){
				this.student = this.slist[i];
				this.student.birthday = this.format(this.student.birthday);
				this.flag="show";
				this.flag2="hidden";
			},
			update(){
				axios.post("${pageContext.request.contextPath}/student/updateStu.action",table.student).then(function(res){
					if(res.data>0){
						table.flag="hidden";
						table.flag2="show";
						 location.href="${pageContext.request.contextPath}/student/toShow.action" ;
						
					};
				})
			},
			del(){
				axios.post("${pageContext.request.contextPath}/student/delStu.action",this.ids).then(function(res){
					if(res.data>0){
						 location.href="${pageContext.request.contextPath}/student/toShow.action" ;
						
					};
				})
			},
			jump(pageNum){
				axios.post("${pageContext.request.contextPath}/student/findAll.action",{pageNum:pageNum,sname:this.student.sname,cid:this.student.cid,start:this.start,end:this.end}).then(function(res){
					table.slist = res.data.list;
					table.page = res.data;
				})
			}
			
		}
	});
</script>
</body>
</html>