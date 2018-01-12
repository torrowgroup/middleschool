/**
 * 学校新闻动态实时刷新
 */
function inquiryNews() {
	var inquiry = $('#inquiry').prop("value");
	var caId = $('#inquiry').prop("name");
		$.ajax({
			type : 'post',
			url : projectPath+'visitorGN/inquiryAjax',
			data : {
				nId:caId,
				inquiry : inquiry
			},
			contentType : 'application/x-www-form-urlencoded;charset=utf-8', // contentType很重要
			success : function(data) {
				var inquiry = data.inquiry;
				var news = data.news;
				var list = data.news.lists;
			    var len = list.length;		//判断提示框是否有必要出现
				if(len!=0){
					$(".prompt").show();
					var htm = '';
					if(len>5){
						len=5;
					}
					for(var i=0;i<len;i++){
						htm+='<a href="/middleschool/visitorGN/viewNews?nId='+caId+'&inquiry='+list[i].reTitle+'">'+list[i].reTitle+'</a> <br/>';
					}
					$(".prompt").html(htm);
				}
				if(len==0||inquiry==''){
					$(".prompt").html('');
					$(".prompt").hide();
				}	
				if(news.totalPage==0){
					$('#noThing').html('<div> <h3 class="titlesc">暂没有相关内容</h3> </div>')
					$('#anyThing').html('');
				} else if(news.totalPage>0) {
					$('#noThing').html('');
					var content ="";	
					for(var i=0;i<news.lists.length;i++){
						var resource = news.lists[i];
						if(!data.download){
							content += '<li><a '+
							'href="/middleschool/visitorGN/newDetails?rId='+resource.reId+'&nId='+resource.caId+'&inquiry='+inquiry+'" '+
							'>'+resource.reTitle+'</a><span >'+resource.reIssuingdate+'</span></li>'
						} else if(data.download) {
							content +='<li> <a href="/middleschool/visitorGN/downloadNews?id='+resource.reId+'">'+
			            		'<span>'+resource.reTitle+'</span>'+
				            	'</a> </li>';
						}
					}
					var pageCut = "";
					if(news.currPage-1>0){	//判断上一页是否可用
						pageCut = '<li> <a '+
						'+href="/middleschool/visitorGN/viewNews?currentPage= '+news.currPage-1+'&nId='+news.lists[0].caId+
						'&inquiry='+inquiry+'">&laquo;</a> </li>';
					} else {
						pageCut = '<li><a href="#">&laquo;</a></li>';
					}
					pageCut +='<li> <a '+
					'href="/middleschool/visitorGN/viewNews?currentPage=1&nId='+news.lists[0].caId+
					'&inquiry='+inquiry+'">1</a> </li>';
					for(var i=2;i<=news.totalPage;i++){	//得到七页内容
						if(i>news.currPage-3&&i<news.currPage+3){
							pageCut +='<li> <a '+
							'href="/middleschool/visitorGN/viewNews?currentPage='+i+'&nId='+news.lists[0].caId+
							'&inquiry='+inquiry+'">'+i+'</a> </li>';
						}
					}
					if(news.currPage+1<=news.totalPage){	//判断下一页
						pageCut +='<li><a '+
						'href="/middleschool/visitorGN/viewNews?currentPage='+news.currPage+1+'&nId='+news.lists[0].caId+
						'&inquiry='+inquiry+'">&raquo;</a> </li>';
					} else {
						pageCut += '<li><a href="#">&raquo;</a> </li>';
					}
					var jumpPage = "";
					for(var i=1;i<=news.totalPage;i++){	//跳转页面html
						jumpPage += '<li><a href="#" onclick="turnPageNew(this)" '+
								' id= "'+news.lists[0].caId+'" name="'+inquiry+'" '+
								' th:text="'+i+'">'+i+'</a></li>';
					}
					$('#anyThing').html('<div>'+ 
							'<h3 class="titlesc" >'+news.lists[0].caName+'</h3>'+
							'<div class="newscontent">'+
								'<ul class="newscontentUl">'+content+'</ul>'+
								'<div class="clearfix"></div>'+
							'</div>'+
							'<div class="paging1"> <ul class="pagingUl">'+pageCut+ '</ul>'+
							'<div class="pagingRight">'+
							'<span>跳转到</span> <div class="selectBox">'+ 
							'<span>'+news.currPage+' <i class="glyphicon glyphicon-chevron-down pull-right"'+
							'style="line-height: 30px"></i></span>'+
						'<ul class="selectBoxUl">'+ jumpPage+ '</ul> </div> <span>页 共<span>'+news.totalPage+'</span>页 </span> </div>'
					)
				}
			}
		});
};