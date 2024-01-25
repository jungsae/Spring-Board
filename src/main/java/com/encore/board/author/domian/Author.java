package com.encore.board.author.domian;

import com.encore.board.post.domian.Post;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
//위와같이 모든 매개변수가 있는 생성자를 생성하는 어노테이션과 빌더를 클래스에 붙여
//모든 매개변수가 있는 생성자 위에 빌더 어노테이션을 붙인것과 같은 효과가 있음
public class Author
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20, nullable = false)
    private String name;
    @Column(length = 20, unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    //author를 조회할 때 post 객체가 필요할 시에 선언
    //mappedBy를 연관관계의 주인을 명시하고, FK를 관리하는 변수명을 명시
//    1:1관계일경우 @OneToOne존재
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch=FetchType.LAZY) //Post 객체에 있는 변수명을 적어 매핑관계 표현
//    @Setter
    private List<Post> posts; // posts 리스트에 post가 생성될 때마다 Post 테이블 가서 생성해줌
    //AuthorRepository.save만 해줘도 자동으로 PostRepository.save까지 해줌

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    LocalDateTime create_Time;
    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    LocalDateTime updatedTime;

//    builder 어노테이션을 통해 빌더패턴으로 객체생성
//    매개변수의 세팅순서, 매개변수의 개수 등을 유연하게 세팅
//    @Builder
//    public Author(String name, String email, String password, Role role)
//    {
//        this.name = name;
//        this.email= email;
//        this.password = password;
//        this.role=role;
//    }

    public void updateAuthor(String name, String password)
    {
        this.name = name;
        this.password = password;
    }
}
