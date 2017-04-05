<%--
    Document   : ChangePassword
    Created on : Apr 4, 2017, 12:37:18 AM
    Author     : Aaron Usher
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>


<t:template>
    <jsp:body>
        <form class="table" id="changePasswordForm" method="GET" action="#">
            <div class="table-row">
                <label class="table-cell" for="oldPassword">Old Password:</label>
                <input class="table-cell required" type="password" name="oldPassword" id="oldPassword" /><br />
            </div>
            <div class="table-row">
                <label class="table-cell" for="newPassword">New Password:</label>
                <input class="table-cell required" type="password" name="newPassword" id="newPassword" /><br />
            </div>
            <div class="table-row">
                <label class="table-cell" for="confirmPassword">Confirm Password:</label>
                <input class="table-cell required" equalTo="#newPassword" type="password" name="confirmPassword" id="confirmPassword" /><br />
            </div>
            <div class="table-row">
                <input class="table-cell" type="submit" value="Update"/>
            </div>
        </form>
    </jsp:body>
</t:template>