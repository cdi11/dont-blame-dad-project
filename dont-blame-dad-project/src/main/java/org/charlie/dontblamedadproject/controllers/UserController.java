package org.charlie.dontblamedadproject.controllers;


import org.charlie.dontblamedadproject.models.Child;
import org.charlie.dontblamedadproject.models.User;
import org.charlie.dontblamedadproject.models.data.ChildDao;
import org.charlie.dontblamedadproject.models.data.UserDao;
import org.charlie.dontblamedadproject.models.forms.AddUserChildForm;
import org.charlie.dontblamedadproject.models.forms.RegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("user")
public class UserController extends AbstractController{

    @Autowired
    private UserDao userDao;

    @Autowired
    private ChildDao childDao;


    // Request path: /user
    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("users", userDao.findAll());
        model.addAttribute("title", "Parents");

        return "user/index";
    }



//    @RequestMapping(value = "add", method = RequestMethod.GET)
//    public String displayAddUserForm(Model model) {
//        model.addAttribute("title", "Add Parent");
//        model.addAttribute(new User());
//
//        return "user/add";
//    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String addUser(@ModelAttribute @Valid RegisterForm form, Errors errors, HttpServletRequest request) {

        if (errors.hasErrors()) {
            return "user/add";
        }

        User existingUser = userDao.findByUsername(form.getUsername());

        if (existingUser != null) {
            errors.rejectValue("username", "username.alreadyexists", "A user with that username already exists");
            return "user/add";
        }

        User newUser = new User(form.getUsername(), form.getPassword());
        userDao.save(newUser);
        setUserInSession(request.getSession(), newUser);

        return "redirect:";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddUserForm(@ModelAttribute @Valid User newUser,
                                     Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Parent");
            return "user/add";

        }
        userDao.save(newUser);
        return "redirect:";
    }


//    @RequestMapping(value = "signin", method = RequestMethod.GET)
//    public String displaySigninForm(Model model) {
//        model.addAttribute("title", "Parent Signin");
//        model.addAttribute(new Parent());
//
//        return "parent/signin";
//    }




    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveUserForm(Model model) {
        model.addAttribute("users", userDao.findAll());
        model.addAttribute("title", "Remove Parent");
        return "user/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveUserForm(@RequestParam int[] uids) {

        for (int uid : uids) {
            userDao.delete(uid);
        }

        return "redirect:";
    }


    @RequestMapping(value = "single/{uid}", method = RequestMethod.GET)
    public String singleParent(Model model, @PathVariable int uid) {
        User user = userDao.findOne(uid);
        model.addAttribute("title", user.getName());
        model.addAttribute("children", user.getChildren());
        model.addAttribute("Uid", user.getUid());

        return "user/single";


    }

    @RequestMapping(value = "add-child/{parentId}", method = RequestMethod.GET)
    public String addChild(Model model, @PathVariable int uid) {

        User user = userDao.findOne(uid);

        AddUserChildForm form = new AddUserChildForm(
                childDao.findAll(),
                user);
        model.addAttribute("title", "Add Child to " + user.getName());
        model.addAttribute("form", form);
        return "user/add-child";
    }


    @RequestMapping(value = "add-child", method = RequestMethod.POST)
    public String addChild(Model model, @ModelAttribute @Valid AddUserChildForm form, Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("form", form);
            return "user/add-child";

        }

        Child selectChild = childDao.findOne(form.getChildId());
        User theUser = userDao.findOne(form.getUserId());
        theUser.addChild(selectChild);
        userDao.save(theUser);

        return "redirect:/user/single/" + theUser.getUid();


    }

    @RequestMapping(value = "remove-child/{uid}", method = RequestMethod.GET)
    public String displayremoveChild(Model model, @PathVariable int userId) {

        User user = userDao.findOne(userId);

        AddUserChildForm form = new AddUserChildForm(
                childDao.findAll(),
                user);
        model.addAttribute("title", "Remove Child from " + user.getName());
        model.addAttribute("form", form);
        model.addAttribute("children", user.getChildren());
        model.addAttribute("userId", user.getUid());
        return "user/remove-child";
    }






    @RequestMapping(value = "remove-child/{parentId}", method = RequestMethod.POST)
    public String processremoveChild(Model model, @ModelAttribute @Valid AddUserChildForm form, Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("form", form);
            return "parent/remove-child";

        }

        Child selectChild = childDao.findOne(form.getChildId());
        User theUser = userDao.findOne(form.getUserId());
        childDao.delete(selectChild);
        userDao.save(theUser);

        return "redirect:/user/single/" + theUser.getUid();


    }






}