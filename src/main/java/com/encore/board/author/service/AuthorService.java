package com.encore.board.author.service;

import com.encore.board.author.domian.Author;
import com.encore.board.author.domian.Role;
import com.encore.board.author.dto.AuthorDetailResDto;
import com.encore.board.author.dto.AuthorListResDto;
import com.encore.board.author.dto.AuthorSaveReqDto;
import com.encore.board.author.dto.AuthorUpdateRequestDto;
import com.encore.board.author.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthorService
{
    private final AuthorRepository authorRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public AuthorService(AuthorRepository authorRepository, PasswordEncoder passwordEncoder)
    {
        this.authorRepository = authorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveReqDto(AuthorSaveReqDto authorSaveReqDto) throws IllegalArgumentException
    {
        Optional<Author> check = authorRepository.findByEmail(authorSaveReqDto.getEmail());
        if (check.isPresent()) throw new IllegalArgumentException("중복이메일");

        Role role = null;
        if (authorSaveReqDto.getRole() == null || authorSaveReqDto.getRole().equals("ROLE_USER"))
            role = Role.ROLE_USER;
        else
            role = Role.ROLE_ADMIN;
        Author author = Author.builder()
                .name(authorSaveReqDto.getName())
                .email(authorSaveReqDto.getEmail())
                .password(passwordEncoder.encode(authorSaveReqDto.getPassword()))
                .role(role)
                .build();
        authorRepository.save(author);
    }

    public List<AuthorListResDto> authorList()
    {
        List<Author> authors = authorRepository.findAll();
        List<AuthorListResDto> authorListResDtos = new ArrayList<>();

        for (Author author: authors)
        {
            AuthorListResDto authorListResDto = new AuthorListResDto();
            authorListResDto.setId(author.getId());
            authorListResDto.setName(author.getName());
            authorListResDto.setEmail(author.getEmail());

            authorListResDtos.add(authorListResDto);
        }
        return authorListResDtos;
    }

    public Author author(Long id) throws EntityNotFoundException
    {
        return authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("없는 사람."));
    }
    public Author findByEmail(String email) throws EntityNotFoundException
    {
        return authorRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("없는 사람."));
    }

    public AuthorDetailResDto findAuthorDetail(Long id) throws EntityNotFoundException
    {
        Author author = authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("없는 사람."));
        AuthorDetailResDto authorDetailResDto = new AuthorDetailResDto();
        String role = null;
        if (author.getRole() == null || author.getRole().equals(Role.ROLE_USER))
            role = "일반유저";
        else
            role = "관리자";

        authorDetailResDto.setId(author.getId());
        authorDetailResDto.setEmail(author.getEmail());
        authorDetailResDto.setName(author.getName());
        authorDetailResDto.setPassword(author.getPassword());
        authorDetailResDto.setCounts(author.getPosts().size());
        authorDetailResDto.setRole(role);
        authorDetailResDto.setCreate_time(author.getCreate_Time());

        return authorDetailResDto;
    }

    public void update(AuthorUpdateRequestDto authorUpdateRequestDto, Long id) throws EntityNotFoundException
    {
//        Author author = authorRepository.findById(id).orElseThrow();
        Author author = this.author(id);
        author.updateAuthor(authorUpdateRequestDto.getName(), authorUpdateRequestDto.getPassword());
        authorRepository.save(author);
    }

    public void delete(Long id)
    {
        authorRepository.delete(authorRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

}
