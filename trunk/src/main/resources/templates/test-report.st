<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Story criterias covered by tests</title>
	<style>
		body {
			font-family: verdana,arial,helvetica,sans-serif;
			font-size: 80%;
		}
		
		.story-summary {
			background-color: #CC6600;
			margin-bottom: 1px;
		}
		
		.test { 
			font: boder;
			background-color: #66CC66;
			margin-bottom: 1px;
		}
		
		.test div {
			margin-left: 10px;
		}
		
	</style>
	
	<script src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
	
	<script language="javascript">
		/** 
		var stories = [{
					  "number" : 731,
					  "link"   : "http://jira.int.corp.sun/jira/browse/LSP-731",
					  "summary": "Function test framework",
					  "tests"  : ['should_abc', 'should_123']
		             }, 
		             {
					  "number" : 732,
					  "link"   : "http://jira.int.corp.sun/jira/browse/LSP-732",
					  "summary": "Motor services for Home",
					  "tests"  : ['should_xyz', 'should_hhh']          
		             }];
		**/
		var stories = #stories#
	</script>
	
</head>
<body>
	<h1>Story criterias covered by tests</h1>
	
	<div class="content">
	<!--
		<div class="story">
			<a href="http://jira.int.corp.sun/jira/browse/LSP-731">
				<div class="story-name" id="731">Story 731</div>
			</a>
			<div class="test"><div>should_1234</div></div>
			<div class="test"><div>should_abcd</div></div>
		</div>
		
		<div class="story">
			<a href="http://jira.int.corp.sun/jira/browse/LSP-732">
				<div class="story-name" id="732">Story 732</div>
			</a>
			<div class="test"><div>should_xyz</div></div>
			<div class="test"><div>should_abc</div></div>
		</div>
	-->	
	</div>
	
	
	<script language="javascript">
		for (var i = 0; i < stories.length; i++) {
			var story = stories[i];
			
			var storySummaryElement = $("<div>Story " + story.number + "</div>");
			storySummaryElement.addClass("story-summary");
			storySummaryElement.attr("id", story.number);
			
			var linkOnStorySummary = $("<a>");
			linkOnStorySummary.attr("href", story.link);
			linkOnStorySummary.append(storySummaryElement);
			
			var storyElement = $('<div class="story">');
			storyElement.append(linkOnStorySummary);
			
			var tests = story.tests;
			for (var j = 0; j < tests.length; j++) {
				var testElement = $("<div>");
				testElement.addClass("test");
				testElement.append("<div>" + tests[j] + "</div>");
				storyElement.append(testElement);
			}
			
			$(".content").append(storyElement);
		}
	</script>
</body>
</html>