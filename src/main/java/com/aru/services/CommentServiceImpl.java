package com.aru.services;

import com.aru.models.Comment;
import com.aru.models.Issue;
import com.aru.models.User;
import com.aru.repositories.CommentRepository;
import com.aru.repositories.IssueRepository;
import com.aru.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment createComment(Long issueId, Long userId, String content) throws Exception {

        Optional <Issue> issueOptional = issueRepository.findById(issueId);
        Optional <User> optionalUser = userRepository.findById(userId);

        if (issueOptional.isEmpty()){
            throw new Exception("Issue not found with " + issueId);
        }
        if (optionalUser.isEmpty()){
            throw new Exception("User not found with id " + userId);
        }

        Issue issue = issueOptional.get();
        User user = optionalUser.get();

        Comment comment = new Comment();
        comment.setIssue(issue);
        comment.setUser(user);
        comment.setCreatedDateTime(LocalDateTime.now());
        comment.setContent(content);

        Comment savedComment = commentRepository.save(comment);
        issue.getComments().add(savedComment);

        return savedComment;
    }

    @Override
    public void deleteComment(Long commentId, Long userId) throws Exception {

        Optional <Comment> commentOptional = commentRepository.findById(commentId);
        Optional <User> optionalUser = userRepository.findById(userId);

        if (commentOptional.isEmpty()){
            throw new Exception("Comment not found with " + commentId);
        }
        if (optionalUser.isEmpty()){
            throw new Exception("User not found with id " + userId);
        }

        Comment comment = commentOptional.get();
        User user = optionalUser.get();

        if (comment.getUser().equals(user)){
            commentRepository.delete(comment);
        }
        else {
            throw new Exception("User does not have permission to delete this comment " + userId);
        }
    }

    @Override
    public List<Comment> findCommentByIssueId(Long issueId) throws Exception {

        return commentRepository.findCommentByIssueId(issueId);
    }
}
