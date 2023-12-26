package com.example.firstproject.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MemberApiController {
    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/api/members")
    public List<Member> index(){
        return memberRepository.findAll();
    }

    @GetMapping("/api/members/{id}")
    public Member show(@PathVariable Long id){
        return memberRepository.findById(id).orElse(null);
    }

    @PostMapping("/api/members")
    public Member created(@RequestBody MemberForm form){
        Member member = form.toEntity();
        return memberRepository.save(member);
    }

    @PatchMapping("/api/members/{id}")
    public ResponseEntity<Member> update(@PathVariable Long id, @RequestBody MemberForm form){
        Member member = form.toEntity();
        Member target = memberRepository.findById(id).orElse(null);

        if(target==null||id!=member.getId()){
            log.info("id:{},member:",id,member.toString());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
        }

        target.patch(member);
        Member updated = memberRepository.save(member);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    
}
