
var API = {
    _fetch: function (path, callback) {
        $.ajax({
            dataType: "json",
            url: "/api" + path,
            success: callback
        });
    },
    fetchTests: function(project, job, build, statusFilter, callback) {
        var path = "/projects/" + project + "/jobs/" + job + "/builds/" + build + "/tests";
        if (statusFilter && statusFilter.length > 0) {
            path = path + "?status=" + statusFilter;
        }
        this._fetch(path, callback);
    },
    fetchTest: function(project, job, build, testId, callback) {
        this._fetch("/projects/" + project + "/jobs/" + job + "/builds/" + build + "/tests/" + testId, callback);
    }
};

var _htmlBadChars = /[&<>"'`=]/g;
var _htmlEscapeSymbols = {
  '&': '&amp;',
  '<': '&lt;',
  '>': '&gt;',
  '"': '&quot;',
  "'": '&#x27;',
  '`': '&#x60;',
  '=': '&#x3D;'
};
function escapeHtml(text) {
    return text.replace(_htmlEscapeSymbols, function (s) {
        return _htmlEscapeSymbols[s];
    });
}

Handlebars.registerHelper("nlToBr", function (text) {
    var lines = text.split("\n");
    var result = "";
    for (var i = 0; i < lines.length; i += 1) {
        if (i > 0) {
            result = escapeHtml(result) + ("<br/>");
        }
        result = result + lines[i];
    }
    return new Handlebars.SafeString(result);
});
Handlebars.registerHelper("renderTestReport", function (reportType, report) {
    var tpl = Handlebars.templates["test-reports/report-" + reportType];
    if (tpl) {
        return new Handlebars.SafeString(tpl({report: report}));
    } else {
        return "Unknown report type: " + reportType;
    }
});
Handlebars.registerHelper("renderByTemplate", function (data, templateName) {
    var tpl = Handlebars.templates[templateName];
    if (tpl) {
        return new Handlebars.SafeString(tpl(data));
    } else {
        return "Unknown template: " + templateName;
    }
});
Handlebars.registerHelper("ifOneOf", function (conditionalA, conditionalB, options) {
    if (conditionalA || conditionalB) {
        options.fn(this);
    }
});

var TestHub = {
    reportHandlers: {},
    applyReportHandler_after: function (type, element, report) {
        if (this.reportHandlers.hasOwnProperty(type) && this.reportHandlers[type].hasOwnProperty("afterRender")) {
            this.reportHandlers[type].afterRender(element, report);
        }
    }
};
