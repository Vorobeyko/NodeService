welcomePage.directive('clickNote', function(){
	return {
		link: function(scope, element){
			element.on('mouseenter', function(){
				element.css('background-color','#f0ad4e');
			})
			.on('mouseleave', function(){
				element.removeAttr('style');
			})
		}
	}
})
.directive('addSourcesDialog', function(){
	return{
		templateUrl: 'pagetemplate/add-sources-dialog.html'
	}
})
.directive('changeSourcesDialog', function(){
	return{
		templateUrl: 'pagetemplate/change-sources-dialog.html'
	}
})
.directive('feedbackDialog', function(){
    return{
        templateUrl: 'pagetemplate/feedback-dialog.html'
    }
});
