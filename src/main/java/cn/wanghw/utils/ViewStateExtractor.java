package cn.wanghw.utils;

import burp.api.montoya.http.message.MimeType;
import burp.api.montoya.http.message.params.HttpParameterType;
import burp.api.montoya.http.message.params.ParsedHttpParameter;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;
import burp.api.montoya.utilities.URLUtils;
import cn.wanghw.models.ViewStateData;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewStateExtractor {
    private static final String
            VIEW_STATE_PARAMETER_NAME = "__VIEWSTATE",
            VIEW_STATE_GENERATOR_PARAMETER_NAME = "__VIEWSTATEGENERATOR",
            VIEW_STATE_ENCRYPTED_PARAMETER_NAME = "__VIEWSTATEENCRYPTED",
            EVENT_VALIDATION_PARAMETER_NAME = "__EVENTVALIDATION";

    private static final String regex = "<input type=\"hidden\" name=\"__(\\w*)\" .* value=\"(.*)\" />";
    private static final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);

    public ViewStateData parseRequest(HttpRequest info) {
        if (!info.hasParameter(VIEW_STATE_PARAMETER_NAME, HttpParameterType.BODY))
            return null;
        URLUtils urlUtils = CommonUtil.montoyaApi.utilities().urlUtils();
        ViewStateData viewState = new ViewStateData();
        List<ParsedHttpParameter> params = info.parameters();
        for (ParsedHttpParameter param : params) {
            fillParams(param.name(), param.value().contains("%") ? urlUtils.decode(param.value()) : param.value(), viewState);
        }
        return viewState;
    }

    public ViewStateData parseResponse(HttpResponse info) {
        if (info.inferredMimeType() != MimeType.HTML)
            return null;
        return parseFromHtml(info.bodyToString());
    }

    private static ViewStateData parseFromHtml(String bodyHtml) {
        ViewStateData viewState = new ViewStateData();
        Matcher matcher = pattern.matcher(bodyHtml);
        while (matcher.find()) {
            if (matcher.groupCount() == 2) {
                String name = matcher.group(1);
                String value = matcher.group(2);
                fillParams(name, value, viewState);
            }
        }
        if (viewState.getViewState() != null)
            return viewState;
        else return null;
    }

    private static void fillParams(String name, String value, ViewStateData viewState) {
        if (name == null)
            return;
        if (!name.startsWith("__"))
            name = "__" + name;
        if (VIEW_STATE_PARAMETER_NAME.equals(name)) {
            viewState.setViewState(value);
        } else if (VIEW_STATE_GENERATOR_PARAMETER_NAME.equals(name)) {
            viewState.setViewStateGenerator(value);
        } else if (VIEW_STATE_ENCRYPTED_PARAMETER_NAME.equals(name)) {
            viewState.setViewStateEncrypted(true);
        } else if (EVENT_VALIDATION_PARAMETER_NAME.equals(name)) {
            viewState.setEventValidation(value);
        }
    }
}
