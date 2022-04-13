package com.tidsbankencaseapi.Repositories;

import com.tidsbankencaseapi.Models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

     List<Comment> getAllByVacationRequestRequestIdOrderByDateCreatedDesc(Integer requestId);
}
