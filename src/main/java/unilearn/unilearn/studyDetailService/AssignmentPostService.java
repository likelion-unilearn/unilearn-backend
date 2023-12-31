package unilearn.unilearn.studyDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unilearn.unilearn.assignmentsPosts.entity.AssignmentListDto;
import unilearn.unilearn.assignmentsPosts.entity.AssignmentsPosts;
import unilearn.unilearn.study.entity.Study;
import unilearn.unilearn.study.repository.StudyRepository;
import unilearn.unilearn.studyDetailRepository.AssignmentPostRepository;
import unilearn.unilearn.user.exception.StudyNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssignmentPostService {

    @Autowired
    private AssignmentPostRepository assignmentPostRepository;
    @Autowired
    private StudyRepository studyRepository;
    //과제 게시글 전체 조회 - 마감전
    public  List<AssignmentListDto> getAllAssignmentPostsBeforeDeadline(Long study_id) {

        LocalDateTime currentDateTime = LocalDateTime.now();
        Study study = studyRepository.findById(study_id)
                .orElseThrow(() -> new StudyNotFoundException("Study not found with id: " + study_id));
        List<AssignmentsPosts> assignmentsPostsList =  assignmentPostRepository.findByStatusAndStudy("before",study);
        return assignmentsPostsList.stream()
                .map(this::convertAssignmentsPosts)
                .collect(Collectors.toList());

    }
    public AssignmentListDto convertAssignmentsPosts(AssignmentsPosts assignmentsPosts) {
        return AssignmentListDto.builder()
                .assignment_id(assignmentsPosts.getId())
                .title(assignmentsPosts.getTitle())
                .deadline(assignmentsPosts.getDeadline())
                .submit_num(assignmentsPosts.getSubmit_num())
                .submit_name(assignmentsPosts.getSubmit_name())
                .build();
    }


    //과제 게시글 전체 조회 - 마감후
    public List<AssignmentListDto> getAllAssignmentPostsAfterDeadline(Long study_id) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        Study study = studyRepository.findById(study_id)
                .orElseThrow(() -> new StudyNotFoundException("Study not found with id: " + study_id));
        List<AssignmentsPosts> assignmentsPostsList = assignmentPostRepository.findByStatusAndStudy("after",study);
        return assignmentsPostsList.stream()
                .map(this::convertAssignmentsPosts)
                .collect(Collectors.toList());
    }
    //과제 게시글 생성
    public AssignmentsPosts createAssignmentPost(AssignmentsPosts assignmentPost) {
        // 추가적인 로직 (예: 작성자, 스터디원에게 알림 등)이 필요하다면 여기에 추가
        return assignmentPostRepository.save(assignmentPost);
    }



    //과제 게시글 수정
    public AssignmentsPosts updateAssignmentPost(Long postId, AssignmentsPosts updatedPost) {
        // 추가적인 로직
        AssignmentsPosts existingPost = assignmentPostRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Assignment Post not found with id: " + postId));

        // 업데이트할 필드가 null이 아닌 경우에만 업데이트
        if (updatedPost.getTitle() != null) {
            existingPost.setTitle(updatedPost.getTitle());
        }


        if (updatedPost.getDeadline() != null) {
            existingPost.setDeadline(updatedPost.getDeadline());
        }

        return assignmentPostRepository.save(existingPost);
    }


}
