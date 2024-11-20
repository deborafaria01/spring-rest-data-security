package br.edu.fatecsjc.lgnspringapi.service;

import br.edu.fatecsjc.lgnspringapi.dto.MemberDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Group;
import br.edu.fatecsjc.lgnspringapi.entity.Member;
import br.edu.fatecsjc.lgnspringapi.repository.GroupRepository;
import br.edu.fatecsjc.lgnspringapi.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final GroupRepository groupRepository;

    public MemberService(MemberRepository memberRepository, GroupRepository groupRepository) {
        this.memberRepository = memberRepository;
        this.groupRepository = groupRepository;
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    public Member save(MemberDTO memberDTO) {
        Group group = validateGroupId(memberDTO.getGroupId());
        Member member = convertToEntity(memberDTO, group);
        return memberRepository.save(member);
    }

    public Optional<Member> update(Long id, MemberDTO memberDTO) {
        return memberRepository.findById(id).map(existingMember -> {
            existingMember.setName(memberDTO.getName());
            existingMember.setAge(memberDTO.getAge());

            if (memberDTO.getGroupId() != null) {
                Group group = validateGroupId(memberDTO.getGroupId());
                existingMember.setGroup(group);
            }

            return memberRepository.save(existingMember);
        });
    }

    public boolean deleteById(Long id) {
        if (memberRepository.existsById(id)) {
            memberRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private Member convertToEntity(MemberDTO memberDTO, Group group) {
        return Member.builder()
                .name(memberDTO.getName())
                .age(memberDTO.getAge())
                .group(group) 
                .build();
    }

    private Group validateGroupId(Long groupId) {
        if (groupId == null) {
            throw new IllegalArgumentException("Group ID cannot be null");
        }
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid group ID: " + groupId));
    }
}
