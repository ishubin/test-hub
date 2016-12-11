TestHub.reportHandlers["json"] = {
    afterRender: function (element) {
        $(".report-json-node-expand").click(function () {
            var $link = $(this);
            var $childrenContainer = $link.parent().next(".report-json-node-children-container");
            if ($childrenContainer.length) {
                $childrenContainer.slideToggle();
            }
        });
    }
};