package com.company.appraisal.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.company.appraisal.entities.Appraisal;
import com.company.appraisal.entities.Comment;
import com.company.appraisal.entities.CommentId;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {

	List<Comment> findByCommentIdAppraisal(Appraisal appraisal);
	List<Comment> findByCommentId(CommentId commentId);
}
