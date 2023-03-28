package org.application.commands.impl;

import org.application.commands.CommandController;
import org.application.models.User;
import org.application.services.UserService;
import org.application.services.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginCommand implements CommandController {
    @Override
    public boolean execute(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        UserService userService = new UserServiceImpl();
        User newUser = userService.loginUser(login, password);
        if (newUser != null) {
            req.getSession().setAttribute("user", newUser);
            return true;
        }
        return false;
    }
}
