package thinking.in.spring.boot.samples.spring4.mvc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import thinking.in.spring.boot.samples.spring4.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserListController {

    @RequestMapping("/list")
    @ResponseBody
    protected ResponseEntity<Object> list() throws Exception {
        List<User> userList = new ArrayList<User>();
        User userA = new User(1L, "u1", 10, "male");
        User userB = new User(2L, "u2", 12, "female");
        userList.add(userA);
        userList.add(userB);
        return ResponseEntity.ok(userList);
    }
}
