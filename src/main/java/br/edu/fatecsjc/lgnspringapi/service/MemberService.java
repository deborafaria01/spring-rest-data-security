package br.edu.fatecsjc.lgnspringapi.service;

import br.edu.fatecsjc.lgnspringapi.dto.MemberDTO;
import br.edu.fatecsjc.lgnspringapi.entity.Group;
import br.edu.fatecsjc.lgnspringapi.entity.Marathon;
import br.edu.fatecsjc.lgnspringapi.entity.Member;
import br.edu.fatecsjc.lgnspringapi.repository.GroupRepository;
import br.edu.fatecsjc.lgnspringapi.repository.MarathonRepository;
import br.edu.fatecsjc.lgnspringapi.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private MarathonRepository marathonRepository;

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    public Member save(Member member) {
        // Validate Group
        Group group = groupRepository.findById(member.getGroup().getId())
                .orElseThrow(() -> new IllegalArgumentException("Group not found with ID: " + member.getGroup().getId()));

        if (group.getOrganization() == null) {
            throw new IllegalArgumentException("Group must be associated with a valid organization.");
        }
        member.setGroup(group);

        // Validate Marathons
        if (member.getMarathons() != null && !member.getMarathons().isEmpty()) {
            List<Long> marathonIds = member.getMarathons().stream()
                    .map(Marathon::getId)
                    .toList();
            List<Marathon> marathons = marathonRepository.findAllById(marathonIds);

            if (marathons.size() != marathonIds.size()) {
                throw new IllegalArgumentException("One or more marathons not found with provided IDs: " + marathonIds);
            }
            member.setMarathons(marathons);
        }

        return memberRepository.save(member);
    }

    public Member update(Long id, Member member) {
        // Find existing member
        Member existingMember = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with ID: " + id));

        // Update member details
        existingMember.setName(member.getName());
        existingMember.setAge(member.getAge());

        // Update Group if provided
        if (member.getGroup() != null) {
            Group group = groupRepository.findById(member.getGroup().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Group not found with ID: " + member.getGroup().getId()));

            if (group.getOrganization() == null) {
                throw new IllegalArgumentException("Group must be associated with a valid organization.");
            }
            existingMember.setGroup(group);
        }

        // Update Marathons if provided
        if (member.getMarathons() != null) {
            List<Long> marathonIds = member.getMarathons().stream()
                    .map(Marathon::getId)
                    .toList();
            List<Marathon> marathons = marathonRepository.findAllById(marathonIds);

            if (marathons.size() != marathonIds.size()) {
                throw new IllegalArgumentException("One or more marathons not found with provided IDs: " + marathonIds);
            }
            existingMember.setMarathons(marathons);
        }

        return memberRepository.save(existingMember);
    }

    public void deleteById(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new IllegalArgumentException("Cannot delete: Member not found with ID: " + id);
        }
        memberRepository.deleteById(id);
    }

    public MemberDTO toDTO(Member member) {
    return MemberDTO.builder()
            .id(member.getId())
            .name(member.getName())
            .age(member.getAge())
            .groupId(member.getGroup().getId())
            .marathonIds(member.getMarathons() != null ? 
                member.getMarathons().stream().map(Marathon::getId).toList() 
                : null)
            .build();
}

}
