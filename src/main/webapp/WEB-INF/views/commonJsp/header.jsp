<%@page import="javax.mail.Flags"%>
<%@page import="javax.mail.Message"%>
<%@page import="javax.mail.Folder"%>
<%@page import="com.sun.mail.imap.IMAPFolder"%>
<%@page import="javax.mail.Store"%>
<%@page import="kr.or.ddit.smartware.employee.model.Employee"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	Employee employee = (Employee) session.getAttribute("S_EMPLOYEE");
	if(employee != null){
		employee.setC_use("false");
		employee.setChat_id("");
		session.setAttribute("S_EMPLOYEE", employee);
	}
	
%>

<%
// 	Integer cnt = (Integer) session.getAttribute("cnt");

// 	Store store = (Store)session.getAttribute("store");
	
// 	int real = 0;
// 	if(store != null || cnt != null){
// 		IMAPFolder folder = (IMAPFolder)store.getFolder("INBOX");
// 		real = folder.getMessageCount() - cnt;
// 		real = real < 0 ? 0 : real;
		
// 		session.setAttribute("real", real);
// 	}
	
	
%>
<script src="${cp }/js/moment.js"></script>
<script>

	function getVideoEmpList(){
		$.ajax({
			url : "${cp}/videoEmpList",
			contentType : "application/json",
			dataType : "json",
			method : "post",
			success : function(data){
				var html = "";
				var empList = data.employeeList;
				empList.forEach(function(emp){
 					html += "<tr class='empList'>";
	 				html += 	"<td> "+emp.EMP_NM+" </td>";
					html += 	"<td> "+emp.DEPART_NM+" </td>";
					html += 	"<td> "+emp.POSI_NM+" </td>";
 					html += 	'<td> <input data-emp_nm="'+emp.EMP_NM+'" data-emp_id = "'+emp.EMP_ID+'" type="checkbox" class="listCheck" style="display: inline-block;"/> </td>';
					html += "</tr>";
				})
				$("#tbody").append(html);
				
				$("#inviteEmp1").click(function(){
					var emp_id;
					 $(':checkbox:checked').each(function(i, a){
						 emp_id=$(this).data("emp_id");
					 })
					 socket.send("video^"+emp_id);
					 $(':checkbox:checked').each(function(i, a){
						 $(this).attr("checked", false);
					 })
					 window.open('https://192.168.0.116:8085', '영상통화방', 'width=1280px, height=1024px');
				});
			}
		});
	}
	
	function getChatList(){
		$.ajax({
			url : "${cp}/getChatList",
			contentType : "application/json",
			dataType : "json",
			method : "get",
			success : function(data){
				var chatList = data.chatList;
				var totalCnt = data.totalCnt;
				if (totalCnt==0){
					totalCnt = "";
				}
				var html = "";
				var emp_id = "${S_EMPLOYEE.emp_id}";
				
				$("#chatDtailList ul").empty();
				chatList.forEach(function(chat){
					var msg_cnt = chat.MSG_CNT;
					if(msg_cnt==0){
						msg_cnt = "";
					}
                    html += '<li id="'+chat.CHAT_ID+'" class="notification-unread chatList">'
                    html +=     '<a class="getChatId" data-chat_id="'+chat.CHAT_ID+'" href="#">'
                    html +=         '<img class="float-left mr-3 avatar-img" src="${cp }/employeePicture?emp_id='+emp_id+'" alt="">'
                    html +=         '<div class="notification-content">'
                    html +=             '<span class="notification-heading">'+chat.CHAT_NM+' / '+chat.EMP_CNT+'명</span>'	
                    html +=             '<i class="fa fa-times x" style="float : right; visibility:hidden;" data-chat_id="'+chat.CHAT_ID+'"></i>'
                    if(chat.MSG_CONT!=null||chat.MSG_CONT!=undefined){
                    	html +=             '<div class="notification-timestamp">'+chat.MSG_CONT+'<br>'+moment(new Date(chat.SEND_DT)).format('YYYY/MM/DD HH:mm')+'</div>'
                    }else{
                    	html += 			'<div class="notification-timestamp">'+'대화방이 개설되었습니다.'+'<br></div>'
                    }
                    html +=         '</div>'
	                html +=         '<span id="'+msg_cnt+'" class="badge badge-pill gradient-1">'+msg_cnt+'</span>'
                    html +=     '</a>'
                    html += '</li>'
				})
				$("#totalCnt").text(totalCnt+"");
				$("#chatDtailList ul").append(html);
				
				$(".chatList").hover(function(){
					if($(".x", this).css("visibility") == "visible")
						$(".x", this).css("visibility", "hidden");
					else
						$(".x", this).css("visibility", "visible");
				});
				
				$(".getChatId").click(function(){
					var chat_id = $(this).data("chat_id")+"";
					$.ajax({
						url : "${cp}/getLastMsg",
						method : "get",
						data : "chat_id="+chat_id,
						success : function(data){
							var param = {};
							var msg_id = data.msg_id + "";
							
							param.chat_id = chat_id;
							param.msg_id = msg_id;
							
							$.ajax({
								url : "${cp}/updateLastMsg",
								contentType : "application/json",
								dataType : "json",
								method : "post",
								data : JSON.stringify(param),
								success : function(data){
									getChatList();
								}
							});
						}
					});
	               var popupX = (window.screen.width / 2) - (500 / 2);
	               var popupY= (window.screen.height / 2) - (650 / 2);
	               
	               window.open('${cp }/chatRoom?chat_id='+chat_id, '채팅방', 'width=500px, height=650px, left='+ popupX + ', top='+ popupY)
				});
				
				$(".x").click(function(event){
					var param={};
					var chat_id = $(this).data('chat_id');
					
					param.chat_id = chat_id;
					
					$.ajax({
						url : "${cp}/deleteChat",
						contentType : "application/json",
						dataType : "json",
						method : "post",
						data : JSON.stringify(param),
						success : function(data){
							$("#"+data.chat_id).remove();
							socket.send("close^삭제");
						}
					});
					return false;
				});
			}
		});
	}

	/* var socket = new SockJS("${cp}/ws/chat");
	
	socket.onmessage = function(evt) {
		var str = evt.data.split("^");
		
		if(str[0]===("msg")){
			getChatList();
			var audio = new Audio('${cp}/audio/Telepathy.ogg');
			audio.play();
			
			const toast = Swal.mixin({
				  toast: true,
				  position: 'top-end',
				  showConfirmButton: false,
				  timer: 1500
				});

			toast({
			  type: 'success',
			  title: '메시지가 왔습니다.'
			})
		}else if(str[0]===("mail")){
			var audio = new Audio('${cp}/audio/Ding-Dong.ogg');
			audio.play();
			const toast = Swal.mixin({
				  toast: true,
				  position: 'top-end',
				  showConfirmButton: false,
				  timer: 1500
				});

			toast({
			  type: 'success',
			  title: '메일이 왔습니다.'
			})
		}else if(str[0]===("video")){
			const toast = Swal.mixin({
				  toast: true,
				  position: 'center',
				  showConfirmButton: false,
				});

			toast({
			  type: 'success',
			  title: '<a href="javascript:void(window.open(\'https://192.168.0.116:8085\', \'영상통화방\',\'width=1280px, height=1024px\'))">영상통화 요청이 왔습니다</a>.',
			})
		}else if(str[0]===("project")){
			getChatList();
			var audio = new Audio('${cp}/audio/Prism.ogg');
			audio.play();
			const toast = Swal.mixin({
				  toast: true,
				  position: 'top-end',
				  showConfirmButton: false,
				  timer: 2500
				});

			toast({
			  type: 'success',
			  title: '프로젝트 채팅방이 개설되었습니다'
			})
		}else if(str[0]===("appr")){
			getChatList();
			var audio = new Audio('${cp}/audio/Ding-Dong.ogg');
			audio.play();
			const toast = Swal.mixin({
				  toast: true,
				  position: 'top-end',
				  showConfirmButton: false,
				  timer: 1500
				});

			toast({
			  type: 'success',
			  title: str[1]
			})
		}else if(str[0]===("refer")){
			getChatList();
			var audio = new Audio('${cp}/audio/Ding-Dong.ogg');
			audio.play();
			const toast = Swal.mixin({
				  toast: true,
				  position: 'top-end',
				  showConfirmButton: false,
				  timer: 1500
				});

			toast({
			  type: 'success',
			  title: str[1]
			})
		}
	}; */
	
	$(function() {
// 		getChatList();
// 		getVideoEmpList();
// 		setTimeout(function() { 
	});
	
