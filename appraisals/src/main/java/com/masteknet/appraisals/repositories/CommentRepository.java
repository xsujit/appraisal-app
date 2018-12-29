package com.masteknet.appraisals.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.masteknet.appraisals.entities.Appraisal;
import com.masteknet.appraisals.entities.Comment;
import com.masteknet.appraisals.entities.CommentId;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {

	List<Comment> findByCommentIdAppraisal(Appraisal appraisal);
	List<Comment> findByCommentId(CommentId commentId);
}
