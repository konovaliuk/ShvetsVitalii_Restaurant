<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Users</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<c:if test="${empty sessionScope.user}">
  <jsp:include page="navbarnotloginned.jsp"/>
</c:if>
<c:if test="${not empty sessionScope.user}">
  <jsp:include page="navbarloginned.jsp"/>
</c:if>

<div class="container">
    <div class="row mt-5 justify-content-center">
            <div>
                <h1>List of Users</h1>
            </div>
            <div>
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>Login</th>
                        <th>Email</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Salary</th>
                        <th>Role</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${listUsers}" var="u">
                        <tr>
                            <td>${u.login}</td>
                            <td>${u.email}</td>
                            <td>${u.firstName}</td>
                            <td>${u.lastName}</td>
                            <td>
                                <form action="http://localhost:8080/html/users/change_salary/${u.userId}" method="post">
                                    <input type="hidden" name="page" value="${page}">
                                    <input type="number" name="salary" value="${u.salary}">
                                    <button type="submit" class="btn btn-primary">Change salary</button>
                                </form>
                            </td>
                            <td>${u.role.name}</td>
                            <td>
                                <form action="http://localhost:8080/html/users/change_role/${u.userId}" method="post">
                                    <input type="hidden" name="page" value="${page}">
                                    <select name="roleId">
                                        <option value="8">Customer</option>
                                        <option value="7">Waiter</option>
                                        <option value="6">Chef</option>
                                    </select>
                                    <button type="submit" class="btn btn-primary">Change role</button>
                                </form>
                            </td>
                            <c:if test="${u.role.name != 'admin'}">
                                <td>
                                    <form action="http://localhost:8080/html/users/delete/${u.userId}" method="post">
                                        <input type="hidden" name="page" value="${page}">
                                        <button type="submit" class="btn btn-danger"
                                                onclick="return confirm('Are you sure?')">Delete</button>
                                    </form>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="d-flex justify-content-center">
                    <nav>
                        <ul class="pagination">
                            <c:choose>
                                <c:when test="${page > 1}">
                                    <li class="page-item">
                                        <a class="page-link" href="${page - 1}" tabindex="-1">Previous</a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item disabled">
                                        <a class="page-link" href="#" tabindex="-1">Previous</a>
                                    </li>
                                </c:otherwise>
                            </c:choose>

                            <c:if test="${startPage > 1}">
                                <li class="page-item disabled">
                                    <a class="page-link" href="#">...</a>
                                </li>
                            </c:if>

                            <c:forEach begin="${startPage}" end="${endPage}" var="i">
                            <c:choose>
                                <c:when test="${i == page}">
                                    <li class="page-item active"><a class="page-link" href="${i}">${i}</a></li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item"><a class="page-link" href="${i}">${i}</a></li>
                                </c:otherwise>
                            </c:choose>
                            </c:forEach>

                            <c:if test="${endPage < totalPages}">
                                <li class="page-item disabled">
                                    <a class="page-link" href="#">...</a>
                                </li>
                            </c:if>

                            <c:choose>
                                <c:when test="${page < totalPages}">
                                    <li class="page-item">
                                        <a class="page-link" href="${page + 1}">Next</a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item disabled">
                                        <a class="page-link" href="#">Next</a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </ul>
                    </nav>
                </div>
        </div>
    </div>
</div>
</body>
</html>