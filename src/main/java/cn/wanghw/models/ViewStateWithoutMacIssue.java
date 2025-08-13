package cn.wanghw.models;

import burp.api.montoya.collaborator.Interaction;
import burp.api.montoya.http.HttpService;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.scanner.audit.issues.AuditIssue;
import burp.api.montoya.scanner.audit.issues.AuditIssueConfidence;
import burp.api.montoya.scanner.audit.issues.AuditIssueDefinition;
import burp.api.montoya.scanner.audit.issues.AuditIssueSeverity;

import java.util.Arrays;
import java.util.List;

public class ViewStateWithoutMacIssue implements AuditIssue {
    private final HttpService httpService;
    private final HttpRequestResponse httpRequestResponse;

    public ViewStateWithoutMacIssue(HttpService httpService, HttpRequestResponse httpRequestResponse) {
        this.httpService = httpService;
        this.httpRequestResponse = httpRequestResponse;
    }

    @Override
    public String name() {
        return "ViewState without MAC found.";
    }

    @Override
    public String detail() {
        return "This ViewState without MAC, can directly trigger deserialization.";
    }

    @Override
    public String remediation() {
        return null;
    }

    @Override
    public HttpService httpService() {
        return httpService;
    }

    @Override
    public String baseUrl() {
        return httpRequestResponse.request().url();
    }

    @Override
    public AuditIssueSeverity severity() {
        return AuditIssueSeverity.HIGH;
    }

    @Override
    public AuditIssueConfidence confidence() {
        return AuditIssueConfidence.CERTAIN;
    }

    @Override
    public List<HttpRequestResponse> requestResponses() {
        return Arrays.asList(httpRequestResponse);
    }

    @Override
    public List<Interaction> collaboratorInteractions() {
        return List.of();
    }

    @Override
    public AuditIssueDefinition definition() {
        return null;
    }
}
