welcomePage.directive('clickNote', function(){
	return {
		link: function(scope, element, attrs){
			element.on('click',function(){
					if (element.hasClass('myactive')){
						element.removeClass('myactive');
						element.removeAttr('style');
					}
					else {
						$('#serviceNote').find('.myactive').removeClass('myactive');
						element.addClass('myactive');
					}
			})
			element
			.on('mouseenter', function(){
				element.css('background-color','#f0ad4e');
			})
			.on('mouseleave', function(){
				element.removeAttr('style');
			})
		}
	}
})
