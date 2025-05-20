package com.example.todo_api.todo;

import com.example.todo_api.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 아무 인자가 없는 생성자
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id 값 자동으로 1씩 증가 // IDENTITY: 키 값 결정을 DB에 위임함
    @Column(name = "todo_id")
    private Long id; // Long은 MySQL에서 bigint와 매칭됨

    @Column(name = "todo_content", columnDefinition = "varchar(200)")
    private String content;

    @Column(name = "todo_is_checked",columnDefinition = "tinyint(1)")
    private boolean isChecked;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY) // 1:다 관계
    private Member member;

    public Todo(String content, boolean isChecked, Member member) {
        this.content = content;
        this.isChecked = isChecked;
        this.member = member;
    }
}
