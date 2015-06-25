<%--
  Created by IntelliJ IDEA.
  User: alessandro
  Date: 25/06/15
  Time: 19.26
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<c:url var="url" value="/jsp/head.jsp">
    <c:param name="title" value="Registration"/>
</c:url>
<c:import url="${url}"/>
<body>
<c:import url="/jsp/header.jsp"/>
<div class="row">
    <form action="doRegister" method="post">
        <div class="large-6 columns">
            <label>First name*
                <input type="text" name="firstname" placeholder="First name" required/>
            </label>
        </div>
        <div class="large-6 columns">
            <label>Last name*
                <input type="text" name="lastname" placeholder="Last name" required/>
            </label>
        </div>
        <div class="large-6 columns">
            <label>Email address*
                <input type="email" name="email" placeholder="Email" required/>
            </label>
        </div>
        <div class="large-6 columns">
            <label>Date of birth*
                <input type="date" name="dob" placeholder="Date of birth" required/>
            </label>
        </div>
        <div class="large-6 columns">
            <label>Mobile phone*
                <input type="tel" name="phone" placeholder="Mobile phone" required/>
            </label>
        </div>
        <div class="large-6 columns">
            <label>Password*</label>
            <input type="password" name="password" placeholder="password" required/>
            <%--TODO: fix medium--%>
            <medium class="input">Password must be at least 8 characters; Contain at least three character types:
                lowercase
                letters, uppercase letters, numbers, punctuation; Do not include your username.
            </medium>
        </div>
        <%--<div class="large-6 columns">--%>
        <%--<label>confirm password*</label>--%>
        <%--<input type="password" name="passwordconfirm" placeholder="password"/>--%>
        <%--</div>--%>
        <div class="medium-6 columns">
            <input type="submit" class="button expand" value="Register"/>
        </div>
    </form>

</div>


<c:import url="/jsp/footer.jsp"/>
</body>
</html>
