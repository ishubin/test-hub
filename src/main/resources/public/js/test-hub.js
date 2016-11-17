
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