</script>

<!--**********************************
    Nav header start
***********************************-->
<div class="nav-header" style="background: #224D77;">
    <div class="brand-logo" style="background: #224D77;">
        <a href="${cp }/main">
<!--             <b class="logo-abbr">P</b> -->
            <span class="logo-compact"></span>
            <span class="brand-title" style="
            	 display: flex;
			     align-items: center;
			     justify-content: center;
			     height: 100%;
			     line-height: 1; /* 또는 다른 적절한 값 */
			     color: white;
			     font-size: 30px;
            ">
                PANTON9
            </span>
        </a>
    </div>
</div>
<!--**********************************
    Nav header end
***********************************-->

<!--**********************************
    Header start
***********************************-->
<div class="header">    
    <div class="header-content clearfix">
        
        <div class="nav-control">
            <div class="hamburger">
                <span class="toggle-icon"><i class="icon-menu"></i></span>
            </div>
        </div>
        
        <div class="header-left">
        </div>
        
        <div class="header-right">
            <ul class="clearfix">
              <c:if test="${sessionScope.S_EMPLOYEE != null}">
                
                <li class="icons dropdown messenger"><a href="javascript:void(0)" data-toggle="dropdown">
                        <i class="fa fa-users"></i>
                    </a>
                    <div class="drop-down animated fadeIn dropdown-menu">
                        <div class="dropdown-content-body">
                            <c:if test="${S_EMPLOYEE != null}">
                            <ul>
                                <li><a href="${cp }/logout"><i class="icon-key"></i> <span>Logout</span></a></li>
                            </ul>
                            </c:if>
                            <c:if test="${sessionScope.S_EMPLOYEE == null}">
	                            <ul>
		                            <li><a href="${cp }/login"><i class="icon-key"></i> <span>Login</span></a></li>
		                        </ul>
                            </c:if>
                        </div>
                    </div>
                </li>
                </c:if>
            </ul>
        </div>
    </div>
</div>