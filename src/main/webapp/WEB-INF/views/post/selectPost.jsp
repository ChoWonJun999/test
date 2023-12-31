<%@page import="kr.or.ddit.smartware.employee.model.Employee"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style>
ol {
	list-style: none;
}

.replyDiv {
	background: #EFEFFB;
	padding: 20px;
}

.delCmt:hover {
	cursor: pointer;
}
</style>
<script>
$(document).ready(function () {
	
	$("#save").on("click", function () {

        var cont = $("#cont").val();
        if (cont == '' || cont.length == 0) {
            Swal({
                type: 'warning', // success, error, warning, info, question
                title: '공백 오류',
                text: '내용을 입력해주세요.'
            })
            return;
        }

        Swal({
            title: '등록 요청',
            text: "저장하시겠습니까?",
            type: 'question',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '네',
            cancelButtonText: '아니오'
        }).then(result => {
            if (result.value) {
                $.ajax({
                    type: "POST",
                    url: "${cp}/insertComments",
                    data: "cont=" + cont + "&post_id=${post_id}",
                    dataType: 'json',
                    success: function (res) {
                        console.log(res);
                        var code = "<div class='replyDiv'>"
                        code += "<span id='"+ res.comments.com_id + "'>"
                        if (res.comments.able == "F") {
                            var reg_dt = new Date(res.comments.reg_dt);
                            var reg_dt_str = reg_dt.getFullYear() + '-' + (reg_dt.getMonth() + 1) + '-' + reg_dt.getDate() + ' ' + reg_dt.getHours() + ':' + reg_dt.getMinutes();
                            code += res.comments.cont + "&nbsp;&nbsp;&nbsp;[" + res.comments.emp_id + " / " + reg_dt_str + "]"
                        }
                        if ('${S_EMPLOYEE.emp_id}' == res.comments.emp_id && res.comments.able == 'F') {
                            code += "&nbsp;&nbsp;<span id=deleteComments class='delCmt glyphicon glyphicon-trash' data-com_id=" + res.comments.com_id + "><i class='fa fa-times' aria-hidden='true'></i></span>"
                        }
                        code += "</span>"
                        code += "</div>"
                        code += "<br>"
                        Swal({
                            title: '등록 완료',
                            text: '저장되었습니다.',
                            type: 'success',
                            confirmButtonText: '확인'
                        })

                        $('#commentsList').append(code);
                        $('#cmtFrm input[type="text"]').val("").focus();
                        // 						$('#cont').val('Default Value')
                        
                        console.log(res);
                    },
                    error: function (xhr, status, error) {
                        Swal({
                            type: 'error', // success, error, warning, info, question
                            title: '에러',
                            text: '다시 시도해주세요.'
                        })
                    }
                });
            }


        });

        // 			$("#cmtFrm").submit();
        //

        

    });
	
	$("#btnDelPost").on("click", function () {
        // 			var result = confirm("해당 게시글을 삭제하시겠습니까?");

        // 			if(result) {
        // 				$('#hiddenBtnV').val($(this).val());
        // 				$("#selectPost").val('${post_id}');
        // 				$("#frm").prop("action", "${cp}/modifyPost");
        // 				$("#frm").submit(); 

        // 				console.log(result)

        // 			}
        // 		

        Swal({
            title: '삭제 요청',
            text: "해당 게시글을 삭제하시겠습니까?",
            type: 'question',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '네',
            cancelButtonText: '아니오'
        }).then((result) => {
            if (result.value) {
                Swal({
                    title: '삭제 완료',
                    text: "삭제되었습니다.",
                    type: 'success',
                    confirmButtonText: '확인'
                })

                $('#hiddenBtnV').val($(this).val());
                $("#selectPost").val('${post_id}');
                $("#frm").prop("action", "${cp}/modifyPost");
                $("#frm").submit();
            }
        });
    });
	
    $(document).on("click", ".delCmt", function (event) {
    	var com_id = $(this).data('com_id')
    	
        Swal({
            title: '삭제 요청',
            text: "해당 댓글을 삭제하시겠습니까?",
            type: 'question',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '네',
            cancelButtonText: '아니오'
        }).then(result => {
            if (result.value) {
                $.ajax({
                    type: "POST",
                    url: "${cp}/deleteComments",
                    data: {
                    	com_id: com_id,
                    	post_id: '${post_id}'
                    },
                    dataType: 'json',
                    success: function (res) {
                        console.log(res);
                        /* var code = "<div class='replyDiv'>"
                        code += "<span>"
                        if (res.comments.able == "T") {
                            code += "<font color='silver'>삭제된 댓글입니다.</font>"
                        }
                        code += "</span>"
                        code += "</div>"
                        code += "<br>" */
                        $("#"+com_id).html("<font color='silver'>삭제된 댓글입니다.</font>");

                        Swal({
                            title: '삭제 완료',
                            text: '삭제되었습니다.',
                            type: 'success',
                            confirmButtonText: '확인'
                        })
                        $.getJSON('${cp}/getCommentList?post_id=${comments.post_id}', function(data) {
                        	var code = '~~~' // data갖고 알아서 잘
                        	$('#commentList').html(code);
                        });
                        $(document).append(code);
//                         var com_id = $(this).data("com_id");
//                         $("#com_id").val(com_id);
// 						   $("#hiddenFrm").submit();
                    },
                    error:function(request,status,error){
                        alert("code = "+ request.status + " message = " + request.responseText + " error = " + error); // 실패 시 처리
                    }
                });

            };
        });
    });
    // 			var result = confirm("해당 댓글을 삭제하시겠습니까?");

    // 			if(result){
    // 				var com_id = $(this).data("com_id");
    // 				$("#com_id").val(com_id);
    // 				$("#hiddenFrm").submit();

    // 			}
    // 		})

    
});
	
