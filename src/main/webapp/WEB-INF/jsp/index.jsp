<%@page import="java.util.List, com.myproject.User, java.util.Set, com.myproject.UserRole"%>
<%@page session="true"%>
<html>
<body>
<h2>Hello World!</h2>

<%
/*
List<User> list = (List<User>)request.getAttribute("list");

for(User user: list){
	
		out.print(user.getLogin() + " : " + user.getPassword() + " : " + user.getEmail() + " : " + 
			user.isEnabled();

}
*/
//User user = (User)request.getAttribute("list");
//out.print(user.getLogin() + " : " + user.getPassword() + " : " + user.getUserRole().iterator().next().getRole());


%>
${list}
</body>
</html>
