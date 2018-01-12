<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="parts/header.jsp" %>
<div class="container-fluid login-page">
    <div class="row">
        <div class="col-md-4 offset-md-4 login-page__panel pt-5 pb-5">
            <div class="row">
                <div class="col-md-12">
                    <h1 class="text-center login-page__panel-title">Logowanie</h1>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <form class="form" method="post">
                        <div class="row">
                            <div class="col-md-10 offset-md-1 mt-3">
                                <input type="text" placeholder="Adres e-mail" name="user-login" class="form-control">
                            </div>

                            <div class="col-md-10 offset-md-1 mt-3">
                                <input type="password" placeholder="Hasło" name="user-password" class="form-control">
                            </div>
                        </div>
                        <div class="row mt-3">
                            <div class="col-md-5 offset-md-1 d-flex justify-content-start align-items-center">
                                <a href="#">Nie pamiętasz hasła?</a>
                            </div>
                            <div class="col-md-5 d-flex justify-content-end align-items-center">
                                <input type="submit" value="Zaloguj" class="btn btn-blue">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>


<%@include file="parts/footer.jsp" %>
