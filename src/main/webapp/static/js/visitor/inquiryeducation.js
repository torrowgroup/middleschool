/**
 * 教育教研动态实时刷新
 */
function inquiryEducation() {
	var inquiry = $('#inquiry').prop("value");
	var caId = $('#inquiry').prop("name");
		$.ajax({
			type : 'post',
			url : projectPath+'visitorNEL/ajaxEducation',
			data : {
				caId:caId,
				inquiry : inquiry
			},
			contentType : 'application/x-www-form-urlencoded;charset=utf-8', // contentType很重要
			success : function(data) {
				var resourceList = data.resourceList;
				var list = data.resourceList.lists;
			    var len = list.length;		//判断提示框是否有必要出现
				if(len!=0){
					$(".prompt").css("border-bottom-color","purple");
					$(".prompt").show();
					var htm = '';
					if(len>5){
						len=5;
					}
					for(var i=0;i<len;i++){
						htm+='<a href="/middleschool/visitorNEL/viewEducation?rId='+caId+'&inquiry='+list[i].reTitle+'">'+list[i].reTitle+'</a> <br/>';
					}
					
					$(".prompt").html(htm);
				}
				if(len==0||inquiry==''){
					$(".prompt").html('');
					$(".prompt").css("border-bottom-color","none");
					$(".prompt").hide();
				}	
				if(resourceList.totalCount==0){
					$('#noThing').html('<div> <h3 class="titlesc">暂没有相关内容</h3> </div>')
					$('#anyThing').html('');
				} else if(resourceList.totalCount>0) {
					$('#noThing').html('');
					var content ="";	
					for(var i=0;i<list.length;i++){
						var resource = resourceList.lists[i];
						content +='<li> <a href="/middleschool/visitorNEL/downEduLiter?rId='+resource.reId+'&inquiry='+inquiry+'">'+
			            	'<span>'+resource.reTitle+'</span>'+ '</a><span >'+resource.reIssuingdate+'</span> </li>';
					}
					var pageCut = "";
					if(resourceList.currPage-1>0){	//判断上一页是否可用
						pageCut = '<li> <a '+
						'+href="/middleschool/visitorNEL/viewEducation?currentPage= '+resourceList.currPage-1+'&rId='+resourceList.lists[0].caId+
						'&inquiry='+inquiry+'">&laquo;</a> </li>';
					} else {
						pageCut = '<li><a href="#">&laquo;</a></li>';
					}
					pageCut +='<li> <a '+
						'href="/middleschool/visitorNEL/viewEducation?currentPage=1&rId='+resourceList.lists[0].caId+
						'&inquiry='+inquiry+'">1</a> </li>';
					for(var i=2;i<=resourceList.totalPage;i++){	//得到七页内容
						if(i>resourceList.currPage-3&&i<resourceList.currPage+3){
							pageCut +='<li> <a '+
							'href="/middleschool/visitorNEL/viewEducation?currentPage='+i+'&rId='+resourceList.lists[0].caId+
							'&inquiry='+inquiry+'">'+i+'</a> </li>';
						}
					}
					if(resourceList.currPage+1<=resourceList.totalPage){	//判断下一页
						pageCut +='<li><a '+
						'href="/middleschool/visitorNEL/viewEducation?currentPage='+resourceList.currPage+1+'&rId='+resourceList.lists[0].caId+
						'&inquiry='+inquiry+'">&raquo;</a> </li>';
					} else {
						pageCut += '<li><a href="#">&raquo;</a> </li>';
					}
					var jumpPage = "";
					for(var i=1;i<=resourceList.totalPage;i++){	//跳转页面html
						jumpPage += '<li><a href="#" onclick="turnPageEducation(this)" '+
								' id= "'+resourceList.lists[0].caId+'" name="'+inquiry+'" '+
								' th:text="'+i+'">'+i+'</a></li>';
					}
					$('#anyThing').html('<div>'+ 
							'<h3 class="titlesc" >'+resourceList.lists[0].caName+'</h3>'+
							'<div class="newscontent">'+
								'<ul class="newscontentUl">'+content+'</ul>'+
								'<div class="clearfix"></div>'+
							'</div>'+
							'<div class="paging1"> <ul class="pagingUl">'+pageCut+ '</ul>'+
							'<div class="pagingRight">'+
							'<span>跳转到</span> <div class="selectBox">'+ 
							'<span>'+resourceList.currPage+' <i class="glyphicon glyphicon-chevron-down pull-right"'+
							'style="line-height: 30px"></i></span>'+
						'<ul class="selectBoxUl">'+ jumpPage+ '</ul> </div> <span>页 共<span>'+resourceList.totalPage+'</span>页 </span> </div>'
					)
				}
			}
		});
};