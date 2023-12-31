package unilearn.unilearn.study.entity;


import lombok.*;


import unilearn.unilearn.study.entity.Study;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Transactional

public class RegularMeeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long regularMeeting_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id", nullable = false)
    private Study study;

    @Column(nullable = false)
    private String day_of_week;

    @Column(nullable = false)
    private LocalTime start_time;

    @Column(nullable = false)
    private LocalTime end_time;

}
