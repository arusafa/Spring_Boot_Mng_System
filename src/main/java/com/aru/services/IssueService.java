package com.aru.services;

import com.aru.models.Issue;
import com.aru.models.User;
import com.aru.requests.IssueRequest;

import java.util.List;
import java.util.Optional;

public interface IssueService {

    Issue getIssueById(Long issueId) throws Exception;

    List <Issue> getIssuedByProjectId(Long projectId) throws Exception;

    Issue createIssue(IssueRequest issueRequest, User user) throws Exception;

    void deleteIssue(Long issueId, Long userId) throws Exception;

    Issue addUserToIssue(Long issueId, Long userId) throws Exception;

    Issue updateStatus(Long issueId, String status) throws Exception;

//    Optional <Issue> updateIssue(Long issueId, IssueRequest updatedIssue, Long userId) throws Exception;
//    List <Issue> getIssuesByAssignedId(Long assignedId) throws Exception;
//    List <Issue> searchIssues(String title, String status, String priority, Long assignedId) throws Exception;
//    List <User> getAssignedForIssue(Long issueId) throws Exception;













}
