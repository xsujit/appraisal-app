package com.company.appraisal.entities;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name="COMMENT")
public class Comment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private long id;
	@Embedded
    private CommentId commentId;
	@Column(name="SUBMIT_DATE", updatable=false, nullable=false)
	private LocalDateTime submitDate;
	@NotBlank(message="Comment cannot be blank")
	@Size(min=2, max=280, message="Comment must be between 2 and 280 characters")
	@Column(name="MESSAGE", nullable=false, columnDefinition="text")
	private String message;

	public Comment() {
		super();
	}

	public Comment(CommentId commentId) {
		super();
		this.commentId = commentId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public CommentId getCommentId() {
		return commentId;
	}

	public void setCommentId(CommentId commentId) {
		this.commentId = commentId;
	}

	public LocalDateTime getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(LocalDateTime submitDate) {
		this.submitDate = submitDate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@PrePersist
	protected void onCreate() {
		submitDate = LocalDateTime.now();
	}

}