</script>
</head>
<body>
	<%
		HttpSession session1 = request.getSession();
		Employee employee = (Employee) session1.getAttribute("S_EMPLOYEE");

		request.setAttribute("employee", employee);
	%>
	<form action="${cp }/deleteComments" method="post" id="hiddenFrm">
		<input type="hidden" id="com_id" name="com_id" /> <input
			type="hidden" id="board_id" name="board_id" value="${board_id }" />
		<input type="hidden" id="post_id" name="post_id"
			value="${post.post_id }" />
	</form>
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<div class="card">
					<div class="card-body">
						<div
							class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
							<h2 class="sub-header">${board_nm }</h2>
							<form id="frm" class="form-horizontal" role="form"
								action="${cp }/modifyPost" method="get">

								<div class="form-group">
									<input type="hidden" name="board_id" value="${board_id }" /> <input
										type="hidden" name="board_nm" value="${board_nm }" /> <input
										id="selectPost" type="hidden" name="post_id"
										value="${post.post_id }" /> <input type="hidden" name="gn"
										value="${post.gn }" /> <input type="hidden" name="title"
										value="${post.title }" /> <input type="hidden" name="emp_id"
										value="${post.emp_id }" /> <input type="hidden"
										name="btnValue" id="hiddenBtnV" />
									<!-- 						<label for="title" class="col-sm-2 control-label">제목</label> -->
									<!-- 						<div class="col-sm-6">-->
									<%-- 	                    	<label class="control-label">${post.title } </label> --%>
									<!-- 						</div> -->
								</div>
							</form>

							<form id="frm2" class="form-horizontal" role="form"
								action="${cp }/modifyPost" method="get">
								<div class="form-group">
									<input type="hidden" name="board_id" value="${board_id }" /> <input
										type="hidden" name="board_nm" value="${board_nm }" /> <input
										id="selectPost" type="hidden" name="post_id"
										value="${post.post_id }" /> <input type="hidden" name="gn"
										value="${post.gn }" /> <input type="hidden" name="title"
										value="${post.title }" /> <input type="hidden" name="emp_id"
										value="${post.emp_id }" /> <label for="title"
										class="col-sm-2 control-label">제목</label>
									<div class="col-sm-6">
										<label class="control-label">${post.title } </label>
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label">글 내용</label>
									<div class="col-sm-8">
										<label>${post.cont } </label>
									</div>
								</div>

								<div class="form-group">
									<label for="postFile" class="col-sm-2 control-label">첨부파일</label>
									<div class="col-sm-6">
										<c:forEach items="${postfileList }" var="postfile">
											<label class="control-label"> <a
												href="${cp }/fileDownloadView?file_id=${postfile.file_id}">${postfile.file_nm }</a>
											</label>
											<br>
										</c:forEach>
									</div>
								</div>

								<div class="form-group">
									<label for="postFile" class="col-sm-2 control-label"></label>
									<div class="col-sm-6">
										<c:if test="${employee.emp_id == post.emp_id}">
											<input type="submit" class="btn btn-dark" id="btnUpdqtePost"
												name="btnValue" value="수정" />
											<input type="button" class="btn btn-danger" id="btnDelPost"
												value="삭제" />
										</c:if>
										<c:if
											test="${S_EMPLOYEE.emp_id == 'admin' || board_id != 'board0001'}">
											<input type="submit" class="btn btn-primary" id="btnAnsPost"
												name="btnValue" value="답글" />
										</c:if>
									</div>
								</div>
							</form>

							<form id="cmtFrm" class="form-horizontal" role="form"
								action="${cp }/insertComments" method="post">
								<c:if
									test="${S_EMPLOYEE.emp_id == 'admin' || board_id != 'board0001'}">
									<div class="form-group">
										<label class="col-sm-2 control-label">댓글</label>
										<div id="commentsList" class="col-sm-6">
											<c:forEach items="${commentsList }" var="comments">
												<div class="replyDiv">
													<span id="${comments.com_id }"> <c:choose>
															<c:when test="${comments.able == 'T' }">
																<font color="silver">삭제된 댓글입니다.</font>
															</c:when>
															<c:otherwise>
											${comments.cont }&nbsp;&nbsp;&nbsp;[${comments.emp_id } / <fmt:formatDate
																	value="${comments.reg_dt }" pattern="yyyy-MM-dd HH:mm" />]
										</c:otherwise>
														</c:choose> <c:if
															test="${S_EMPLOYEE.emp_id == comments.emp_id && comments.able=='F'}">
										&nbsp;<span id="deleteComments"
																class="delCmt glyphicon glyphicon-trash"
																data-com_id="${comments.com_id }"><i
																class="fa fa-times" aria-hidden="true"></i></span>
														</c:if>
													</span>
												</div>
												<br>
											</c:forEach>
										</div>
									</div>


									<div class="form-group">
										<div class="col-sm-offset-2 col-sm-10">
											<div class="col-sm-6">
												<input type="hidden" name="post_id" value="${post.post_id }" />
												<input type="hidden" name="board_id" value="${board_id }" />
												<input type="text" class="form-control" id="cont"
													name="cont" />
											</div>
											<br> <br>
											<div class="col-sm-2">
												<input id="save" type="button"
													class="btn mb-1 btn-outline-primary" value="댓글저장" />
											</div>
										</div>
									</div>
								</c:if>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>