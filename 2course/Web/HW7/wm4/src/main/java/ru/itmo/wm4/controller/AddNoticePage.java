package ru.itmo.wm4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wm4.form.NoticeForm;
import ru.itmo.wm4.service.NoticeService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class AddNoticePage extends Page {
    private final NoticeService noticeService;


    public AddNoticePage(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping(path = "/add")
    public String registerGet(Model model) {
        model.addAttribute("noticeForm", new NoticeForm());
        return "AddNoticePage";
    }

    @PostMapping(path = "/add")
    public String registerPost(@Valid @ModelAttribute("noticeForm") NoticeForm noticeForm,
                               BindingResult bindingResult, HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "AddNoticePage";
        }

        noticeService.addNotice(noticeForm);

        return "redirect:/";
    }
}
