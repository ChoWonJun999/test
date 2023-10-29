<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="${cp }/js/jquery-3.4.1.min.js"></script>
<script>
//사용자 정보 클릭시 이벤트 핸들러
$(function(){


});
</script>
<div class="container-fluid">
	<div class="row">
		<div class="col-12">
			<div class="card">
				<div class="card-body">
					<div>
						<h3>예약</h3>
					</div>
					<div class="header-content clearfix">
					</div>
					<div class="table-responsive">
						<div class="form-group">
							<table class="table table-hover departTable">
							 <div>
								 	<span id="subtitle">
								 	
								 	</span>
									<thead>
										<tr>
											<th>예약채널</th>
											<th>구분</th>
											<th>날짜</th>
											<th>이름</th>
											<th>인원</th>
											<th>바베큐</th>
											<th>예약금액</th>
											<th>전화번호</th>
											<th>예약 시점(일자)</th>
											<th>예약 시점(시간)</th>
											<th>비고</th>
										</tr>
									</thead>
									<tbody id="tBody">
										<%-- <c:forEach items="${employeeList }" var="employee">
											<c:if test="${employee.EMP_ID != 'admin' }">
												<tr>
													<td>${employee.EMP_ID }</td>
													<td>${employee.EMP_NM }</td>
													<td>${employee.DEPART_NM }</td>
													<td>${employee.POSI_NM }</td>
													<c:choose>
														<c:when test="${employee.JOB_NM eq undefined}">
															<td>(직책없음)</td>
														</c:when>
														<c:otherwise>
															<td>${employee.JOB_NM}</td>
														</c:otherwise>
													</c:choose>
													<td><fmt:formatDate value="${employee.JOIN_DT }" pattern="yyyy-MM-dd"/></td>
													<td><button type="button" class="btn btn-info detailEmp" data-id="${employee.EMP_ID }" data-toggle="modal" data-target=".bd-example-modal-lg">사원 상세보기</button></td>
												</tr>
											</c:if>
										</c:forEach> --%>
									</tbody>
								</div>
							</table>
						</div>
					</div>
					
					
					<%-- <div id="page" class="bootstrap-pagination">
						<nav>
							<ul class="pagination justify-content-center">
								<!-- 이전 페이지 가기 : 지금 있는 페이지에서 한페이지 전으로 -->

								<c:choose>
									<c:when test="${map.page == 1 }">
										<li class="page-item disabled"><a class="page-link"
											href="#" tabindex="-1">&laquo;</a></li>
										<span aria-hidden="Previous"></span>
									</c:when>
									<c:otherwise>
										<li class="page-item"><a class="page-link"
											href="${cp }/employeeSearch?page=1"
											aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
										</a></li>
									</c:otherwise>
								</c:choose>


								<c:choose>
									<c:when test="${map.page == 1 }">
										<li class="page-item disabled"><a class="page-link"
											href="#" tabindex="-1">&lt;</a></li>
									</c:when>
									<c:otherwise>
										<li class="page-item"><a class="page-link"
											href="${cp }/employeeSearch?page=${map.page-1}"
											tabindex="-1">&lt;</a></li>
									</c:otherwise>
								</c:choose>

								<c:forEach begin="1" end="${paginationSize }" var="i">
									<c:choose>
										<c:when test="${i == map.page }">
											<li class="page-item disabled"><span class="page-link">${i }</span></li>
										</c:when>
										<c:otherwise>
											<li class="page-item"><a class="page-link"
												href="${cp }/employeeSearch?page=${i }">${i }</a></li>
										</c:otherwise>
									</c:choose>
								</c:forEach>

								<c:choose>
									<c:when test="${map.page == paginationSize || paginationSize == 0}">
										<li class="page-item disabled"><a class="page-link"
											href="#">&gt;</a></li>
									</c:when>
									<c:otherwise>
										<li class="page-item"><a class="page-link"
											href="${cp }/employeeSearch?page=${page+1}">&gt;</a>
										</li>
									</c:otherwise>
								</c:choose>

								<c:choose>
									<c:when test="${map.page == paginationSize || paginationSize == 0}">
										<li class="page-item disabled"><a class="page-link"
											href="#">&raquo;</a></li>
										<span aria-hidden="Next"></span>
									</c:when>
									<c:otherwise>
										<li class="page-item"><a class="page-link"
											href="${cp }/employeeSearch?page=${paginationSize }"
											aria-label="Previous"> <span aria-hidden="true">&raquo;</span>
										</a></li>
									</c:otherwise>
								</c:choose>
							</ul>
						</nav>
					</div> --%>
				</div>
			</div>
		</div>
	</div>
</div>
