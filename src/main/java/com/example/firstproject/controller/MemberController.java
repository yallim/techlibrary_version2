package com.example.firstproject.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.*;

import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MemberController {
    
    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/signUp")
    public String customerJoin(){
        return "member/signUp";
    }

    @PostMapping("/signUp/customer")
    public String newCustomerJoin(MemberForm memberForm){

        //DTO를 entity에저장
        Member member = memberForm.toEntity();
        log.info(member.toString());

        //entity를 레포지토리에 저장
        Member saved = memberRepository.save(member);
        log.info(saved.toString());
 
        return "redirect:/signUp/"+saved.getId();
    }

    @GetMapping("/signUp/{id}")
    public String lookUpOne(@PathVariable Long id,Model model){
        Member memberLookUp = memberRepository.findById(id).orElse(null);
        model.addAttribute("member", memberLookUp);

        return "member/show";
    }

    @GetMapping("/members")
    public String lookUpAll(Model model){
        ArrayList<Member> memberLookUpAll = memberRepository.findAll();
        model.addAttribute("memberList", memberLookUpAll);

        return "member/index";
    }

    @GetMapping("/signUp/{id}/edit")
    public String edit(@PathVariable Long id,Model model){
        Member memberEntity = memberRepository.findById(id).orElse(null);
        model.addAttribute("member", memberEntity);

        return "member/edit";
    }

    @PostMapping("/signUp/update")
    public String update(MemberForm form){
        Member memberEntity = form.toEntity();
        Member target = memberRepository.findById(memberEntity.getId()).orElse(null);

        if(target!=null){
            memberRepository.save(memberEntity);
        }

        return "redirect:/signUp/" + memberEntity.getId();
    }

    @GetMapping("/signUp/{id}/delete")
    public String delete(@PathVariable Long id,MemberForm form, RedirectAttributes rtrr){
        Member target = memberRepository.findById(id).orElse(null);

        if(target!=null){
            memberRepository.delete(target);
            rtrr.addFlashAttribute("msg", target.getId()+"번 아이디가 삭제되었습니다!");
        }
        return "redirect:/members";
    }
    

    
}
