package org.application.commands.impl;

import org.application.commands.CommandController;
import org.application.models.Role;
import org.application.models.User;
import org.application.services.impl.RoleServiceImpl;
import org.application.services.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUpCommand implements CommandController {

    @Override
    public boolean execute(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        double salary = 0;
        String roleName = "customer";
        Role role = new RoleServiceImpl().getRoleByName(roleName);
        User user = new User(0, login, password, email, firstName, lastName, salary, role);
        if (role != null) {
            User newUser = new UserServiceImpl().registerUser(user);
            if (newUser != null) {
                req.getSession().setAttribute("user", newUser);
                return true;
            }
        }
        return false;
    }
}
