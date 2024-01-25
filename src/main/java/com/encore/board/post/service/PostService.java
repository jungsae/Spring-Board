package com.encore.board.post.service;

import com.encore.board.author.domian.Author;
import com.encore.board.author.repository.AuthorRepository;
import com.encore.board.post.domian.Post;
import com.encore.board.post.dto.PostDetailResDto;
import com.encore.board.post.dto.PostListResDto;
import com.encore.board.post.dto.PostSaveReqDto;
import com.encore.board.post.dto.PostUpdateReqDto;
import com.encore.board.post.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PostService
{
    private final PostRepository postRepository;
    private final AuthorRepository authorRepository;

    public PostService(PostRepository postRepository, AuthorRepository authorRepository)
    {
        this.authorRepository= authorRepository;
        this.postRepository = postRepository;
    }

    public Post post(Long id)
    {
        return postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("없는 post."));
    }

    public PostDetailResDto postDetail(Long id) throws EntityNotFoundException
    {
        Post post = this.post(id);
        PostDetailResDto postDetailResDto = new PostDetailResDto();
        postDetailResDto.setId(post.getId());
        postDetailResDto.setTitle(post.getTitle());
        postDetailResDto.setContents(post.getContents());
        postDetailResDto.setCreate_time(post.getCreate_Time());

        return postDetailResDto;
    }

    public void posting(PostSaveReqDto postSaveReqDto)
    {
        Author author = authorRepository.findByEmail(postSaveReqDto.getEmail()).orElse(null);
        Post post = Post.builder()
                        .title(postSaveReqDto.getTitle())
                        .contents(postSaveReqDto.getContents())
                        .author(author)
                        .build();
//        더티체킹 테스트
//        author.updateAuthor("dirty checking test", "1234");

        postRepository.save(post);
    }

    public List<PostListResDto> findAll()
    {
//        List<Post> postList = postRepository.findAll();
//        List<Post> postList = postRepository.findAllJoin();
        List<Post> postList = postRepository.findAllFetchJoin();
        List<PostListResDto> postListResDtoList= new ArrayList<>();

        for (Post post:postList)
        {
            PostListResDto postListResDto = new PostListResDto();
            postListResDto.setId(post.getId());
            postListResDto.setTitle(post.getTitle());
            postListResDto.setAuthor_email(post.getAuthor()==null ? "익명유저":post.getAuthor().getEmail());

            postListResDtoList.add(postListResDto);
        }
        return postListResDtoList;
    }

    public void update(PostUpdateReqDto postUpdateReqDto, Long id)
    {
        Post post = this.post(id);
        post.updatePost(postUpdateReqDto.getTitle(), postUpdateReqDto.getContents());
        postRepository.save(post);
    }

    public void delete(Long id)
    {
        postRepository.delete(this.post(id));
    }
}
