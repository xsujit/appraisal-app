package com.masteknet.appraisal.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.masteknet.appraisal.entities.Appraisal;
import com.masteknet.appraisal.entities.Comment;
import com.masteknet.appraisal.entities.CommentId;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {

	List<Comment> findByCommentIdAppraisal(Appraisal appraisal);
	List<Comment> findByCommentId(CommentId commentId);
}
