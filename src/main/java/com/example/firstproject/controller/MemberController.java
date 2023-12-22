package com.example.firstproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/join")
    public String customerJoin(){
        return "member/joinpage";
    }

    @PostMapping("/join/customer")
    public String newCustomerJoin(MemberForm memberForm){

        //DTO를 entity에저장
        Member member = memberForm.toEntity();
        log.info(member.toString());

        //entity를 레포지토리에 저장
        Member saved = memberRepository.save(member);
        log.info(saved.toString());
 
        return "";
    }

    @GetMapping("/join/{id}")
    public String lookUpAll(@PathVariable Long id,Model model){
        Member memberlookup = memberRepository.findById(id).orElse(null);
        model.addAttribute("member", memberlookup);

        return "member/show";
    }

    
}
